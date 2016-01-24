package com.nik.shouts.models;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;
import com.nik.shouts.utils.ApiUtils;
import com.nik.shouts.utils.Configurations;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by nik on 23/11/15.
 */

public class User {

    // static objects
    private String id;
    private String username;
    private String password;
    private String email;
    private String nameAndSurname;
    private String[] friendsIDs;
    private String[] interests;
    private Calendar createdDt;

    // extra
    private Bitmap imageProfile;
    private LatLng lastKnownCoordinates;
    private ArrayList<User> friends;

    public User(String id) {
        this.id = id;
        this.username = "Guest";
        this.nameAndSurname = "Guest User";
        this.friendsIDs = new String[]{};
        this.interests = new String[]{};
        this.createdDt = Calendar.getInstance();
        this.friends = new ArrayList<>();
    }

    public User(JSONObject jsonObject){
        parseUserJson(jsonObject);
        this.friends = new ArrayList<>();
    }

    public User(String id, String username, String email, String nameAndSurname, String password, String[] friendsIDs, String[] interests) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nameAndSurname = nameAndSurname;
        this.password = password;
        this.friendsIDs = friendsIDs;
        this.interests = interests;
        this.friends = new ArrayList<>();
    }

    public String getInterestsAsString() {
        String interestsStr = "";

        if(interests == null)
            return "";

        if(interests.length == 0)
            return "";

        for(int i = 0; i < interests.length - 1; i++){
            interestsStr += interests[i] + ", ";
        }
        interestsStr += interests[interests.length - 1]; // add last element without coma
        return interestsStr;
    }

    public String getFriendsIDsAsString(){
        String friendsStr = "";

        if(getFriendsIDs().length == 0)
            return "";

        for(int i = 0; i < getFriendsIDs().length; i++){
            friendsStr += getFriendsIDs()[i] + ", ";
        }
        return friendsStr;
    }

    /**
     * Convert object to json string
     * @return json string object
     */
    public String toJSON(){
        /*
        $user = $data["user"];
		$email = $user["email"];
		$password = $user["password"];
		$nameAndSurname = $user["nameAndSurname"];
		$friendsIDs = $user["friendsIDs"];
		$interests = $user["interests"];
         */
        JSONObject resultJson = new JSONObject();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", getEmail())
                .put("username", getUsername())
                    .put("password", getPassword())
                    .put("nameAndSurname", getNameAndSurname())
                    .put("friendsIDs", getFriendsIDsAsString())
                    .put("interests", getInterestsAsString())
                    .put("create_dt", ApiUtils.getToday((SimpleDateFormat) Configurations.DATE_TIME_FORMAT_DB));
            resultJson.put("user", jsonObject);
            return resultJson.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }

    /**
     * get the last shouts of the user with limit
     * @return
     */
    public ArrayList<Shout> getLastShoutsOfUser(int limitList){
        ArrayList<Shout> userShouts = new ArrayList<Shout>();

        for(Shout shout : App.shoutsCollections.getShouts()){
            if(getId().equals(shout.getCreator().getId())) {
                userShouts.add(shout);
                if (userShouts.size() == limitList)
                    break;
            }
        }
        return userShouts;
    }

    /**
     * get the last accepted shouts by the user with limit
     * @return
     */
    public ArrayList<Shout> getLastActivitiesOfUser(int limitList){
        ArrayList<Shout> usersLastAcceptedShouts = new ArrayList<Shout>();
        for(Shout shout : App.shoutsCollections.getShouts()){
            if(shout.isUserAParticipant(getId())){
                usersLastAcceptedShouts.add(shout);
                if (usersLastAcceptedShouts.size() == limitList)
                    break;
            }
        }
        return usersLastAcceptedShouts;
    }

    /**
     * Set user's current last location
     * @param lastKnownCoordinates
     */
    public void setLastKnownCoordinates(LatLng lastKnownCoordinates) {
        this.lastKnownCoordinates = lastKnownCoordinates;
    }

    /**
     * Set array list friends
     * @param friends
     */
    public void setFriends(ArrayList<User> friends){
        this.friends = friends;
    }

    /**
     * Return last known location
     * @return lastKnownCoordinates
     */
    public LatLng getLastKnownCoordinates() {
        return lastKnownCoordinates;
    }

    public Bitmap getImageProfile(){
        return imageProfile;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getNameAndSurname() {
        return nameAndSurname;
    }

    public String getName() {
        String[] splitName = nameAndSurname.split(" ");
        return splitName[0];
    }

    public String getUsername() {
        return username;
    }

    public String getSurname() {
        String[] splitName = nameAndSurname.split(" ");
        return splitName[splitName.length - 1];
    }

    public String[] getInterests() {
        return interests;
    }

    public String[] getFriendsIDs() {
        return friendsIDs;
    }

    public ArrayList<User> getFriends(){
        return friends;
    }

    // JSON PARSER

    private void parseUserJson(JSONObject jsonObj){
        // parse single values

        try {
            id = jsonObj.getString("id");
            password = jsonObj.getString("password");
            username = jsonObj.getString("username");
            email = jsonObj.getString("email");
            nameAndSurname = jsonObj.getString("nameAndSurname");
            String friendsStr = jsonObj.getString("friendsIDs");
            friendsIDs = friendsStr.split(",");
            String createdDtStr = jsonObj.getString("create_dt");
            if(createdDtStr != null) {
                createdDt = Calendar.getInstance();
                createdDt.setTime(Configurations.DATE_FORMAT_SHOUT_CREATION_DB.parse(createdDtStr));
            }
            String interestsStr = jsonObj.getString("interests");
            interests = interestsStr.split(",");

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("email " + email);
    }
}