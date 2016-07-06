define([], function() {
	return Backbone.View.extend({
		initialize: function() {
			this.checkbox = '#hotel-mini-checkbox-'
			this.checkbox = this.checkbox + this.model.getCountry() + '-'
			this.checkbox = this.checkbox + this.model.getCity() + '-'
			this.checkbox = this.checkbox + this.model.getId();

			$(this.checkbox).on('click', {model: this.model}, this.onClick);

			this.render();
		},

		render: function() {
			$(this.checkbox).prop('checked', window.repository['view/basket'].hotels.findWhere(this.model.toJSON()));
		},

		onClick: function(event) {
			if($(event.currentTarget).is(':checked')) {
				window.repository['view/basket'].hotels.add(event.data.model.toJSON());

				return;
			}

			window.repository['view/basket'].hotels.remove(event.data.model.toJSON());
                }
	});
});
