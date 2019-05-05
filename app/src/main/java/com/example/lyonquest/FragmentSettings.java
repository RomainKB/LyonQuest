package com.example.lyonquest;

import android.content.Intent;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentSettings extends Fragment implements View.OnClickListener{

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
        Button button1 =(Button)view.findViewById(R.id.change_theme);
        Button button2 =(Button)view.findViewById(R.id.profil);
        button.setTag(0);
        button1.setTag(1);
        button2.setTag(2);

        button.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {
        // Get back the tag button to know which route the user selected
        int responseIndex = (int) v.getTag();

        switch(responseIndex) {

            case 0:
                SharedPrefs.saveSharedSetting(getActivity(), "USER", "false");
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;

            case 1:
                Intent intent1 = new Intent(getActivity(), ThemeChange.class);
                startActivity(intent1);

                break;

            case 2:
                Intent intent2 = new Intent(getActivity(), UserProfil.class);
                startActivity(intent2);
                break;

        }
    }
}
