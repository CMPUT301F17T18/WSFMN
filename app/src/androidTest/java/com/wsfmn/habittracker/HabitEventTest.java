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
<<<<<<< HEAD
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

=======
        Habit habit = new Habit("Habit", new Date());
        HabitEvent habitEvent = new HabitEvent(habit, habit.getDate(), "Comment", true);
>>>>>>> d7b6932dd7c07addb7120dfa9edfff350bfbb494
        assertEquals(habitEvent.getComment(), "Comment");
    }

    public void testSetComment(){
<<<<<<< HEAD
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

=======
        Habit habit = new Habit("Habit", new Date());
        HabitEvent habitEvent = new HabitEvent(habit, habit.getDate(), "Comment", true);
        habitEvent.setComment("Comment2Test");
>>>>>>> d7b6932dd7c07addb7120dfa9edfff350bfbb494
        assertEquals(habitEvent.getComment(), "Comment2Test");
    }

    public void testGetHabit(){
<<<<<<< HEAD
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
=======
        Habit habit = new Habit("Habit", new Date());
        HabitEvent habitEvent = new HabitEvent(habit, habit.getDate(), "Comment", true);
>>>>>>> d7b6932dd7c07addb7120dfa9edfff350bfbb494

        assertEquals(habitEvent.getHabit(), habit);
    }

    public void testGetLocation(){
<<<<<<< HEAD
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

=======
        Habit habit = new Habit("Habit", new Date());
        HabitEvent habitEvent = new HabitEvent(habit, habit.getDate(), "Comment", true);
>>>>>>> d7b6932dd7c07addb7120dfa9edfff350bfbb494
        assertNull(habitEvent.getLocation());
        habitEvent.location();
        assertNotNull(habitEvent.getLocation());
    }

    public void testGetPic(){
<<<<<<< HEAD
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
=======
        Habit habit = new Habit("Habit", new Date());
        HabitEvent habitEvent = new HabitEvent(habit, habit.getDate(), "Comment", true);
>>>>>>> d7b6932dd7c07addb7120dfa9edfff350bfbb494

        assertNull(habitEvent.getPic());
        habitEvent.AddPic();
        assertNotNull(habitEvent.getPic());
    }
}
