package com.nik.shouts.models;

import com.nik.shouts.utils.UserUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by nik on 22/11/15.
 */

public class Shout {

    private String ID;
    private String content;
    private String title;
    private String creatorId;
    private String[] participationsIDs;
    private Date date;
    private String locationName;
    private int participationLimit;
    private String locationCoordinates;

    /**
     * Create shout object with all parameters
     * @param ID
     * @param title
     * @param content
     * @param creatorId
     * @param participationsIDs
     * @param date
     * @param participationLimit
     * @param locationName
     * @param locationCoordinates
     */
    public Shout(String ID, String title, String content, String creatorId, String[] participationsIDs, Date date, int participationLimit, String locationName, String locationCoordinates) {
        this.ID = ID;
        this.content = content;
        this.title = title;
        this.creatorId = creatorId;
        this.participationsIDs = participationsIDs;
        this.date = date;
        this.participationLimit = participationLimit;
        this.locationName = locationName;
        this.locationCoordinates = locationCoordinates;
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
    public Shout(String title, String content, String creatorId, Date date, int participationLimit, String locationName, String locationCoordinates) {
        this.title = title;
        this.content = content;
        this.creatorId = creatorId;
        this.date = date;
        this.participationLimit = participationLimit;
        this.locationName = locationName;
        this.locationCoordinates = locationCoordinates;
    }

    /*
    * CHECK IF USER IS A PARTICIPANT OF THIS EVENT
    * */
    public boolean isUserAParticipant(String userId){
        for(int i = 0; i < participationsIDs.length; i++){
            if(userId.equals(participationsIDs[i]))
                return true;
        }
        return false;
    }

    /**
     * Convert object to json string
     * @return json string object
     */
    public String toJSON(){
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("title", getTitle());
            jsonObject.put("content", getContent());
            jsonObject.put("$creator", getCreator().getId());
            System.out.println("particitionaption ids " + getParticipationsIDs().toString());
            jsonObject.put("participations", getParticipationsIDs().toString());
            jsonObject.put("locationCoordinates", getLocationCoordinates());
            jsonObject.put("locationName", getLocationName());
            jsonObject.put("participationLimit", getParticipationLimit());
            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }
    // GETTERS

    public int getParticipationLimit(){
        return participationLimit;
    }

    public User getCreator() {
        return UserUtils.getUserById(creatorId);
    }

    public String[] getParticipationsIDs() {
        return participationsIDs;
    }

    public Date getDate() {
        return date;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public String getID() {
        return ID;
    }

    public String getContent() {
        return content;
    }

    public String getLocationName() { return locationName; }

    public String getTitle() { return title; }

    public String getLocationCoordinates() { return locationCoordinates; }

}
