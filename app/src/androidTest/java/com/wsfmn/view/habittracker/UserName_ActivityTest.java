package com.wsfmn.view.habittracker;


import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;
import com.wsfmn.controller.OnlineController;
import com.wsfmn.view.ProfileActivity;
import com.wsfmn.view.R;
import com.wsfmn.view.UserName_Activity;

/**
 * Created by Fredric on 2017-11-12.
 */

public class UserName_ActivityTest extends ActivityInstrumentationTestCase2<UserName_Activity> {
    private Solo solo;

    public UserName_ActivityTest() {
        super(com.wsfmn.view.UserName_Activity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();

    }

    // Testing if name is checked on ElasticSearch and if we can store it.
    // Test a name already on ElasticSearch, Then test a name not on ElasticSearch.
    // yourUserName is sometimes not found. Clean Project if resource name: 'yourUserName' is not found!.
    public void testUniqueName() {
        solo.assertCurrentActivity("Wrong Activity", UserName_Activity.class);
        solo.enterText((EditText) solo.getView(R.id.yourUserName), "test");
        solo.clickOnButton("Confirm");
        solo.clickOnButton("OK");
        solo.assertCurrentActivity("Wrong Activity", UserName_Activity.class);
        solo.enterText((EditText) solo.getView(R.id.yourUserName), "DifferentTest");
        solo.clickOnButton("Confirm");
        solo.clickOnButton("OK");
        solo.waitForActivity(ProfileActivity.class);

        OnlineController check= new OnlineController();
        assertEquals(false, check.checkName("nametestdifferenttest"));

        OnlineController.DeleteProfileName online = new OnlineController.DeleteProfileName();
        online.execute("nametestdifferenttest");


        assertEquals(true, check.checkName("namedifferenttest"));
    }




    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

}
