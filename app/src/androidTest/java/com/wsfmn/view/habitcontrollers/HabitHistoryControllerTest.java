package com.wsfmn.view.habitcontrollers;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.wsfmn.model.Date;
import com.wsfmn.model.DateNotValidException;
import com.wsfmn.model.Habit;
import com.wsfmn.model.HabitCommentTooLongException;
import com.wsfmn.model.HabitEvent;
import com.wsfmn.model.HabitEventNameException;
import com.wsfmn.model.HabitTitleTooLongException;
import com.wsfmn.controller.HabitHistoryController;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

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
    public void testAdd() throws Exception{
        HabitHistoryController.getInstance();
        // Clear out the habit history.
        while (!HabitHistoryController.isEmpty()){HabitHistoryController.remove(0);}

        assertTrue("HabitHistory should have been empty", HabitHistoryController.isEmpty());

        HabitEvent he = null;
        try {
            Habit h = new Habit("Feed the Cat", new Date());
            he = new HabitEvent(h, "Title", "Did my habit!", null, null);
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
    public void testGet() throws  Exception{
        HabitHistoryController.getInstance();
        // Clear out the habit history.
        while (!HabitHistoryController.isEmpty()){HabitHistoryController.remove(0);}

        HabitEvent he = null;
        try {
            Habit h = new Habit("Feed the Cat", new Date());
            he = new HabitEvent(h, "Title", "Did my habit!", null, null);
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
    public void testRemove() throws Exception{
        HabitHistoryController.getInstance();
        // Clear out the habit history.
        while (!HabitHistoryController.isEmpty()){HabitHistoryController.remove(0);}

        HabitEvent he = null;
        try {
            Habit h = new Habit("Feed the Cat", new Date());
            he = new HabitEvent(h, "Title", "Did my habit!", null, null);
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
    public void testAddAll() throws Exception{
        HabitHistoryController.getInstance();
        // Clear out the habit history.
        while (!HabitHistoryController.isEmpty()){HabitHistoryController.remove(0);}

        HabitEvent he = null;
        try {
            Habit h = new Habit("Feed the Cat", new Date());
            he = new HabitEvent(h, "Title", "Did my habit!", null, null);
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

    /**
     * For a given Habit, test that HabitOccurrence returns the correct number of HabitEvents in
     * the HabitHistory.
     */
    public void testHabitOccurrence() throws Exception{
        HabitHistoryController.getInstance();
        // Clear out the habit history.
        while (!HabitHistoryController.isEmpty()){HabitHistoryController.remove(0);}

        HabitHistoryController c = HabitHistoryController.getInstance();
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

        c.add(he1);
        assertEquals("HabitEvent 1 should have existed for habit h1", c.habitOccurrence(h1), 1);
        c.add(he2);
        assertEquals("HabitEvent 2 should have existed for habit h1", c.habitOccurrence(h1), 2);
        c.remove(1);
        assertEquals("A HabitEvent for h1 wasn't removed", c.habitOccurrence(h1), 1);

        c.add(he3);
        c.add(he3);
        c.add(he3);
        assertEquals("Adding HabitEvents for a different Habit changed the" +
                "number of occurrences of another habit", c.habitOccurrence(h1), 1);

        assertEquals("The sum of all Habit occurrences in the HabitHistory" +
                        "was not the same as the size of the HabitHistory",
                c.size(), (c.habitOccurrence(h1) + c.habitOccurrence(h2)));
    }

    /**
     *  Tests sorting habit history list based on Date, most recent coming first
     *
     */
    public void testSortHabitHistory() throws Exception{
        HabitHistoryController.getInstance();
        // Clear out the habit history.
        while (!HabitHistoryController.isEmpty()){HabitHistoryController.remove(0);}

        HabitHistoryController c = HabitHistoryController.getInstance();

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

        c.add(habitEvent);
        c.add(habitEvent1);
        c.sortHabitHistory();

        String title1 = null;
        String title2 = null;

        try {
            title1 = c.get(0).getHabitEventTitle();
        }
        catch(HabitEventNameException e){
            //null
        }

        try{
            title2 = c.get(1).getHabitEventTitle();
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
    public void testFilterByTitle() throws Exception{
        HabitHistoryController c = HabitHistoryController.getInstance();

        // Clear out the habit history.
        while (!c.isEmpty()){c.remove(0);}

        HabitEvent he = new HabitEvent(new Habit("Basketball", new Date()),
                "Swimmed with Jack", null, null, null);
        HabitEvent he2 = new HabitEvent(new Habit("Swimming", new Date()),
                "Swimmed with Jack", null, null, null);
        HabitEvent he3 = new HabitEvent(new Habit("Playing With Jack", new Date()),
                "Swimmed with Jack", null, null, null);


        c.add(he);
        c.add(he2);
        c.add(he3);
        c.filterByTitle("Swimming");

        assertEquals("Swimming", c.get(0).getHabitTitle());
        assertEquals(1, c.size());

        c.filterByTitle("Playing With Jack");
        assertEquals(0, c.size());
    }

    /**
     *  Tests filtering habit history list by habits containing a comment
     *
     */
    public void testFilterByComment() throws Exception{
        HabitHistoryController c = HabitHistoryController.getInstance();

        // Clear out the habit history.
        while (!c.isEmpty()){c.remove(0);}

        HabitEvent he = new HabitEvent(new Habit("Basketball", new Date()),
                "Swimmed with Jack", "Fun", null, null);
        HabitEvent he2 = new HabitEvent(new Habit("Swimming", new Date()),
                "Swimmed with Jack", "Not Happy", null, null);

        c.add(he);
        c.add(he2);
        c.filterByComment("Fun");

        assertEquals("Fun", c.get(0).getComment());
        assertEquals(1, c.size());
    }


}