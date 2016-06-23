define([], function() {
	return Backbone.Model.extend({
		defaults: {
			id: 'Not specified',
			city: 'Not specified',
			type: 'Not specified'
		}
	});
});
