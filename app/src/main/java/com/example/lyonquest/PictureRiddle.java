package com.example.lyonquest;

import org.json.JSONException;
import org.json.JSONObject;

public class PictureRiddle extends Riddle {


    private String label;
    private boolean custom;
    /**
     * Constructor of the class.
     *
     */
    public PictureRiddle(String mTitle, String mDescription) {
        super(mTitle,mDescription);

    }

    public PictureRiddle(String mTitle, String mDescription, String label, boolean custom) {
        super(mTitle,mDescription);
        this.label = label;
        this.custom = custom;
    }

    public JSONObject toJSON(){
        JSONObject json = new JSONObject();

        try {
            json.put("text", mDescription);
            json.put("label", label);
            json.put("type","picture");
            if(custom){
                json.put("method","custom");
            }else{
                json.put("method","google");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return json;
    }



}
