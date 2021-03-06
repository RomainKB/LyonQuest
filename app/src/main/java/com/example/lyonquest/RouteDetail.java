package com.example.lyonquest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by romaink on 02/05/2019.
 *
 * Activity where the user can see details about the route he has chosen.
 *
 */
public class RouteDetail extends AppCompatActivity implements View.OnClickListener{
    /**
     * The actual route selected by the user
     */
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
     * The text view where we display the route score
     */
    private TextView mScore;
    /**
     * The text view where we display the route nb finished
     */
    private TextView mFinished;
    /**
     * The button to start the route.
     */
    private Button mStart;

    private List<String> mComments = new ArrayList<>();
    private List<String> mNotes = new ArrayList<>();
    private List<String> mEmails = new ArrayList<>();


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
        mScore = (TextView) findViewById(R.id.activity_display_score);
        mFinished = (TextView) findViewById(R.id.activity_display_nb_finished);
        mStart = (Button) findViewById(R.id.start_route_button);

        mTitle.setText(route.getmName());
        mDescription.setText(route.getmDescription());
        mScore.setText("Score : "+ Integer.toString(route.getmNote()));
        mFinished.setText("Nombre de fois fini : "+ Integer.toString(route.getmNbTimeFinished()));

        mStart.setTag(0);
        mStart.setOnClickListener(this);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();
        try {
            json.put(getString(R.string.route_id),route.getmId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getString(R.string.db_route_comments);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = new JSONArray(response.getString("comments"));
                            for (int i = 0; i < array.length(); i++) {
                                // On récupère un objet JSON du tableau
                                JSONObject obj = new JSONObject(array.getString(i));
                                mComments.add(obj.getString("comment"));
                                mNotes.add(obj.getString("score"));
                                mEmails.add(obj.getString("email"));
                            }
                            construction();
                        }catch(JSONException e){e.printStackTrace();}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR : "+error);
            }
        });
        queue.add(jsonObjectRequest);


    }

    @Override
    public void onClick(View v) {
        int responseIndex = (int) v.getTag();

        switch(responseIndex) {
            case 0:
                final String email = SharedPrefs.readSharedSetting(RouteDetail.this, getString(R.string.email), null);

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                JSONObject json = new JSONObject();
                try {
                    json.put(getString(R.string.route_id),route.getmId());
                    json.put(getString(R.string.email),email);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String url = getString(R.string.db_start_route);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

                                    String type = response.getString(getString(R.string.db_key_type));
                                    switch (type){

                                        case "password":
                                            Intent intent1 = new Intent(RouteDetail.this, DisplayTextRiddle.class);
                                            TextualRiddle r1 = new TextualRiddle("Enigme", response.getString(getString(R.string.db_key_description)),"" );
                                            Bundle bundle1 = new Bundle();
                                            bundle1.putSerializable(getString(R.string.riddle),r1);
                                            bundle1.putSerializable(getString(R.string.route),route);
                                            intent1.putExtras(bundle1);
                                            startActivity(intent1);
                                            break;

                                         case "geocoords":
                                            Intent intent2 = new Intent(RouteDetail.this, DisplayDestinationRiddle.class);
                                            DestinationRiddle r2 = new DestinationRiddle("Enigme", response.getString(getString(R.string.db_key_description)),0.0,0.0);
                                            Bundle bundle2 = new Bundle();
                                            bundle2.putSerializable(getString(R.string.riddle),r2);
                                            bundle2.putSerializable(getString(R.string.route),route);
                                            intent2.putExtras(bundle2);
                                            startActivity(intent2);
                                            break;

                                        case "picture":
                                            Intent intent3 = new Intent(RouteDetail.this, DisplayPictureRiddle.class);
                                            PictureRiddle r3 = new PictureRiddle("Enigme", response.getString(getString(R.string.db_key_description)));
                                            Bundle bundle3 = new Bundle();
                                            bundle3.putSerializable(getString(R.string.riddle),r3);
                                            bundle3.putSerializable(getString(R.string.route),route);
                                            intent3.putExtras(bundle3);
                                            startActivity(intent3);
                                            break;

                                        case "dest_pict":
                                            Intent intent4 = new Intent(RouteDetail.this, DisplayDestPictRiddle.class);
                                            DestPictRiddle r4 = new DestPictRiddle("Enigme", response.getString(getString(R.string.db_key_description)),0.0,0.0);
                                            Bundle bundle4 = new Bundle();
                                            bundle4.putSerializable(getString(R.string.riddle),r4);
                                            bundle4.putSerializable(getString(R.string.route),route);
                                            intent4.putExtras(bundle4);
                                            startActivity(intent4);
                                            break;
                                    }

                                }catch(JSONException e){e.printStackTrace();}
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("ERROR : "+error);
                    }
                });
                queue.add(jsonObjectRequest);

                break;
        }
    }

    private void construction(){
        /*Get back the layout*/
        LinearLayout monLayout = (LinearLayout) findViewById(R.id.activity_display_routes_layoutOfDynamicContent);

        // From here add 1 by 1 all comment
        int i=0;
        for ( i = 0; i<mNotes.size(); i++){



            TextView noteEmail = new TextView(this);
            noteEmail.setText("Note de "+mNotes.get(i)+" donnée par "+mEmails.get(i));
            noteEmail.setTextSize(20);
            monLayout.addView(noteEmail,0);

            TextView comment = new TextView(this);
            comment.setText(mComments.get(i));
            comment.setTextSize(20);
            monLayout.addView(comment,0);

            //Just to create a space between buttons
            TextView espace = new TextView(this);
            espace.setHeight(30);
            monLayout.addView(espace,0);
        }


    }

    @Override
    public void onBackPressed() {
        Intent back = new Intent(RouteDetail.this, MainActivity.class);
        startActivity(back);
    }
}
