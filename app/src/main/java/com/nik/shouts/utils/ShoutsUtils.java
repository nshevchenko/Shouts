package com.nik.shouts.utils;

import com.nik.shouts.models.App;
import com.nik.shouts.models.Shout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nik on 12/12/15.
 */

public class ShoutsUtils {

    /**
     * Get Shout by its ID
     * @param id
     * @return
     */
    public static Shout getShoutById(String id){
        for (Shout shout : App.shoutsCollections.getShouts())
            if (shout.getId().equals(id))
                return shout;
        return null;
    }

    /**
     * Parse shouts initial data json data
     * @param mainObject
     * @throws JSONException
     */
    public static void parseJsonShouts(JSONObject mainObject) throws JSONException {
        JSONArray users = mainObject.getJSONArray("shouts");
        for(int i = 0; i < users.length(); i++) {
            JSONObject jsonObj = users.getJSONObject(i);
            // create the user and add to app's data
            App.shoutsCollections.createNewShout(jsonObj);
        }
    }

    /**
     * Filter shouts by hashtags
     * @param hashtag
     * @return
     */
    public static ArrayList<Shout> filterShoutsByHashtag(String hashtag){
        ArrayList<Shout> shouts = new ArrayList<>();
        for(Shout shout: App.shoutsCollections.getShouts()) {
            if (shout.getHashtagsAsString().toLowerCase().contains(hashtag.toLowerCase()))
                shouts.add(shout);
        }
        return shouts;
    }

    /**
     * Filter shouts by hashtags
     * @param title
     * @return
     */
    public static ArrayList<Shout> filterShoutsByTitle(String title){
        ArrayList<Shout> shouts = new ArrayList<>();
        for(Shout shout: App.shoutsCollections.getShouts()) {
            if (shout.getTitle().toLowerCase().contains(title.toLowerCase()))
                shouts.add(shout);
        }
        return shouts;
    }
}
