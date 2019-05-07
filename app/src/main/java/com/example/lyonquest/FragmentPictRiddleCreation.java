package com.example.lyonquest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static android.app.Activity.RESULT_OK;

public class FragmentPictRiddleCreation extends Fragment implements View.OnClickListener {

    private String answer;
    private boolean custom;
    private Button button;
    private boolean isChoosen;

    public static FragmentPictRiddleCreation newInstance() {
        FragmentPictRiddleCreation fragment = new FragmentPictRiddleCreation();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pict_riddle_creation, container, false);
        button = view.findViewById(R.id.riddle_creation_choose_picture);
        button.setOnClickListener(this);
        isChoosen = false;
        //editTextAnswer = view.findViewById(R.id.riddle_creation_riddle_text_edit);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), PictureChooseActivity.class);
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            answer = data.getExtras().getString("answer");
            custom = data.getExtras().getBoolean("custom");
            button.setText(R.string.riddle_creation_picture_choosen);
            isChoosen = true;
        }
    }

    public boolean isChoosen(){
        return isChoosen;
    }


    public boolean isCustom() {
        return custom;
    }

    public String getAnswer() {
        return answer;
    }
}