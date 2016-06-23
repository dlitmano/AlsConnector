define(['model/club'], function(Club) {
	return Backbone.Collection.extend({
		model: Club,
	});
});
