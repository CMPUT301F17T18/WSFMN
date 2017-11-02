package com.wsfmn.habittracker;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by musaed on 2017-10-21.
 */

public class HabitListTest extends ActivityInstrumentationTestCase2 {

    public HabitListTest(){
        super(HabitList.class);
    }

    public void testAddHabit(){
        HabitList habits = new HabitList();
        Habit habit = null;

        try {
            habit = new Habit("TestHabit", new Date());
        }
        catch(HabitTitleTooLongException e){
            // TODO: handle exception
        }
        catch(DateNotValidException e){
            //null
        }

        habits.addHabit(habit);
        assertTrue(habits.hasHabit(habit));
    }

    public void testDeleteHabit(){
        HabitList habits = new HabitList();

        Habit habit = null;

        try {
            habit = new Habit("TestHabit", new Date());
        }
        catch(HabitTitleTooLongException e){
            // TODO: handle exception
        }
        catch(DateNotValidException e){
            //null
        }

        habits.addHabit(habit);
        habits.deleteHabit(habit);
        assertFalse(habits.hasHabit(habit));
    }

    public void testGetHabit(){
        HabitList habits = new HabitList();

        Habit habit = null;

        try {
            habit = new Habit("title", new Date());
        }
        catch(HabitTitleTooLongException e){
            // TODO: handle exception
        }
        catch(DateNotValidException e){
            //null
        }

        habits.addHabit(habit);
        assertEquals(habits.getHabit(0).getTitle(), "title");
    }

    public void testHasHabit(){
        HabitList habits = new HabitList();

        Habit habit = null;

        try {
            habit = new Habit("TestHabit", new Date());
        }
        catch(HabitTitleTooLongException e){
            // TODO: handle exception
        }
        catch(DateNotValidException e){
            //null
        }

        habits.addHabit(habit);
        assertTrue(habits.hasHabit(habit));
    }


}