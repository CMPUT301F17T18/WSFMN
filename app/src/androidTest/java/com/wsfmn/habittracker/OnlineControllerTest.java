package com.wsfmn.habittracker;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import java.util.Date;


/**
 * Created by nicholasmayne on 2017-10-17.
 */

public class OnlineControllerTest extends ActivityInstrumentationTestCase2 {

    public OnlineControllerTest() {
        super(MainActivity.class);
    }


    public void testAddHabitOnline(){
        Habit newHabit = null;

<<<<<<< HEAD
        try {
            newHabit = new Habit("TestHabit", new Date());
        }
        catch(HabitTitleTooLongException e){
            // TODO: handle exception
        }

=======
        HabitList habitList;
        Habit newHabit = new Habit("TestHabit", new Date());
>>>>>>> dev
        assertNull("New habit ID was not null", newHabit.getId());

        OnlineController.AddHabit addHabitOnline
                = new OnlineController.AddHabit();
        addHabitOnline.execute(newHabit);

<<<<<<< HEAD
<<<<<<< HEAD
        OnlineController.GetHabitList getHabitList;
=======
        Log.d("NewHabitID", newHabit.getId());
>>>>>>> d7b6932dd7c07addb7120dfa9edfff350bfbb494
=======

        Log.d("NewHabitID", "NewHabit Id: " + newHabit.getId());
>>>>>>> dev
        assertNotNull("New habit ID was null", newHabit.getId());

    }

}