package com.wsfmn.habittracker.habitcontrollers;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.wsfmn.habit.Date;
import com.wsfmn.habit.DateNotValidException;
import com.wsfmn.habit.Habit;
import com.wsfmn.habit.HabitCommentTooLongException;
import com.wsfmn.habit.HabitEvent;
import com.wsfmn.habit.HabitHistory;
import com.wsfmn.habit.HabitTitleTooLongException;
import com.wsfmn.habitcontroller.HabitHistoryController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicholasmayne on 2017-11-08.
 */

public class HabitHistoryControllerTest extends ActivityInstrumentationTestCase2 {
    public HabitHistoryControllerTest() {
        super(HabitHistoryController.class);
    }

    /**
     * Test to ensure Singleton class HabitHistoryController returns correctly typed instance
     */
    public void testGetInstance(){
        assertEquals("The instance returned was not a HabitHistoryController",
                HabitHistoryController.getInstance().getClass(), HabitHistoryController.class);
    }

    /**
     * Test that HabitHistory is empty before adding a HabitEvent, and not empty after
     * adding a HabitEvent. This tests both add(HabitEvent h) and isEmpty()
     */
    public void testAdd(){
        assertTrue("HabitHistory should have been empty", HabitHistoryController.isEmpty());

        HabitEvent he = null;
        try {
            Habit h = new Habit("Feed the Cat", new Date());
            he = new HabitEvent(h, "Title", "Did my habit!", null);
            HabitHistoryController.add(he);
        } catch (HabitCommentTooLongException e) {
            e.printStackTrace();
        } catch (HabitTitleTooLongException e) {
            e.printStackTrace();
        } catch (DateNotValidException e) {
            e.printStackTrace();
        }

        assertFalse("HabitHistory should not have been empty", HabitHistoryController.isEmpty());

        HabitHistoryController.remove(he);
        assertTrue("HabitHistory should be empty", HabitHistoryController.isEmpty());
    }

    /**
     * Test that you can get back the same HabitEvent that you add to the HabitHistory
     */
    public void testGet(){
        HabitEvent he = null;
        try {
            Habit h = new Habit("Feed the Cat", new Date());
            he = new HabitEvent(h, "Title", "Did my habit!", null);
            HabitHistoryController.add(he);
        } catch (HabitCommentTooLongException e) {
            e.printStackTrace();
        } catch (HabitTitleTooLongException e) {
            e.printStackTrace();
        } catch (DateNotValidException e) {
            e.printStackTrace();
        }

        assertTrue("HabitHistory doesn't contain the HabitEvent",
                HabitHistoryController.contains(he));

        HabitEvent returnedHabitEvent =
                HabitHistoryController.get(HabitHistoryController.indexOf(he));

        assertEquals("HabitEvents were not the same", returnedHabitEvent, he);
    }

    /**
     * Test that you can remove a HabitEvent from the HabitHistory
     */
    public void testRemove() {
        HabitEvent he = null;
        try {
            Habit h = new Habit("Feed the Cat", new Date());
            he = new HabitEvent(h, "Title", "Did my habit!", null);
            HabitHistoryController.add(he);
        } catch (HabitCommentTooLongException e) {
            e.printStackTrace();
        } catch (HabitTitleTooLongException e) {
            e.printStackTrace();
        } catch (DateNotValidException e) {
            e.printStackTrace();
        }

        assertTrue("HabitHistory doesn't contain the HabitEvent",
                HabitHistoryController.contains(he));
        HabitEvent removedHabitEvent = HabitHistoryController.remove(he);

        assertEquals("HabitEvents were not the same", removedHabitEvent, he);

        assertFalse("HabitHistory still contains the HabitEvent",
                HabitHistoryController.contains(he));
    }

    /**
     * Tests adding a list of HabitEvents to HabitHistory
     * Also tests remove(int idx) to ensure that HabitHistory isEmpty
     */
    public void testAddAll(){

        // Clear out the habit history.
        while (!HabitHistoryController.isEmpty()){HabitHistoryController.remove(0);}

        HabitEvent he = null;
        try {
            Habit h = new Habit("Feed the Cat", new Date());
            he = new HabitEvent(h, "Title", "Did my habit!", null);
        } catch (HabitCommentTooLongException e) {
            e.printStackTrace();
        } catch (HabitTitleTooLongException e) {
            e.printStackTrace();
        } catch (DateNotValidException e) {
            e.printStackTrace();
        }

        // Add 10 new habit events
        int addNum = 10;
        ArrayList<HabitEvent> habitEvents = new ArrayList<HabitEvent>();
        for (int i = 0; i < addNum; i++){
            habitEvents.add(he);
        }

        int size = habitEvents.size();
        Log.d("Size:", String.valueOf(size));

        assertTrue(size == addNum);
        HabitHistoryController.addAllHabitEvents(habitEvents);

        size = HabitHistoryController.size();
        Log.d("Size:", String.valueOf(size));

        assertTrue(size == addNum);
    }

}