define(['require',

	'model/filter'

], function(require) {
	return Backbone.View.extend({
		events: {
			'keyup #query': 'onQuery',

			'click #content': 'onContent'
		},

		initialize: function() {
			var Filter = require('model/filter');

			window.repository['/filter'] = new Filter();

			window.repository['/filter'].on('change', this.query, this);
		},

		onQuery: function(event) {
			event.preventDefault();

			window.repository['/filter'].setQuery($(event.currentTarget).val());

			return true;
		},

		onContent: function(event) {
			event.preventDefault();

			// TODO fetch ...

			window.repository['/cities'].remove({id: '1', name: 'City 1', description: 'Description 1'});
			window.repository['/cities'].remove({id: '2', name: 'City 2', description: 'Description 2'});
			window.repository['/cities'].remove({id: '3', name: 'City 3', description: 'Description 3'});
			window.repository['/cities'].remove({id: '4', name: 'City 4', description: 'Description 4'});
			window.repository['/cities'].remove({id: '5', name: 'City 5', description: 'Description 5'});

			return true;
		},

		query: function(model) {
//			console.log(model.getQuery());

			// TODO fetch ...

			window.repository['/cities'].add({id: '1', name: 'City 1', description: 'Description 1'});
			window.repository['/cities'].add({id: '2', name: 'City 2', description: 'Description 2'});
			window.repository['/cities'].add({id: '3', name: 'City 3', description: 'Description 3'});
			window.repository['/cities'].add({id: '4', name: 'City 4', description: 'Description 4'});
			window.repository['/cities'].add({id: '5', name: 'City 5', description: 'Description 5'});
		}
	});
});
