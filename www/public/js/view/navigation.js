define([], function() {
	return Backbone.View.extend({
		initialize: function() {
			$('#query').on('keyup', {this: this}, this.onQuery);
		},

		onQuery: function(event) {
			window.repository['model/query'].setQuery({query: $(event.currentTarget).val()});
		}
	});
});
