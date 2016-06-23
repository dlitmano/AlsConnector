define(['model/city'], function(City) {
	return Backbone.Collection.extend({
		model: City,
	});
});
