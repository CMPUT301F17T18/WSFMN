package com.wsfmn.habittracker;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
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

        HabitList habitList;
        Habit newHabit = new Habit("TestHabit", new Date());
        assertNull("New habit ID was not null", newHabit.getId());

        OnlineController.AddHabit addHabitOnline
                = new OnlineController.AddHabit();
        addHabitOnline.execute(newHabit);


        Log.d("NewHabitID", "NewHabit Id: " + newHabit.getId());
        assertNotNull("New habit ID was null", newHabit.getId());

    }

}