package com.example.lyonquest;

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

    public String getmSolution() {
        return mSolution;
    }

    public void setmSolution(String mSolution) {
        this.mSolution = mSolution;
    }
}
