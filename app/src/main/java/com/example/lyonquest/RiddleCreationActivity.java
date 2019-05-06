package com.example.lyonquest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
/**
 * Created by victorl on 30/04/2019.
 *
 * This activity allow the user to create a riddle and directly add it to the route which it currently creating.
 */
public class RiddleCreationActivity extends AppCompatActivity implements View.OnClickListener{
    /**
     * The route that is being created by the user
     */
    private Route route;
    /**
     * The button to add a new riddle
     */
    private Button btnNextRiddle;
    /**
     * The button to finish the route
     */
    private Button btnFinishRoute;
    /**
     * The edit text to add the riddle description
     */
    private EditText editTextRiddleText;
    /**
     * The edit text to add the riddle solution
     */
    private EditText editTextRiddleAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.SHARED_PREFS), MODE_PRIVATE);
        int choice = Integer.parseInt(sharedPreferences.getString(getString(R.string.set_theme), "1"));
        Utils.onActivityCreateSetTheme(this,choice);
        setContentView(R.layout.activity_riddle_creation);

        btnNextRiddle = findViewById(R.id.riddle_creation_next_riddle_button);
        btnNextRiddle.setTag(0);
        btnNextRiddle.setOnClickListener(this);

        btnFinishRoute = findViewById(R.id.riddle_creation_finish_route_button);
        btnFinishRoute.setTag(1);
        btnFinishRoute.setOnClickListener(this);

        editTextRiddleText = findViewById(R.id.riddle_creation_riddle_text_edit);
        editTextRiddleAnswer = findViewById(R.id.riddle_creation_answer_riddle_edit);

        route = (Route) getIntent().getSerializableExtra(getString(R.string.route));

        System.out.println(route);
        System.out.println(route.getmName());
        System.out.println(route.getmDescription());
        System.out.println(route.getRiddles());
    }

    @Override
    public void onClick(View v) {
        if(((int)v.getTag()) == 0){
            nextRiddle();
        }else if(v.getTag() == btnFinishRoute.getTag()){
            finishRoute();
        }
    }

    private void finishRoute() {
        String text = editTextRiddleText.getText().toString();
        String answer =  editTextRiddleAnswer.getText().toString();
        View focusView = null;
        boolean empty = true;

        if(!TextUtils.isEmpty(answer)){
            editTextRiddleAnswer.setError(getString(R.string.riddle_creation_empty_riddle_before_finish));
            focusView = editTextRiddleAnswer;
            empty = false;
        }
        if(!TextUtils.isEmpty(text)){
            editTextRiddleText.setError(getString(R.string.riddle_creation_empty_riddle_before_finish));
            focusView = editTextRiddleText;
            empty = false;
        }

        if(empty) {
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

            String url = getString(R.string.db_route_creation_url);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, route.toJSON(),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //try {

                                //String aJsonString = response.getString(getString(R.string.db_status));
                                //if(aJsonString.equals(getString(R.string.db_success))){
                                    Intent intent = new Intent(RiddleCreationActivity.this, MainActivity.class);
                                    startActivity(intent);
                                //}else{
                                //    System.out.println("Oops !!");

                            //}catch(JSONException e){e.printStackTrace();}
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("ERROR : "+error);
                }
            });

            queue.add(jsonObjectRequest);

        }else{
            focusView.requestFocus();
        }
    }

    private void nextRiddle() {
        String text = editTextRiddleText.getText().toString();
        String answer =  editTextRiddleAnswer.getText().toString();
        View focusView = null;

        boolean complete = true;
        if(TextUtils.isEmpty(answer)){
            editTextRiddleAnswer.setError(getString(R.string.error_field_required));
            focusView = editTextRiddleAnswer;
            complete = false;
        }
        if(TextUtils.isEmpty(text)){
            editTextRiddleText.setError(getString(R.string.error_field_required));
            focusView = editTextRiddleText;
            complete = false;
        }
        if(complete) {
            TextualRiddle riddle = new TextualRiddle("", text, answer);
            route.getRiddles().add(riddle);

            Intent intent = new Intent(this, RiddleCreationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(getString(R.string.route), route);
            intent.putExtras(bundle);
            startActivity(intent);
        }else{
            focusView.requestFocus();
        }
    }

}
