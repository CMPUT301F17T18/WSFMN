package com.wsfmn.habittracker.habittracker;


import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;
import com.wsfmn.habitcontroller.OnlineController;
import com.wsfmn.habittracker.ProfileActivity;
import com.wsfmn.habittracker.R;
import com.wsfmn.habittracker.UserName_Activity;

/**
 * Created by Fredric on 2017-11-12.
 */

public class UserName_ActivityTest extends ActivityInstrumentationTestCase2<UserName_Activity> {
    private Solo solo;

    public UserName_ActivityTest() {
        super(com.wsfmn.habittracker.UserName_Activity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();

    }

    // Testing if name is checked on ElasticSearch and if we can store it.
    // Test a name already on ElasticSearch, Then test a name not on ElasticSearch.
    public void testUniqueName() {
        solo.assertCurrentActivity("Wrong Activity", UserName_Activity.class);
        solo.enterText((EditText) solo.getView(R.id.yourUserName), "Test");
        solo.clickOnButton("Confirm");
        solo.clickOnButton("OK");
        solo.assertCurrentActivity("Wrong Activity", UserName_Activity.class);
        solo.enterText((EditText) solo.getView(R.id.yourUserName), "DifferentTest");
        solo.clickOnButton("Confirm");
        solo.clickOnButton("OK");
        solo.waitForActivity(ProfileActivity.class);

        OnlineController.DeleteProfileName online = new OnlineController.DeleteProfileName();
        online.execute("DifferentTest");

        OnlineController check= new OnlineController();
        assertEquals(true, check.checkName("DifferentTest"));
    }




    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

}
