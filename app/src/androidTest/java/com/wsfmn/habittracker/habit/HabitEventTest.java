package com.wsfmn.habittracker.habit;

import android.test.ActivityInstrumentationTestCase2;

import com.wsfmn.habit.Date;
import com.wsfmn.habit.DateNotValidException;
import com.wsfmn.habit.Geolocation;
import com.wsfmn.habit.Habit;
import com.wsfmn.habit.HabitCommentTooLongException;
import com.wsfmn.habit.HabitEvent;
import com.wsfmn.habit.HabitEventCommentTooLongException;
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
            habitEvent = new HabitEvent(habit, "Title", "Did my habit!", null, null);
        }
        catch(HabitCommentTooLongException e){
            //null
        }


        assertEquals(habitEvent.getComment(), "Did my habit!");
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
            habitEvent = new HabitEvent(habit, "Title", "Did my habit!", null, null);
        }
        catch(HabitCommentTooLongException e){
            //null
        }

        try{
            habitEvent.setComment("Comment2Test");
        } catch (HabitEventCommentTooLongException e) {
            e.printStackTrace();
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
            habitEvent = new HabitEvent(habit, "Title", "Did my habit!", null, null);
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
        catch(DateNotValidException e){
            //null
        }

        HabitEvent habitEvent = null;

        try {
            habitEvent = new HabitEvent(habit, "Title", "Did my habit!", null, null);
        }
        catch(HabitCommentTooLongException e){
            //null
        }

//        assertNull("Habit's Location is not null", habitEvent.getLocation());
//        habitEvent.setLocation(new Geolocation());
//        assertNotNull("Habit's Location is null", habitEvent.getLocation());
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
            habitEvent = new HabitEvent(habit, "Title", "Did my habit!", null, null);
        }
        catch(HabitCommentTooLongException e){
            //null
        }


        assertNull(habitEvent.getImage());
        habitEvent.setImage(null);
        assertNotNull(habitEvent.getImage());
    }
}
