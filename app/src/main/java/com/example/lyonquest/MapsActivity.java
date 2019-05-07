package com.example.lyonquest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private LatLng point;
    private Button button;
    private EditText editTextDelta;

    private boolean geoChoosen;
    private boolean deltaChoosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.SHARED_PREFS), MODE_PRIVATE);
        int choice = Integer.parseInt(sharedPreferences.getString(getString(R.string.set_theme), "1"));
        Utils.onActivityCreateSetTheme(this,choice);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        geoChoosen = false;
        deltaChoosen = false;
        editTextDelta = findViewById(R.id.maps_activity_edit_delta);
        button = findViewById(R.id.maps_activity_choose_localisation);
        button.setOnClickListener(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(45.755874,4.838552),11));
        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onClick(View v) {

        deltaChoosen = !(TextUtils.isEmpty(editTextDelta.getText().toString()));

        if(deltaChoosen && geoChoosen) {
            Intent returnIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("longitude", point.longitude);
            bundle.putSerializable("latitude", point.latitude);
            bundle.putSerializable("delta", Double.valueOf(editTextDelta.getText().toString()));
            returnIntent.putExtras(bundle);
            setResult(RESULT_OK, returnIntent);
            finish();
        }
    }

    @Override
    public void onMapClick(LatLng point) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(point));
        this.point = point;
        geoChoosen = true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
