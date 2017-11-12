package com.wsfmn.habittracker.habit;

import android.test.ActivityInstrumentationTestCase2;

import com.wsfmn.habit.Date;
import com.wsfmn.habit.DateNotValidException;
import com.wsfmn.habit.Habit;
import com.wsfmn.habit.HabitCommentTooLongException;
import com.wsfmn.habit.HabitEvent;
import com.wsfmn.habit.HabitEventCommentTooLongException;
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
            habitEvent = new HabitEvent(myHabit, "Title", "Did my habit!", null, null);

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
            habitEvent = new HabitEvent(myHabit, "Title", "Did my habit!", null, null);
        }
        catch(HabitCommentTooLongException e){
            //null
        }


        habitHistory.add(habitEvent);

        HabitEvent receivedHabitEvent = habitHistory.get(0);
        try {
            assertEquals("HabitEvent in HabitHistory was not the same as the HabitEvent just added.",
                    habitEvent.getComment(), receivedHabitEvent.getComment());
        } catch (HabitEventCommentTooLongException e) {
            e.printStackTrace();
        }
    }

    /**
     * For a given Habit, test that HabitOccurrence returns the correct number of HabitEvents in
     * the HabitHistory.
     */
    public void testHabitOccurrence(){
        HabitHistory hh = new HabitHistory();
        Habit h1 = null;
        Habit h2 = null;
        HabitEvent he1 = null;
        HabitEvent he2 = null;
        HabitEvent he3 = null;


        try {
            h1 = new Habit("My Habit", new Date());
        } catch (HabitTitleTooLongException e) {
            e.printStackTrace();
        } catch (DateNotValidException e) {
            e.printStackTrace();
        }


        try {
            h2 = new Habit("My Habit", new Date());
        } catch (HabitTitleTooLongException e) {
            e.printStackTrace();
        } catch (DateNotValidException e) {
            e.printStackTrace();
        }

        try {
            he1 = new HabitEvent(h1, "Habit Event1", "I did the Habit", null, null);
        } catch (HabitCommentTooLongException e) {
            e.printStackTrace();
        }

        try {
            he2 = new HabitEvent(h1, "Habit Event2", "I did the Habit", null, null);
        } catch (HabitCommentTooLongException e) {
            e.printStackTrace();
        }

        try {
            he3 = new HabitEvent(h2, "Habit Event3", "I did the Habit", null, null);
        } catch (HabitCommentTooLongException e) {
            e.printStackTrace();
        }

        hh.add(he1);
        assertEquals("HabitEvent 1 should have existed for habit h1", hh.habitOccurrence(h1), 1);
        hh.add(he2);
        assertEquals("HabitEvent 2 should have existed for habit h1", hh.habitOccurrence(h1), 2);
        hh.remove(1);
        assertEquals("A HabitEvent for h1 wasn't removed", hh.habitOccurrence(h1), 1);

        hh.add(he3);
        hh.add(he3);
        hh.add(he3);
        assertEquals("Adding HabitEvents for a different Habit changed the" +
                "number of occurrences of another habit", hh.habitOccurrence(h1), 1);

        assertEquals("The sum of all Habit occurrences in the HabitHistory" +
                "was not the same as the size of the HabitHistory",
                hh.size(), (hh.habitOccurrence(h1) + hh.habitOccurrence(h2)));
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
            habitEvent = new HabitEvent(myHabit,"Title", "Did my habit!", null, null);
        }
        catch(HabitCommentTooLongException e){
            //null
        }

        habitHistory.add(habitEvent);

        HabitEvent removedHabitEvent = habitHistory.remove(0);
        try {
            assertEquals("HabitEvent removed from HabitHistory was not the same as the HabitEvent just added.",
                    habitEvent.getComment(), removedHabitEvent.getComment());
        } catch (HabitEventCommentTooLongException e) {
            e.printStackTrace();
        }
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
            habitEvent = new HabitEvent(myHabit, "Title", "Did my habit!", null, null);

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
            habitEvent = new HabitEvent(myHabit, "Title", "Did my habit!", null, null);
        }
        catch(HabitCommentTooLongException e){
            //null
        }

        habitHistory.add(habitEvent);

        assertEquals("Habit history index is incorrect for habitEvent", habitHistory.indexOf(habitEvent), 0);
    }
}