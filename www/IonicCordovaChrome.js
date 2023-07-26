var exec = require('cordova/exec');

exports.coolMethod = function (arg0, success, error) {
    exec(success, error, 'IonicCordovaChrome', 'coolMethod', [arg0]);
};
exec(function (data) {
}, function (data) {
}, 'IonicCordovaChrome', 'init', []);
