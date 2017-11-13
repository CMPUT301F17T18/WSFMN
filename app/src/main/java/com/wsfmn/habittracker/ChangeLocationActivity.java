package com.wsfmn.habittracker;

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
import android.os.Message;
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ChangeLocationActivity extends AppCompatActivity {

    private Button button;
    private Button B_new;
    private Button B_confirm;
    private TextView T_address;
    private TextView T_coord;
    private LocationManager locationManager;
    private LocationListener listener;
    private EditText E_address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_change_location);

        T_coord = (TextView) findViewById(R.id.T_C_coordination);
        T_address = (TextView) findViewById(R.id.T_C_address);
        button = (Button) findViewById(R.id.B_C_Current);
        B_new = (Button) findViewById(R.id.B_C_New);
        B_confirm = (Button) findViewById(R.id.B_C_confirm);
        E_address = (EditText) findViewById(R.id.E_C_address);

        readLatlng();
        readAddress();





        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                T_coord.setText("");
                T_address.setText("");

                T_coord.append("\n " + location.getLongitude() + " " + location.getLatitude());
                Geocoder geocoder = new Geocoder(ChangeLocationActivity.this);
                LatLng latLng = new LatLng(location.getLongitude(),location.getLatitude());
                try {
                    List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    Address myAddress = addressList.get(0);

                    String knownName = addressList.get(0).getFeatureName();
                    //set to Geolocation
                    Geolocation geolocation = new Geolocation(knownName,latLng);
                    T_address.append(myAddress.toString());

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


    public void readLatlng()
    {
        try {
            String latLngRead;
            FileInputStream fileInputStream = openFileInput("save_coordination");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();

            while((latLngRead = bufferedReader.readLine())!= null)
            {
                stringBuffer.append(latLngRead);
            }
            T_coord.setText(stringBuffer.toString());
            T_coord.setVisibility(View.VISIBLE);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readAddress()
    {
        try {
            String addressRead;
            FileInputStream fileInputStream = openFileInput("save_address");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();

            while((addressRead = bufferedReader.readLine())!= null)
            {
                stringBuffer.append(addressRead);
            }
            T_address.setText(stringBuffer.toString());
            T_address.setVisibility(View.VISIBLE);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    void confirm_button(){
        B_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveLatlng();
                saveAddress();

                Intent returnIntent = new Intent();
                setResult(ChangeLocationActivity.RESULT_CANCELED, returnIntent);
                finish();
                //Intent  intent = new Intent(ChangeLocationActivity.this,habitHistoryDetailActivity.class);
                //startActivity(intent);

            }

        });
    }

    void newplace_button(){


        B_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //noinspection MissingPermission
                if (ActivityCompat.checkSelfPermission(ChangeLocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ChangeLocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                String location = E_address.getText().toString();
                List<Address> addressList = null;

                //use Geocoder class here
                Geocoder geocoder = new Geocoder(ChangeLocationActivity.this);
                try {
                    addressList = geocoder.getFromLocationName(location, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Address myAddress = addressList.get(0);
                String knownName = addressList.get(0).getFeatureName();
                LatLng latlng = new LatLng(myAddress.getLatitude(), myAddress.getLongitude());
                T_coord.setText("");
                T_address.setText("");

                T_coord.append("\n"+myAddress.getLatitude()+" "+myAddress.getLongitude());
                T_address.append(myAddress.toString());


                Geolocation geolocation = new Geolocation(knownName,latlng);


            }
        });


    }

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
                if (ActivityCompat.checkSelfPermission(ChangeLocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ChangeLocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
