var exec = require("cordova/exec");
var PLUGIN_NAME = "GeocoderPlugin";

module.exports = {
    geocode: function(locationName, options) {
        return new Promise(function(resolve, reject) {
            exec(resolve, reject, PLUGIN_NAME, "geocode", [locationName, options || {}]);
        });
    },
    reverseGeocode: function(latitude, longitude, options) {
        return new Promise(function(resolve, reject) {
            exec(resolve, reject, PLUGIN_NAME, "reverseGeocode", [latitude, longitude, options || {}]);
        });
    }
};
