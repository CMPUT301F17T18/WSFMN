package com.wsfmn.habittracker.habittracker;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import com.wsfmn.habit.HabitEvent;
import com.wsfmn.habitcontroller.HabitHistoryController;
import com.wsfmn.habittracker.HabitHistoryActivity;

/**
 * Created by siddhant on 2017-11-13.
 */

public class HabitHistoryActivityTest extends ActivityInstrumentationTestCase2<HabitHistoryActivity> {
    private Solo solo;

    public HabitHistoryActivityTest() {
        super(com.wsfmn.habittracker.HabitHistoryActivity.class);
    }

    protected void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testHabitHistory() {
        HabitHistoryActivity activity = (HabitHistoryActivity) solo.getCurrentActivity();
        solo.assertCurrentActivity("Could not open HabitEventActivity", HabitHistoryActivity.class);

        solo.clickOnButton("Add Event");
        solo.goBackToActivity("HabitHistoryActivity");
        solo.clickInList(0);
        solo.goBackToActivity("HabitHistoryActivity");
        HabitHistoryController control = HabitHistoryController.getInstance();
        HabitEvent habitE = control.get(control.size()-1);
        assertNotNull(habitE);

    }
}