package com.example.lyonquest;

import android.content.Intent;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentSettings extends Fragment {

    public static FragmentSettings newInstance() {
        FragmentSettings fragment = new FragmentSettings();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_fragment_settings, container, false);

        Button button =(Button)view.findViewById(R.id.sign_out_button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SharedPrefs.saveSharedSetting(getActivity(), "USER", "false");
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

            }
        });
        return view;

    }
}
