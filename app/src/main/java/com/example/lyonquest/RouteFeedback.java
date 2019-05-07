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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by romaink on 04/05/2019.
 *
 * Activity where the user can say his feedback about the route he just finished.
 *
 */
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
    /**
     * The route finished by the user
     */
    private Route route;

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

        mValidation.setTag(10);
        mValidation.setOnClickListener(this);

        route = (Route) getIntent().getSerializableExtra(getString(R.string.route));

    }

    @Override
    public void onClick(View v) {
        int responseIndex = (int) v.getTag();

        switch(responseIndex) {
            case 10:

                final String email = SharedPrefs.readSharedSetting(RouteFeedback.this, getString(R.string.email), null);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                JSONObject json = new JSONObject();
                try {
                    json.put(getString(R.string.db_key_end_comment),mComment.getText().toString());
                    json.put(getString(R.string.db_key_route_score),String.valueOf(mRatingBar.getRating()));
                    json.put(getString(R.string.email),email);
                    json.put(getString(R.string.route_id),route.getmId());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String url = getString(R.string.db_feedback);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Intent intent = new Intent(RouteFeedback.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("ERROR : "+error);
                    }
                });
                queue.add(jsonObjectRequest);

               /* Intent intent = new Intent(RouteFeedback.this, MainActivity.class);
                startActivity(intent);*/
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Context context = getApplicationContext();
        CharSequence text = getString(R.string.onBackPressed_forbiden);
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
