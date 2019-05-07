package com.example.lyonquest;

import org.json.JSONException;
import org.json.JSONObject;

public class DestinationRiddle extends Riddle {

    /**
     * The solution of the riddle
     */
    private double latitude;
    private double longitude;
    private double delta;

    /**
     * Constructor of the class.
     */
    public DestinationRiddle(String mTitle, String mDescription, double latitude, double longitude, double delta) {
        super(mTitle,mDescription);
        this.latitude = latitude;
        this.longitude = longitude;
        this.delta = delta;
    }



    public JSONObject toJSON(){
        JSONObject json = new JSONObject();

        try {
            json.put("text", mDescription);
            json.put("latitude", latitude);
            json.put("longitude", longitude);
            json.put("delta", delta);
            json.put("type","geocoords");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }
}
