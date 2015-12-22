package com.nik.shouts.models.collections;

import com.nik.shouts.models.Shout;

import java.util.ArrayList;

/**
 * Created by nik on 20/12/15.
 */

public class ShoutsCollections {

    private ArrayList<Shout> shouts;

    public void init(){
        shouts = new ArrayList<Shout>();
    }

    public void addShout(Shout shout){
        shouts.add(shout);
    }

    public ArrayList<Shout> getShouts(){
        return this.shouts;
    }

}
