package com.example.lyonquest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class UserProfil extends AppCompatActivity {

    /**
     * The username of the user
     */
    private TextView mUsername;
    /**
     * The email of the user
     */
    private TextView mEmail;
    /**
     * The rank of the user
     */
    private TextView mRank;
    /**
     * The number of routes that the user did
     */
    private TextView mRouteNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.SHARED_PREFS), MODE_PRIVATE);
        int choice = Integer.parseInt(sharedPreferences.getString(getString(R.string.set_theme), "1"));
        Utils.onActivityCreateSetTheme(this,choice);
        setContentView(R.layout.activity_user_profil);

        mUsername = (TextView) findViewById(R.id.activity_display_username);
        mEmail = (TextView) findViewById(R.id.activity_display_email);
        mRank = (TextView) findViewById(R.id.activity_display_rank);
        mRouteNumber = (TextView) findViewById(R.id.activity_display_route_numbers);


        String email = SharedPrefs.readSharedSetting(this, getString(R.string.email), null);
        mEmail.setText(email);

         /*RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                JSONObject json = new JSONObject();
                try {
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
                                    String username = response.getString(getString(R.string.db_key_username));
                                    String rank = response.getString(getString(R.string.db_key_rank));
                                    String routeNumber = response.getString(getString(R.string.db_key_route_number));


                                    mUsername.setText(username);
                                    mRank.setText(rank);
                                    mRouteNumber.setText(routeNumber);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("ERROR : "+error);
                    }
                });
                queue.add(jsonObjectRequest);*/
    }
}
