define(['model/content-entry'], function(Content) {
	return Backbone.Collection.extend({
		model: Content
	});
});
