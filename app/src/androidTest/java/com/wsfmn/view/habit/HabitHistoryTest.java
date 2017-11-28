package com.wsfmn.view.habit;

import android.test.ActivityInstrumentationTestCase2;

import com.wsfmn.model.Date;
import com.wsfmn.model.DateNotValidException;
import com.wsfmn.model.Habit;
import com.wsfmn.model.HabitCommentTooLongException;
import com.wsfmn.model.HabitEvent;
import com.wsfmn.model.HabitEventCommentTooLongException;
import com.wsfmn.model.HabitEventNameException;
import com.wsfmn.model.HabitHistory;
import com.wsfmn.model.HabitTitleTooLongException;

import static java.lang.Thread.sleep;


/**
 * Created by nicholasmayne on 2017-10-17.
 */

public class HabitHistoryTest extends ActivityInstrumentationTestCase2 {


    public HabitHistoryTest() {
        super(HabitHistory.class);
    }


    public void testIsEmpty() throws Exception{
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

    public void testAdd() throws HabitEventCommentTooLongException {
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
        assertEquals("HabitEvent in HabitHistory was not the same as the HabitEvent just added.",
                habitEvent.getComment(), receivedHabitEvent.getComment());
    }

    /**
     * For a given Habit, test that HabitOccurrence returns the correct number of HabitEvents in
     * the HabitHistory.
     */
    public void testHabitOccurrence() throws Exception{
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

    public void testRemove() throws HabitEventCommentTooLongException {
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
        assertEquals("HabitEvent removed from HabitHistory was not the same as the HabitEvent just added.",
                habitEvent.getComment(), removedHabitEvent.getComment());
    }

    public void testContains() throws Exception{
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

    public void testIndexOf() throws Exception{
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

    /**
     *  Tests the method 'sortHabitHistory'
     *
     */
    public void testSortHabitHistory() throws Exception{
        HabitHistory habitHistory = new HabitHistory();

        Habit myHabit = null;
        HabitEvent habitEvent = null;
        HabitEvent habitEvent1 = null;

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
            habitEvent = new HabitEvent(myHabit, "Ate Pizza With Jack", "Did my habit!", null,
                    "13/11/2017,00:01");
        }
        catch(HabitCommentTooLongException e){
            //null
        }

        try {
            habitEvent1 = new HabitEvent(myHabit, "Ate Pizza With Mike", "Did my habit!", null,
                    "14/11/2017,00:01");
        }
        catch(HabitCommentTooLongException e){
            //null
        }

        habitHistory.add(habitEvent);
        habitHistory.add(habitEvent1);
        habitHistory.sortHabitHistory();

        String title1 = null;
        String title2 = null;

        try {
            title1 = habitHistory.get(0).getHabitEventTitle();
        }
        catch(HabitEventNameException e){
            //null
        }

        try{
            title2 = habitHistory.get(1).getHabitEventTitle();
        }
        catch(HabitEventNameException e){
            //null
        }


        assertEquals("Ate Pizza With Mike", title1);
        assertEquals("Ate Pizza With Jack", title2);
    }

    /**
     *  Tests filtering habit history list by title
     *
     */
    public void testFilterByTitle() throws  Exception{
        HabitHistory habitHistory = new HabitHistory();
        HabitEvent he = new HabitEvent(new Habit("Basketball", new Date()),
                "Swimmed with Jack", null, null, null);
        HabitEvent he2 = new HabitEvent(new Habit("Swimming", new Date()),
                "Swimmed with Jack", null, null, null);

        habitHistory.add(he);
        habitHistory.add(he2);
        habitHistory.filterByTitle("Swimming");

        assertEquals("Swimming", habitHistory.get(0).getHabitTitle());
    }

    /**
     *  Tests filtering habit history list by habits containing a comment
     *
     */
    public void testFilterByComment() throws Exception{
        HabitHistory habitHistory = new HabitHistory();
        HabitEvent he = new HabitEvent(new Habit("Basketball", new Date()),
                "Swimmed with Jack", "Fun", null, null);
        HabitEvent he2 = new HabitEvent(new Habit("Swimming", new Date()),
                "Swimmed with Jack", "Not Happy", null, null);

        habitHistory.add(he);
        habitHistory.add(he2);
        habitHistory.filterByComment("Fun");

        assertEquals("Fun", habitHistory.get(0).getComment());
        assertEquals(1, habitHistory.size());
    }
}