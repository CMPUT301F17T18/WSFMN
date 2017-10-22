package com.wsfmn.habittracker;

import android.test.ActivityInstrumentationTestCase;
import android.test.ActivityInstrumentationTestCase2;

import java.util.Date;

/**
 * Created by siddhant on 2017-10-22.
 */

public class HabitEventTest extends ActivityInstrumentationTestCase2 {
    public HabitEventTest() {
        super(HabitEvent.class);
    }

    public void testGetComment(){
        Habit habit = new Habit("Habit", new Date());
        HabitEvent habitEvent = new HabitEvent(habit, habit.getDate(), "Comment");
        assertEquals(habitEvent.getComment(), "Comment");
    }

    public void testSetComment(){
        Habit habit = new Habit("Habit", new Date());
        HabitEvent habitEvent = new HabitEvent(habit, habit.getDate(), "Comment");
        habitEvent.setComment("Comment2Test");
        assertEquals(habitEvent.getComment(), "Comment2Test");
    }

    public void testGetHabit(){
        Habit habit = new Habit("Habit", new Date());
        HabitEvent habitEvent = new HabitEvent(habit, habit.getDate(), "Comment");

        assertEquals(habitEvent.getHabit(), habit);
    }

    public void testGetLocation(){
        Habit habit = new Habit("Habit", new Date());
        HabitEvent habitEvent = new HabitEvent(habit, habit.getDate(), "Comment");
        assertNull(habitEvent.getLocation());
        habitEvent.location();
        assertNotNull(habitEvent.getLocation());
    }

    public void testGetPic(){
        Habit habit = new Habit("Habit", new Date());
        HabitEvent habitEvent = new HabitEvent(habit, habit.getDate(), "Comment");

        assertNull(habitEvent.getPic());
        habitEvent.AddPic();
        assertNotNull(habitEvent.getPic());
    }
}
