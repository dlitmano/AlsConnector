var express = require('express');

var server = express();

var parser = require('body-parser');

server.use(parser.urlencoded({extended: true}));

server.use(parser.json());

var index_countries = 0;

var on_countries = express.Router();

on_countries.post('/', function(request, response) {
	console.log('POST /on-countries\n\n' + JSON.stringify(request.body) + '\n');

	if(index_countries > 0) {
		index_countries--;

		response.json([{id: '1', name: 'Country 1', description: 'Country from server'},
			       {id: '3', name: 'Country 3', description: 'Country from server'}]);

		return;
	}

	index_countries++;

	response.json([{id: '2', name: 'Country 2', description: 'Country from server'}]);
});

server.use('/on-countries', on_countries);

var index_cities = 0;

var on_cities = express.Router();

on_cities.post('/', function(request, response) {
	console.log('POST /on-cities\n\n' + JSON.stringify(request.body) + '\n');

	if(index_cities > 1) {
		index_cities = 0;

		response.json([{country: '2', id: '1', name: 'City 1', description: 'City from server'},
			       {country: '3', id: '3', name: 'City 3', description: 'City from server'}]);

		return;
	}

	index_cities++;

	response.json([{country: '2', id: '2', name: 'City 2', description: 'City from server'}]);
});

server.use('/on-cities', on_cities);

var on_hotels = express.Router();

on_hotels.post('/', function(request, response) {
	console.log('POST /on-hotels\n\n' + JSON.stringify(request.body) + '\n');

	response.json([{country: '2', city: '2', id: '2', name: 'Hotel 2', description: 'Hotel from server'},
		       {country: '3', city: '3', id: '3', name: 'Hotel 3', description: 'Hotel from server'}]);
});

server.use('/on-hotels', on_hotels);

var on_pdf = express.Router();

on_pdf.post('/', function(request, response) {
	console.log('POST /on-pdf\n\n' + JSON.stringify(request.body) + '\n');

	response.send('generated-id');
});

server.use('/on-pdf', on_pdf);

var fs = require("fs");

var on_download_pdf = express.Router();

on_download_pdf.get('/', function(request, response) {
	console.log('GET /on-download-pdf?id=hash-id');

	var file = __dirname + "/trash/pdf-to-send.pdf";

	fs.readFile(file, function(error, data) {
		response.setHeader('content-disposition', 'attachment; filename=TravelGuide.pdf');

		response.contentType("application/pdf");

		response.send(data);
	});
});

server.use('/on-download-pdf', on_download_pdf);

server.listen(9090);
