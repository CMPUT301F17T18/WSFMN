package com.wsfmn.habittracker;

import android.test.ActivityInstrumentationTestCase2;

import com.wsfmn.habit.Date;
import com.wsfmn.habit.DateNotValidException;
import com.wsfmn.habit.Habit;
import com.wsfmn.habit.HabitCommentTooLongException;
import com.wsfmn.habit.HabitEvent;
import com.wsfmn.habit.HabitHistory;
import com.wsfmn.habit.HabitTitleTooLongException;


/**
 * Created by nicholasmayne on 2017-10-17.
 */

public class HabitHistoryTest extends ActivityInstrumentationTestCase2 {


    public HabitHistoryTest() {
        super(HabitHistory.class);
    }


    public void testIsEmpty() {
        HabitHistory habitHistory = new HabitHistory();
        assertTrue("Habit History should have been empty.", habitHistory.isEmpty());

        Habit myHabit = null;
        HabitEvent habitEvent = null;

        try {
            myHabit = new Habit("Eating Pizza", new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(DateNotValidException e){
            //null
        }

        try {
            habitEvent = new HabitEvent(myHabit, new Date(), true, "I ate all the pizza!");

        }
        catch(HabitCommentTooLongException e){
            //null
        }


        habitHistory.add(habitEvent);
        assertFalse("Habit History should not have been empty.", habitHistory.isEmpty());
    }

    public void testAdd() {
        HabitHistory habitHistory = new HabitHistory();

        Habit myHabit = null;
        HabitEvent habitEvent = null;

        try {
            myHabit = new Habit("Eating Pizza", new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(DateNotValidException e){
            //null
        }

        try {
            habitEvent = new HabitEvent(myHabit, new Date(), true, "I ate all the pizza!");
        }
        catch(HabitCommentTooLongException e){
            //null
        }


        habitHistory.add(habitEvent);

        HabitEvent receivedHabitEvent = habitHistory.get(0);
        assertEquals("HabitEvent in HabitHistory was not the same as the HabitEvent just added.",
                habitEvent.getComment(), receivedHabitEvent.getComment());
    }


    public void testRemove() {
        HabitHistory habitHistory = new HabitHistory();

        Habit myHabit = null;
        HabitEvent habitEvent = null;

        try {
            myHabit = new Habit("Eating Pizza", new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(DateNotValidException e){
            //null
        }

        try {
            habitEvent = new HabitEvent(myHabit, new Date(), true, "I ate all the pizza!");
        }
        catch(HabitCommentTooLongException e){
            //null
        }

        habitHistory.add(habitEvent);

        HabitEvent removedHabitEvent = habitHistory.remove(0);
        assertEquals("HabitEvent removed from HabitHistory was not the same as the HabitEvent just added.",
                habitEvent.getComment(), removedHabitEvent.getComment());
    }

    public void testContains() {
        HabitHistory habitHistory = new HabitHistory();

        Habit myHabit = null;
        HabitEvent habitEvent = null;

        try {
            myHabit = new Habit("Eating Pizza", new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(DateNotValidException e){
            //null
        }

        try {
            habitEvent = new HabitEvent(myHabit, new Date(), true, "I ate all the pizza!");

        }
        catch(HabitCommentTooLongException e){
            //null
        }

        habitHistory.add(habitEvent);

        assertTrue("Habit history does not contain my habit's event.", habitHistory.contains(habitEvent));
    }

    public void testIndexOf() {
        HabitHistory habitHistory = new HabitHistory();

        Habit myHabit = null;
        HabitEvent habitEvent = null;

        try {
            myHabit = new Habit("Eating Pizza", new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(DateNotValidException e){
            //null
        }

        try {
            habitEvent = new HabitEvent(myHabit, new Date(), true, "I ate all the pizza!");
        }
        catch(HabitCommentTooLongException e){
            //null
        }

        habitHistory.add(habitEvent);

        assertEquals("Habit history index is incorrect for habitEvent", habitHistory.indexOf(habitEvent), 0);
    }
}