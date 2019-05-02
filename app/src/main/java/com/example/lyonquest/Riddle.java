package com.example.lyonquest;

import java.io.Serializable;

/**
 * Created by romaink on 02/05/2019.
 *
 * This class is the riddle class.
 */
public class Riddle implements Serializable {

    /**
     * The title of the riddle
     */
    private String mTitle;
    /**
     * The description of the riddle
     */
    private String mDescription;


    /**
     * Constructor of the class.
     * @param mTitle The name of the riddle.
     * @param mDescription The riddle description.
     */
    public Riddle(String mTitle, String mDescription) {
        this.setmTitle(mTitle);
        this.setmDescription(mDescription);
    }

    /*-----------------Getter et setter--------------------------------- */

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }


}
