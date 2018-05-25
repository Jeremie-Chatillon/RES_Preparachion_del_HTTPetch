const request = require('request');

request({
    uri: "http://www.heig-vd.ch",
    method:"GET",
    timeout: 10000,
    followRedirect: true
}, function(error, response, body) {
    console.log(body);
});

request({
    uri: "http://www.heig-vd.ch",
    method: "POST",
    form: {
        name: "Bob"
    }
}, function(error, response, body) {
    console.log(body);
});