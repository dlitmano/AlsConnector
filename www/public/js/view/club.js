define([], function() {
	return Backbone.View.extend({
		initialize: function() {
			var id = '#club-checkbox-' + this.model.getCity() + '-' + this.model.getId();

			$(id).on('click', {model: this.model}, this.onClub);

			if(window.repository['/content/clubs'].findWhere(this.model.toJSON())) {
				$(id).prop('checked', true);
			}
		},

		onClub: function(event) {
			if($(event.currentTarget).is(':checked')) {
				window.repository['/content/clubs'].add(event.data.model.toJSON());

				return;
			}

			window.repository['/content/clubs'].remove(event.data.model.toJSON());
		}
	});
});
