package com.wsfmn.habittracker;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;

import java.util.Date;

/**
 * Created by musaed on 2017-10-21.
 */

public class HabitTest extends ActivityInstrumentationTestCase2 {

    public HabitTest(){
        super(Habit.class);
    }

    public void testGetTitle(){
        Habit habit = new Habit("title", new Date());
        assertEquals(habit.getTitle(), "title");
    }

    public void testSetTitle(){
        Habit habit = new Habit("title", new Date());
        try{
            habit.setTitle("a title that is more than twenty characters");
            Assert.fail();
        }
        catch(HabitTitleTooLongException e){

        }
    }

    public void testGetReason(){
        Habit habit = new Habit("title", "reason", new Date());
        assertEquals(habit.getReason(), "reason");
    }

    public void testSetReason(){
        Habit habit = new Habit("title", new Date());
        try{
            habit.setReason("a reason that contains more than thirty characters");
            Assert.fail();
        }
        catch(HabitReasonTooLongException e){

        }
    }

    public void testGetId(){
        Habit habit = new Habit("title", "reason", new Date());
        assertNull("Habit ID was not null", habit.getId());
    }

    public void testSetId(){
        Habit habit = new Habit("title", "reason", new Date());
        habit.setId("My Unique ID");
        assertNotNull("Habit ID was null", habit.getId());
        assertEquals("Habit ID was not equal to the one set.",habit.getId(), "My Unique ID");
    }

}
