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

public class DisplayDestinationRiddle extends AppCompatActivity implements View.OnClickListener{
    /**
     * The text view where we display the riddle title
     */
    private TextView mTitle;
    /**
     * The text view where we display the riddle description
     */
    private TextView mDescription;


    /**
     * The button to check the answer.
     */
    private Button mCheck;
    /**
     * The button to give up the route.
     */
    private Button mGiveUp;

    private Riddle riddle;
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
        mCheck = (Button) findViewById(R.id.check_button);
        mGiveUp = (Button) findViewById(R.id.give_up_button);

        mCheck.setTag(0);
        mGiveUp.setTag(1);
        mCheck.setOnClickListener(this);
        mGiveUp.setOnClickListener(this);

        riddle = (Riddle) getIntent().getSerializableExtra(getString(R.string.riddle));
        route = (Route) getIntent().getSerializableExtra(getString(R.string.route));

        mTitle.setText(riddle.getmTitle());
        mDescription.setText(riddle.getmDescription());

    }

    @Override
    public void onClick(View v) {
        int responseIndex = (int) v.getTag();

        switch(responseIndex) {
            case 0:

                final String email = SharedPrefs.readSharedSetting(DisplayDestinationRiddle.this, getString(R.string.email), null);
                //TODO : il faut récupérer la latitude et longitude !
                String latitude ="";
                String longitude ="";

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                JSONObject json = new JSONObject();
                try {
                    json.put(getString(R.string.route_id),route.getmId());
                    json.put(getString(R.string.email),email);
                    json.put(getString(R.string.db_key_latitude),latitude);
                    json.put(getString(R.string.db_key_longitude),longitude);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //TODO : Penser à rentrer le bon url
                String url = getString(R.string.db_verification);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

                                    String aJsonString = response.getString(getString(R.string.db_status));
                                    if(aJsonString.equals(getString(R.string.db_success))){

                                        String end = response.getString(getString(R.string.db_end));

                                        if(end.equals(getString(R.string.db_fini))){
                                            //Cas ou l'utilisateur a fini le parcours
                                            Intent intent = new Intent(DisplayDestinationRiddle.this, RouteFeedback.class);
                                            startActivity(intent);
                                        }else {
                                            //Cas ou l'utilisateur a valider l'énigme mais pas fini le parcours
                                            String type = response.getString(getString(R.string.db_key_type));

                                            switch (type){
                                                case "password":
                                                    Intent intent1 = new Intent(DisplayDestinationRiddle.this, DisplayTextRiddle.class);
                                                    JSONObject rid = new JSONObject(response.getString("riddle"));
                                                    TextualRiddle r1 = new TextualRiddle("Enigme", response.getString(getString(R.string.db_key_description)),"" );
                                                    Bundle bundle1 = new Bundle();
                                                    bundle1.putSerializable(getString(R.string.riddle),r1);
                                                    bundle1.putSerializable(getString(R.string.route),route);
                                                    intent1.putExtras(bundle1);
                                                    startActivity(intent1);
                                                    break;

                                                case "destination":
                                                   /*Intent intent2 = new Intent(DisplayDestinationRiddle.this, DisplayDestinationRiddle.class);
                                                    DestinationRiddle r2 = new DestinationRiddle("Enigme", response.getString(getString(R.string.db_key_description)),"","");
                                                    Bundle bundle2 = new Bundle();
                                                    bundle2.putSerializable(getString(R.string.riddle),r2);
                                                    bundle2.putSerializable(getString(R.string.route),route);
                                                    intent2.putExtras(bundle2);
                                                    startActivity(intent2);*/
                                                    break;
                                            }

                                        }
                                    }else{
                                        //Cas ou l'utilisateur a donné une mauvaise réponse
                                        Context context = getApplicationContext();
                                        CharSequence text = "La réponse n'est pas correct, veuillez ressayer.";
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
                Intent intent = new Intent(DisplayDestinationRiddle.this, MainActivity.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        Context context = getApplicationContext();
        CharSequence text = "Vous ne pouvez pas revenir en arrière désolé. Veuillez abandonner si vous souhaitez quitter le parcours.";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}
