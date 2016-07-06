define(['model/city', 'model/query'], function(City, Query) {
	return Backbone.Collection.extend({
		url: '/on-cities',

		model: City
	});
});
