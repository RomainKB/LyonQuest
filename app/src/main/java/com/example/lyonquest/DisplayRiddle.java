package com.example.lyonquest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DisplayRiddle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_display_riddle);
    }
}
