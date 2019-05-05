package com.example.lyonquest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RouteDetail extends AppCompatActivity implements View.OnClickListener{

    private Route route;
    /**
     * The text view where we display the route title
     */
    private TextView mTitle;
    /**
     * The text view where we display the route description
     */
    private TextView mDescription;
    /**
     * The button to start the route.
     */
    private Button mStart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.SHARED_PREFS), MODE_PRIVATE);
        int choice = Integer.parseInt(sharedPreferences.getString(getString(R.string.set_theme), "1"));
        Utils.onActivityCreateSetTheme(this,choice);
        setContentView(R.layout.activity_route_detail);

        route = (Route) getIntent().getSerializableExtra(getString(R.string.route));

        mTitle = (TextView) findViewById(R.id.activity_display_title);
        mDescription = (TextView) findViewById(R.id.activity_display_description);
        mStart = (Button) findViewById(R.id.start_route_button);

        mTitle.setText(route.getmName());
        mDescription.setText(route.getmDescription());
        mStart.setTag(0);
        mStart.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int responseIndex = (int) v.getTag();

        switch(responseIndex) {
            case 0:
                final String email = SharedPrefs.readSharedSetting(RouteDetail.this, getString(R.string.email), null);

               /* RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                JSONObject json = new JSONObject();
                try {
                    json.put(getString(R.string.route_id),route.getmId());
                    json.put(getString(R.string.email),email);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //TODO : Penser à rentrer le bon url
                String url = getString(R.string.db_start_route);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    System.out.println(response);
                                    Intent intent = new Intent(RouteDetail.this, DisplayTextRiddle.class);
                                    String type = response.getString(getString(R.string.db_key_type));
                                    switch (type){
                                        case "password":
                                            TextualRiddle r1 = new TextualRiddle("Enigme", response.getString(getString(R.string.db_key_description)),"" );
                                            Bundle bundle = new Bundle();
                                            bundle.putSerializable(getString(R.string.riddle),r1);
                                            System.out.println(r1.getmDescription());
                                            bundle.putSerializable(getString(R.string.route),route);
                                            intent.putExtras(bundle);
                                            break;
                                    }
                                        startActivity(intent);

                                }catch(JSONException e){e.printStackTrace();}
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("ERROR : "+error);
                    }
                });
                queue.add(jsonObjectRequest);*/

                Intent intent = new Intent(RouteDetail.this, DisplayTextRiddle.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent back = new Intent(RouteDetail.this, MainActivity.class);
        startActivity(back);
    }
}
