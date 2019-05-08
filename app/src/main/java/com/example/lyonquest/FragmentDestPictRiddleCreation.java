package com.example.lyonquest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static android.app.Activity.RESULT_OK;

public class FragmentDestPictRiddleCreation extends Fragment implements View.OnClickListener {

    private Button buttonGeo;
    private boolean isChoosenGeo;
    private double longitude;
    private double latitude;
    private double delta;
    private String answer;
    private boolean custom;

    private boolean activityGeo;
    private Button buttonPict;
    private boolean isChoosenPict;
    private boolean activityPict;

    public static FragmentDestPictRiddleCreation newInstance() {
        FragmentDestPictRiddleCreation fragment = new FragmentDestPictRiddleCreation();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dest_pict_riddle_creation, container, false);
        buttonGeo = view.findViewById(R.id.riddle_creation_choose_localisation);
        buttonGeo.setTag(0);
        buttonGeo.setOnClickListener(this);
        isChoosenGeo = false;

        buttonPict = view.findViewById(R.id.riddle_creation_choose_picture);
        buttonPict.setTag(1);
        buttonPict.setOnClickListener(this);
        isChoosenPict = false;

        activityPict = false;
        activityGeo = false;
        //editTextAnswer = view.findViewById(R.id.riddle_creation_riddle_text_edit);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getTag()==buttonGeo.getTag()) onClickGeo(v);

        if(v.getTag()==buttonPict.getTag()) onClickPict(v);
    }

    private void onClickPict(View v) {
        activityPict = true;
        Intent intent = new Intent(getActivity(), PictureChooseActivity.class);
        startActivityForResult(intent,1);
    }

    public void onClickGeo(View v) {
        activityGeo = true;
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK){
            if(activityGeo) {
                activityGeo = false;
                buttonGeo.setText(R.string.riddle_creation_localisation_choosen);
                isChoosenGeo = true;
                latitude = data.getExtras().getDouble("latitude");
                longitude = data.getExtras().getDouble("longitude");
                delta = data.getExtras().getDouble("delta");
            }
            if(activityPict){
                activityPict = false;
                answer = data.getExtras().getString("answer");
                custom = data.getExtras().getBoolean("custom");
                buttonPict.setText(R.string.riddle_creation_picture_choosen);
                isChoosenPict = true;
            }
        }

    }

    public boolean isChoosen(){
        return isChoosenGeo && isChoosenPict;
    }


    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getDelta() {
        return delta;
    }

    public boolean isCustom() {
        return custom;
    }

    public String getAnswer() {
        return answer;
    }
}