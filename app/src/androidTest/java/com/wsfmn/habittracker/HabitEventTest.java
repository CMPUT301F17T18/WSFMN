package com.wsfmn.habittracker;

import android.test.ActivityInstrumentationTestCase2;

import com.wsfmn.habit.Date;
import com.wsfmn.habit.DateNotValidException;
import com.wsfmn.habit.Geolocation;
import com.wsfmn.habit.Habit;
import com.wsfmn.habit.HabitCommentTooLongException;
import com.wsfmn.habit.HabitEvent;
import com.wsfmn.habit.HabitTitleTooLongException;


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
        catch(DateNotValidException e){
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
        catch(DateNotValidException e){
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
        catch(DateNotValidException e){
            //null
        }

        HabitEvent habitEvent = null;

        try {
            habitEvent = new HabitEvent(habit, habit.getDate(), true, "Comment");
        }
        catch(HabitCommentTooLongException e){
            //null
        }


        assertEquals(habitEvent.getHabitType(), habit);
    }

    public void testGetLocation(){

        Habit habit = null;

        try {
            habit = new Habit("TestHabit", new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(DateNotValidException e){
            //null
        }

        HabitEvent habitEvent = null;

        try {
            habitEvent = new HabitEvent(habit, habit.getDate(), true, "Comment");
        }
        catch(HabitCommentTooLongException e){
            //null
        }

        assertNull("Habit's Location is not null", habitEvent.getLocation());
        habitEvent.setLocation(new Geolocation());
        assertNotNull("Habit's Location is null", habitEvent.getLocation());
    }

    public void testGetPic(){

        Habit habit = null;

        try {
            habit = new Habit("TestHabit", new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(DateNotValidException e){

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
