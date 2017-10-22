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
        HabitHistory habitHistory = new HabitHistory();
        assertNull(habitHistory.getHabitEventAt(0));        // there is no habit event history yet

<<<<<<< HEAD
        HabitEvent habitEvent = new HabitEvent(new Date(), "I ate all the pizza!");
        habitHistory.add(habitEvent);
        assertNotNull(habitHistory.getHabitEventAt(0));     // the habit event is in the history
=======
        HabitEvent habitEvent = new HabitEvent(new Habit(), new Date(), "I ate all the pizza!");
        habitHistory.addHabitEvent(habitEvent);
        assertNotNull(habitHistory.getHabitEventAt(0));  // there is now a habit event in the history
>>>>>>> 21a636291a7d6bb2bae0fa476c63c4307275b0a3

        HabitEvent receivedHabitEvent = habitHistory.getHabitEventAt(0);
        assertTrue(habitEvent.getComment().equals(receivedHabitEvent.getComment()));    // same habit event

        assertNull(habitHistory.getHabitEventAt(1));    // there is only one habitEvent in the history
    }


    public void testRemoveHabitEvent() {
        HabitHistory habitHistory = new HabitHistory();
        HabitEvent habitEvent = new HabitEvent(new Date(), "I ate all the pizza!");
        habitHistory.add(habitEvent);
        assertNotNull(habitHistory.getHabitEventAt(0));     // the habit event is in the history

        habitHistory
    }

}
