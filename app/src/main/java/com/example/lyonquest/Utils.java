package com.example.lyonquest;

import android.app.Activity;
import android.content.Intent;

/**
 * This class is here to set the activity theme of the application.
 */
public class Utils {

    /**
     * The theme index chose by the user on clicking on the corresponding button.
     */
    private static int sTheme;
    /**
     * Index of each theme.
     */
    public final static int THEME_ONE = 0;
    public final static int THEME_TWO = 1;
    public final static int THEME_THREE = 2;

    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     */
    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }


    /**
     * Set the theme of the activity, according to the configuration.
     */
    public static void onActivityCreateSetTheme(Activity activity, int choice) {

        switch (choice-1) {
            default:
            case THEME_ONE:
                activity.setTheme(R.style.FirstTheme);
                break;
            case THEME_TWO:
                activity.setTheme(R.style.SecondTheme);
                break;
            case THEME_THREE:
                activity.setTheme(R.style.Thirdheme);
                break;
        }
    }
}

