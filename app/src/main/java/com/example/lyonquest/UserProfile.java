package com.example.lyonquest;

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

/**
 * Created by romaink on 05/05/2019.
 *
 * Activity where the user can see details about his profile.
 *
 */
public class UserProfile extends AppCompatActivity {

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
     * The score that the user reach (addition of all points he wins when he finished a route).
     */
    private TextView mScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.SHARED_PREFS), MODE_PRIVATE);
        int choice = Integer.parseInt(sharedPreferences.getString(getString(R.string.set_theme), "1"));
        Utils.onActivityCreateSetTheme(this,choice);
        setContentView(R.layout.activity_user_profile);

        mUsername = (TextView) findViewById(R.id.activity_display_username);
        mEmail = (TextView) findViewById(R.id.activity_display_email);
        mRank = (TextView) findViewById(R.id.activity_display_rank);
        mScore = (TextView) findViewById(R.id.activity_display_score);

        String email = SharedPrefs.readSharedSetting(this, getString(R.string.email), null);
        mEmail.setText(email);

         RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                JSONObject json = new JSONObject();
                try {
                    json.put(getString(R.string.email),email);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String url = getString(R.string.db_user_profile);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String username = response.getString(getString(R.string.db_key_username));
                                    String rank = response.getString(getString(R.string.db_key_rank));
                                    String score = response.getString(getString(R.string.db_key_score));

                                    mUsername.setText(username);
                                    mRank.setText("Classemennt : "+rank);
                                    mScore.setText("Score : "+score);
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
                queue.add(jsonObjectRequest);
    }
}
