define(['view/hotels'], function(Hotels) {
	return Backbone.View.extend({
		initialize: function() {
			this.hotels = new Hotels({el: $('#city-hotels-' + this.model.getCountry() + '-' + this.model.getId())});

			this.listenTo(window.repository['model/query'], 'change', this.onQuery);
		},

		onQuery: function(model) {
			this.hotels.fetch(model.getQuery(), this.model);
		},

		destroy: function() {
			this.stopListening(window.repository['model/query']);

			this.hotels.destroy();
		}
	});
});
