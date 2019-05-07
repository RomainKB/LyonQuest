package com.example.lyonquest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.OnMapReadyCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class RiddleCreationActivity extends FragmentActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Route route;

    private Button btnNextRiddle;
    private Button btnCancel;
    private Button btnFinishRoot;
    private EditText editTextRiddleText;
    private EditText editTextRiddleAnswer;
    private Fragment fragment;

    private double longitude;
    private double latitude;
    private double delta;
    private String answer;
    private boolean custom;

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

        btnFinishRoot = findViewById(R.id.riddle_creation_finish_route_button);
        btnFinishRoot.setTag(1);
        btnFinishRoot.setOnClickListener(this);


        btnCancel = findViewById(R.id.riddle_creation_cancel_button);
        btnCancel.setTag(2);
        btnCancel.setOnClickListener(this);


        editTextRiddleText = findViewById(R.id.riddle_creation_riddle_text_edit);

        Spinner spinner = findViewById(R.id.spinner_riddle_type);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Riddle_types, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        fragment = FragmentTextualRiddleCreation.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.riddle_creation_fragment_layout, fragment);
        transaction.commit();

        route = (Route) getIntent().getSerializableExtra(getString(R.string.route));
    }

    @Override
    public void onClick(View v) {
        if(v.getTag() == btnNextRiddle.getTag()){
            nextRiddle();
        }
        if(v.getTag() == btnFinishRoot.getTag()){
            finishRoot();
        }else if(v.getTag() == btnCancel.getTag()){
            cancel();
        }
    }

    private void cancel() {
        Intent intent = new Intent(RiddleCreationActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void finishRoot() {
        if(fragment instanceof FragmentTextualRiddleCreation){
            FragmentTextualRiddleCreation fragmentTextualRiddleCreation = (FragmentTextualRiddleCreation) fragment;
            editTextRiddleAnswer = fragmentTextualRiddleCreation.getEditTextAnswer();
        }

        String text = editTextRiddleText.getText().toString();
        View focusView = null;
        boolean empty = true;

        if(!TextUtils.isEmpty(text)){
            editTextRiddleText.setError(getString(R.string.riddle_creation_empty_riddle_before_finish));
            focusView = editTextRiddleText;
            empty = false;
        }

        System.out.println(route.toJSON());

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
        System.out.println("NEXT RIDDLE");
        if(fragment instanceof FragmentTextualRiddleCreation){
            System.out.println("TEXTUAL");
            FragmentTextualRiddleCreation fragmentTextualRiddleCreation = (FragmentTextualRiddleCreation) fragment;
            editTextRiddleAnswer = fragmentTextualRiddleCreation.getEditTextAnswer();
            System.out.println(editTextRiddleAnswer);
            nextRiddleTextual();
        } else if (fragment instanceof FragmentGeoRiddleCreation) {
            System.out.println("GEO");
            FragmentGeoRiddleCreation fragmentGeoRiddleCreation = (FragmentGeoRiddleCreation) fragment;
            if(fragmentGeoRiddleCreation.isChoosen()) {
                System.out.println("CHOOSEN");
                latitude = fragmentGeoRiddleCreation.getLatitude();
                longitude = fragmentGeoRiddleCreation.getLongitude();
                delta = fragmentGeoRiddleCreation.getDelta();
                nextRiddleGeo();
            }
        } else if (fragment instanceof FragmentPictRiddleCreation) {
            System.out.println("Picture");
            FragmentPictRiddleCreation fragmentPictRiddleCreation = (FragmentPictRiddleCreation) fragment;
            if(fragmentPictRiddleCreation.isChoosen()) {
                System.out.println("CHOOSEN");
                answer = fragmentPictRiddleCreation.getAnswer();
                System.out.println("ANSWER :::" + answer);
                custom = fragmentPictRiddleCreation.isCustom();
                nextRiddlePict();
            }
        }


    }

    private void nextRiddlePict() {
        String text = editTextRiddleText.getText().toString();
        View focusView = null;

        boolean complete = true;
        if(TextUtils.isEmpty(text)){
            editTextRiddleText.setError(getString(R.string.error_field_required));
            focusView = editTextRiddleText;
            complete = false;
        }

        PictureRiddle riddle = new PictureRiddle("",text,answer,custom);
        if(complete) {
            addRiddle(riddle);
        }else{
            focusView.requestFocus();
        }
    }

    private void nextRiddleGeo() {
        System.out.println("NEXT RIDDLE GEO");

        String text = editTextRiddleText.getText().toString();
        View focusView = null;

        boolean complete = true;
        if(TextUtils.isEmpty(text)){
            editTextRiddleText.setError(getString(R.string.error_field_required));
            focusView = editTextRiddleText;
            complete = false;
        }

        System.out.println("DELTA = " + delta);
        DestinationRiddle riddle = new DestinationRiddle("",text,latitude,longitude,delta);
        if(complete) {
            addRiddle(riddle);
        }else{
            focusView.requestFocus();
        }
    }

    private void nextRiddleTextual() {
        System.out.println("NEXT RIDDLE TEXT");

        String text = editTextRiddleText.getText().toString();
        String answer = editTextRiddleAnswer.getText().toString();
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

        TextualRiddle riddle = new TextualRiddle("", text, answer);
        if(complete) {
            addRiddle(riddle);
        }else{
            focusView.requestFocus();
        }
    }

    private void addRiddle(Riddle riddle)
    {
        route.getRiddles().add(riddle);

        Intent intent = new Intent(this, RiddleCreationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.route), route);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        FragmentTransaction transaction;
        switch (position){
            case 0:
                fragment = FragmentTextualRiddleCreation.newInstance();

                break;
            case 1:
                fragment = FragmentGeoRiddleCreation.newInstance();
                break;
            case 2:
                fragment = FragmentPictRiddleCreation.newInstance();
                break;
        }
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.riddle_creation_fragment_layout, fragment);
        transaction.commit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
