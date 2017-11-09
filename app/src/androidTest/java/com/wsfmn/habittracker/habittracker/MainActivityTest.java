package com.wsfmn.habittracker.habittracker;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.robotium.solo.Solo;
import com.wsfmn.habittracker.HabitListViewActivity;
import com.wsfmn.habittracker.HabitsForTodayActivity;
import com.wsfmn.habittracker.MainActivity;
import com.wsfmn.habittracker.ProfileActivity;

/**
 * Created by musaed on 2017-11-06.
 */

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    public MainActivityTest() {
        super(com.wsfmn.habittracker.MainActivity.class);
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
        solo.assertCurrentActivity("Wrong Activity", HabitListViewActivity.class);
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
}
