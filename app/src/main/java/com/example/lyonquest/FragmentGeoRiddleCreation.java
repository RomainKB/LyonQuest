package com.example.lyonquest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;

import static android.app.Activity.RESULT_OK;

public class FragmentGeoRiddleCreation extends Fragment implements View.OnClickListener {

    private Button button;
    private boolean isChoosen;
    private double longitude;
    private double latitude;
    private double delta;

    public static FragmentGeoRiddleCreation newInstance() {
        FragmentGeoRiddleCreation fragment = new FragmentGeoRiddleCreation();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_geo_riddle_creation, container, false);
        button = view.findViewById(R.id.riddle_creation_choose_localisation);
        button.setOnClickListener(this);
        isChoosen = false;
        //editTextAnswer = view.findViewById(R.id.riddle_creation_riddle_text_edit);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK){
            button.setText(R.string.riddle_creation_localisation_choosen);
            isChoosen = true;
            latitude = data.getExtras().getDouble("latitude");
            longitude = data.getExtras().getDouble("longitude");
            delta = data.getExtras().getDouble("delta");
        }

    }

    public boolean isChoosen(){
        return isChoosen;
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
}