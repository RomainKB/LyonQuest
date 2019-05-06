package com.example.lyonquest;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by romaink on 30/04/2019.
 *
 * This class is used to save to the user sharepreferences somme informations.
 */
public class SharedPrefs {

    final static String FileName = "UserInformations";

    public static String readSharedSetting(Context ctx, String settingEmail, String defaultValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        return sharedPref.getString(settingEmail, defaultValue);
    }

    public static void saveSharedSetting(Context ctx, String user, String settingValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(user, settingValue);
        editor.apply();
    }

}
