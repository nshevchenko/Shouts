package com.nik.shouts.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.nik.shouts.utils.ApiUtils;
import com.nik.shouts.utils.Configurations;
import com.nik.shouts.utils.UserUtils;

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
 * Created by nik on 22/11/15.
 */

public class Shout {

    private String id;
    private String content;
    private String title;
    private String creatorId;
    private ArrayList<String> participationsIDs;
    private Calendar date;
    private Calendar createdDt;
    private String locationName;
    private int participationLimit;
    private String locationCoordinates;
    private String[] hashtags;

    // dynamic
    private Marker shoutMarker = null;

    /**
     * Create shout from a jsonObject and parse its json
     * @param jsonObj
     */
    public Shout(JSONObject jsonObj){
        parseJsonShout(jsonObj);
    }
    /**
     * Create shout object with all parameters
     * @param id
     * @param title
     * @param content
     * @param creatorId
     * @param createdDt
     * @param date
     * @param participationLimit
     * @param locationName
     * @param locationCoordinates
     */
    public Shout(String id, String title, String content, String creatorId, Calendar createdDt, Calendar date, int participationLimit, String locationName, String locationCoordinates, String[] hashtags) {
        this.id = id;
        this.content = content;
        this.title = title;
        this.creatorId = creatorId;
        this.date = date;
        this.createdDt = createdDt;
        this.participationLimit = participationLimit;
        this.locationName = locationName;
        this.locationCoordinates = locationCoordinates;
        this.participationsIDs = new ArrayList<>();
        this.hashtags = hashtags;
    }

    /**
     * Create new Shout from user prospective
     * @param title
     * @param content
     * @param creatorId
     * @param createdDt
     * @param date
     * @param participationLimit
     * @param locationName
     * @param locationCoordinates
     */
    public Shout(String title, String content, String creatorId, Calendar createdDt, Calendar date, int participationLimit, String locationName, String locationCoordinates, String[] hashtags) {
        this.title = title;
        this.content = content;
        this.creatorId = creatorId;
        this.date = date;
        this.createdDt = createdDt;
        this.participationLimit = participationLimit;
        this.locationName = locationName;
        this.locationCoordinates = locationCoordinates;
        this.participationsIDs = new ArrayList<>();
        this.hashtags = hashtags;
    }

