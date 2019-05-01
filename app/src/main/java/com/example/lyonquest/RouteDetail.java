package com.example.lyonquest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class RouteDetail extends AppCompatActivity {

    private Route route;
    /**
     * The text view where we display the route title
     */
    private TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_route_detail);

        mTitle = (TextView) findViewById(R.id.activity_display_title);

        route = (Route) getIntent().getSerializableExtra(getString(R.string.route));
        mTitle.setText(route.getmName());

    }
}
