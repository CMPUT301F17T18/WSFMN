package com.wsfmn.view.view;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.robotium.solo.Solo;
import com.wsfmn.view.ViewHabitHistoryActivity;
import com.wsfmn.view.ViewHabitListActivity;
import com.wsfmn.view.HabitsForTodayActivity;
import com.wsfmn.view.MainActivity;
import com.wsfmn.view.ProfileActivity;

/**
 * Created by musaed on 2017-11-06.
 */

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    public MainActivityTest() {
        super(com.wsfmn.view.MainActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void setUp() throws Exception {
        Log.d("TAG1", "setUp()");
        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void testViewHabitsButton() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("View Habits");
        solo.assertCurrentActivity("Wrong Activity", ViewHabitListActivity.class);
    }

    public void testHabitsForTodayButton() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("Habits For Today");
        solo.assertCurrentActivity("Wrong Activity", HabitsForTodayActivity.class);
    }

    public void testProfileButton() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("Profile");
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
    }

    public void testViewHabitHistory(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("View Habit History");
        solo.assertCurrentActivity("Wrong Activity", ViewHabitHistoryActivity.class);
    }
}
