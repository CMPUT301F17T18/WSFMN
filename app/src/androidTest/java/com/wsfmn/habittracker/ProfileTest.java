package com.wsfmn.habittracker;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Fredric on 2017-10-21.
 */

public class ProfileTest extends ActivityInstrumentationTestCase2{

    public ProfileTest() {
        super(MainActivity.class);
    }

    public void testfollowUser(){
        Profile requestTest = new Profile();
        requestTest.followUser("Own Username");

        
    }

    public void testshareWithUser(){
        Profile requestTest = new Profile();


    }



}
