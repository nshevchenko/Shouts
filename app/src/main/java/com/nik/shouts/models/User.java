package com.nik.shouts.models;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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


    // extra
    private Bitmap imageProfile;
    private LatLng lastKnownCoordinates;

    public User(String id) {
        this.id = id;
        this.username = "Guest";
        this.email = "Guest";
        this.nameAndSurname = "Guest User";
        this.friendsIDs = new String[]{};
        this.interests = new String[]{};
    }

    public User(String id, String username, String email, String nameAndSurname, String password, String[] friendsIDs, String[] interests) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nameAndSurname = nameAndSurname;
        this.password = password;
        this.friendsIDs = friendsIDs;
        this.interests = interests;
    }

    public String getInterestsAsString() {
        String interestsStr = "";

        if(interests.length == 0)
            return "";

        for(int i = 0; i < interests.length; i++){
            interestsStr += interests[i] + ", ";
        }
        return interestsStr;
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
            jsonObject.put("email", getEmail());
            jsonObject.put("username", getUsername());
            jsonObject.put("password", getPassword());
            jsonObject.put("nameAndSurname", getNameAndSurname());
            System.out.println("friendsIDs ids " + getFriendsIDs().toString());
            jsonObject.put("friendsIDs", getFriendsIDs().toString());
            System.out.println("interests " + getInterestsAsString());
            jsonObject.put("interests", getInterestsAsString());
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

}