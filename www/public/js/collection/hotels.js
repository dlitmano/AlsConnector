define(['model/hotel', 'model/query'], function(Hotel, Query) {
	return Backbone.Collection.extend({
		url: '/on-hotels',

		model: Hotel
	});
});
