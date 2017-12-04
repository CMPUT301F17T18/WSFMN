/*
 * Copyright © 2017 Team 18 (WSFMN), CMPUT301, University of Alberta – All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact nmayne@ualberta.ca.
 *
 *  Team 18 is: Musaed Alsobaie, Siddhant Khanna, Wei Li, Nicholas Mayne, Fredric Mendi.
 */

package com.wsfmn.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ${WeiLi5} on ${12}.
 */
public class Geolocation {
    private String myAddress;
    private LatLng latLng;

    /**
     * Create a Geolocation object.
     */
    public Geolocation(){
        myAddress = null;
        latLng = null;
    }

    /**
     * Create a Geolocation object.
     *
     * @param myAddress is the address that can be identified by google map
     * @param latLng is a coordination(latitude, longtitude)
     */
    public Geolocation(String myAddress, LatLng latLng){
        this.setMyAddress(myAddress);
        this.setLatLng(latLng);
    }

    /**
     * Get the address of the Geolocation.
     *
     * @Return return the address
     */
    public String getAddress(){
        return this.myAddress;
    }

    /**
     * Get the LatLng coordinates of the geolocation.
     *
     * @Return return the latLng
     */
    public LatLng getLatLng(){
        return this.latLng;
    }

    /**
     * Set the address of the geolocation.
     *
     * @param myAddress the address of a location
     */
    public void setMyAddress(String myAddress){
        this.myAddress = myAddress;
    }

    /**
     * Set the coordinates of the geolocation.
     *
     * @param latLng the coordinates of a location
     */
    public void setLatLng(LatLng latLng){
        this.latLng = latLng;
    }
}