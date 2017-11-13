package com.wsfmn.habit;

import android.location.Address;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ${WeiLi5} on ${12}.
 */

public class Geolocation {
    private Address myAddress;
    private LatLng latLng;

    /**
     *  Created a Habit object
     *

     */

    public Geolocation(){
        myAddress = null;
        latLng = null;

    }
    /**
     *  Created a Habit object
     *
     * @param myAddress is the address that can be identified by google map
     * @param latLng is a coordination(latitude, longtitude)
     */
    public Geolocation(Address myAddress, LatLng latLng){
        this.setMyAddress(myAddress);
        this.setLatLng(latLng);

    }
    /**
     * @Return return the address
     */
    public Address getAddress(){
        return this.myAddress;

    }
    /**
     * @Return return the latLng
     *
     */
    public LatLng getLatLng(){
        return this.latLng;

    }

    /**
     *
     * @param myAddress the address of a location
     */

    public void setMyAddress(Address myAddress){
        this.myAddress = myAddress;

    }

    /**
     *
     * @param latLng the coordination of a location
     */

    public void setLatLng(LatLng latLng){
        this.latLng = latLng;

    }


}