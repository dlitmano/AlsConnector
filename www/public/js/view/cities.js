define(['text!template/city',

	'collection/cities',

	'view/city'

], function(city, Cities, City) {
	return Backbone.View.extend({
		city: _.template(city),

		initialize: function() {
			this.repository = new Array();

			this.collection = new Cities();

			this.collection.on('add', this.onAdd, this);

			this.collection.on('remove', this.onRemove, this);
		},

		fetch: function(query, country) {
			this.collection.fetch({type: 'POST', data: {query: query, country: country.toJSON()}});
		},

		onAdd: function(model) {
			$(this.el).append(this.city({country: model.getCountry(),
							  id: model.getId(),
							name: model.getName(),
						 description: model.getDescription()}));

			var id = '#city-' + model.getCountry() + '-' + model.getId();

			this.repository[id] = new City({el: $(id), model: model});

			this.repository[id].onQuery(window.repository['model/query']);
		},

		onRemove: function(model) {
			var id = '#city-' + model.getCountry() + '-' + model.getId();

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
