package com.example.lyonquest;

import org.json.JSONException;
import org.json.JSONObject;

public class DestPictRiddle extends Riddle {

    /**
     * The solution of the riddle.
     */
    private double mLatitude;
    private double mLongitude;

    private double delta;
    private String label;
    private boolean custom;


    /**
     * Constructor of the class.
     *
     * @param latitude  The latitude of the solution
     * @param longitude The latitude of the solution
     */
    public DestPictRiddle(String mTitle, String mDescription, double latitude, double longitude) {
        super(mTitle, mDescription);
        this.setmLatitude(latitude);
        this.setmLongitude(longitude);
    }

    public DestPictRiddle(String mTitle, String mDescription, double latitude, double longitude, double delta, String label, boolean custom) {
        super(mTitle, mDescription);
        this.setmLatitude(latitude);
        this.setmLongitude(longitude);
        this.delta = delta;
        this.custom = custom;
        this.label = label;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        try {
            json.put("text", mDescription);
            json.put("latitude", mLatitude);
            json.put("longitude", mLongitude);
            json.put("delta", delta);
            json.put("type","destpict");
            json.put("text", mDescription);
            json.put("solution", label);
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

    /*-----------------Getter et setter--------------------------------- */

    public double getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }
}