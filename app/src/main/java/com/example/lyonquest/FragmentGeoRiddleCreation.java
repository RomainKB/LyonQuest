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

public class FragmentGeoRiddleCreation extends Fragment{

    private Button button;

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
        //editTextAnswer = view.findViewById(R.id.riddle_creation_riddle_text_edit);
        return view;
    }

}