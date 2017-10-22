package com.wsfmn.habittracker;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Fredric on 2017-10-21.
 */


public class OfflineTest extends ActivityInstrumentationTestCase2 {

    public OfflineTest() {
        super(Offline.class);
    }


    // hard to test needs to already have something stored in Local
    public void testGetLocal(){
        HabitList habits = new HabitList();
        Date date = new Date();
        Offline offline = new Offline();
        Habit testHabit = new Habit("Test", date);
        habits.addHabit(testHabit);


        offline.storeLocal();
        habits.deleteHabit(testHabit);
        assertFalse(habits.hasHabit(testHabit));
        offline.getLocal();

        assertEquals(habits.getHabit(0), testHabit);

    }

    //hard to test needs to load from file.
    public void testStoreLocal(){
        HabitList habits = new HabitList();
        Date date = new Date();
        Offline offline = new Offline();

        Habit testHabit = new Habit("Test", date);
        habits.addHabit(testHabit);

        offline.storeLocal();
        habits.deleteHabit(testHabit);
        assertFalse(habits.hasHabit(testHabit));
        offline.getLocal();
        assertEquals(habits.getHabit(0), testHabit);


    }
}
