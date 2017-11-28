package com.wsfmn.view.habitcontrollers;

import android.test.ActivityInstrumentationTestCase2;

import com.wsfmn.model.Date;
import com.wsfmn.model.DateNotValidException;
import com.wsfmn.model.Habit;
import com.wsfmn.model.HabitTitleTooLongException;
import com.wsfmn.controller.HabitListController;

/**
 * Created by musaed on 2017-11-12.
 */

public class HabitListControllerTest extends ActivityInstrumentationTestCase2 {

    public HabitListControllerTest(){
        super(HabitListController.class);
    }

    /**
     * Test to ensure Singleton class HabitHistoryController returns correctly typed instance
     */
    public void testGetInstance(){
        assertEquals("The instance returned was not a HabitListController",
                HabitListController.getInstance().getClass(), HabitListController.class);
    }

    /**
     *  Test that we can add a new habit to the list of habits
     */
    public void testAddHabit(){
        HabitListController c = HabitListController.getInstance();

        //  Empty the habit list first
        while(!c.isEmpty()) c.deleteHabitAt(0);

        Habit habit = null;

        try{
            habit = new Habit("Feeding The Dog", new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(DateNotValidException e){
            //null
        }

        //  add a new habit
        c.addHabit(habit);
        assertEquals("Feeding The Dog", c.getHabit(c.size()-1).getTitle());
    }

    /**
     *  Test that it deletes a habit from the list of habits, by being provided
     *  with a habit as an argument
     *
     */
    public void testDeleteHabit(){
        HabitListController c = HabitListController.getInstance();

        //  Empty the habit list first
        while(!c.isEmpty()) c.deleteHabitAt(0);

        //  should be empty now
        assertTrue(c.isEmpty());

        Habit habit = null;

        try{
            habit = new Habit("Feeding The Dog", new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(DateNotValidException e){
            //null
        }

        // add a new habit
        c.addHabit(habit);
        //  should not be empty
        assertFalse(c.isEmpty());
        //  delete the habit
        c.deleteHabit(habit);
        //  should be empty
        assertTrue(c.isEmpty());
    }

    /**
     *  Test that it can delete a habit from the list of habits using an index
     *
     *
     */
    public void testDeleteHabitAt(){
        HabitListController c = HabitListController.getInstance();

        //  Empty the habit list first
        while(!c.isEmpty()) c.deleteHabitAt(0);

        //  should be empty now
        assertTrue(c.isEmpty());

        Habit habit = null;

        try{
            habit = new Habit("Feeding The Cat", new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(DateNotValidException e){
            //null
        }


        //  add a new habit
        c.addHabit(habit);
        //  should not be empty
        assertFalse(c.isEmpty());
        //  delete the habit
        c.deleteHabitAt(0);
        //  should be empty
        assertTrue(c.isEmpty());
    }

    /**
     *  Test that we can retrieve a habit from the list of habits
     *
     *
     */
    public void testGetHabit(){
        HabitListController c = HabitListController.getInstance();

        //  empty the habit list first
        while(!c.isEmpty()) c.deleteHabitAt(0);

        //  should be empty now
        assertTrue(c.isEmpty());

        Habit habit = null;

        try{
            habit = new Habit("Feeding The Dog", new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(DateNotValidException e){
            //null
        }

        //  add a new habit
        c.addHabit(habit);

        assertEquals("Feeding The Dog", c.getHabit(0).getTitle());
    }

    /**
     *  Test that we can set an entry in the habit list to a new habit
     *
     *
     */
    public void testSetHabit(){
        HabitListController c = HabitListController.getInstance();

        //  Empty the habit list first
        while(!c.isEmpty()) c.deleteHabitAt(0);

        //  should be empty now
        assertTrue(c.isEmpty());

        Habit habit = null;
        Habit habit2 = null;

        try{
            habit = new Habit("Feeding The Dog", new Date());
            habit2 = new Habit("Playing With Son", new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(DateNotValidException e){
            //null
        }

        //  add a new habit
        c.addHabit(habit);
        //  set index zero to habit2
        c.setHabit(0, habit2);

        assertEquals("Playing With Son", c.getHabit(0).getTitle());
    }

    /**
     *  Test that we can get habits for today
     *
     *
     */
    public void testGetHabitsForToday(){
        HabitListController c = HabitListController.getInstance();

        //  Empty the habit list first
        while(!c.isEmpty()) c.deleteHabitAt(0);

        //  should be empty now
        assertTrue(c.isEmpty());

        Habit habit = null;

        try{
            habit = new Habit("Feeding The Dog", new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(DateNotValidException e){
            //null
        }

        Date today = new Date();
        int day = today.getDayOfWeek();
        habit.getWeekDays().setDay(day-1);

        //  add a new habit
        c.addHabit(habit);

        assertFalse(c.getHabitsForToday().isEmpty());

        assertEquals("Feeding The Dog", c.getHabitsForToday().get(0).getTitle());
    }



}
