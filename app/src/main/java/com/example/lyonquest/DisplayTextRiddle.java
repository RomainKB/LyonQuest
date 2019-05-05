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
    
    private List<TextualRiddle> mRiddle = new ArrayList<>();

    //TODO : a retirer quand le serveur nous donnera l'énigme
    public static int enigmeNum = 0;

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
        mAnswer = (EditText) findViewById(R.id.riddle_text_answer);
        mCheck = (Button) findViewById(R.id.check_button);
        mGiveUp = (Button) findViewById(R.id.give_up_button);

        mCheck.setTag(0);
        mGiveUp.setTag(1);
        mCheck.setOnClickListener(this);
        mGiveUp.setOnClickListener(this);



       /* riddle = (Riddle) getIntent().getSerializableExtra(getString(R.string.riddle));
        route = (Route) getIntent().getSerializableExtra(getString(R.string.route));*/


        // TODO : Delete this function call when we receive information from the server
        riddlelist();
        //TODO : récupérer l'énigme passée par le intent.
        mTitle.setText(mRiddle.get(enigmeNum).getmTitle());
        mDescription.setText(mRiddle.get(enigmeNum).getmDescription());


       /* mTitle.setText(riddle.getmTitle());
        mDescription.setText(riddle.getmDescription());*/

    }

    @Override
    public void onClick(View v) {
        int responseIndex = (int) v.getTag();

        switch(responseIndex) {
            case 0:

                final String email = SharedPrefs.readSharedSetting(DisplayTextRiddle.this, getString(R.string.email), null);


                if(enigmeNum == mRiddle.size()-1){
                    enigmeNum = 0;
                    Intent intent = new Intent(DisplayTextRiddle.this, RouteFeedback.class);
                    startActivity(intent);
                }else if(mAnswer.getText().toString().equals(mRiddle.get(enigmeNum).getmSolution())){
                    enigmeNum+=1;
                    Intent intent = new Intent(DisplayTextRiddle.this, DisplayTextRiddle.class);
                    startActivity(intent);
                }else{
                    Context context = getApplicationContext();
                    CharSequence text = "Mauvaise réponse ! Essaye encore";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

               /* String solution = mAnswer.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                JSONObject json = new JSONObject();
                try {
                    json.put(getString(R.string.route_id),route.getmId());
                    json.put(getString(R.string.email),email);
                    json.put(getString(R.string.db_key_textual_solution),solution);
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

                                            Intent intent = new Intent(DisplayTextRiddle.this, RouteFeedback.class);
                                            //TODO: Penser à afficher à l'utilisateur fini
                                            startActivity(intent);
                                        }else {
                                            //Cas ou l'utilisateur a valider l'énigme mais pas fini le parcours

                                            Intent intent = new Intent(DisplayTextRiddle.this, DisplayTextRiddle.class);
                                            JSONObject rid = new JSONObject(response.getString("riddle"));
                                            TextualRiddle r1 = new TextualRiddle("Enigme", response.getString(getString(R.string.db_key_description)),"" );
                                            Bundle bundle = new Bundle();
                                            bundle.putSerializable(getString(R.string.riddle),r1);
                                            bundle.putSerializable(getString(R.string.route),route);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
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
                */
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
        CharSequence text = "Vous ne pouvez pas revenir en arrière désolé. Veuillez abandonner si vous souhaitez quitter le parcours.";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    //TODO : a retirer quand le serveur nous donnera l'énigme
    private void riddlelist(){
        TextualRiddle r1 = new TextualRiddle("Le parc de la tête d'or", "En quel année a été ouvert pour la première fois ce parc ?","1857" );
        TextualRiddle r2 = new TextualRiddle("Toujours des nombres !", "Quelle est la superficie de ce parc (en ha) ?","117" );
        TextualRiddle r3 = new TextualRiddle("L'heureux propriétaire", "Qui est le propiétaire de ce parc ?", "Lyon");
        mRiddle.add(r1);
        mRiddle.add(r2);
        mRiddle.add(r3);
    }
}
