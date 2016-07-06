define(['text!template/hotel-mini',

	'collection/hotels',

	'view/hotel-mini'

], function(hotel, Hotels, Hotel) {
	return Backbone.View.extend({
		hotel: _.template(hotel),

		initialize: function() {
			this.repository = new Array();

			this.hotels = new Hotels();

			this.hotels.on('add', this.onAddHotel, this);

			this.hotels.on('remove', this.onRemoveHotel, this);

			$('#basket').on('shown.bs.modal', {view: this, hotels: this.hotels}, this.onShow);
		},

		onShow: function(event) {
			$('#basket-on-pdf').attr('href', 'javascript:');

			$('#basket-on-pdf').on('click', {hotels: event.data.hotels}, event.data.view.onPDF);
		},

		onAddHotel: function(model) {
			$('#basket-hotels').append(this.hotel({country: model.getCountry(),
								  city: model.getCity(),
								    id: model.getId(),
								  name: model.getName(),
							   description: model.getDescription()}));

			var id = '#hotel-mini-' + model.getCountry() + '-' + model.getCity() + '-' + model.getId();

			this.repository[id] = new Hotel({el: $(id), model: model});
		},

		onRemoveHotel: function(model) {
			var id = '#hotel-mini-' + model.getCountry() + '-' + model.getCity() + '-' + model.getId();

			this.repository[id].remove();	// TODO remove element from repository ...

			$(id).remove();
		},

		onPDF: function(event) {
			var collection = new Array();

			event.data.hotels.each(function(model) {
				collection.push(model.toJSON());
			});

			$.ajax({type: 'POST',
				url: '/on-pdf',
				contentType: "application/json",
				data: JSON.stringify(collection),
				dataType: 'text',
				success: function(id) {
					$('#basket-on-pdf').attr('href', '/on-download-pdf?id=' + id);

					$('#basket-on-pdf').html('Download PDF');

					$('#basket-on-pdf').off('click');

					$('#basket-on-pdf').on('click', function(event) {
						$('#basket-on-pdf').html('Generate PDF');

						$('#basket-on-pdf').off('click');

						$('#basket-on-close').click();

						return true;
					});
				}
			});
		}
	});
});
