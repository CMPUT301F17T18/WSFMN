package com.wsfmn.habittracker;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.robotium.solo.Solo;

/**
 * Created by musaed on 2017-11-06.
 */

public class HabitListViewDetailActivityTest extends ActivityInstrumentationTestCase2<HabitListViewDetailActivity> {

    private Solo solo;

    public HabitListViewDetailActivityTest() {
        super(com.wsfmn.habittracker.HabitListViewDetailActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void setUp() throws Exception {
        Log.d("TAG1", "setUp()");
        solo = new Solo(getInstrumentation(),getActivity());
    }
}
