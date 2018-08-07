package by.chemerisuk.cordova.geocoder;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import by.chemerisuk.cordova.support.CordovaMethod;
import by.chemerisuk.cordova.support.ReflectiveCordovaPlugin;
import by.chemerisuk.cordova.support.ReflectiveCordovaPlugin.ExecutionThread;

import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class GeocoderPlugin extends ReflectiveCordovaPlugin {
    @CordovaMethod(ExecutionThread.WORKER)
    public void geocode(String locationName, JSONObject options, CallbackContext callbackContext) throws IOException, JSONException {
        Geocoder geocoder = getGeocoder(options);
        int maxResults = options.optInt("maxResults", 1);
        JSONArray results = new JSONArray();
        List<Address> addresses = geocoder.getFromLocationName(locationName, maxResults);
        if (addresses != null) {
            for (Address address : addresses) {
                results.put(addressToJSON(address));
            }
        }

        callbackContext.success(results);
    }

    @CordovaMethod(ExecutionThread.WORKER)
    public void reverseGeocode(double latitude, double longitude, JSONObject options, CallbackContext callbackContext) throws IOException, JSONException {
        Geocoder geocoder = getGeocoder(options);
        int maxResults = options.optInt("maxResults", 1);
        JSONArray results = new JSONArray();
        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, maxResults);
        if (addresses != null) {
            for (Address address : addresses) {
                results.put(addressToJSON(address));
            }
        }

        callbackContext.success(results);
    }

    private Geocoder getGeocoder(JSONObject options) {
        Context context = cordova.getActivity();
        String lang = options.optString("lang", "");
        if (lang.isEmpty()) {
            return new Geocoder(context);
        } else {
            return new Geocoder(context, new Locale(lang));
        }
    }

    private JSONObject addressToJSON(Address address) throws JSONException {
        JSONObject result = new JSONObject();

        result.put("latitude", address.getLatitude());
        result.put("longitude", address.getLongitude());

        result.put("country", address.getCountryName());
        result.put("countryCode", address.getCountryCode());
        result.put("postalCode", address.getPostalCode());
        result.put("administrativeArea", address.getAdminArea());
        result.put("subAdministrativeArea", address.getSubAdminArea());
        result.put("thoroughfare", address.getThoroughfare());
        result.put("subThoroughfare", address.getSubThoroughfare());
        result.put("locality", address.getLocality());
        result.put("subLocality", address.getSubLocality());

        if (address.getMaxAddressLineIndex() >= 0) {
            result.put("formattedAddress", address.getAddressLine(0));
        }

        // result.put("featureName", address.getFeatureName());
        // result.put("premises", address.getPremises());
        // result.put("phone", address.getPhone());
        // result.put("url", address.getUrl());

        return result;
    }
}
