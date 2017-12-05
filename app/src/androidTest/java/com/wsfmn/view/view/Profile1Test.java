package com.wsfmn.view.view;


import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;
import com.wsfmn.controller.OnlineController;
import com.wsfmn.view.ProfileActivity;
import com.wsfmn.view.R;
import com.wsfmn.view.UserNameActivity;

/**
 * Created by Fredric on 2017-11-12.
 */

public class Profile1Test extends ActivityInstrumentationTestCase2<UserNameActivity> {
    private Solo solo;
    private OnlineController check= new OnlineController();

    public Profile1Test() {
        super(UserNameActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();

    }

    // NOTICE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // YOU WILL NEED TO WIPE DATA IF YOU WISH TO USE THE APP AFTER TESTING ALL INTENT TESTING .

    // PLEASE WIPE MEMORY TO TEST!! "MIGHT" NEED TO DELETE "test123456' on elasticsearch manually.
    // Testing if name is checked on ElasticSearch and if we can store it.
    // Test a name already on ElasticSearch, Then test a name not on ElasticSearch.
    // yourUserName is sometimes not found. Clean Project if resource name: 'yourUserName' is not found!.
    // DO PROFILE1 FIRST
    public void testUniqueName() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", UserNameActivity.class);
        solo.enterText((EditText) solo.getView(R.id.yourUserName), "test123456");
        solo.clickOnButton("Confirm");
        solo.clickOnButton("OK");
        solo.assertCurrentActivity("Wrong Activity", UserNameActivity.class);
        solo.enterText((EditText) solo.getView(R.id.yourUserName), "test");
        solo.clickOnButton("Confirm");
        solo.clickOnButton("OK");
        solo.waitForActivity(ProfileActivity.class);
        assertEquals(false, check.checkName("test123456test"));

    }

    // NOTICE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // YOU WILL NEED TO WIPE DATA IF YOU WISH TO USE THE APP AFTER TESTING ALL INTENT TESTING .

    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

}
