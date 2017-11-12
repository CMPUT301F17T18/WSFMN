package com.wsfmn.habit;

import android.location.Address;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ${WeiLi5} on ${12}.
 */

public class Geolocation {
    private Address myAddress;
    private LatLng latLng;

    public Geolocation(){
        myAddress = null;
        latLng = null;

    }

    public Geolocation(Address myAddress, LatLng latLng){
        this.setMyAddress(myAddress);
        this.setLatLng(latLng);

    }

    public Address getAddress(){
        return this.myAddress;

    }

    public LatLng getLatLng(){
        return this.latLng;

    }

    public void setMyAddress(Address myAddress){
        this.myAddress = myAddress;

    }

    public void setLatLng(LatLng latLng){
        this.latLng = latLng;

    }


}