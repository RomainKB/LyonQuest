package com.example.lyonquest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by romaink on 01/05/2019.
 *
 * Object which represent textual riddle.
 */
public class TextualRiddle extends Riddle {

    /**
     * The solution of the riddle
     */
    private String mSolution;

    /**
     * Constructor of the class.
     * @param mSolution The riddle solution.
     */
    public TextualRiddle(String mTitle, String mDescription, String mSolution) {
        super(mTitle,mDescription);
        this.setmSolution(mSolution);
    }

    public JSONObject toJSON(){
        JSONObject json = new JSONObject();

        try {
            json.put("text", mDescription);
            json.put("solution", mSolution);
            json.put("type","password");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    /*-----------------Getter et setter--------------------------------- */

    public String getmSolution() {
        return mSolution;
    }

    public void setmSolution(String mSolution) {
        this.mSolution = mSolution;
    }


}
