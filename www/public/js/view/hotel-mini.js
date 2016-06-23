define([], function() {
	return Backbone.View.extend({
		initialize: function() {
			var id = '#hotel-mini-' + this.model.getCity() + '-' + this.model.getId();

			$(id).on('click', {model: this.model}, this.onHotel);
		},

		onHotel: function(event) {
			window.repository['/content/hotels'].remove(event.data.model.toJSON());
		}
	});
});
