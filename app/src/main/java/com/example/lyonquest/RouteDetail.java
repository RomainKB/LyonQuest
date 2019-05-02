package com.example.lyonquest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
    private TextView mStart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
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
               /* RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                JSONObject json = new JSONObject();
                try {
                    json.put(getString(R.string.route_id),route.getmId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //TODO : Penser à rentrer le bon url
                String url = getString(R.string.db_login_url);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String aJsonString = response.getString(getString(R.string.db_status));
                                    if(aJsonString.equals(getString(R.string.db_success))){
                                        Intent intent = new Intent(RouteDetail.this, DisplayRiddle.class);
                                        //TODO : récupérer l'énigme envoyé par le serveur pour la passer à la prochaine activité

                                        startActivity(intent);
                                    }else{
                                        Context context = getApplicationContext();
                                        CharSequence text = "Problème de connexion avec le serveur";
                                        int duration = Toast.LENGTH_LONG;

                                        Toast toast = Toast.makeText(context, text, duration);
                                        toast.show();

                                    }
                                }catch(JSONException e){e.printStackTrace();}
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("ERROR : "+error);
                    }
                });
                queue.add(jsonObjectRequest); */

                Intent intent = new Intent(RouteDetail.this, DisplayRiddle.class);
                startActivity(intent);
                break;
        }
    }
}
