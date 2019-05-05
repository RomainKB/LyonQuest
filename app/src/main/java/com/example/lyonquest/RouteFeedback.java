package com.example.lyonquest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RouteFeedback extends AppCompatActivity implements View.OnClickListener {

    /**
     * The edit text where the user can enter the comment
     */
    private EditText mComment;
    /**
     * The ratingBar to give the note
     */
    private RatingBar mRatingBar;
    /**
     * The button to validate his feedback
     */
    private Button mValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.SHARED_PREFS), MODE_PRIVATE);
        int choice = Integer.parseInt(sharedPreferences.getString(getString(R.string.set_theme), "1"));
        Utils.onActivityCreateSetTheme(this,choice);
        setContentView(R.layout.activity_route_feedback);

        mComment = (EditText) findViewById(R.id.activity_end_route_comment_input_field);
        mRatingBar = (RatingBar) findViewById(R.id.ratingBar);
        mValidation = (Button) findViewById(R.id.activity_end_route_validation);

        mValidation.setTag(0);
        mValidation.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int responseIndex = (int) v.getTag();

        switch(responseIndex) {
            case 0:

                /*RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                JSONObject json = new JSONObject();
                try {
                    json.put(getString(R.string.db_key_end_comment),mComment.getText().toString());
                    json.put(getString(R.string.db_key_route_note),String.valueOf(mRatingBar.getRating()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //TODO : Penser à rentrer le bon url
                String url = getString(R.string.db_verification);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                               //TODO: voir avec taki ce que le service retourne
                                Intent intent = new Intent(RouteFeedback.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("ERROR : "+error);
                    }
                });
                queue.add(jsonObjectRequest);*/

                Intent intent = new Intent(RouteFeedback.this, MainActivity.class);
                startActivity(intent);
                break;
        }

    }
}