package com.wsfmn.habittracker.habittracker;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.robotium.solo.Solo;
import com.wsfmn.habittracker.AddNewHabitActivity;

/**
 * Created by musaed on 2017-11-06.
 */

public class AddNewHabitActivityTest extends ActivityInstrumentationTestCase2<AddNewHabitActivity> {

    private Solo solo;

    public AddNewHabitActivityTest() {
        super(com.wsfmn.habittracker.AddNewHabitActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void setUp() throws Exception {
        Log.d("TAG1", "setUp()");
        solo = new Solo(getInstrumentation(),getActivity());
    }

    /*
        public void testClickTweetList(){
            LonelyTwitterActivity activity = (LonelyTwitterActivity) solo.getCurrentActivity();

            solo.assertCurrentActivity("Wrong Activity", LonelyTwitterActivity.class);
            solo.clickOnButton("Clear");

            solo.enterText((EditText) solo.getView(R.id.body), "Test Tweet!");
            solo.clickOnButton("Save");
            solo.waitForText("Test Tweet");


            final ListView oldTweetsList = activity.getOldTweetsList();
            Tweet tweet =(Tweet) oldTweetsList.getItemAtPosition(0);
            assertEquals("Test Tweet!", tweet.getMessage());


            solo.clickInList(0);


            solo.assertCurrentActivity("Wrong Activity", EditTweetActivity.class);

            assertTrue(solo.waitForText("New Activity"));

            solo.goBack();
            solo.assertCurrentActivity("Wrong Activity", LonelyTwitterActivity.class);

        }*/
}
