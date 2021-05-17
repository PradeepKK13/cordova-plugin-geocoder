

cordova.addConstructor(function() {
    function GeocoderPlugin() {

    }

    GeocoderPlugin.prototype.geocode = function( locationName, options, successCallback, errorCallback ){
        cordova.exec(successCallback, errorCallback, "GeocoderPlugin", "geocode", [locationName, options || {}]);
    }
  GeocoderPlugin.prototype.reverseGeocode = function( latitude, longitude, options, successCallback, errorCallback ){
        cordova.exec(successCallback, errorCallback, "GeocoderPlugin", "reverseGeocode", [latitude, longitude, options || {}]);
    }
   
    window.GeocoderPlugin = new GeocoderPlugin()
    return window.GeocoderPlugin
});
