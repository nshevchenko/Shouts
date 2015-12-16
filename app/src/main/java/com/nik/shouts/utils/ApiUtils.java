package com.nik.shouts.utils;

import android.util.Log;

import com.nik.shouts.interfaces.*;
import com.nik.shouts.models.Shout;
import com.nik.shouts.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by nik on 14/12/15.
 */

public class ApiUtils {

//    public static void getFromApi(String request, ){
//        new HttpRequestCallback(new OnRequestFinished()
//        {
//            @Override
//            public void onRequestComplete(String feeds)
//            {
//                //do whatever you want to do with the feeds
//            }
//        }).execute("http://enterurlhere.com");
//    }

    /*
    * Download main data for the user's login = shouts and users(friends)
    */
    public static void downloadAppData(final ApiRequestCallback apiFinalCallback) {
        // download users
        HttpRequestCallback httpRequest = new HttpRequestCallback(new ApiRequestCallback() {
            @Override
            public void onRequestComplete(String result) {
                System.out.println(result);
                parseAppData(result);
                apiFinalCallback.onRequestComplete("");
            }
        }, "GET");
        httpRequest.execute("http://lionsrace.altervista.org/apiShouts.php");
    }

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
     * Prase users json object
     * @param mainObject
     * @throws JSONException
     */
    public static void parseUsers(JSONObject mainObject) throws JSONException {
        JSONArray users = mainObject.getJSONArray("users");
        for(int i = 0; i < users.length(); i++) {
            System.out.println("1");
            JSONObject jsonObj = users.getJSONObject(i);
            // parse single values
            System.out.println("2");
            String Id = jsonObj.getString("id");
            String password = jsonObj.getString("password");
            String userName = jsonObj.getString("username");
            String nameAndSurname = jsonObj.getString("nameAndSurname");
            String friendsStr = jsonObj.getString("friendsIDs");
            String[] friends = friendsStr.split(",");
            String interestsStr = jsonObj.getString("interests");
            String[] interests = interestsStr.split(",");
            System.out.println("3");
            // create the user and add to app's data
            User tempNewUser = new User(Id, userName, password, nameAndSurname, friends, interests);
            App.data.addUser(tempNewUser);
        }
    }

    /**
     * Parse shouts json data
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

            String dateStr = jsonObj.getString("date");
            Date date = Calendar.getInstance().getTime();
            String participationLimitStr = jsonObj.getString("participationLimit");
            int participationLimit = Integer.parseInt(participationLimitStr);
            String locationName = jsonObj.getString("locationName");
            String locationCoordinates = jsonObj.getString("locationCoordinates");
            // create the user and add to app's data
            Shout tempNewShout = new Shout(ID, title, content, creatorId, participations, date, participationLimit, locationName, locationCoordinates);
            App.data.addShout(tempNewShout);
        }
    }
}
