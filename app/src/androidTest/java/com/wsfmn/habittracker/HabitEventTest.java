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

        Habit habit = null;

        try {
            habit = new Habit("TestHabit", new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }

        HabitEvent habitEvent = null;

        try {
            habitEvent = new HabitEvent(habit, habit.getDate(), true, "Comment");
        }
        catch(HabitCommentTooLongException e){
            //null
        }


        assertEquals(habitEvent.getComment(), "Comment");
    }

    public void testSetComment(){

        Habit habit = null;

        try {
            habit = new Habit("TestHabit", new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }

        HabitEvent habitEvent = null;

        try {
            habitEvent = new HabitEvent(habit, habit.getDate(), true, "Comment");
        }
        catch(HabitCommentTooLongException e){
            //null
        }

        try{
            habitEvent.setComment("Comment2Test");
        }
        catch(HabitCommentTooLongException e){
            //null
        }


        assertEquals(habitEvent.getComment(), "Comment2Test");
    }

    public void testGetHabit(){

        Habit habit = null;

        try {
            habit = new Habit("TestHabit", new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }

        HabitEvent habitEvent = null;

        try {
            habitEvent = new HabitEvent(habit, habit.getDate(), true, "Comment");
        }
        catch(HabitCommentTooLongException e){
            //null
        }


        assertEquals(habitEvent.getHabit(), habit);
    }

    public void testGetLocation(){

        Habit habit = null;

        try {
            habit = new Habit("TestHabit", new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }

        HabitEvent habitEvent = null;

        try {
            habitEvent = new HabitEvent(habit, habit.getDate(), true, "Comment");
        }
        catch(HabitCommentTooLongException e){
            //null
        }



        assertNull(habitEvent.getLocation());
        habitEvent.setLocation();
        assertNotNull(habitEvent.getLocation());
    }

    public void testGetPic(){

        Habit habit = null;

        try {
            habit = new Habit("TestHabit", new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }

        HabitEvent habitEvent = null;

        try {
            habitEvent = new HabitEvent(habit, habit.getDate(), true, "Comment");
        }
        catch(HabitCommentTooLongException e){
            //null
        }


        assertNull(habitEvent.getPic());
        habitEvent.AddPic();
        assertNotNull(habitEvent.getPic());
    }
}
