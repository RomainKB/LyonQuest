package com.example.lyonquest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DisplayTextRiddle extends AppCompatActivity implements View.OnClickListener{
    /**
     * The text view where we display the riddle title
     */
    private TextView mTitle;
    /**
     * The text view where we display the riddle description
     */
    private TextView mDescription;
    /**
     * The edit text where the user can enter the answer
     */
    private EditText mAnswer;

    /**
     * The button to check the answer.
     */
    private Button mCheck;
    /**
     * The button to give up the route.
     */
    private Button mGiveUp;



    private List<TextualRiddle> mRiddle = new ArrayList<>();

    //TODO : a retirer quand le serveur nous donnera l'énigme
    public static int enigmeNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.SHARED_PREFS), MODE_PRIVATE);
        int choice = Integer.parseInt(sharedPreferences.getString(getString(R.string.set_theme), "1"));
        Utils.onActivityCreateSetTheme(this,choice);
        setContentView(R.layout.activity_display_text_riddle);

        mTitle = (TextView) findViewById(R.id.activity_display_title);
        mDescription = (TextView) findViewById(R.id.activity_display_description);
        mAnswer = (EditText) findViewById(R.id.riddle_text_answer);
        mCheck = (Button) findViewById(R.id.check_button);
        mGiveUp = (Button) findViewById(R.id.give_up_button);

        mCheck.setTag(0);
        mGiveUp.setTag(1);
        mCheck.setOnClickListener(this);
        mGiveUp.setOnClickListener(this);

        // TODO : Delete this function call when we receive information from the server
        riddlelist();
        //TODO : récupérer l'énigme passée par le intent.

        mTitle.setText(mRiddle.get(enigmeNum).getmTitle());
        mDescription.setText(mRiddle.get(enigmeNum).getmDescription());
    }
    //TODO il faudra totalement revoir cette fonction avec l'envoie des énigmes par le serveur.
    // Vérifier avec serveur réponse, puis passer en intent la prochaine énigme.
    // Que nous envoi le serveur pour dire que c'est fini ?
    @Override
    public void onClick(View v) {
        int responseIndex = (int) v.getTag();

        switch(responseIndex) {
            case 0:

                if(enigmeNum == mRiddle.size()-1){
                    enigmeNum = 0;
                    Intent intent = new Intent(DisplayTextRiddle.this, MainActivity.class);
                    startActivity(intent);
                }else if(mAnswer.getText().toString().equals(mRiddle.get(enigmeNum).getmSolution())){
                    enigmeNum+=1;
                    Intent intent = new Intent(DisplayTextRiddle.this, DisplayTextRiddle.class);
                    startActivity(intent);
                }else{
                    Context context = getApplicationContext();
                    CharSequence text = "Mauvaise réponse ! Essaye encore";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                break;

            case 1:
                Intent intent = new Intent(DisplayTextRiddle.this, MainActivity.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        Context context = getApplicationContext();
        CharSequence text = "Vous ne pouvez pas revenir en arrière désolé. Veuillez abandonner si vous souhaitez quitter le parcours.";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    //TODO : a retirer quand le serveur nous donnera l'énigme
    private void riddlelist(){
        TextualRiddle r1 = new TextualRiddle("Le parc de la tête d'or", "En quel année a été ouvert pour la première fois ce parc ?","1857" );
        TextualRiddle r2 = new TextualRiddle("Toujours des nombres !", "Quelle est la superficie de ce parc (en ha) ?","117" );
        TextualRiddle r3 = new TextualRiddle("L'heureux propriétaire", "Qui est le propiétaire de ce parc ?", "Lyon");
        mRiddle.add(r1);
        mRiddle.add(r2);
        mRiddle.add(r3);
    }
}
