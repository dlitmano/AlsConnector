define(['model/country', 'model/query'], function(Country, Query) {
	return Backbone.Collection.extend({
		url: '/on-countries',

		model: Country
	});
});
