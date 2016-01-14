package com.nik.shouts.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nik on 23/11/15.
 */

public class User {

    // static objects
    private String id;
    private String password;
    private String userName;
    private String nameAndSurname;
    private String[] friendsIDs;
    private String[] interests;


    public User() {
        userName = "Guest";
        nameAndSurname = "Guest User";
    }

    public User(String id, String userName, String password, String nameAndSurname, String[] friendsIDs, String[] interests) {
        this.id = id;
        this.password = password;
        this.userName = userName;
        this.nameAndSurname = nameAndSurname;
        this.friendsIDs = friendsIDs;
        this.interests = interests;
    }

    public String getInterestsAsString() {
        String interestsStr = "";
        int interestsListSize = interests.length;
        for(int i = 0; i < interestsListSize - 1; i++){
            interestsStr += interests[i] + ", ";
        }
        interestsStr += interests[interestsListSize - 1];
        return interestsStr;
    }

    /**
     * Convert object to json string
     * @return json string object
     */
    public String toJSON(){
        /*
        $user = $data["user"];
		$userName = $user["userName"];
		$password = $user["password"];
		$nameAndSurname = $user["nameAndSurname"];
		$friendsIDs = $user["friendsIDs"];
		$interests = $user["interests"];
         */
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("userName", getUserName());
            jsonObject.put("password", getPassword());
            jsonObject.put("nameAndSurname", getNameAndSurname());
            System.out.println("friendsIDs ids " + getFriendsIDs().toString());
            jsonObject.put("friendsIDs", getFriendsIDs().toString());
            System.out.println("interests " + getInterestsAsString());
            jsonObject.put("interests", getInterestsAsString());
            return jsonObject.toString();
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

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public String getNameAndSurname() {
        return nameAndSurname;
    }

    public String getName() {
        String[] splitName = nameAndSurname.split(" ");
        return splitName[0];
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
