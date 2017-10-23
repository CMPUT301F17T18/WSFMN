package com.wsfmn.habittracker;

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
            final Habit newHabit = new Habit("HabitTitle", new Date());

            assertNull("New habit ID was not null", newHabit.getId());

            OnlineController.AddHabit addHabitOnline = new OnlineController.AddHabit();
            addHabitOnline.execute(newHabit);

            // Delay 1 second for transaction to finish (usual time is around 200 ms)
            long currentTime = Calendar.getInstance().getTimeInMillis();
            while(newHabit.getId() == null && (Calendar.getInstance().getTimeInMillis() - currentTime) < 1000 ){}

            assertNotNull("New habit ID was null", newHabit.getId());
        }
        catch(HabitTitleTooLongException e){
            // TODO: handle exception
        }

    }


    public void testGetHabitList() {
        HabitList habits = null;
        OnlineController.GetHabitList getHabitList = new OnlineController.GetHabitList();
        String searchString = "testhabit";

        assertNull("New Habit List was not null", habits);
        try {
            getHabitList.execute(searchString);
            habits = getHabitList.get();
        } catch (Exception e) {
            Log.i("Error", "Failed to get the habits from the async object");
        }

        // Delay 1 second for transaction to finish (usual time is around 200 ms)
        long currentTime = Calendar.getInstance().getTimeInMillis();
        while((Calendar.getInstance().getTimeInMillis() - currentTime) < 1000 ){}

        assertNotNull("Habit List from server was null", habits);
        assertTrue("Habit in Habit List does not contain search string",
                habits.getHabit(0).getTitle().toLowerCase().contains(searchString));
    }
}