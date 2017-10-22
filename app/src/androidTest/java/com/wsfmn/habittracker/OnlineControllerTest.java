package com.wsfmn.habittracker;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import java.util.Date;
import java.util.Scanner;


/**
 * Created by nicholasmayne on 2017-10-17.
 */

public class OnlineControllerTest extends ActivityInstrumentationTestCase2 {

    public OnlineControllerTest() {
        super(MainActivity.class);
    }


    public void testAddHabitOnline(){
        Habit newHabit = null;

        try {
            newHabit = new Habit("TestHabit", new Date());
        }
        catch(HabitTitleTooLongException e){
            // TODO: handle exception
        }

        assertNull("New habit ID was not null", newHabit.getId());
        OnlineController.AddHabit addHabitOnline
                = new OnlineController.AddHabit();
        addHabitOnline.execute(newHabit);

        OnlineController.GetHabitList getHabitList;
        assertNotNull("New habit ID was null", newHabit.getId());

    }



}