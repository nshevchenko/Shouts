package com.nik.shouts.models.collections;

import com.nik.shouts.models.Shout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by nik on 20/12/15.
 */

public class ShoutsCollections {

    private ArrayList<Shout> shouts;

    public void init(){
        shouts = new ArrayList<Shout>();
    }

    public void addShout(Shout shout){
        shouts.add(0, shout);
    }

    public ArrayList<Shout> getShouts(){
        return this.shouts;
    }

    /**
     * Create new shout
     * @param title
     * @param content
     * @param creatorId
     * @param date
     * @param participationLimit
     * @param locationName
     * @param locationCoordinates
     * @return
     */
    public Shout createNewShout(String title, String content, String creatorId, Date date, int participationLimit, String locationName, String locationCoordinates) {
        String newShoutID = (shouts.size() - 1) + "";
        Shout newShout = new Shout(newShoutID, title, content, creatorId, date, participationLimit, locationName, locationCoordinates);
        addShout(newShout);
        return newShout;
    }

    /**
     * Create new Shout
     * @param title
     * @param content
     * @param creatorId
     * @param participationsIDs
     * @param date
     * @param participationLimit
     * @param locationName
     * @param locationCoordinates
     * @return
     */
    public Shout createNewShout(String title, String content, String creatorId, ArrayList<String> participationsIDs, Date date, int participationLimit, String locationName, String locationCoordinates) {
        String newShoutID = (shouts.size() - 1) + "";
        Shout newShout = new Shout(newShoutID, title, content, creatorId, date, participationLimit, participationsIDs, locationName, locationCoordinates);
        addShout(newShout);
        return newShout;
    }

    /**
     * Get Shout by its ID
     * @param id
     * @return
     */
    public Shout getShoutById(int id){
        return shouts.get(id);
    }
}
