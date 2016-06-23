define(['require',

	'text!template/city-container-row',

	'text!template/hotel',
	'text!template/club',

	'view/hotel',
	'view/club',

	'collection/hotels',
	'collection/clubs'

], function(require, row, hotel, club, Hotel, Club) {
	return Backbone.View.extend({
		row: _.template(row),

		hotel: _.template(hotel),

		club: _.template(club),

		initialize: function() {
			this.repository = new Array();

			var key = '/' + this.model.getId() + '/hotels';

			var Hotels = require('collection/hotels');

			window.repository[key] = new Hotels();

			window.repository[key].on('add', this.addHotel, this);

			window.repository[key].on('remove', this.removeHotel, this);

			key = '/' + this.model.getId() + '/clubs';

			var Clubs = require('collection/clubs');

			window.repository[key] = new Clubs();

			window.repository[key].on('add', this.addClub, this);

			window.repository[key].on('remove', this.removeClub, this);

			$('#city-hotels-' + this.model.getId()).on('click', {repository: this.repository, model: this.model}, this.onHotels);

			$('#city-clubs-' + this.model.getId()).on('click', {repository: this.repository, model: this.model}, this.onClubs);
		},

		addHotel: function(model) {
			var content = {id: model.getId(), city: this.model.getId(), name: model.getName(), description: model.getDescription()};

			content = this.hotel(content);

			content = this.row({id: model.getId(), city: this.model.getId(), content: content});

			$('#city-container-' + this.model.getId()).append(content);

			var key = '#hotel-' + model.getId();

			this.repository[key] = new Hotel({el: $(key), model: model});
		},

		removeHotel: function(model) {
			var key = '#hotel-' + model.getId();

			this.repository[key].remove();

			// TODO remove element from repository ...

			$('#city-container-row-' + this.model.getId() + '-' + model.getId()).remove();
		},

		addClub: function(model) {
			var content = {id: model.getId(), city: this.model.getId(), name: model.getName(), description: model.getDescription()};

			content = this.club(content);

			content = this.row({id: model.getId(), city: this.model.getId(), content: content});

			$('#city-container-' + this.model.getId()).append(content);

			var key = '#club-' + model.getId();

			this.repository[key] = new Club({el: $(key), model: model});
		},

		removeClub: function(model) {
			var key = '#club-' + model.getId();

			this.repository[key].remove();

			// TODO remove element from repository ...

			$('#city-container-row-' + this.model.getId() + '-' + model.getId()).remove();
		},

		onHotels: function(event) {
			// TODO window.repository[this.model.getId() + '/hotels'].fetch ...

			var id = event.data.model.getId();

			var key = '/' + id + '/clubs';

			if(event.data.repository['container-clubs']) {
				event.data.repository['container-clubs'] = false;

				window.repository[key].remove({id: '1', city: id, name: 'Club 1', description: 'Description 1'});
			}

			key = '/' + id + '/hotels';

			if(event.data.repository['container-hotels']) {
				event.data.repository['container-hotels'] = false;

				window.repository[key].remove({id: '1', city: id, name: 'Hotel 1', description: 'Description 1'});

				return;
			}

			event.data.repository['container-hotels'] = true;

			window.repository[key].add({id: '1', city: id, name: 'Hotel 1', description: 'Description 1'});
		},

		onClubs: function(event) {
			// TODO window.repository[this.model.getId() + '/clubs'].fetch ...

			var id = event.data.model.getId();

			var key = '/' + id + '/hotels';

			if(event.data.repository['container-hotels']) {
				event.data.repository['container-hotels'] = false;

				window.repository[key].remove({id: '1', city: id, name: 'Hotel 1', description: 'Description 1'});
			}

			key = '/' + id + '/clubs';

			if(event.data.repository['container-clubs']) {
				event.data.repository['container-clubs'] = false;

				window.repository[key].remove({id: '1', city: id, name: 'Club 1', description: 'Description 1'});

				return;
			}

			event.data.repository['container-clubs'] = true;

			window.repository[key].add({id: '1', city: id, name: 'Club 1', description: 'Description 1'});		}
	});
});
