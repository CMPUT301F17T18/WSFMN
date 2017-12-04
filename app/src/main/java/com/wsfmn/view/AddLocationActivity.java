package com.wsfmn.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.wsfmn.controller.OnlineController;
import com.wsfmn.model.Geolocation;

import java.io.IOException;
import java.util.List;

/**
 * A class for getting location details from a user for inclusion in a Habit Event.
 */
public class AddLocationActivity extends AppCompatActivity {

    private Button use_GPS;
    private Button search_for_location;
    private Button B_confirm;
    private TextView T_address;
    private TextView T_coord;
    private LocationManager locationManager;
    private LocationListener listener;
    private EditText E_address;
    private Geolocation geolocation;
    private LatLng latLng;
    private String knownName;
    private double latitude;
    private double longitude;


    /**
     * Setup activity for capturing user's current location or a location of their choice.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_location);

        T_coord = (TextView) findViewById(R.id.t_coordination);
        T_address = (TextView) findViewById(R.id.t_address);
        use_GPS = (Button) findViewById(R.id.use_GPS);
        search_for_location = (Button) findViewById(R.id.search_for_location);
        B_confirm = (Button) findViewById(R.id.b_confirm);
        E_address = (EditText) findViewById(R.id.e_address);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Instantiate vars with data from current HabitEvent
        Bundle b = getIntent().getExtras();
        if (b != null) {
            knownName = b.getString("address");
            latitude = b.getDouble("latitude");
            longitude = b.getDouble("longitude");
            T_address.setText(knownName);
            T_coord.setText(latitude + " " + longitude);
        }

        listener = new LocationListener() {
            /**
             *
             * @param location
             */
            @Override
            public void onLocationChanged(Location location) {
                T_coord.setText(location.getLongitude() + " " + location.getLatitude());
                Geocoder geocoder = new Geocoder(AddLocationActivity.this);

                latitude = location.getLatitude();
                longitude = location.getLongitude();

                latLng = new LatLng(location.getLongitude(),location.getLatitude());
                try {
                    List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                    knownName = addressList.get(0).getFeatureName();
                    geolocation = new Geolocation(knownName,latLng);
                    // Using "GPS" instead of knownName because knownName is often garbage if using GPS
                    if (knownName.matches("\\d.*")) { // starts with a digit
                        knownName = "GPS";
                    }

                    T_address.setText(knownName);

                    //Intent  intent = new Intent(AddLocationActivity.this,AddNewHabitEventActivity.class);
                    //startActivity(intent);


                } catch (IOException e) {
                    geolocation = null;
                    e.printStackTrace();
                }
            }

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

        configure_button();
    }

    /**
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    /**
     * Return to AddNewHabitEventActivity to save the GeoLocation results.
     *
     */
    public void confirmLocation(View view) {
        Intent returnIntent = new Intent();

        if (latitude != 0 && longitude != 0) {
            returnIntent.putExtra("latitude", latitude);
            returnIntent.putExtra("longitude", longitude);
            returnIntent.putExtra("address", knownName);
            setResult(RESULT_OK, returnIntent);
        }

        finish();
    }


    /**
     * Search for the coordinates and name details of a location entered by user.
     *
     */
    public void searchForLocation(View view){
        // Check if the User is connected to the internet
        if (!OnlineController.isConnected()) {
            Toast.makeText(getApplicationContext(), "Requires Internet Connection", Toast.LENGTH_LONG).show();
            return;
        }

        //Check if the user has permission
        if (ActivityCompat.checkSelfPermission(
                AddLocationActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                        AddLocationActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        List<Address> addressList;
        Geocoder geocoder = new Geocoder(AddLocationActivity.this);
        try {
            String location = E_address.getText().toString();

            addressList = geocoder.getFromLocationName(location, 1);
            //check if the input address can be found
            if (addressList.size() == 0) {
                Toast.makeText(getApplicationContext(), "Invalid Address", Toast.LENGTH_LONG).show();
            } else {
                location = E_address.getText().toString();
                addressList = geocoder.getFromLocationName(location, 1);

                Address myAddress = addressList.get(0);

                knownName = myAddress.getAddressLine(0) + "\n" +
                        myAddress.getAddressLine(1) + "\n";
                if (myAddress.getAddressLine(2) != null) {
                    knownName = knownName + myAddress.getAddressLine(2);
                }

                latLng = new LatLng(myAddress.getLatitude(), myAddress.getLongitude());

                latitude = myAddress.getLatitude();
                longitude = myAddress.getLongitude();

                T_coord.setText(myAddress.getLatitude() + " " + myAddress.getLongitude());
                T_address.setText(knownName);

                geolocation = new Geolocation(knownName, latLng);
            }

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Invalid Address", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * When user click the confirm button.
     * Check permission and get the current location.
     *
     */

    void configure_button() {
        // Check for permissions
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                        this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                        new String[]{
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.INTERNET}
                        , 10);
            }
            return;
        }
        // this code won't go textView execute IF permissions are not allowed, because in the line above there is return statement.
        use_GPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //noinspection MissingPermission
                if (ActivityCompat.checkSelfPermission(
                        AddLocationActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(
                                AddLocationActivity.this,
                                Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates("gps", 5000, 0, listener);
            }
        });
    }
}