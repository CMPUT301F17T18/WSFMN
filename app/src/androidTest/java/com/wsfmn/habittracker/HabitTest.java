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
        Habit habit = null;

        try {
            habit = new Habit("title", new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }

        assertEquals(habit.getTitle(), "title");
    }

    public void testSetTitle(){
        Habit habit = null;
        try{
            habit = new Habit("a title that is more than twenty characters", new Date());
            Assert.fail();
        }
        catch(HabitTitleTooLongException e){
            //null
        }
    }

    public void testGetReason(){
        Habit habit = null;

        try{
            new Habit("title", "reason", new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(HabitReasonTooLongException e){
            //null
        }

        assertEquals(habit.getReason(), "reason");
    }

    public void testSetReason(){
        Habit habit = null;
        try{
            habit = new Habit("title", "a reason that contains more than thirty characters", new Date());
            Assert.fail();
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(HabitReasonTooLongException e){
            //null
        }
    }

}
