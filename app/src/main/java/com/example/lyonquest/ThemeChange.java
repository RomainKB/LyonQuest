package com.example.lyonquest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ThemeChange extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_change);

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.SHARED_PREFS), MODE_PRIVATE);
        int choice = Integer.parseInt(sharedPreferences.getString(getString(R.string.set_theme), "1"));
        Utils.onActivityCreateSetTheme(this,choice);
        setContentView(R.layout.activity_theme_change);

        Button button =(Button)findViewById(R.id.activity_theme_choice_button1);
        Button button1 =(Button)findViewById(R.id.activity_theme_choice_button2);
        Button button2 =(Button)findViewById(R.id.activity_theme_choice_button3);
        button.setTag(0);
        button1.setTag(1);
        button2.setTag(2);

        button.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.SHARED_PREFS), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (v.getId()) {
            case R.id.activity_theme_choice_button1:
                editor.putString(getString(R.string.set_theme), "1");
                editor.apply();
                Utils.changeToTheme(this, Utils.THEME_ONE);
                break;
            case R.id.activity_theme_choice_button2:
                editor.putString(getString(R.string.set_theme), "2");
                editor.apply();
                Utils.changeToTheme(this, Utils.THEME_TWO);
                break;
            case R.id.activity_theme_choice_button3:
                editor.putString(getString(R.string.set_theme), "3");
                editor.apply();
                Utils.changeToTheme(this, Utils.THEME_THREE);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent back = new Intent(ThemeChange.this, MainActivity.class);
        startActivity(back);
    }
}
