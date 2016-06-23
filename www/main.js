var express = require('express');

var server = express();

server.use(express.static(__dirname + '/public'));

server.use('/lib', express.static(__dirname + '/public/js/lib'));

server.use('/model', express.static(__dirname + '/public/js/model'));

server.use('/view', express.static(__dirname + '/public/js/view'));

server.use('/collection', express.static(__dirname + '/public/js/collection'));

var parser = require('body-parser');

server.use(parser.urlencoded({ extended: true }));

server.use(parser.json());

var generate_pdf_router = express.Router();

generate_pdf_router.post('/', function(request, response) {
	console.log(request.body);

	response.send('generated-id');
});

server.use('/generate-pdf', generate_pdf_router);

var fs = require("fs");

var download_as_pdf_router = express.Router();

download_as_pdf_router.get('/', function(request, response) {
	var file = __dirname + "/trash/pdf-to-send.pdf";

	fs.readFile(file, function(error, data) {
		response.setHeader('content-disposition', 'attachment; filename=TravelGuide.pdf');

		response.contentType("application/pdf");

		response.send(data);
	});
});

server.use('/download-as-pdf', download_as_pdf_router);

server.listen(8080);