    /**
     * Constructor with all parameters
     * @param id
     * @param title
     * @param content
     * @param creatorId
     * @param createdDt
     * @param date
     * @param participationLimit
     * @param participationsIDs
     * @param locationName
     * @param locationCoordinates
     */
    public Shout(String id, String title, String content, String creatorId, Calendar createdDt, Calendar date, int participationLimit, ArrayList<String> participationsIDs, String locationName, String locationCoordinates, String[] hashtags) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.creatorId = creatorId;
        this.date = date;
        this.createdDt = createdDt;
        this.participationsIDs = participationsIDs;
        this.participationLimit = participationLimit;
        this.locationName = locationName;
        this.locationCoordinates = locationCoordinates;
        this.participationsIDs = new ArrayList<>();
        this.hashtags = hashtags;
    }

    public void parseJsonShout(JSONObject jsonObj){
        try {
            id = jsonObj.getString("id");
            content = jsonObj.getString("content");
            title = jsonObj.getString("title");

            creatorId = jsonObj.getString("creatorID");

            String participationsStr = jsonObj.getString("participationsIDs");
            String[] participations = participationsStr.split(",");
            participationsIDs = new ArrayList<>();
            for (String participant : participations)
                participationsIDs.add(participant);

            // create date time
            String createDtStr = jsonObj.getString("create_dt");
            if(createDtStr != null) {
                createdDt = Calendar.getInstance();
                createdDt.setTime(Configurations.DATE_FORMAT_SHOUT_CREATION_DB.parse(createDtStr));
            }
            // date of the shout
            String dateStr = jsonObj.getString("date");
            if (dateStr != null) {
                date = Calendar.getInstance();
                date.setTime(Configurations.DATE_FORMAT_SHOUT_CREATION_DB.parse(dateStr));
            }

            // participation limit
            String participationLimitStr = jsonObj.getString("participationLimit");
            participationLimit = Integer.parseInt(participationLimitStr);

            //location
            locationName = jsonObj.getString("locationName");
            locationCoordinates = jsonObj.getString("locationCoordinates");

            // hashtags
            hashtags = jsonObj.getString("hashtags").split(",");

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /*
    * CHECK IF USER IS A PARTICIPANT OF THIS EVENT
    * */
    public boolean isUserAParticipant(String userId){
        for(int i = 0; i < participationsIDs.size(); i++){
            if(userId.equals(participationsIDs.get(i)))
                return true;
        }
        return false;
    }

    /**
     * Convert object to json string
     * @return json string object
     */
    public String toJSON(){

        JSONObject resultJson = new JSONObject();
        try {
            JSONObject newShoutJson = new JSONObject();
            newShoutJson.put("title", getTitle())
            .put("content", getContent())
            .put("creatorID", getCreatorId())
            .put("participationsIDs", getParticipationsIDs().toString())
            .put("date", Configurations.DATE_FORMAT_SHOUT_CREATION_DB.format(getDate().getTime()))
            .put("create_dt", Configurations.DATE_FORMAT_SHOUT_CREATION_DB.format(getCreatedDt().getTime()))
            .put("locationCoordinates", getLocationCoordinates())
            .put("locationName", getLocationName())
            .put("participationLimit", getParticipationLimit())
                    .put("hashtags", getHashtagsAsString());
            resultJson.put("shout", newShoutJson);
            return resultJson.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }

    public LatLng getLatLng(){
        if(getLocationCoordinates() == null)
            return null;
        String[] latlngStr = getLocationCoordinates().split(",");
        if(latlngStr.length == 0 )
            return null;
        double lat = Double.parseDouble(latlngStr[0]);
        double lng = Double.parseDouble(latlngStr[1]);
        LatLng latLng = new LatLng(lat, lng);
        return latLng;
    }
    /**
     * Add a new participant ID to this shout
     * @param user
     */
    public void addNewParticipant(User user){
        participationsIDs.add(user.getId());
    }

    // GETTERS

    public void setShoutMarker(Marker marker){
        this.shoutMarker = marker;
    }

    /**
     * Find how many users can still participate to the shout
     * @return
     */
    public int getParticipationLeft(){
        int participationLeft = getParticipationLimit() - participationsIDs.size();
        if(participationLeft <=  0)
            return 0;
        return participationLeft;
    }

    public String getHashtagsAsString(){
        if(hashtags == null)
            return "";
        if(hashtags.length == 0)
            return "";
        String result = "";
        for (int i = 0; i < hashtags.length - 1; i++)
            result += hashtags[i] + ", ";
        result += hashtags[hashtags.length - 1];
        return result;
    }


    public String getDateAsStringInList() {
        if(date == null)
            return "...";
        return Configurations.DATE_FORMAT_SHOUT_CREATION_USER.format(date.getTime());
    }

    public String getTimeAsString(){
        if(getDate() == null)
            return null;
        return Configurations.TIME_FORMAT_DB.format(getDate().getTime());
    }

    public String getParticipationsAsString(){
        String result = "";
        for(User user : App.usersCollections.getUsers()){
            if(getParticipationsIDs().contains(user.getId()))
                result += user.getName() + ", ";
        }
        if(result.length() > 0)
            result.substring(0, result.length() - 2);
        return result;
    }

    public ArrayList<String> getParticipationsIDs() {
        return participationsIDs;
    }

    public Calendar getDate() {
        return date;
    }

    public Calendar getCreatedDt(){ return createdDt;}

    public String getId() { return id;}

    public String getContent() { return content;}

    public String getLocationName() { return locationName; }

    public User getCreator() {  return UserUtils.getUserById(creatorId);}

    public String getCreatorId() {  return creatorId; }

    public String getTitle() { return title; }

    public int getParticipationLimit(){
        return participationLimit;
    }

    public String getLocationCoordinates() { return locationCoordinates; }

    public String[] getHashtags(){  return hashtags; }

    public Marker getMarker() { return shoutMarker;}

}

