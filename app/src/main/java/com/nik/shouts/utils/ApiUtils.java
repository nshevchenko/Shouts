package com.nik.shouts.utils;

import android.text.format.DateFormat;
import android.util.Log;

import com.nik.shouts.interfaces.*;
import com.nik.shouts.models.App;

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
            System.out.println("result " + jsonDataStr);
            JSONObject mainObject = new JSONObject(jsonDataStr);
            System.out.println("0");
            App.usersCollections.parseJsonUsers(mainObject);
            App.shoutsCollections.parseJsonShouts(mainObject);
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


}
