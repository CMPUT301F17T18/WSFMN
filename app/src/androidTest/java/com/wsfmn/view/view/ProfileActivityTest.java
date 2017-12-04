package com.wsfmn.view.view;


import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;
import com.wsfmn.controller.OnlineController;
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

    // Must wipe memory first, There needs to be no username in local files.
    // Test if we can get to UserNameActivity from ProfileActivity if there is no profilename.
    public void testProfileName(){
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
        solo.enterText((EditText) solo.getView(R.id.yourUserName), "test123456");
        solo.clickOnButton("Confirm");
        solo.clickOnButton("OK");

        solo.enterText((EditText) solo.getView(R.id.yourUserName), "test");
        solo.clickOnButton("Confirm");
        solo.clickOnButton("OK");

        solo.waitForActivity(ProfileActivity.class, 100);
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);


        OnlineController.DeleteProfileName online = new OnlineController.DeleteProfileName();
        online.execute("test123456test");



        OnlineController check= new OnlineController();
        assertEquals(true, check.checkName("test123456test"));


    }

    //Part 5
    public void testShareButton(){

    }

    //Part 5
    public void testFollowButton(){

    }


    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

}
