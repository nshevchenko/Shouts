package com.nik.shouts.models;

import com.nik.shouts.utils.Configurations;
import com.nik.shouts.utils.UserUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
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
    private String locationName;
    private int participationLimit;
    private String locationCoordinates;

    /**
     * Create shout object with all parameters
     * @param id
     * @param title
     * @param content
     * @param creatorId
     * @param date
     * @param participationLimit
     * @param locationName
     * @param locationCoordinates
     */
    public Shout(String id, String title, String content, String creatorId, Calendar date, int participationLimit, String locationName, String locationCoordinates) {
        this.id = id;
        this.content = content;
        this.title = title;
        this.creatorId = creatorId;
        this.date = date;
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
     * @param date
     * @param participationLimit
     * @param locationName
     * @param locationCoordinates
     */
    public Shout(String title, String content, String creatorId, Calendar date, int participationLimit, String locationName, String locationCoordinates) {
        this.title = title;
        this.content = content;
        this.creatorId = creatorId;
        this.date = date;
        this.participationLimit = participationLimit;
        this.locationName = locationName;
        this.locationCoordinates = locationCoordinates;
        this.participationsIDs = new ArrayList<>();
    }


    public Shout(String id, String title, String content, String creatorId, Calendar date, int participationLimit, ArrayList<String> participationsIDs, String locationName, String locationCoordinates) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.creatorId = creatorId;
        this.date = date;
        this.participationsIDs = participationsIDs;
        this.participationLimit = participationLimit;
        this.locationName = locationName;
        this.locationCoordinates = locationCoordinates;
        this.participationsIDs = new ArrayList<>();
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
            newShoutJson.put("title", getTitle());
            newShoutJson.put("content", getContent());

//            if(getCreator() != null)
//                newShoutJson.put("creator", getCreator().getId());

            System.out.println("particitionaption ids " + getParticipationsIDs().toString());
            newShoutJson.put("creatorID", "Guest");
            newShoutJson.put("participationsIDs", getParticipationsIDs().toString());
            newShoutJson.put("date", "Today");
            newShoutJson.put("locationCoordinates", getLocationCoordinates());
            newShoutJson.put("locationName", getLocationName());
            newShoutJson.put("participationLimit", getParticipationLimit());
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

//    public String getDateStrDbFormat() {
//        if(date == null)
//            return "";
//        return Configurations.DATE_FORMAT_SHOUT_CREATION_DB.format(date.getTime());
//    }
//
//    public void setDateUserFormat(String dateStrUserFormat){
//        date.setTime(Configurations.DATE_FORMAT_SHOUT_CREATION_USER);
//    }

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
