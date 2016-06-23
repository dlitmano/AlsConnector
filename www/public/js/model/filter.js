define([], function() {
	return Backbone.Model.extend({
		defaults: {
			query: ''
		},

		getQuery: function() {
			return this.get('query');
		},

		setQuery: function(query) {
			this.set('query', query);
		}
	});
});
