define(['require',

	'text!template/container-row',

	'text!template/city',

	'view/city',

	'collection/cities'

], function(require, row, city, City) {
	return Backbone.View.extend({
		row: _.template(row),

		city: _.template(city),

		initialize: function() {
			this.repository = new Array();

			var Cities = require('collection/cities');

			window.repository['/cities'] = new Cities();

			window.repository['/cities'].on('add', this.addCity, this);

			window.repository['/cities'].on('remove', this.removeCity, this);
		},

		addCity: function(model) {
			var content = '';

			content = this.city({id: model.getId(), name: model.getName(), description: model.getDescription()});

			content = this.row({id: model.getId(), content: content});

			$(this.el).append(content);

			var id = '#city-' + model.getId();

			this.repository[id] = new City({el: $(id), model: model});
		},

		removeCity: function(model) {
			var id = '#city-' + model.getId();

			this.repository[id].remove();

			// TODO remove element from repository ...

			$('#container-row-' + model.getId()).remove();
		}
	});
});
