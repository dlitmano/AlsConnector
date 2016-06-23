define([], function() {
	return Backbone.Model.extend({
		defaults: {
			id: 'Not specified',

			name: 'Not specified',

			description: 'Not specified'
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
