package com.wsfmn.habittracker;

import android.Manifest;
import android.app.Activity;
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
import com.wsfmn.habit.Geolocation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class AddLocationActivity extends AppCompatActivity {

    private Button button;
    private Button B_new;
    private Button B_confirm;
    private TextView T_address;
    private TextView T_coord;
    private LocationManager locationManager;
    private LocationListener listener;
    private EditText E_address;
    private  Geolocation geolocation;
    private LatLng latLng;
    private String knownName;
    private double latitude;
    private double longtitude;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_location);

        T_coord = (TextView) findViewById(R.id.T_coordination);
        T_address = (TextView) findViewById(R.id.T_address);
        button = (Button) findViewById(R.id.B_Current);
        B_new = (Button) findViewById(R.id.B_New);
        B_confirm = (Button) findViewById(R.id.B_confirm);
        E_address = (EditText) findViewById(R.id.E_address);



        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        listener = new LocationListener() {
            @Override
            /**
             *
             * @param location
             */
            public void onLocationChanged(Location location) {

                T_coord.setText("");
                T_address.setText("");

                T_coord.append("\n " + location.getLongitude() + " " + location.getLatitude());
                Geocoder geocoder = new Geocoder(AddLocationActivity.this);

                latitude = location.getLatitude();
                longtitude = location.getLongitude();

                latLng = new LatLng(location.getLongitude(),location.getLatitude());
                try {
                    List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    Address myAddress = addressList.get(0);
                    //set to Geolocation

                    knownName = addressList.get(0).getFeatureName();
                    geolocation = new Geolocation(knownName,latLng);
                    T_address.append(knownName);


                    //Intent  intent = new Intent(AddLocationActivity.this,HabitEventActivity.class);
                    //startActivity(intent);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        configure_button();
        newplace_button();
        confirm_button();
    }
    /**
     *
     * Button method it will save LatLng(coordination)
     */

    public void saveLatlng()
    {
        String latLngSave = T_coord.getText().toString();
        String file_name = "save_coordination";
        try {
            FileOutputStream fileOutputStream = openFileOutput(file_name, MODE_PRIVATE);
            fileOutputStream.write(latLngSave.getBytes());
            fileOutputStream.close();
            Toast.makeText(getApplicationContext(), "Coordination saved", Toast.LENGTH_LONG).show();



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     *
     * Button method it will save Address
     */
    public void saveAddress()
    {
        String addressSave = T_address.getText().toString();
        String file_name = "save_address";
        try {
            FileOutputStream fileOutputStream = openFileOutput(file_name, MODE_PRIVATE);
            fileOutputStream.write(addressSave.getBytes());
            fileOutputStream.close();
            Toast.makeText(getApplicationContext(), "Address saved", Toast.LENGTH_LONG).show();



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

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
     *
     * Button method it will go back to HabitEventActivity
     */

    void confirm_button(){
        B_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveLatlng();
                saveAddress();

                Intent returnIntent = new Intent();
                //returnIntent.putExtra("new_coordination",latLng);
                returnIntent.putExtra("new_address", knownName);
                returnIntent.putExtra("new_latitude", latitude);
                returnIntent.putExtra("new_longtitude", longtitude);

                setResult(RESULT_OK, returnIntent);
                finish();

                //Intent  intent = new Intent(AddLocationActivity.this,HabitEventActivity.class);
                //startActivity(intent);

            }

        });
    }

    /**
     *
     * Button method it will get the location which is entered by user
     */

    void newplace_button(){


        B_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //noinspection MissingPermission
                if (ActivityCompat.checkSelfPermission(AddLocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddLocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                //String location = E_address.getText().toString();
                List<Address> addressList = null;


                    //use Geocoder class here
                    Geocoder geocoder = new Geocoder(AddLocationActivity.this);
                    try {
                        String location = E_address.getText().toString();
                        addressList = geocoder.getFromLocationName(location, 1);
                        //check if the input address can be found
                        while (addressList.size() == 0){
                            Toast.makeText(getApplicationContext(), "Please enter a validate address", Toast.LENGTH_LONG).show();

                            location = E_address.getText().toString();
                            addressList = geocoder.getFromLocationName(location, 1);

                            Address myAddress = addressList.get(0);
                            knownName = addressList.get(0).getFeatureName();

                            latLng = new LatLng(myAddress.getLatitude(), myAddress.getLongitude());

                            latitude = myAddress.getLatitude();
                            longtitude = myAddress.getLongitude();

                            T_coord.setText("");
                            T_address.setText("");

                            T_coord.append("\n" + myAddress.getLatitude() + " " + myAddress.getLongitude());

                            T_address.append(knownName);


                            geolocation = new Geolocation(knownName, latLng);
                        }

                        Address myAddress = addressList.get(0);
                        knownName = addressList.get(0).getFeatureName();

                        latLng = new LatLng(myAddress.getLatitude(), myAddress.getLongitude());

                        latitude = myAddress.getLatitude();
                        longtitude = myAddress.getLongitude();

                        T_coord.setText("");
                        T_address.setText("");

                        T_coord.append("\n" + myAddress.getLatitude() + " " + myAddress.getLongitude());

                        T_address.append(knownName);


                        geolocation = new Geolocation(knownName, latLng);

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Address is not validate", Toast.LENGTH_LONG).show();

                    }




            }
        });


    }
// code for get readable address
//    Geocoder geocoder;
//    List<Address> addresses;
//    geocoder = new Geocoder(this, Locale.getDefault());
//
//    addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//
//    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//    String city = addresses.get(0).getLocality();
//    String state = addresses.get(0).getAdminArea();
//    String country = addresses.get(0).getCountryName();
//    String postalCode = addresses.get(0).getPostalCode();
//    String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

    /**
     *
     * Button method it will check the permission and get the current location
     */

    void configure_button() {
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
            return;
        }
        // this code won'textView execute IF permissions are not allowed, because in the line above there is return statement.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //noinspection MissingPermission
                if (ActivityCompat.checkSelfPermission(AddLocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddLocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
