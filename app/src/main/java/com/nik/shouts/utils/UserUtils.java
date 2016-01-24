package com.nik.shouts.utils;

import com.nik.shouts.models.App;
import com.nik.shouts.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nik on 12/12/15.
 */

public class UserUtils {

    /**
     * get User by its username
     * @param username
     * @return
     */
    public static User getUserByUserName(String username) {
        for(User user : App.usersCollections.getUsers())
            if(user.getUsername().equals(username))
                return user;
        return null;
    }

    /**
     * Get user By Id
     * @param ID
     * @return
     */
    public static User getUserById(String ID) {
        for(User user : App.usersCollections.getUsers())
            if(user.getId().equals(ID))
                return user;
        return null;
    }


    /**
     * Prase users initial json object
     * @param mainObject
     * @throws JSONException
     */
    public static void parseJsonUsers(JSONObject mainObject) throws JSONException {
        JSONArray users = mainObject.getJSONArray("users");
        for(int i = 0; i < users.length(); i++) {
            JSONObject jsonObj = users.getJSONObject(i);
            // create the user and add to app's data
            App.usersCollections.createNewUser(jsonObj);
        }
    }

    /**
     * filter Users by a string
     * @param search
     * @return
     */
    public static ArrayList<User> filterUsersByStr(String search){
        ArrayList<User> result = new ArrayList<>();
        for(User user : App.usersCollections.getUsers()){
            if (user.getNameAndSurname().toLowerCase().contains(search.toLowerCase()))
                result.add(user);
        }
        return result;
    }
}
