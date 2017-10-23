package com.wsfmn.habittracker;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

/**
 * Created by ${WeiLi5} on ${12}.
 */


public class Geolocation {
    Context context;
    Location location;
    LocationManager locationManager;
    String provider = LocationManager.GPS_PROVIDER;

    public Geolocation(Context context){
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //TODO: I think it requires permission in Manifest
    }

    public Location getLocation(){
        return this.location;
    }

    public void setLocation(Location location){
        this.location = location;
    }
}