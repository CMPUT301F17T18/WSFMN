package com.wsfmn.habittracker;

import android.test.ActivityInstrumentationTestCase2;

import com.wsfmn.habit.Date;
import com.wsfmn.habit.DateNotValidException;
import com.wsfmn.habit.Habit;
import com.wsfmn.habit.HabitCommentTooLongException;
import com.wsfmn.habit.HabitEvent;
import com.wsfmn.habit.HabitTitleTooLongException;
import com.wsfmn.habitcontroller.HabitHistoryController;

/**
 * Created by nicholasmayne on 2017-11-08.
 */

public class HabitHistoryControllerTest extends ActivityInstrumentationTestCase2 {
    public HabitHistoryControllerTest() {
        super(HabitHistoryController.class);
    }

    /**
     * Test that HabitHistory is empty before adding a HabitEvent, and not empty after
     * adding a HabitEvent. This tests both add(HabitEvent h) and isEmpty()
     */
    public void testAdd(){

        assertTrue("HabitHistory should have been empty", HabitHistoryController.isEmpty());

        try {
            Habit h = new Habit("Walk the dog", new Date());
            HabitHistoryController.add(new HabitEvent(h, new Date(), true, ""));
        } catch (HabitCommentTooLongException e) {
            e.printStackTrace();
        } catch (HabitTitleTooLongException e) {
            e.printStackTrace();
        } catch (DateNotValidException e) {
            e.printStackTrace();
        }

        assertFalse("HabitHistory should not have been empty", HabitHistoryController.isEmpty());
    }

    /**
     * Test that you can get back the same HabitEvent that you add to the HabitHistory
     */
    public void testGet(){
        HabitEvent he = null;
        try {
            Habit h = new Habit("Feed the Cat", new Date());
            he = new HabitEvent(h, new Date(), true, "");
            HabitHistoryController.add(he);
        } catch (HabitCommentTooLongException e) {
            e.printStackTrace();
        } catch (HabitTitleTooLongException e) {
            e.printStackTrace();
        } catch (DateNotValidException e) {
            e.printStackTrace();
        }

        assertFalse("HabitHistory should not have been empty", HabitHistoryController.isEmpty());

        assertTrue(HabitHistoryController.contains(he));
        HabitEvent returnedHabitEvent =
                HabitHistoryController.get(HabitHistoryController.indexOf(he));

        assertEquals("HabitEvents were not the same", returnedHabitEvent, he);
    }

}