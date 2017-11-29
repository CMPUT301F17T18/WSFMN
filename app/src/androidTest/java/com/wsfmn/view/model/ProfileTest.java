package com.wsfmn.view.model;

import android.test.ActivityInstrumentationTestCase2;

import com.wsfmn.model.Profile;
import com.wsfmn.model.Request;
import com.wsfmn.view.MainActivity;

/**
 * Created by Fredric on 2017-10-21.
 */

public class ProfileTest extends ActivityInstrumentationTestCase2{

    public ProfileTest() {
        super(MainActivity.class);
    }

    public void testfollowUser(){
        Profile requestTest = new Profile();
        Request test = new Request("Own Username");
        requestTest.followUser("Own Username");

        //Other user should check their profile, but in this case own user sends to themself.
        // Following request.
        assertTrue(requestTest.hasRequest(test));
    }

    public void testshareWithUser(){
        Profile requestTest = new Profile();
        Request test = new Request("Own Username");
        requestTest.followUser("Own Username");

        //Other user should check their profile, but in this case own user sends to themself.
        // Sharing request
        assertTrue(requestTest.hasRequest(test));

    }

    public void testAddRequest(){
        Profile profile = new Profile();
        Request request = new Request("Jimmy");
        profile.addRequest(request);
        assertTrue(profile.hasRequest(request));
    }

    public void testDeclineRequest(){
        Profile profile = new Profile();
        Request request = new Request("Jimmy");
        profile.addRequest(request);
        profile.declineRequest(request);
        assertFalse(profile.hasRequest(request));
    }

    public void testAcceptRequest(){
        Profile profile = new Profile();
        Request request = new Request("Jimmy");
        profile.addRequest(request);
        profile.acceptRequest(request);
        assertFalse(profile.hasRequest(request));
    }



}
