define([], function() {
	return Backbone.View.extend({
		initialize: function() {
			var id = '#hotel-checkbox-' + this.model.getCity() + '-' + this.model.getId();

			$(id).on('click', {model: this.model}, this.onHotel);

			if(window.repository['/content/hotels'].findWhere(this.model.toJSON())) {
				$(id).prop('checked', true);
			}
		},

		onHotel: function(event) {
			if($(event.currentTarget).is(':checked')) {
				window.repository['/content/hotels'].add(event.data.model.toJSON());

				return;
			}

			window.repository['/content/hotels'].remove(event.data.model.toJSON());
		}
	});
});
