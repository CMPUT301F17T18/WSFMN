package com.wsfmn.view.habit;

import android.test.ActivityInstrumentationTestCase2;

import com.wsfmn.model.Date;
import com.wsfmn.model.DateNotValidException;
import com.wsfmn.model.Habit;
import com.wsfmn.model.HabitList;
import com.wsfmn.model.HabitReasonTooLongException;
import com.wsfmn.model.HabitTitleTooLongException;
import com.wsfmn.model.WeekDays;

/**
 * Created by musaed on 2017-10-21.
 *
 *  A class to test HabitList
 */

public class HabitListTest extends ActivityInstrumentationTestCase2 {

    public HabitListTest(){
        super(HabitList.class);
    }

    /**
     *  Test that we can add a habit to the list of habits
     *
     */
    public void testAddHabit(){
        HabitList habits = new HabitList();
        Habit habit = null;

        try {
            habit = new Habit("Playing With Son", new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(DateNotValidException e){
            //null
        }

        habits.addHabit(habit);
        assertEquals("Playing With Son", habits.getHabit(0).getTitle());
    }

    /**
     *  Test that we can delete a habit, given that habit as an argument
     *
     */
    public void testDeleteHabit(){
        HabitList habits = new HabitList();

        Habit habit = null;

        try {
            habit = new Habit("Visiting Father", new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(DateNotValidException e){
            //null
        }

        habits.addHabit(habit);
        habits.deleteHabit(habit);
        assertFalse(habits.hasHabit(habit));
    }

    /**
     *  Test that we can retrieve a habit
     *
     */
    public void testGetHabit(){
        HabitList habits = new HabitList();

        Habit habit = null;

        try {
            habit = new Habit("Going To Coffee Shop", new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(DateNotValidException e){
            //null
        }

        habits.addHabit(habit);
        assertEquals("Going To Coffee Shop", habits.getHabit(0).getTitle());
    }

    /**
     *  Test that hasHabit returns true if the habit exists and false
     *  otherwise
     *
     */
    public void testHasHabit(){
        HabitList habits = new HabitList();

        Habit habit = null;

        try {
            habit = new Habit("Setting With Daughter", new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(DateNotValidException e){
            //null
        }

        habits.addHabit(habit);
        assertTrue(habits.hasHabit(habit));

        habits.deleteHabitAt(0);
        assertFalse(habits.hasHabit(habit));
    }

    /**
     *  Test that we can replace an entry in the habit list with another
     *  habit
     *
     */
    public void testSetHabit(){
        HabitList habits = new HabitList();

        Habit habit = null;
        Habit habit2 = null;

        try{
            habit = new Habit("Running", new Date());
            habit2 = new Habit("Running With Jack", new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(DateNotValidException e){
            //null
        }

        habits.addHabit(habit);
        habits.setHabit(0, habit2);

        assertEquals("Running With Jack", habits.getHabit(0).getTitle());
    }

    /**
     *  Test that we can retrieve a list of the habits for today.
     *
     *
     */
    public void testGetHabitsForToday(){
        HabitList habitList = new HabitList();

        Habit habit = null;

        try{
            habit = new Habit("Walking", "To Lose Wait", new Date(), new WeekDays());
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(DateNotValidException e){
            //null
        }
        catch(HabitReasonTooLongException e){
            //null
        }

        habit.getWeekDays().setDay(new Date().getDayOfWeek()-1);
        habitList.addHabit(habit);

        assertEquals(habitList.getHabitsForToday().size(), 1);
    }

}