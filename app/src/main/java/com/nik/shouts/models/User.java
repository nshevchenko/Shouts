package com.nik.shouts.models;

import com.nik.shouts.utils.App;
import com.nik.shouts.utils.UserUtils;

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

    // dynamic objects

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
