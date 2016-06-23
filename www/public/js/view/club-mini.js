define([], function() {
	return Backbone.View.extend({
		initialize: function() {
			var id = '#club-mini-' + this.model.getCity() + '-' + this.model.getId();

			$(id).on('click', {model: this.model}, this.onClub);
		},

		onClub: function(event) {
			window.repository['/content/clubs'].remove(event.data.model.toJSON());
		}
	});
});
