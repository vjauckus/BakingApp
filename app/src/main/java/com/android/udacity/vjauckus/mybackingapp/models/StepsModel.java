package com.android.udacity.vjauckus.mybackingapp.models;

import java.io.Serializable;

/**
 * Created by veronika on 30.01.18.
 */

public class StepsModel implements Serializable {

    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;


    @Override
    public String toString() {
        return "Short description: "+ this.shortDescription+ " id: "+ this.id;
    }


    public String getShortDescription(){
        return this.shortDescription;
    }
    public String getLongDescription(){
        return this.description;
    }

    public String getVideoURL(){
        return this.videoURL;
    }

    public String getThumbnailURL(){return this.thumbnailURL;}

}
