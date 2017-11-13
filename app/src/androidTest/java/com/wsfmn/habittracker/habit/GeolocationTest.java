package com.wsfmn.habittracker.habit;

import android.test.ActivityInstrumentationTestCase2;

import com.google.android.gms.maps.model.LatLng;
import com.wsfmn.habit.Geolocation;

/**
 * Created by ${WeiLi5} on ${12}.
 */

public class GeolocationTest extends ActivityInstrumentationTestCase2 {

    public GeolocationTest() {super(Geolocation.class);}

    public void testGetAddress(){
        Geolocation geolocation = null;
        LatLng latLng = new LatLng(23.4555453556, 11.145315551);
        geolocation = new Geolocation("Gharb Darfur", latLng);

        assertEquals(geolocation.getAddress(), "Gharb Darfur");
    }

    public void testGetLatlng(){
        Geolocation geolocation = null;
        LatLng latLng = new LatLng(23.4555453556, 11.145315551);
        geolocation = new Geolocation("Gharb Darfur", latLng);

        assertEquals(geolocation.getLatLng(), latLng);

    }

    public  void testSetAddress(){
        Geolocation geolocation = null;
        LatLng latLng = new LatLng(23.4555453556, 11.145315551);
        geolocation = new Geolocation("Tokyo", latLng);

        geolocation.setMyAddress("Gharb Darfur");

        assertEquals(geolocation.getAddress(), "Gharb Darfur");



    }

    public  void testSetLatLng() {
        Geolocation geolocation = null;
        LatLng latLng = new LatLng(12.1115145311, 99.333455333);
        geolocation = new Geolocation("Tokyo", latLng);

        LatLng newLatlng = new LatLng(23.4555453556, 11.145315551);
        geolocation.setLatLng(newLatlng);

        assertEquals(geolocation.getLatLng(), newLatlng);
    }

}