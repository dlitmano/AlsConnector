define([], function() {
	return Backbone.Model.extend({
		defaults: {
			country: 'Not specified',
			id: 'Not specified',
			name: 'Not specified',
			description: 'Not specified'
		},

		getCountry: function() {
			return this.get('country');
		},

		getId: function() {
			return this.get('id');
		},

		getName: function() {
			return this.get('name');
		},

		getDescription: function() {
			return this.get('description');
		}
	});
});
