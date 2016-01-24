package com.nik.shouts.utils;

import android.text.format.DateFormat;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.nik.shouts.interfaces.*;
import com.nik.shouts.models.App;
import com.nik.shouts.models.PlaceResult;
import com.nik.shouts.models.SearchResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by nik on 14/12/15.
 */

public class ApiUtils {


    /**
     * Parse the initial downloaded application data from server
     * @param jsonDataStr
     */
    public static void parseAppData(String jsonDataStr) {
        try {
            JSONObject mainObject = new JSONObject(jsonDataStr);
            UserUtils.parseJsonUsers(mainObject);
            ShoutsUtils.parseJsonShouts(mainObject);
        } catch (JSONException jsonEx) {
            Log.d("LOG", "Json parsing error");
        }
    }

    // HTTP REQUESTS


    /**
     * GET REQUEST
     * @param apiFinalCallback
     */

    public static void getRequestWithCallBack(String url, final RequestCallback apiFinalCallback) {
        // download content
        HttpRequestCallback httpRequest = new HttpRequestCallback(apiFinalCallback, "GET");
        httpRequest.execute(url);
    }

    /**
     * GET REQUEST AND DOWNLOAD PNG
     * @param apiFinalCallback
     */
    public static void getPNGBitmap(String url, final RequestCallback apiFinalCallback) {
        // download users
        HttpRequestCallback httpRequest = new HttpRequestCallback(apiFinalCallback, "GET RAW");
        httpRequest.execute(url);
    }

    /**
     * POST REQUEST
     * @param uploadCallback
     * @param jsonNewObject
     */
    public static void uploadNewJsonObject(RequestCallback uploadCallback, String jsonNewObject) {
        HttpRequestCallback httpRequest = new HttpRequestCallback(uploadCallback, "POST", jsonNewObject);
        httpRequest.execute("http://lionsrace.altervista.org/apiShouts.php");
    }

    public static String getToday(SimpleDateFormat format){
        Calendar todayCalendar = Calendar.getInstance();
        return format.format(todayCalendar.getTime());
    }

    public static String createUrlForPlaceSearch(String search){
        // get current user last location
        String locationParamter = "";
        LatLng loc = App.usersCollections.getCurrentlyLoggedInUser().getLastKnownCoordinates();

        String parameter = "?location=" + loc.latitude + "," +loc.longitude
                + "&radius=20000&query=" + search.replace(" ", "+")
                + "&key=" + Configurations.GOOGLE_API_KEY;

        return parameter;
    }

    /**
     * Parse json from place search and resutrn an search result array
     * @param placesResultStr
     * @return
     */
    public static ArrayList<SearchResult> parsePlacesResult(String placesResultStr){
        ArrayList<SearchResult> searchResults= new ArrayList<>();
        try {
            JSONObject placesResult = new JSONObject(placesResultStr);
            JSONArray results = placesResult.getJSONArray("results");
            for(int i = 0; i < results.length(); i++) {
                JSONObject resJson = results.getJSONObject(i);
                String name = resJson.getString("name");
                JSONObject location = resJson.getJSONObject("geometry").getJSONObject("location");
                String coordinates = location.getString("lat") + "," + location.getString("lng");
                String address = resJson.getString("formatted_address");
                searchResults.add(new PlaceResult("", null, name, address, coordinates));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return searchResults;
    }


}
