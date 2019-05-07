package com.example.lyonquest;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

/**
 * Created by romaink on 07/05/2019.
 *
 * Activity where the user try to answer to a textual riddle.
 *
 */
public class DisplayDestPictRiddle extends AppCompatActivity implements View.OnClickListener {
    /**
     * The text view where we display the riddle title
     */
    private TextView mTitle;
    /**
     * The text view where we display the riddle description
     */
    private TextView mDescription;
    /**
     * The button to take a picture
     */
    private Button mPicture;
    /**
     * The button to give up the route.
     */
    private Button mGiveUp;
    /**
     * The actual destination riddle show to the player
     */
    private DestPictRiddle riddle;
    /**
     * The actual route played by the player.
     */
    private Route route;
    /**
     * Location attributs use to have the latitude and longitude.
     */
    private LocationManager locationManager;
    private LocationListener listener;

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private Uri mImageUri;
    private static final int MY_SOCKET_TIMEOUT_MS = 20000;
    private Bitmap photo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.SHARED_PREFS), MODE_PRIVATE);
        int choice = Integer.parseInt(sharedPreferences.getString(getString(R.string.set_theme), "1"));
        Utils.onActivityCreateSetTheme(this, choice);
        setContentView(R.layout.activity_display_dest_pict_riddle);

        mTitle = (TextView) findViewById(R.id.activity_display_title);
        mDescription = (TextView) findViewById(R.id.activity_display_description);
        mPicture = (Button) findViewById(R.id.take_picture);
        mGiveUp = (Button) findViewById(R.id.give_up_button);

        mPicture.setTag(0);
        mGiveUp.setTag(1);
        mPicture.setOnClickListener(this);
        mGiveUp.setOnClickListener(this);

        riddle = (DestPictRiddle) getIntent().getSerializableExtra(getString(R.string.riddle));
        route = (Route) getIntent().getSerializableExtra(getString(R.string.route));

        mTitle.setText(riddle.getmTitle());
        mDescription.setText(riddle.getmDescription());

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        gpsListener();

    }

    /**
     * This method set a GPS listener. It's used to get the actual location.
     */
    private void gpsListener() {
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {}

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {}

            @Override
            public void onProviderEnabled(String s) {}

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };
    }

    @Override
    public void onClick(View v) {
        int responseIndex = (int) v.getTag();

        switch (responseIndex) {
            case 0:

                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivity(cameraIntent);
                }

                //Direction to onActivityResult when the user open the camera
                break;

            case 1:
                Intent intent = new Intent(DisplayDestPictRiddle.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            //get  image thumbnail
            photo = (Bitmap) data.getExtras().get("data");
            //encode image
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            //Let's start this method to chose which activity will start.
            serveur(encoded);
        }
    }

    @Override
    public void onBackPressed() {
        Context context = getApplicationContext();
        CharSequence text = getString(R.string.onBackPressed_forbiden);
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


    private void serveur(String encoded){
        final String email = SharedPrefs.readSharedSetting(DisplayDestPictRiddle.this, getString(R.string.email), null);

        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
            return; //It will return if permissions aren't allowed.
        }
        locationManager.requestLocationUpdates("gps", 5000, 0, listener);
        Location location = locationManager.getLastKnownLocation("gps");

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();
        try {
            json.put(getString(R.string.route_id),route.getmId());
            json.put(getString(R.string.email),email);
            json.put(getString(R.string.db_picture),encoded);
            json.put(getString(R.string.db_key_latitude),latitude);
            json.put(getString(R.string.db_key_longitude),longitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getString(R.string.db_verification);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString(getString(R.string.db_status));
                            if(status.equals(getString(R.string.db_success))){

                                String finished = response.getString(getString(R.string.db_end));

                                if(finished.equals(getString(R.string.db_fini))){
                                    //Case where the user finished the route

                                    Intent intent0 = new Intent(DisplayDestPictRiddle.this, RouteFeedback.class);
                                    Bundle bundle0 = new Bundle();
                                    bundle0.putSerializable(getString(R.string.route),route);
                                    intent0.putExtras(bundle0);
                                    startActivity(intent0);
                                }else {
                                    //Case where the user validate the riddle but didn't finished the route.
                                    String type = response.getString(getString(R.string.db_key_type));
                                    System.out.println(type);

                                    switch (type){
                                        case "password":
                                            Intent intent1 = new Intent(DisplayDestPictRiddle.this, DisplayTextRiddle.class);
                                            TextualRiddle r1 = new TextualRiddle("Enigme", response.getString(getString(R.string.db_key_description)),"" );
                                            Bundle bundle1 = new Bundle();
                                            bundle1.putSerializable(getString(R.string.riddle),r1);
                                            bundle1.putSerializable(getString(R.string.route),route);
                                            intent1.putExtras(bundle1);
                                            startActivity(intent1);
                                            break;

                                        case "geocoords":
                                            Intent intent2 = new Intent(DisplayDestPictRiddle.this, DisplayDestinationRiddle.class);
                                            DestinationRiddle r2 = new DestinationRiddle("Enigme", response.getString(getString(R.string.db_key_description)),0.0,0.0);
                                            Bundle bundle2 = new Bundle();
                                            bundle2.putSerializable(getString(R.string.riddle),r2);
                                            bundle2.putSerializable(getString(R.string.route),route);
                                            intent2.putExtras(bundle2);
                                            startActivity(intent2);
                                            break;

                                        case "picture":
                                            System.out.println("je suis iciiiio");
                                            Intent intent3 = new Intent(DisplayDestPictRiddle.this, DisplayPictureRiddle.class);
                                            PictureRiddle r3 = new PictureRiddle("Enigme", response.getString(getString(R.string.db_key_description)));
                                            Bundle bundle3 = new Bundle();
                                            bundle3.putSerializable(getString(R.string.riddle),r3);
                                            bundle3.putSerializable(getString(R.string.route),route);
                                            intent3.putExtras(bundle3);
                                            startActivity(intent3);
                                            break;

                                        case "dest_pict":
                                            Intent intent4 = new Intent(DisplayDestPictRiddle.this, DisplayDestPictRiddle.class);
                                            DestPictRiddle r4 = new DestPictRiddle("Enigme", response.getString(getString(R.string.db_key_description)),0.0,0.0);
                                            Bundle bundle4 = new Bundle();
                                            bundle4.putSerializable(getString(R.string.riddle),r4);
                                            bundle4.putSerializable(getString(R.string.route),route);
                                            intent4.putExtras(bundle4);
                                            startActivity(intent4);
                                            break;
                                    }
                                }
                            }else{
                                //Case where the user gave a false answer
                                Context context = getApplicationContext();
                                CharSequence text = getString(R.string.answer_false_dp);
                                int duration = Toast.LENGTH_LONG;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            }

                        }catch(JSONException e){e.printStackTrace();}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR : "+error);
            }
        });
        queue.add(jsonObjectRequest);
    }


}

