package com.wsfmn.view.view;

import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;
import com.wsfmn.exceptions.HabitCommentTooLongException;
import com.wsfmn.exceptions.HabitEventCommentTooLongException;
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
import com.wsfmn.view.HabitHistoryDetailActivity;
import com.wsfmn.view.MainActivity;
import com.wsfmn.view.ViewHabitHistoryActivity;
import com.wsfmn.view.R;

import java.text.ParseException;


/**
 * Created by siddhant on 2017-11-13.
 */

public class AddNewHabitEventActivityTest extends ActivityInstrumentationTestCase2<AddNewHabitEventActivity> {

    private Solo solo;

    public AddNewHabitEventActivityTest(){
        super(AddNewHabitEventActivity.class);}

    protected void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());

//        Intent intent = new Intent();
//        intent.putExtra("caller","");

    }

    public void testAddHabitEvent() throws HabitEventNameException, DateNotValidException, HabitTitleTooLongException, HabitReasonTooLongException, ParseException, HabitCommentTooLongException, HabitEventCommentTooLongException {

        HabitListController c = HabitListController.getInstance();
        Habit habit = new Habit("Swimming","lose Weight", new Date());
        c.addHabit(habit);

        solo.assertCurrentActivity("Could not open AddNewHabitEventActivity", AddNewHabitEventActivity.class);
        solo.sleep(5000);
//        solo.enterText((EditText) solo.getView(R.id.nameEvent), "Swimming Class");
        solo.clickOnButton("Select Habit");
        solo.sleep(5000);
        solo.clickInList(0);
        solo.enterText((EditText)solo.getView(R.id.Comment), "Butterfly");

        solo.clickOnButton("Save Habit Event");
        solo.sleep(5000);
        solo.assertCurrentActivity("Could not view list of HabitEvents", ViewHabitHistoryActivity.class);
        solo.sleep(5000);

        HabitHistoryController control = HabitHistoryController.getInstance();
        HabitEvent habitE = control.get(control.size()-1);
        assertNotNull(habitE);
        assertEquals("Swimming", habitE.getHabitEventTitle());
//        HabitHistoryController.getInstance().remove(habitE);
//        HabitHistoryController.getInstance().store();
//        c.deleteHabit(habit);


        //Testing the Edit Of HabitEventDetail Activity

//        solo.assertCurrentActivity("Could not open MainActivity", MainActivity.class);
        solo.clickInList(0);
        Habit habit2 = new Habit("Golf","Become Pro", new Date());
        c.addHabit(habit2);
        solo.sleep(5000);
        solo.clickOnButton("Change Habit");
        solo.clickInList(1);
        solo.clearEditText((EditText)solo.getView(R.id.hd_editComment));
        solo.enterText((EditText)solo.getView(R.id.hd_editComment), "100 m");

        solo.clickOnButton("Save");
        HabitHistoryController control2 = HabitHistoryController.getInstance();
        HabitEvent habitE2 = control2.get(0);
        solo.sleep(5000);

        assertEquals("Habit Event not Modified", "Swimming", habitE.getHabitEventTitle());
        final int size = control.size();
        solo.clickInList(0);
        solo.clickOnButton("Delete");
        solo.sleep(5000);
        int size2 = control.size();
        assertEquals("Delete Habit Event did not occur", size-1, size2);
        c.deleteHabit(habit);
        c.deleteHabit(habit2);
        c.store();
    }
}