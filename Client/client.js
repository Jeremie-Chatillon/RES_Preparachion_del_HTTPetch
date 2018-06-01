const request = require('request');

const optionsGET = {
    url: 'http://localhost:8000/time',
    method: 'GET',
};

const optionsPOST = {
    url: 'http://localhost:8000/time',
    method: 'POST',
    encoding: 'utf-8',
    form: {
        content: "00:00:00",
    }
};

const optionsPOST2 = {
    url: 'http://localhost:8000/time',
    method: 'POST',
    encoding: 'utf-8',
    form: {
        content: "13:00:00",
    }
};

request(optionsGET, function(err, res, body) {
    console.log(body);
});

request(optionsGET, function(err, res, body) {
    console.log(body);
});

request(optionsPOST, function(err, res, body) {
    console.log(body);
});

request(optionsGET, function(err, res, body) {
    console.log(body);
});

request(optionsPOST2, function(err, res, body) {
    console.log(body);
});