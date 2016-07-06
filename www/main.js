var express = require('express');

var server = express();

server.use(express.static(__dirname + '/public'));

server.use('/lib', express.static(__dirname + '/public/js/lib'));

server.use('/model', express.static(__dirname + '/public/js/model'));

server.use('/view', express.static(__dirname + '/public/js/view'));

server.use('/collection', express.static(__dirname + '/public/js/collection'));

var on_countries = express.Router();

on_countries.post('/', function(request, response) {
	var __request = require('request');

	request.pipe(__request('http://localhost:9090/on-countries')).pipe(response);
});

server.use('/on-countries', on_countries);

var on_cities = express.Router();

on_cities.post('/', function(request, response) {
	var __request = require('request');

	request.pipe(__request('http://localhost:9090/on-cities')).pipe(response);
});

server.use('/on-cities', on_cities);

var on_hotels = express.Router();

on_hotels.post('/', function(request, response) {
	var __request = require('request');

	request.pipe(__request('http://localhost:9090/on-hotels')).pipe(response);
});

server.use('/on-hotels', on_hotels);

var on_pdf = express.Router();

on_pdf.post('/', function(request, response) {
	var __request = require('request');

	request.pipe(__request('http://localhost:9090/on-pdf')).pipe(response);
});

server.use('/on-pdf', on_pdf);

var on_download_pdf = express.Router();

on_download_pdf.get('/', function(request, response) {
	var __request = require('request');

	request.pipe(__request('http://localhost:9090/on-download-pdf')).pipe(response);
});

server.use('/on-download-pdf', on_download_pdf);

server.listen(8080);
