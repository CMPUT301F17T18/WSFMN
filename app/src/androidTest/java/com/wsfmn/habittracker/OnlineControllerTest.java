package com.wsfmn.habittracker;

import android.os.Handler;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;


/**
 * Created by nicholasmayne on 2017-10-17.
 */

public class OnlineControllerTest extends ActivityInstrumentationTestCase2 {

    public OnlineControllerTest() {
        super(MainActivity.class);
    }


    public void testAddHabitOnline(){

        try {
            final Habit newHabit = new Habit("TestHabit", new Date());

            assertNull("New habit ID was not null", newHabit.getId());

            Log.d("NewHabitIs:", newHabit.toString());

            OnlineController.AddHabit addHabitOnline
                    = new OnlineController.AddHabit();
            addHabitOnline.execute(newHabit);

            // Delay 1 second for transaction to finish (usual time is around 200 ms)
            long currentTime = Calendar.getInstance().getTimeInMillis();
            while(Calendar.getInstance().getTimeInMillis() - currentTime < 1000 ){}


            assertNotNull("New habit ID was null", newHabit.getId());
        }
        catch(HabitTitleTooLongException e){
            // TODO: handle exception
        }


    }
}