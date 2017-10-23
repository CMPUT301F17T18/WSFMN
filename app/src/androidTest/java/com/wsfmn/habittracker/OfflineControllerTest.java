package com.wsfmn.habittracker;

import android.test.ActivityInstrumentationTestCase2;

import java.util.Date;

/**
 * Created by Fredric on 2017-10-21.
 */


public class OfflineControllerTest extends ActivityInstrumentationTestCase2 {

    public OfflineControllerTest() {
        super(OfflineController.class);
    }


    // hard to test needs to already have something stored in Local
    public void testGetLocal(){
        HabitList habits = new HabitList();
        Date date = new Date();
        OfflineController offline = new OfflineController();

        Habit testHabit = null;

        try {
            testHabit = new Habit("Test", date);
        }
        catch(HabitTitleTooLongException e){
            //null
        }

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
        OfflineController offline = new OfflineController();

        Habit testHabit = null;

        try {
            testHabit = new Habit("Test", date);
        }
        catch(HabitTitleTooLongException e){
            //null
        }

        habits.addHabit(testHabit);

        offline.storeLocal();
        habits.deleteHabit(testHabit);
        assertFalse(habits.hasHabit(testHabit));
        offline.getLocal();
        assertEquals(habits.getHabit(0), testHabit);


    }
}
