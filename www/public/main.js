require.config({
	baseUrl: 'js/lib',

	paths: {
		model: '/model',

		view: '/view',

		collection: '/collection',

		template: '/template'
	},

	shim: {
		backbone: {
			deps: ['jquery', 'underscore'],

			exports: 'Backbone'
		},

		underscore: {
			exports: '_'
		},

		bootstrap: {
			deps: ['jquery']
		},

		jquery: {
			exports: '$'
		}
	}
});

require(['backbone', 'bootstrap'], function(Backbone) {
	_.templateSettings = {
		interpolate: /\{(.+?)\}/g
	};

	require(['model/query', 'view/navigation', 'view/countries', 'view/basket'], function(Query, Navigation, Countries, Basket) {
		window.repository = new Array();

		window.repository['model/query'] = new Query();

		window.repository['view/navigation'] = new Navigation({el: $('#navigation')});

		window.repository['view/basket'] = new Basket({el: $('#basket')});

		window.repository['view/countries'] = new Countries({el: $('#countries')});

		window.repository['model/query'].on('change', function(model) {
			window.repository['view/countries'].collection.fetch({type: 'POST', data: {query: model.getQuery()}});
		});
	});
});
