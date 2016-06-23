define(['model/hotel'], function(Hotel) {
	return Backbone.Collection.extend({
		model: Hotel,
	});
});
