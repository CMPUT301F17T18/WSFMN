/*
 * Copyright © 2017 Team 18 (WSFMN), CMPUT301, University of Alberta – All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact nmayne@ualberta.ca.
 *
 *  Team 18 is: Musaed Alsobaie, Siddhant Khanna, Wei Li, Nicholas Mayne, Fredric Mendi.
 */

package com.wsfmn.view.view;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import com.wsfmn.controller.OnlineController;
import com.wsfmn.view.FriendActivity;
import com.wsfmn.view.FriendHabitActivity;

/**
 * Created by Fredric on 2017-12-04.
 */

public class Profile3Test extends ActivityInstrumentationTestCase2<FriendActivity> {

    private Solo solo;

    public Profile3Test() {
        super(com.wsfmn.view.FriendActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();

    }
    // NOTICE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // YOU WILL NEED TO WIPE DATA IF YOU WISH TO USE THE APP AFTER TESTING ALL INTENT TESTING .
    // USE AFTER PROFILE2TEST
    // User After ProfileActivity Test, Also tests FriendHabitActivity.
    // Premade friends to test on a single emulator.
    public void testFriend() {
        solo.assertCurrentActivity("Wrong Activity", FriendActivity.class);

        // Can we grab habits from friends on elastic search?
        assertTrue(solo.waitForText("test2b"));
        assertTrue(solo.waitForText("Project Demo"));

        // Can we go to their habit and view their details and events?
        solo.clickInList(0);
        solo.assertCurrentActivity("wrong activity", FriendHabitActivity.class);
        // Can we see the event details and it's most recent event?
        assertTrue(solo.waitForText("Project Demo"));
        assertTrue(solo.waitForText("testing"));
        assertTrue(solo.waitForText("2017 / 12 / 4"));
        assertTrue(solo.waitForText("recent event"));

        OnlineController.DeleteProfileName online = new OnlineController.DeleteProfileName();
        online.execute("test123456test");
    }



    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

    // NOTICE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // YOU WILL NEED TO WIPE DATA IF YOU WISH TO USE THE APP AFTER TESTING ALL INTENT TESTING .


}
