package com.wsfmn.habittracker.habittracker;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.robotium.solo.Solo;
import com.wsfmn.habittracker.AddNewHabitActivity;
import com.wsfmn.habittracker.HabitListViewActivity;

/**
 * Created by musaed on 2017-11-06.
 */

public class HabitListViewActivityTest extends ActivityInstrumentationTestCase2<HabitListViewActivity> {

    private Solo solo;

    public HabitListViewActivityTest() {
        super(com.wsfmn.habittracker.HabitListViewActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void setUp() throws Exception {
        Log.d("TAG1", "setUp()");
        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void testClickHabitList(){
        HabitListViewActivity activity = (HabitListViewActivity) solo.getCurrentActivity();

        solo.assertCurrentActivity("Wrong Activity", HabitListViewActivity.class);
        solo.clickOnButton("Add Habit");
        solo.assertCurrentActivity("Wrong Activity", AddNewHabitActivity.class);

        solo.goBack();
    }
}
