define(['text!template/hotel',

	'collection/hotels',

	'view/hotel'

], function(hotel, Hotels, Hotel) {
	return Backbone.View.extend({
		hotel: _.template(hotel),

		initialize: function() {
			this.repository = new Array();

			this.collection = new Hotels();

			this.collection.on('add', this.onAdd, this);

			this.collection.on('remove', this.onRemove, this);
		},

		fetch: function(query, city) {
			this.collection.fetch({type: 'POST', data: {query: query, city: city.toJSON()}});
		},

		onAdd: function(model) {
			$(this.el).append(this.hotel({country: model.getCountry(),
							 city: model.getCity(),
							   id: model.getId(),
							 name: model.getName(),
						  description: model.getDescription()}));

			var id = '#hotel-' + model.getCountry() + '-' + model.getCity() + '-' + model.getId();

			this.repository[id] = new Hotel({el: $(id), model: model});
		},

		onRemove: function(model) {
			var id = '#hotel-' + model.getCountry() + '-' + model.getCity() + '-' + model.getId();

			this.repository[id].destroy();

			$(id).remove();
		},

		destroy: function() {
			var model = this.collection.first();

			while(model) {
				this.collection.remove(model);

				model = this.collection.first();
			}

			this.collection.off('add');

			this.collection.off('remove');
		}
	});
});
