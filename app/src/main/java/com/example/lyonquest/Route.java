package com.example.lyonquest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by romaink on 01/05/2019.
 *
 * This class is the route class.
 */
public class Route implements Serializable {

    /**
     * The id of the route.
     */
    private int mId;
    /**
     * The name of the route.
     */
    private String mName;
    /**
     * The route description.
     */
    private String mDescription;
    /**
     * The score of the route.
     */
    private int mNote;
    /**
     * The number of people who finished the route.
     */
    private int mNbTimeFinished;
    /**
     * The number of people who abandoned the route.
     */
    private int mNbTimeAbandoned;
    /**
     * The average time to realise the route.
     */
    private int mLength;

    private ArrayList<Riddle> riddles;

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
        this.riddles = new ArrayList<>();
    }

    public Route(){
        this.mId = 0;
        this.mName = null;
        this.mDescription = null;
        this.mNote = 0;
        this.mLength = 0;
        this.mNbTimeAbandoned = 0;
        this.mNbTimeFinished = 0;
        this.riddles = new ArrayList<>();
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

    public ArrayList<Riddle> getRiddles() {
        return riddles;
    }

    public void setRiddles(ArrayList<Riddle> riddles) {
        this.riddles = riddles;
    }
}
