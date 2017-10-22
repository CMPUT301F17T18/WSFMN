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

}
