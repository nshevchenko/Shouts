package com.nik.shouts.utils;

import android.util.Log;

import com.nik.shouts.interfaces.*;
import com.nik.shouts.models.App;
import com.nik.shouts.models.Shout;
import com.nik.shouts.models.User;
import com.nik.shouts.models.collections.UsersCollections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
            System.out.println("result " + jsonDataStr);
            JSONObject mainObject = new JSONObject(jsonDataStr);
            System.out.println("0");
            parseUsers(mainObject);
            parseShouts(mainObject);
        } catch (JSONException jsonEx) {
            Log.d("LOG", "Json parsing error");
        }
    }

    /**
     * Prase users initial json object
     * @param mainObject
     * @throws JSONException
     */
    public static void parseUsers(JSONObject mainObject) throws JSONException {
        JSONArray users = mainObject.getJSONArray("users");
        for(int i = 0; i < users.length(); i++) {
            JSONObject jsonObj = users.getJSONObject(i);
            // parse single values
            String Id = jsonObj.getString("id");
            String password = jsonObj.getString("password");
            String username = jsonObj.getString("username");
            String email = jsonObj.getString("email");
            String nameAndSurname = jsonObj.getString("nameAndSurname");
            String friendsStr = jsonObj.getString("friendsIDs");
            String[] friends = friendsStr.split(",");
            String interestsStr = jsonObj.getString("interests");
            String[] interests = interestsStr.split(",");
            System.out.println("email " + email);
            // create the user and add to app's data
            App.usersCollections.createNewUser(username, email, nameAndSurname, password, friends, interests);
        }
    }

    /**
     * Parse shouts initial data json data
     * @param mainObject
     * @throws JSONException
     */
    public static void parseShouts(JSONObject mainObject) throws JSONException {
        JSONArray users = mainObject.getJSONArray("shouts");
        for(int i = 0; i < users.length(); i++) {
            JSONObject jsonObj = users.getJSONObject(i);
            // parse single values

            String ID = jsonObj.getString("id");
            String content = jsonObj.getString("content");
            String title = jsonObj.getString("title");

            String creatorId = jsonObj.getString("creatorID");
            String participationsStr = jsonObj.getString("participationsIDs");

            String[] participations = participationsStr.split(",");
            ArrayList<String> participationsIDs = new ArrayList<>();
            for (String participant : participations)
                participationsIDs.add(participant);

            String dateStr = jsonObj.getString("date");
            Calendar date = Calendar.getInstance();
            String participationLimitStr = jsonObj.getString("participationLimit");
            int participationLimit = Integer.parseInt(participationLimitStr);
            String locationName = jsonObj.getString("locationName");
            String locationCoordinates = jsonObj.getString("locationCoordinates");
            // create the user and add to app's data
            App.shoutsCollections.createNewShout(title, content, creatorId, participationsIDs, date, participationLimit, locationName, locationCoordinates);
        }
    }




    // HTTP REQUESTS


    /**
     * GET REQUEST
     * @param apiFinalCallback
     */
    public static void getRequestWithCallBack(String url, final ApiRequestCallback apiFinalCallback) {
        // download users
        HttpRequestCallback httpRequest = new HttpRequestCallback(apiFinalCallback, "GET");
        httpRequest.execute(url);
    }

    /**
     * GET REQUEST AND DOWNLOAD PNG
     * @param apiFinalCallback
     */
    public static void getPNGBitmap(String url, final ApiRequestCallback apiFinalCallback) {
        // download users
        HttpRequestCallback httpRequest = new HttpRequestCallback(apiFinalCallback, "GET RAW");
        httpRequest.execute(url);
    }

    /**
     * POST REQUEST
     * @param uploadCallback
     * @param jsonNewObject
     */
    public static void uploadNewJsonObject(ApiRequestCallback uploadCallback, String jsonNewObject) {
        HttpRequestCallback httpRequest = new HttpRequestCallback(uploadCallback, "POST", jsonNewObject);
        httpRequest.execute("http://lionsrace.altervista.org/apiShouts.php");
    }
}
