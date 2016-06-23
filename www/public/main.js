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

	require(['view/navigation', 'view/container', 'view/content'], function(Navigation, Container, Content) {
		window.repository = new Array();

		new Navigation({el: $('#navigation')});

		new Container({el: $('#container')});

		new Content({el: $('#content')});

		// TODO fetch most popular cities ...

		window.repository['/cities'].add({id: '1', name: 'City 1', description: 'Description 1'});
	});
});
