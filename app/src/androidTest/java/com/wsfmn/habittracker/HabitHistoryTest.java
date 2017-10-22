package com.wsfmn.habittracker;

import android.test.ActivityInstrumentationTestCase2;

import java.util.Date;

/**
 * Created by nicholasmayne on 2017-10-17.
 */

public class HabitHistoryTest extends ActivityInstrumentationTestCase2 {


    public HabitHistoryTest() {
        super(HabitHistory.class);
    }

    public void testAddHabitEvent() {
        Habit habit = new Habit("Eat Pizza", new Date());
        HabitHistory habitHistory = new HabitHistory();
        assertNull(habitHistory.getHabitEventAt(0));     // there is no habit event history yet

        HabitEvent habitEvent = new HabitEvent(habit, new Date(), "I ate all the pizza!");
        habitHistory.addHabitEvent(habitEvent);
        assertNotNull(habitHistory.getHabitEventAt(0));  // there is now a habit event in the history

        HabitEvent retrieved = habitHistory.getHabitEventAt(0);
        assertTrue(retrieved.comment == habitEvent.comment);


    }

}
