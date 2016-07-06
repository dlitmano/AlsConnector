define(['view/cities'], function(Cities) {
	return Backbone.View.extend({
		initialize: function() {
			this.cities = new Cities({el: $('#country-cities-' + this.model.getId())});

			this.listenTo(window.repository['model/query'], 'change', this.onQuery);
		},

		onQuery: function(model) {
			this.cities.fetch(model.getQuery(), this.model);
		},

		destroy: function() {
			this.stopListening(window.repository['model/query']);

			this.cities.destroy();
		}
	});
});
