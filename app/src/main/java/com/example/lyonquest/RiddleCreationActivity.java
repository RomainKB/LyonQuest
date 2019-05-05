package com.example.lyonquest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RiddleCreationActivity extends AppCompatActivity implements View.OnClickListener{

    private Route route;

    private Button btnNextRiddle;
    private Button btnFinishRoot;
    private EditText editTextRiddleText;
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

        btnFinishRoot = findViewById(R.id.riddle_creation_finish_route_button);
        btnFinishRoot.setTag(1);
        btnFinishRoot.setOnClickListener(this);

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
        }else if(v.getTag() == btnFinishRoot.getTag()){
            finishRoot();
        }
    }

    private void finishRoot() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void nextRiddle() {
        System.out.println("////////////////////////// NEXT RIDDLE ////////////////////////////");
        TextualRiddle riddle = new TextualRiddle("",editTextRiddleText.getText().toString(), editTextRiddleAnswer.getText().toString());
        route.getRiddles().add(riddle);

        System.out.println(route.toJSON());
        Intent intent = new Intent(this, RiddleCreationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.route),route);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
