package com.wsfmn.habittracker.habittracker;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;
import com.wsfmn.habit.Date;
import com.wsfmn.habit.DateNotValidException;
import com.wsfmn.habit.Habit;
import com.wsfmn.habit.HabitEvent;
import com.wsfmn.habit.HabitEventNameException;
import com.wsfmn.habit.HabitReasonTooLongException;
import com.wsfmn.habit.HabitTitleTooLongException;
import com.wsfmn.habitcontroller.HabitHistoryController;
import com.wsfmn.habitcontroller.HabitListController;
import com.wsfmn.habittracker.AddNewHabitActivity;
import com.wsfmn.habittracker.HabitEventActivity;
import com.wsfmn.habittracker.HabitHistoryActivity;
import com.wsfmn.habittracker.HabitListViewActivity;
import com.wsfmn.habittracker.R;

/**
 * Created by siddhant on 2017-11-13.
 */

public class HabitEventActivityTest extends ActivityInstrumentationTestCase2<HabitEventActivity> {

    private Solo solo;

    public HabitEventActivityTest(){
        super(com.wsfmn.habittracker.HabitEventActivity.class);}

    protected void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testAddHabitEvent() throws HabitEventNameException, DateNotValidException, HabitTitleTooLongException, HabitReasonTooLongException {

        HabitListController c = HabitListController.getInstance();
        Habit habit = new Habit("Swimming","lose Weight", new Date());
        c.addHabit(habit);

        solo.assertCurrentActivity("Could not open HabitEventActivity", HabitEventActivity.class);
        solo.sleep(5000);
        solo.enterText((EditText) solo.getView(R.id.nameEvent), "Swimming Class");
        solo.clickOnButton("Select Habit");
        solo.sleep(5000);
        solo.clickInList(0);
        solo.enterText((EditText)solo.getView(R.id.Comment), "Butterfly");

        solo.clickOnButton("Add Habit Event");
        solo.sleep(5000);
        solo.assertCurrentActivity("Could not view list of HabitEvents", HabitHistoryActivity.class);
        solo.sleep(5000);

        HabitHistoryController control = HabitHistoryController.getInstance();
        HabitEvent habitE = control.get(control.size()-1);
        assertNotNull(habitE);
        assertEquals("Swimming Class", habitE.getHabitEventTitle());
        HabitHistoryController.remove(habitE);
        HabitHistoryController.store();
        c.deleteHabit(habit);
    }
}