define(['require',

	'text!template/content-container-row',

	'text!template/hotel-mini',
	'text!template/club-mini',

	'view/hotel-mini',
	'view/club-mini',

	'collection/cities',
	'collection/hotels',
	'collection/clubs'

], function(require, row, hotel, club, Hotel, Club) {
	return Backbone.View.extend({
		row: _.template(row),

		hotel: _.template(hotel),

		club: _.template(club),

		initialize: function() {
			this.repository = new Array();

			var Cities = require('collection/cities');

			window.repository['/content/cities'] = new Cities();

			var Hotels = require('collection/hotels');

			window.repository['/content/hotels'] = new Hotels();

			window.repository['/content/hotels'].on('add', this.addHotel, this);

			window.repository['/content/hotels'].on('remove', this.removeHotel, this);

			var Clubs = require('collection/clubs');

			window.repository['/content/clubs'] = new Clubs();

			window.repository['/content/clubs'].on('add', this.addClub, this);

			window.repository['/content/clubs'].on('remove', this.removeClub, this);

			$('#content-download-as-pdf').on('click', {}, this.onGeneratePDF);
		},

		addHotel: function(model) {
			var content = {id: model.getId(), city: model.getCity(), name: model.getName(), description: model.getDescription()};

			content = this.hotel(content);

			content = this.row({id: model.getId(), city: model.getCity(), suffix: 'hotel', content: content});

			$('#content-hotels').append(content);

			var key = '#content-hotel-' + model.getId();

			this.repository[key] = new Hotel({el: $(key), model: model});
		},

		removeHotel: function(model) {
			var key = '#content-hotel-' + model.getId();

			this.repository[key].remove();

			// TODO remove element from repository ...

			$('#content-container-row-' + model.getCity() + '-' + model.getId() + '-' + 'hotel').remove();
		},

		addClub: function(model) {
			var content = {id: model.getId(), city: model.getCity(), name: model.getName(), description: model.getDescription()};

			content = this.club(content);

			content = this.row({id: model.getId(), city: model.getCity(), suffix: 'club', content: content});

			$('#content-clubs').append(content);

			var key = '#content-club-' + model.getId();

			this.repository[key] = new Club({el: $(key), model: model});
		},

		removeClub: function(model) {
			var key = '#content-club-' + model.getId();

			this.repository[key].remove();

			// TODO remove element from repository ...

			$('#content-container-row-' + model.getCity() + '-' + model.getId() + '-' + 'club').remove();
		},

		onGeneratePDF: function(event) {
			var collection = new Array();

			var index = 0;

			window.repository['/content/hotels'].each(function(model) {
				collection[index++] = {id: model.getId(), city: model.getCity(), type: 'hotel'};
			});

			window.repository['/content/clubs'].each(function(model) {
				collection[index++] = {id: model.getId(), city: model.getCity(), type: 'hotel'};
			});

			$.ajax({type: 'POST', url: '/generate-pdf', data: JSON.stringify(collection.toJSON()),
				contentType: "application/json", dataType: 'text', success: function(id) {
					$('#content-download-as-pdf').attr('href', '/download-as-pdf');//?id=' + id);

					$('#content-download-as-pdf').html('Download as PDF');

					$('#content-download-as-pdf').off('click');

					$('#content-download-as-pdf').on('click', function(event) {
						$('#content-close').click();

						return true;
					});
				}
			});
		}
	});
});
