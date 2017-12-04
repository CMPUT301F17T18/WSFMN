package com.wsfmn.view.view;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;
import com.wsfmn.model.Date;
import com.wsfmn.exceptions.DateNotValidException;
import com.wsfmn.model.Habit;
import com.wsfmn.model.HabitEvent;
import com.wsfmn.exceptions.HabitEventNameException;
import com.wsfmn.exceptions.HabitReasonTooLongException;
import com.wsfmn.exceptions.HabitTitleTooLongException;
import com.wsfmn.controller.HabitHistoryController;
import com.wsfmn.controller.HabitListController;
import com.wsfmn.view.AddNewHabitEventActivity;
import com.wsfmn.view.ViewHabitHistoryActivity;
import com.wsfmn.view.R;

/**
 * Created by siddhant on 2017-11-13.
 */

public class AddNewHabitEventActivityTest extends ActivityInstrumentationTestCase2<AddNewHabitEventActivity> {

    private Solo solo;

    public AddNewHabitEventActivityTest(){
        super(AddNewHabitEventActivity.class);}

    protected void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testAddHabitEvent() throws HabitEventNameException, DateNotValidException, HabitTitleTooLongException, HabitReasonTooLongException {

        HabitListController c = HabitListController.getInstance();
        Habit habit = new Habit("Swimming","lose Weight", new Date());
        c.addHabit(habit);

        solo.assertCurrentActivity("Could not open AddNewHabitEventActivity", AddNewHabitEventActivity.class);
        solo.sleep(5000);
        solo.enterText((EditText) solo.getView(R.id.nameEvent), "Swimming Class");
        solo.clickOnButton("Select Habit");
        solo.sleep(5000);
        solo.clickInList(0);
        solo.enterText((EditText)solo.getView(R.id.Comment), "Butterfly");

        solo.clickOnButton("Add Habit Event");
        solo.sleep(5000);
        solo.assertCurrentActivity("Could not view list of HabitEvents", ViewHabitHistoryActivity.class);
        solo.sleep(5000);

        HabitHistoryController control = HabitHistoryController.getInstance();
        HabitEvent habitE = control.get(control.size()-1);
        assertNotNull(habitE);
        assertEquals("Swimming Class", habitE.getHabitEventTitle());
        HabitHistoryController.getInstance().remove(habitE);
        HabitHistoryController.getInstance().store();
        c.deleteHabit(habit);
    }
}