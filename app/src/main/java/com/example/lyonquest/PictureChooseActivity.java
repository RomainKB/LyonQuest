package com.example.lyonquest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class PictureChooseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private ArrayList<CharSequence> sponsored;
    private String sponsoredChoosed;
    private ArrayList<CharSequence> tags;
    private String tagChoosed;

    private Spinner spinnerSponsored;
    private Spinner spinnerTags;

    private Button validationSponsored;
    private Button validationTags;
    private Button searchTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.SHARED_PREFS), MODE_PRIVATE);
        int choice = Integer.parseInt(sharedPreferences.getString(getString(R.string.set_theme), "1"));
        Utils.onActivityCreateSetTheme(this,choice);
        setContentView(R.layout.activity_picture_choose);

        sponsored = new ArrayList<>();
        tags = new ArrayList<>();

        spinnerSponsored = findViewById(R.id.spinner_sponsored);
        spinnerSponsored.setTag(0);
        spinnerSponsored.setOnItemSelectedListener(this);

        spinnerTags = findViewById(R.id.spinner_tags);
        spinnerTags.setTag(1);
        spinnerTags.setOnItemSelectedListener(this);

        validationSponsored = findViewById(R.id.choose_picture_validation_sponsored);
        validationSponsored.setTag(0);
        validationSponsored.setOnClickListener(this);

        searchTags = findViewById(R.id.choose_picture_button_research_tag);
        searchTags.setTag(1);
        searchTags.setOnClickListener(this);

        validationTags = findViewById(R.id.choose_picture_validation_tags);
        validationTags.setTag(2);
        validationTags.setOnClickListener(this);

        fetchSponsored();
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, sponsored);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerSponsored.setAdapter(adapter);
    }

    private void fetchSponsored(){
        sponsored.clear();
        sponsored.add("rhino");
        sponsored.add("fourvière");
        sponsored.add("Gaston Berger");
        sponsored.add("Capelle");
    }

    private void fetchtags(){
        tags.clear();
        tags.add("chat");
        tags.add("chien");
        tags.add("souris");
        tags.add("lion");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if(v.getTag() == validationSponsored.getTag()){
            sponsoredChoosed = (String) spinnerSponsored.getSelectedItem();
            close(sponsoredChoosed,true);
        }else if(v.getTag() == validationTags.getTag()){
            tagChoosed = (String) spinnerTags.getSelectedItem();
            close(tagChoosed,false);
        }else if(v.getTag() == searchTags.getTag()){
            fetchtags();
            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, tags);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerTags.setAdapter(adapter);
        }
    }

    private void close(String answer, boolean custom) {
        Intent returnIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("answer", answer);
        bundle.putSerializable("custom", custom);
        returnIntent.putExtras(bundle);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
