package com.wsfmn.habittracker.habittracker;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;
import com.wsfmn.habit.HabitEvent;
import com.wsfmn.habit.HabitEventNameException;
import com.wsfmn.habitcontroller.HabitHistoryController;
import com.wsfmn.habittracker.HabitHistoryActivity;
import com.wsfmn.habittracker.R;
import com.wsfmn.habittracker.habitHistoryDetailActivity;

/**
 * Created by siddhant on 2017-11-13.
 */

public class HabitHistoryDetailActivityTest extends ActivityInstrumentationTestCase2<habitHistoryDetailActivity> {
    private Solo solo;

    public HabitHistoryDetailActivityTest(){
        super(com.wsfmn.habittracker.habitHistoryDetailActivity.class);}

    protected void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testAddHabitEvent() throws HabitEventNameException {
        solo.assertCurrentActivity("Could not open HabitEventDetail", habitHistoryDetailActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.nameEvent2));
        solo.enterText((EditText) solo.getView(R.id.nameEvent2), "Swimming Competition");
        solo.clickOnButton("Change Habit");
        solo.clickInList(0);
        solo.clearEditText((EditText)solo.getView(R.id.Comment2));
        solo.enterText((EditText)solo.getView(R.id.Comment2), "100 m");

        solo.clickOnButton("Confirm");
        HabitHistoryController control = HabitHistoryController.getInstance();
        HabitEvent habitE = control.get(0);
        assertEquals("Habit Event not Modified", "Swimming Competition", habitE.getHabitEventTitle());

        int size = control.size();
        solo.clickOnButton("DELETE");
        int size2 = control.size();
        size = size;
        assertEquals("Delete Habit Event did not occur", size, size2);


    }
}

