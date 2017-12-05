package com.wsfmn.view.view;

/*
 * Copyright © 2017 Team 18 (WSFMN), CMPUT301, University of Alberta – All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact nmayne@ualberta.ca.
 *
 *  Team 18 is: Musaed Alsobaie, Siddhant Khanna, Wei Li, Nicholas Mayne, Fredric Mendi.
 */

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.TextView;

import com.robotium.solo.Solo;
import com.wsfmn.controller.App;
import com.wsfmn.controller.OnlineController;
import com.wsfmn.view.FriendActivity;
import com.wsfmn.view.FriendHabitActivity;
import com.wsfmn.view.ProfileActivity;
import com.wsfmn.view.R;

import static java.lang.Thread.sleep;


/**
 * Created by Fredric on 2017-11-12.
 */



public class ProfileActivityTest extends ActivityInstrumentationTestCase2<ProfileActivity> {
    private Solo solo;

    public ProfileActivityTest() {
        super(com.wsfmn.view.ProfileActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();

    }

    // TEST THIS AFTER TESTING UserName_ActivityTest.
    public void testProfile(){
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
        assertTrue(solo.waitForText("WELCOME"));
        assertTrue(solo.waitForText("test123456")); //check if your name displays

        // check if you are collecting requests from other users.
        // Premade requests so testing can be done
        assertTrue(solo.waitForText("test123456buddy"));
        assertTrue(solo.waitForText("wants to follow you"));


        // test that you cannot send request to yourself
        solo.enterText((EditText) solo.getView(R.id.userName), "test123456test");
        solo.clickOnButton("Follow");
        assertTrue(solo.waitForText("Sorry, Can't Add Yourself!"));
        solo.clearEditText((EditText) solo.getView(R.id.userName));

        // test that you cannot send request to names that dont exist
        solo.enterText((EditText) solo.getView(R.id.userName), "test123456testDIFFERENT");
        solo.clickOnButton("Follow");
        assertTrue(solo.waitForText("User Doesn't Exist!"));
        solo.clearEditText((EditText) solo.getView(R.id.userName));

        // test that you cannot send a request already sent. (Premade to test)
        solo.enterText((EditText) solo.getView(R.id.userName), "test123456buddy");
        solo.clickOnButton("Follow");
        assertTrue(solo.waitForText("Request Already Sent!"));
        solo.clearEditText((EditText) solo.getView(R.id.userName));

        // test that you cannot send a request to someone already in your friend's list
        solo.enterText((EditText) solo.getView(R.id.userName), "test2b");
        solo.clickOnButton("Follow");
        assertTrue(solo.waitForText("This Person Is On Your FriendList Already!"));
        solo.clearEditText((EditText) solo.getView(R.id.userName));

        //Go to another activity.
        solo.clickOnButton("Friend's Events");
        solo.assertCurrentActivity("wrong activity", FriendActivity.class);



    }



    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

}
