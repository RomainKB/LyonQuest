package com.example.lyonquest;

import java.io.Serializable;

/**
 * Created by romaink on 01/05/2019.
 *
 * This class is the route class.
 */
public class Route implements Serializable {

    /**
     * The name of the route.
     */
    public String mName;
    /**
     * The route description.
     */
    public String mDescription;
    /**
     * The place where start the route.
     */
    public String mStart;
    /**
     * The score of the route.
     */
    public int mNote;
    /**
     * The average time to realise the route.
     */
    public int mLength;

    /**
     * Constructor of the class.
     * @param mName The name of the route.
     * @param mDescription The route description.
     * @param mStart The place whhere start the route.
     * @param mNote The note given by other users.
     * @param mLength The average length of the route
     */
    public Route(String mName, String mDescription, String mStart, int mNote, int mLength) {
        this.setmName(mName);
        this.setmDescription(mDescription);
        this.setmStart(mStart);
        this.setmNote(mNote);
        this.setmLength(mLength);
    }

    /*-----------------Getter et setter--------------------------------- */
    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmLength() {
        return mLength;
    }

    public void setmLength(int mLength) {
        this.mLength = mLength;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmStart() {
        return mStart;
    }

    public void setmStart(String mStart) {
        this.mStart = mStart;
    }

    public int getmNote() {
        return mNote;
    }

    public void setmNote(int mNote) {
        this.mNote = mNote;
    }
}