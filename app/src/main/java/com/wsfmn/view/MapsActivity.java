package com.wsfmn.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wsfmn.controller.HabitHistoryController;
import com.wsfmn.controller.HabitListController;
import com.wsfmn.controller.OnlineController;
import com.wsfmn.exceptions.HabitEventNameException;
import com.wsfmn.model.Geolocation;
import com.wsfmn.model.HabitEvent;
import com.wsfmn.model.HabitHistory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentLocationMarker;
    private Marker habitEventMarker;
    public static final int REQUEST_LOCATION_CODE= 99;
    private LatLng currentLocation;
    //private HabitEvent habitEvent;


    private ArrayList<HabitEvent> eventList = new ArrayList<HabitEvent>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public void buttonFollows(View v){

    }

    public void buttonHighlight(View v){
        Bundle bundle = getIntent().getExtras();
        //String filterString = bundle.getString("filterString");
        int highlightMode = bundle.getInt("highlightMode", 7);


        if (currentLocation != null) {
            Button Highlight = (Button) findViewById(R.id.B_highlight);
            //Highlight.setText("clicked");
            Toast.makeText(getApplicationContext(), "0", Toast.LENGTH_LONG).show();

            if (highlightMode == 7) {

                for (int i = 0; i < HabitHistoryController.getInstance().size(); i++) {
                    Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_LONG).show();

                    HabitEvent habitEvent = HabitHistoryController.getInstance().get(i);
                    Geolocation geolocation = habitEvent.getGeolocation();
                    LatLng eventCoord = geolocation.getLatLng();

                    float results[] = new float[10];
                    Location.distanceBetween(currentLocation.latitude, currentLocation.longitude, eventCoord.latitude, eventCoord.longitude, results);
                    if (results[0] <= 5000) {


                        MarkerOptions mo = new MarkerOptions();
                        mo.position(eventCoord);
                        try {
                            mo.title(habitEvent.getHabitEventTitle());
                        } catch (HabitEventNameException e) {
                            e.printStackTrace();
                        }
                        mo.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                        habitEventMarker = mMap.addMarker(mo);
                        //add marker
                        mMap.addMarker(mo);
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(eventCoord));


                    } else {
                        try {
                            mMap.addMarker(new MarkerOptions().position(eventCoord).title(habitEvent.getHabitEventTitle()));
                        } catch (HabitEventNameException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
            else if(highlightMode == 5){

                for (int i = 0; i < HabitHistoryController.getInstance().getFilteredHabitHistory().size(); i++) {
                    Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_LONG).show();

                    HabitEvent habitEvent = HabitHistoryController.getInstance().getFilteredHabitHistory().get(i);
                    Geolocation geolocation = habitEvent.getGeolocation();
                    LatLng eventCoord = geolocation.getLatLng();

                    float results[] = new float[10];
                    Location.distanceBetween(currentLocation.latitude, currentLocation.longitude, eventCoord.latitude, eventCoord.longitude, results);
                    if (results[0] <= 5000) {


                        MarkerOptions mo = new MarkerOptions();
                        mo.position(eventCoord);
                        try {
                            mo.title(habitEvent.getHabitEventTitle());
                        } catch (HabitEventNameException e) {
                            e.printStackTrace();
                        }
                        mo.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                        habitEventMarker = mMap.addMarker(mo);
                        //add marker
                        mMap.addMarker(mo);
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(eventCoord));


                    } else {
                        try {
                            mMap.addMarker(new MarkerOptions().position(eventCoord).title(habitEvent.getHabitEventTitle()));
                        } catch (HabitEventNameException e) {
                            e.printStackTrace();
                        }
                    }

                }

            }
            else if(highlightMode == 6){
                for (int i = 0; i < HabitHistoryController.getInstance().getFilteredHabitHistory().size(); i++) {
                    Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_LONG).show();

                    HabitEvent habitEvent = HabitHistoryController.getInstance().getFilteredHabitHistory().get(i);
                    Geolocation geolocation = habitEvent.getGeolocation();
                    LatLng eventCoord = geolocation.getLatLng();

                    float results[] = new float[10];
                    Location.distanceBetween(currentLocation.latitude, currentLocation.longitude, eventCoord.latitude, eventCoord.longitude, results);
                    if (results[0] <= 5000) {


                        MarkerOptions mo = new MarkerOptions();
                        mo.position(eventCoord);
                        try {
                            mo.title(habitEvent.getHabitEventTitle());
                        } catch (HabitEventNameException e) {
                            e.printStackTrace();
                        }
                        mo.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                        habitEventMarker = mMap.addMarker(mo);
                        //add marker
                        mMap.addMarker(mo);
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(eventCoord));


                    } else {
                        try {
                            mMap.addMarker(new MarkerOptions().position(eventCoord).title(habitEvent.getHabitEventTitle()));
                        } catch (HabitEventNameException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "Cannot find current location! ", Toast.LENGTH_LONG).show();

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case REQUEST_LOCATION_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    //permission is granted
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        if (client == null){
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                //permission is denied
                else{
                    Toast.makeText(this,"Permission Denied", Toast.LENGTH_LONG).show();

                }
                return;

        }
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

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);

        }
    }

//    public void onClick(View v){
//        if(v.getId() == R.id.B_highlight) {
//
//            int len = eventList.size();
//            for (int i = 0; i < len; i++) {
//
//                habitEvent = eventList.get(i);
//                Geolocation geolocation = habitEvent.getGeolocation();
//                LatLng eventCoord = geolocation.getLatLng();
//                MarkerOptions mo = new MarkerOptions();
//                mo.position(eventCoord);
//                mo.title("Your Search result");
//
//                habitEventMarker=mMap.addMarker(mo);
//
//                //add marker
//                mMap.addMarker(mo);
//                mMap.animateCamera(CameraUpdateFactory.newLatLng(eventCoord));
//                Toast.makeText(getApplicationContext(), "Genji", Toast.LENGTH_LONG).show();
//
//
//            }
//        }
//        if(v.getId() == R.id.B_followed){
//            Toast.makeText(getApplicationContext(), "Rua!", Toast.LENGTH_LONG).show();
//        }
//    }




    protected synchronized void buildGoogleApiClient(){
        client= new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        client.connect();
    }


    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;

        //check if there is a currentLocationMarker already
        if(currentLocationMarker != null){
            currentLocationMarker.remove();
        }

        //set the new location
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        //new marker's option
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

        currentLocationMarker = mMap.addMarker(markerOptions);

        //move camera to the current position
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));

        currentLocation = latLng;

        if (client != null){
            LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
        }

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();

        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }

    }


    public boolean checkLocationPermission(){

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);

            }
            else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
            return false;
        }
        else
            return true;
    }
    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

