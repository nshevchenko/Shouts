package com.nik.shouts.models;

import android.graphics.Bitmap;

/**
 * Created by nik on 23/01/16.
 */

public class PlaceResult extends SearchResult {

    private String location;

    public PlaceResult(String id, String title, String subTitle) {
        super(id, title, subTitle);
    }

    public PlaceResult(String id, Bitmap imageView, String title, String subTitle, String location) {
        super(id, imageView, title, subTitle);
        this.location = location;
    }

    public String getLocation(){
        return location;
    }
}
