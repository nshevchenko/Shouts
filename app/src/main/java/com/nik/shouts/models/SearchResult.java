package com.nik.shouts.models;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by nik on 22/01/16.
 */

public class SearchResult {


    private String id;
    private Bitmap imageView;
    private String title;
    private String subTitle;

    public SearchResult(String id, String title, String subTitle) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
    }

    public SearchResult(String id, Bitmap imageView, String title, String subTitle){
        this.id = id;
        this.imageView = imageView;
        this.title = title;
        this.subTitle = subTitle;
    }

    public Bitmap getImageView() {
        return imageView;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getId() {
        return id;
    }
}
