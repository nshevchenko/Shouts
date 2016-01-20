package com.nik.shouts.models;

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
    public Shout(String id, String title, String content, String creatorId, Calendar createdDt, Calendar date, int participationLimit, String locationName, String locationCoordinates) {
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
    public Shout(String title, String content, String creatorId, Calendar createdDt, Calendar date, int participationLimit, String locationName, String locationCoordinates) {
        this.title = title;
        this.content = content;
        this.creatorId = creatorId;
        this.date = date;
        this.createdDt = createdDt;
        this.participationLimit = participationLimit;
        this.locationName = locationName;
        this.locationCoordinates = locationCoordinates;
        this.participationsIDs = new ArrayList<>();
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
    public Shout(String id, String title, String content, String creatorId, Calendar createdDt, Calendar date, int participationLimit, ArrayList<String> participationsIDs, String locationName, String locationCoordinates) {
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
            createdDt = Calendar.getInstance();
            createdDt.setTime(Configurations.DATE_FORMAT_SHOUT_CREATION_DB.parse(jsonObj.getString("created_dt")));

            // date of the shout
            String dateStr = jsonObj.getString("date");
            date = Calendar.getInstance();
            date.setTime(Configurations.DATE_FORMAT_SHOUT_CREATION_DB.parse(dateStr));

            // participation limit
            String participationLimitStr = jsonObj.getString("participationLimit");
            participationLimit = Integer.parseInt(participationLimitStr);
            locationName = jsonObj.getString("locationName");
            locationCoordinates = jsonObj.getString("locationCoordinates");
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
            .put("date", "Today")
            .put("create_dt", getCreatedDt())
            .put("locationCoordinates", getLocationCoordinates())
            .put("locationName", getLocationName())
            .put("participationLimit", getParticipationLimit());
            resultJson.put("shout", newShoutJson);
            return resultJson.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }



    /**
     * Add a new participant ID to this shout
     * @param user
     */
    public void addNewParticipant(User user){
        participationsIDs.add(user.getId());
    }

    // GETTERS

    public Calendar getCreatedDt(){
        return createdDt;
    }
    public int getParticipationLimit(){
        return participationLimit;
    }

    public User getCreator() {
        return UserUtils.getUserById(creatorId);
    }

    public ArrayList<String> getParticipationsIDs() {
        return participationsIDs;
    }

    public Calendar getDate() {
        return date;
    }

    public String getDateStrShout() {
        if(date == null)
            return "...";
        return Configurations.DATE_FORMAT_SHOUT_CREATION_USER.format(date.getTime());
    }

    public String getCreatorId() {
        return creatorId;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getLocationName() { return locationName; }

    public String getTitle() { return title; }

    public String getLocationCoordinates() { return locationCoordinates; }

}
