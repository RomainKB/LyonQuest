package com.example.lyonquest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
 * Created by romaink on 03/05/2019.
 *
 * Activity where the user try to answer to a textual riddle.
 *
 */
public class DisplayTextRiddle extends AppCompatActivity implements View.OnClickListener{
    /**
     * The text view where we display the riddle title
     */
    private TextView mTitle;
    /**
     * The text view where we display the riddle description
     */
    private TextView mDescription;
    /**
     * The edit text where the user can enter the answer
     */
    private EditText mAnswer;

    /**
     * The button to check the answer.
     */
    private Button mCheck;
    /**
     * The button to give up the route.
     */
    private Button mGiveUp;
    /**
     * The actual textual riddle show to the player
     */
    private TextualRiddle riddle;
    /**
     * The actual route played by the player.
     */
    private Route route;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.SHARED_PREFS), MODE_PRIVATE);
        int choice = Integer.parseInt(sharedPreferences.getString(getString(R.string.set_theme), "1"));
        Utils.onActivityCreateSetTheme(this,choice);
        setContentView(R.layout.activity_display_text_riddle);

        mTitle = (TextView) findViewById(R.id.activity_display_title);
        mDescription = (TextView) findViewById(R.id.activity_display_description);
        mAnswer = (EditText) findViewById(R.id.riddle_text_answer);
        mCheck = (Button) findViewById(R.id.check_button);
        mGiveUp = (Button) findViewById(R.id.give_up_button);

        mCheck.setTag(0);
        mGiveUp.setTag(1);
        mCheck.setOnClickListener(this);
        mGiveUp.setOnClickListener(this);

        riddle = (TextualRiddle) getIntent().getSerializableExtra(getString(R.string.riddle));
        route = (Route) getIntent().getSerializableExtra(getString(R.string.route));

        mTitle.setText(riddle.getmTitle());
        mDescription.setText(riddle.getmDescription());

    }

    @Override
    public void onClick(View v) {
        int responseIndex = (int) v.getTag();

        switch(responseIndex) {
            case 0:

                final String email = SharedPrefs.readSharedSetting(DisplayTextRiddle.this, getString(R.string.email), null);

                String solution = mAnswer.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                JSONObject json = new JSONObject();
                try {
                    json.put(getString(R.string.route_id),route.getmId());
                    json.put(getString(R.string.email),email);
                    json.put(getString(R.string.db_key_textual_solution),solution);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String url = getString(R.string.db_verification);
                System.out.println(json);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

                                    String status = response.getString(getString(R.string.db_status));
                                    if(status.equals(getString(R.string.db_success))){

                                        String finished = response.getString(getString(R.string.db_end));

                                        if(finished.equals(getString(R.string.db_fini))){
                                            //Case where the user finished the route
                                            Intent intent0 = new Intent(DisplayTextRiddle.this, RouteFeedback.class);
                                            Bundle bundle0 = new Bundle();
                                            bundle0.putSerializable(getString(R.string.route),route);
                                            intent0.putExtras(bundle0);
                                            startActivity(intent0);
                                        }else {
                                            //Case where the user validate the riddle but didn't finished the route.
                                            String type = response.getString(getString(R.string.db_key_type));

                                            switch (type){
                                                case "password":
                                                    Intent intent1 = new Intent(DisplayTextRiddle.this, DisplayTextRiddle.class);
                                                    TextualRiddle r1 = new TextualRiddle("Enigme", response.getString(getString(R.string.db_key_description)),"" );
                                                    Bundle bundle1 = new Bundle();
                                                    bundle1.putSerializable(getString(R.string.riddle),r1);
                                                    bundle1.putSerializable(getString(R.string.route),route);
                                                    intent1.putExtras(bundle1);
                                                    startActivity(intent1);
                                                    break;

                                                case "geocoords":
                                                    Intent intent2 = new Intent(DisplayTextRiddle.this, DisplayDestinationRiddle.class);
                                                    DestinationRiddle r2 = new DestinationRiddle("Enigme", response.getString(getString(R.string.db_key_description)),0.0,0.0);
                                                    Bundle bundle2 = new Bundle();
                                                    bundle2.putSerializable(getString(R.string.riddle),r2);
                                                    bundle2.putSerializable(getString(R.string.route),route);
                                                    intent2.putExtras(bundle2);
                                                    startActivity(intent2);
                                                    break;

                                                case "picture":
                                                    Intent intent3 = new Intent(DisplayTextRiddle.this, DisplayPictureRiddle.class);
                                                    PictureRiddle r3 = new PictureRiddle("Enigme", response.getString(getString(R.string.db_key_description)));
                                                    Bundle bundle3 = new Bundle();
                                                    bundle3.putSerializable(getString(R.string.riddle),r3);
                                                    bundle3.putSerializable(getString(R.string.route),route);
                                                    intent3.putExtras(bundle3);
                                                    startActivity(intent3);
                                                    break;

                                                case "dest_pict":
                                                    Intent intent4 = new Intent(DisplayTextRiddle.this, DisplayDestPictRiddle.class);
                                                    DestPictRiddle r4 = new DestPictRiddle("Enigme", response.getString(getString(R.string.db_key_description)),0.0,0.0);
                                                    Bundle bundle4 = new Bundle();
                                                    bundle4.putSerializable(getString(R.string.riddle),r4);
                                                    bundle4.putSerializable(getString(R.string.route),route);
                                                    intent4.putExtras(bundle4);
                                                    startActivity(intent4);
                                                    break;
                                            }

                                        }
                                    }else{
                                        //Case where the user gave a false answer

                                        Context context = getApplicationContext();
                                        CharSequence text = getString(R.string.answer_false);
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
                queue.add(jsonObjectRequest);

                break;

            case 1:
                Intent intent = new Intent(DisplayTextRiddle.this, MainActivity.class);
                startActivity(intent);
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
