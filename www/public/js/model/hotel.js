define([], function() {
	return Backbone.Model.extend({
		defaults: {
			country: 'Not specified',
			city: 'Not specified',
			id: 'Not specified',
			name: 'Not specified',
			description: 'Not specified'
		},

		getCountry: function() {
			return this.get('country');
		},

		getCity: function() {
			return this.get('city');
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
