package com.example.lyonquest;

import java.io.Serializable;

/**
 * Created by romaink on 01/05/2019.
 *
 * This class is the route class.
 */
public class Route implements Serializable {

    /**
     * The id of the route.
     */
    public int mId;
    /**
     * The name of the route.
     */
    public String mName;
    /**
     * The route description.
     */
    public String mDescription;
    /**
     * The score of the route.
     */
    public int mNote;
    /**
     * The number of people who finished the route.
     */
    public int mNbTimeFinished;
    /**
     * The number of people who abandoned the route.
     */
    public int mNbTimeAbandoned;
    /**
     * The average time to realise the route.
     */
    public int mLength;



    /**
     * Constructor of the class.
     * @param mId The id of the route.
     * @param mName The name of the route.
     * @param mDescription The route description.
     * @param mNote The note given by other users.
     * @param mLength The average length of the route.
     * @param mNbTimeFinished The number of people who had finished the route.
     * @param mNbTimeAbandoned The number of people who had abandoned the route.
     */
    public Route(int mId, String mName, String mDescription, int mNote, int mLength, int mNbTimeFinished, int mNbTimeAbandoned ) {
        this.setmName(mName);
        this.setmDescription(mDescription);
        this.setmNote(mNote);
        this.setmLength(mLength);
        this.setmNbTimeFinished(mNbTimeFinished);
        this.setmNbTimeAbandoned(mNbTimeAbandoned);
        this.setmId(mId);
    }

    /*-----------------Getter et setter--------------------------------- */

    public int getmId() { return mId; }

    public void setmId(int mId) { this.mId = mId; }

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

    public int getmNote() {
        return mNote;
    }

    public void setmNote(int mNote) {
        this.mNote = mNote;
    }

    public int getmNbTimeFinished() { return mNbTimeFinished; }

    public void setmNbTimeFinished(int mNbTimeFinished) { this.mNbTimeFinished = mNbTimeFinished; }

    public int getmNbTimeAbandoned() { return mNbTimeAbandoned; }

    public void setmNbTimeAbandoned(int mNbTimeAbandoned) { this.mNbTimeAbandoned = mNbTimeAbandoned; }
}
