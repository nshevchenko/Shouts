package com.nik.shouts.models.collections;

import android.util.Log;

import com.nik.shouts.models.App;
import com.nik.shouts.models.Shout;
import com.nik.shouts.utils.ApiUtils;
import com.nik.shouts.utils.Configurations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by nik on 20/12/15.
 */

public class ShoutsCollections {

    private ArrayList<Shout> shouts;

    public void init(){
        shouts = new ArrayList<Shout>();
    }

    public void addShout(Shout shout){
        shouts.add(0, shout);
    }

    public ArrayList<Shout> getShouts(){
        return this.shouts;
    }

    /**
     * Create new shout
     * @param title
     * @param content
     * @param creatorId
     * @param date
     * @param participationLimit
     * @param locationName
     * @param locationCoordinates
     * @return
     */
    public Shout createNewShout(String title, String content, String creatorId, Calendar date, int participationLimit, String locationName, String locationCoordinates, String[] hashtags) {
        String newShoutID = (shouts.size() - 1) + "";
        Calendar createDt = Calendar.getInstance();
        Shout newShout = new Shout(newShoutID, title, content, creatorId, createDt, date, participationLimit, locationName, locationCoordinates, hashtags);
        addShout(newShout);
        return newShout;
    }

    public Shout createNewShout(JSONObject jsonObject){
        Shout newShout = new Shout(jsonObject);
        addShout(newShout);
        return newShout;
    }
}
