package com.example.lyonquest;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

public final class LocationGpsListener implements LocationListener {

    public boolean isGPSEnabled = false;

    private Location location;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    protected LocationManager locationManager;

    public LocationGpsListener(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

        location = new Location("fake");
        location.setLatitude(0);
        location.setLongitude(0);
    }

    public void stopLocationUpdates() {
        if (locationManager != null) {
            locationManager.removeUpdates(LocationGpsListener.this);
        }
    }

    public double getLatitude() {
        System.out.println("je suis latitude "+location.getLatitude());
        return location.getLatitude();
    }

    public double getLongitude() {
        return location.getLongitude();
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    @Override
    public void onProviderDisabled(String provider) {
        // NO-OP
    }

    @Override
    public void onProviderEnabled(String provider) {
        // NO-OP
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // NO-OP
    }

}