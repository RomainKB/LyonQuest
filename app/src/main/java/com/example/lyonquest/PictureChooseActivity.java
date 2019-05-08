package com.example.lyonquest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PictureChooseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private ArrayList<CharSequence> sponsoredDisplay;
    private ArrayList<CharSequence> sponsoredSelect;
    private String sponsoredChoosed;
    private ArrayList<CharSequence> tags;
    private String tagChoosed;

    private Spinner spinnerSponsored;
    private Spinner spinnerTags;

    private EditText editText;

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

        sponsoredDisplay = new ArrayList<>();
        sponsoredSelect = new ArrayList<>();
        tags = new ArrayList<>();

        spinnerSponsored = findViewById(R.id.spinner_sponsored);
        spinnerSponsored.setTag(0);
        spinnerSponsored.setOnItemSelectedListener(this);

        spinnerTags = findViewById(R.id.spinner_tags);
        spinnerTags.setTag(1);
        spinnerTags.setOnItemSelectedListener(this);

        editText = findViewById(R.id.choose_picture_tag_research);

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
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, sponsoredDisplay);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerSponsored.setAdapter(adapter);
    }

    private void fetchSponsored(){
        sponsoredDisplay.clear();
        sponsoredSelect.add("basilique_fourviere");
        sponsoredDisplay.add("Basilique de Fourvière");

        sponsoredSelect.add("beaux_arts");
        sponsoredDisplay.add("Beaux Arts");

        sponsoredSelect.add("bellecour");
        sponsoredDisplay.add("Place Bellecour");

        sponsoredSelect.add("bmc");
        sponsoredDisplay.add("Bibliothèque Marie Curie");

        sponsoredSelect.add("cite_internationale");
        sponsoredDisplay.add("La cité internationnale");


        sponsoredSelect.add("cone_insa");
        sponsoredDisplay.add("Cone INSA");

        sponsoredSelect.add("hotel_dieu");
        sponsoredDisplay.add("Hotel Dieu");

        sponsoredSelect.add("mur_insa");
        sponsoredDisplay.add("Mur de l'Insa");

        sponsoredSelect.add("musee_confluences");
        sponsoredDisplay.add("Musée des Confluences");

        sponsoredSelect.add("opera");
        sponsoredDisplay.add("Opéra");

        sponsoredSelect.add("palais_justice");
        sponsoredDisplay.add("Palais de Justice");

        sponsoredSelect.add("part_dieu");
        sponsoredDisplay.add("La Part-Dieu");

        sponsoredSelect.add("pont_raymond_barre");
        sponsoredDisplay.add("Pont Raymond-Barre");

        sponsoredSelect.add("rhino_insa");
        sponsoredDisplay.add("Le thino de l'INSA");

        sponsoredSelect.add("saint_jean");
        sponsoredDisplay.add("La cathédrale Saint-Jean");

        sponsoredSelect.add("saint_paul");
        sponsoredDisplay.add("Gare de Lyon-Saint-Paul");

        sponsoredSelect.add("terreaux");
        sponsoredDisplay.add("Place des Terreaux");

        sponsoredSelect.add("theatre_celestins");
        sponsoredDisplay.add("Théâtre Celestins");

        sponsoredSelect.add("theatre_fourviere");
        sponsoredDisplay.add("Théâtre Fourvière");

        sponsoredSelect.add("scutu");
        sponsoredDisplay.add("Marian Scuturici");
    }

    private void fetchtags(){
        tags.clear();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = getString(R.string.db_fetch_tags);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("label", editText.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(jsonObject);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            JSONArray array = new JSONArray(response.getJSONArray("labels"));
                            for (int i = 0; i < array.length(); i++) {
                                // On récupère un objet JSON du tableau
                                tags.add(array.getString(i));
                                System.out.println(tags);
                                ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(PictureChooseActivity.this,android.R.layout.simple_spinner_item, tags);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerTags.setAdapter(adapter);
                            }
                        }catch(JSONException e){e.printStackTrace();}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR : "+error);
            }
        });
        getRequest.setRetryPolicy(new DefaultRetryPolicy(15000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(getRequest);
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
            sponsoredChoosed = (String) sponsoredSelect.get(spinnerSponsored.getSelectedItemPosition());
            close(sponsoredChoosed,true);
        }else if(v.getTag() == validationTags.getTag()){
            tagChoosed = (String) spinnerTags.getSelectedItem();
            close(tagChoosed,false);
        }else if(v.getTag() == searchTags.getTag()){
            fetchtags();
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
