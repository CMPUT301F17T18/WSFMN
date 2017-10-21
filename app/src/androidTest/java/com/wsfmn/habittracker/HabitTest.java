package com.wsfmn.habittracker;

import android.test.ActivityInstrumentationTestCase2;

import java.util.Date;

/**
 * Created by musaed on 2017-10-21.
 */

public class HabitTest extends ActivityInstrumentationTestCase2 {

    public HabitTest(){
        super(Habit.class);
    }

    public void testGetTitle(){
        Habit habit = new Habit();
        habit.setTitle("habit");
        assertEquals(habit.getTitle(), "habit");
    }

    public void testGetReason(){
        Habit habit = new Habit();
        habit.setReason("reason");
        assertEquals(habit.getReason(), "reason");
    }

}
