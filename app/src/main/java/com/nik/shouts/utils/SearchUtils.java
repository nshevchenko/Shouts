package com.nik.shouts.utils;

import com.nik.shouts.models.App;
import com.nik.shouts.models.SearchResult;
import com.nik.shouts.models.Shout;
import com.nik.shouts.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nik on 22/01/16.
 */

public class SearchUtils {

    /**
     * Parse array list of shouts to searchresult
     * @param shouts
     * @return
     */
    public static ArrayList<SearchResult> getSearchResultFromShouts(ArrayList<Shout> shouts){
        ArrayList<SearchResult> searchResults = new ArrayList<>();
        for(Shout shout : shouts){
            searchResults.add(new SearchResult(shout.getId(), shout.getTitle(), shout.getHashtagsAsString()));
        }
        return searchResults;
    }

    public static ArrayList<SearchResult> getSearchResultFromLocationJson(String locationJson){
        ArrayList<SearchResult> searchResults = new ArrayList<>();

        try {
            System.out.println("" + locationJson);
            JSONObject jsonObject = new JSONObject(locationJson);
            //        for(String string: strings)
//            searchResults.add(new SearchResult(string));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return searchResults;
    }

    /**
     * Parse array list of users to searchResult
     * @param users
     * @return
     */
    public static ArrayList<SearchResult> getSearchResultFromUsers(ArrayList<User> users) {
        ArrayList<SearchResult> results = new ArrayList<>();
        for(User user: users)
            results.add(new SearchResult(user.getId(), user.getImageProfile(), user.getNameAndSurname(), user.getEmail()));
        return results;
    }

}
