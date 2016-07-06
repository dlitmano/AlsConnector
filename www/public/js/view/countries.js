define(['text!template/country',

	'collection/countries',

	'view/country'

], function(country, Countries, Country) {
	return Backbone.View.extend({
		country: _.template(country),

		initialize: function() {
			this.repository = new Array();

			this.collection = new Countries();

			this.collection.on('add', this.onAdd, this);

			this.collection.on('remove', this.onRemove, this);
		},

		onAdd: function(model) {
			$(this.el).append(this.country({id: model.getId(), name: model.getName(), description: model.getDescription()}));

			var id = '#country-' + model.getId();

			this.repository[id] = new Country({el: $(id), model: model});

			this.repository[id].onQuery(window.repository['model/query']);
		},

		onRemove: function(model) {
			var id = '#country-' + model.getId();

			this.repository[id].destroy();

			$(id).remove();
		}
	});
});
