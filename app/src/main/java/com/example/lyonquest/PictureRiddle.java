package com.example.lyonquest;

import org.json.JSONException;
import org.json.JSONObject;

public class PictureRiddle extends Riddle {


    /**
     * Constructor of the class.
     *
     */
    public PictureRiddle(String mTitle, String mDescription) {
        super(mTitle,mDescription);

    }

    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        return json;
    }



}
