package com.example.lyonquest;

import org.json.JSONObject;

public class DestPictRiddle extends Riddle {

    /**
     * The solution of the riddle.
     */
    private double mLatitude;
    private double mLongitude;


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

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
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