define([], function() {
	return Backbone.Model.extend({
		defaults: {
			id: 'Not specified',

			city: 'Not specified',

			name: 'Not specified',

			description: 'Not specified'
		},

		getId: function() {
			return this.get('id');
		},

		getCity: function() {
			return this.get('city');
		},

		getName: function() {
			return this.get('name');
		},

		getDescription: function() {
			return this.get('description');
		}
	});
});
