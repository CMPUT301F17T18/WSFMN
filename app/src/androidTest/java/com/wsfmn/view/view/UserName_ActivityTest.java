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

public class UserName_ActivityTest extends ActivityInstrumentationTestCase2<UserNameActivity> {
    private Solo solo;
    private OnlineController check= new OnlineController();

    public UserName_ActivityTest() {
        super(UserNameActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();

    }


    //UNCOMMENT private static final String SERVER_URL = "https://5b3c205796b755b5db6f9b28b41fa441.us-east-1.aws.found.io:9243/";
    // COMMENT THE OTHER URL... ELASTIC SEARCH COULD BE DOWN

    // PLEASE WIPE MEMORY TO TEST!! "MIGHT" NEED TO DELETE "test123456' on elasticsearch manually.
    // elastic search has a bit of a problem right now...
    // Testing if name is checked on ElasticSearch and if we can store it.
    // Test a name already on ElasticSearch, Then test a name not on ElasticSearch.
    // yourUserName is sometimes not found. Clean Project if resource name: 'yourUserName' is not found!.
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
    //test profile... Check/ decline the request/ have friend added already... to the index
    //

    public void testProfile(){


    }




    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

}
