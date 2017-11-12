package com.wsfmn.habittracker.habitcontrollers;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.wsfmn.habit.Date;
import com.wsfmn.habit.DateNotValidException;
import com.wsfmn.habit.Habit;
import com.wsfmn.habit.HabitCommentTooLongException;
import com.wsfmn.habit.HabitEvent;
import com.wsfmn.habit.HabitEventCommentTooLongException;
import com.wsfmn.habit.HabitHistory;
import com.wsfmn.habit.HabitList;
import com.wsfmn.habit.HabitTitleTooLongException;
import com.wsfmn.habitcontroller.OnlineController;
import com.wsfmn.habittracker.MainActivity;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;


/**
 * Created by nicholasmayne on 2017-10-17.
 */

public class OnlineControllerTest extends ActivityInstrumentationTestCase2 {

    /**
     *
     */
    public OnlineControllerTest() {
        super(MainActivity.class);
        OnlineController.setUSERNAME("testing");
    }

    /**
     * Test the online connection
     */
    public void testIsConnected(){
        assertTrue(OnlineController.isConnected());

    }

    /**
     * Test that Habits can be successfully stored via ElasticSearch
     */
    public void testStoreHabits(){
        Habit myHabit1 = null;
        Habit myHabit2 = null;
        OnlineController.StoreHabits storeHabits = new OnlineController.StoreHabits();
        OnlineController.DeleteHabits deleteHabits = new OnlineController.DeleteHabits();

        try {
            myHabit1 = new Habit("Walk the Dog", new Date());
            myHabit2 = new Habit("Feed the Cat", new Date());
            assertNull(myHabit1.getId());
            assertNull(myHabit2.getId());
        } catch (HabitTitleTooLongException e) {
            e.printStackTrace();
        } catch (DateNotValidException e) {
            e.printStackTrace();
        }

        try {
            storeHabits.execute(myHabit1, myHabit2);
            storeHabits.get(); // wait for thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Delay(1000); // Wait for server to catch up

        assertNotNull(myHabit1.getId());
        assertNotNull(myHabit2.getId());

        try {
            deleteHabits.execute(myHabit1, myHabit2);
            deleteHabits.get(); // wait for thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    /**
     * Test that GetHabits returns a HabitList via ElasticSearch
     */
    public void testGetHabits() {
        HabitList habits = null;
        Habit myHabit1 = null;
        Habit myHabit2 = null;
        OnlineController.StoreHabits storeHabits = new OnlineController.StoreHabits();
        OnlineController.DeleteHabits deleteHabits = new OnlineController.DeleteHabits();
        OnlineController.GetHabits getHabits = new OnlineController.GetHabits();
        String searchString = "the";

        assertNull("New Habit List was not null", habits);

        try {
            myHabit1 = new Habit("Walk the dog", new Date());
            myHabit2 = new Habit("Feed the cat", new Date());
            assertNull(myHabit1.getId());
            assertNull(myHabit2.getId());
        } catch (HabitTitleTooLongException e) {
            e.printStackTrace();
        } catch (DateNotValidException e) {
            e.printStackTrace();
        }

        try {
            storeHabits.execute(myHabit1, myHabit2);
            storeHabits.get(); // wait for thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Delay(1000); // Wait for server to catch up


        getHabits.execute(searchString);
        try {
            habits = getHabits.get();
        } catch (Exception e) {
            Log.i("Error", "Failed to get the habits from the async object");
        }

        assertNotNull("Habit List from server was null", habits);

        Habit[] toDelete = new Habit[habits.size()];
        for (int i = 0; i < habits.size(); i++) {
            assertTrue("Habit in Habit List does not contain search string",
                    habits.getHabit(i).getTitle().toLowerCase().contains(searchString));
            toDelete[i] = habits.getHabit(i);
        }

        // Delete all the matching habits from the server
        try {
            deleteHabits.execute(toDelete);
            deleteHabits.get(); // wait for thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test that HabitEvents can be successfully stored via ElasticSearch
     */
    public void testStoreHabitEvents() {
        HabitEvent habitEvent1 = null;
        HabitEvent habitEvent2 = null;

        OnlineController.StoreHabitEvents storeHabitEvents = new OnlineController.StoreHabitEvents();
        OnlineController.DeleteHabitEvents deleteHabitEvents = new OnlineController.DeleteHabitEvents();

        try {
            habitEvent1 = new HabitEvent(
                    new Habit("My Habit 1", new Date()), "Title", "Did my habit 1!", null, null);
            habitEvent2 = new HabitEvent(
                    new Habit("My Habit 2", new Date()), "Title", "Did my habit 2!", null, null);
        } catch (HabitCommentTooLongException e) {
            e.printStackTrace();
        } catch (DateNotValidException e) {
            e.printStackTrace();
        } catch (HabitTitleTooLongException e) {
            e.printStackTrace();
        }

        assertNull("NewHabitEvent1 ID was not null", habitEvent1.getId());
        assertNull("NewHabitEvent2 ID was not null", habitEvent2.getId());

        try {
            storeHabitEvents.execute(habitEvent1, habitEvent2);
            storeHabitEvents.get(); // wait for thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Delay(1000); // Wait for server to catch up

        assertNotNull("NewHabitEvent ID was null", habitEvent1.getId());
        assertNotNull("NewHabitEvent ID was null", habitEvent1.getId());

        try {
            deleteHabitEvents.execute(habitEvent1, habitEvent2);
            deleteHabitEvents.get(); // wait for thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test that GetHabitEvents returns a HabitHistory via ElasticSearch
     */
    public void testGetHabitEvents() {
        HabitHistory habitHistory = null;
        HabitEvent habitEvent1 = null;
        HabitEvent habitEvent2 = null;

        OnlineController.StoreHabitEvents storeHabitEvents = new OnlineController.StoreHabitEvents();
        OnlineController.DeleteHabitEvents deleteHabitEvents = new OnlineController.DeleteHabitEvents();
        OnlineController.GetHabitEvents getHabitEvents = new OnlineController.GetHabitEvents();
        String searchString = "habit";

        assertNull("New Habit History was not null", habitHistory);

        try {
            habitEvent1 = new HabitEvent(
                    new Habit("My Habit 1", new Date()), "Title", "Did my habit 1!", null, null);
            habitEvent2 = new HabitEvent(
                    new Habit("My Habit 2", new Date()), "Title", "Did my habit 2!", null, null);
        } catch (HabitCommentTooLongException e) {
            e.printStackTrace();
        } catch (DateNotValidException e) {
            e.printStackTrace();
        } catch (HabitTitleTooLongException e) {
            e.printStackTrace();
        }

        try {
            storeHabitEvents.execute(habitEvent1, habitEvent2);
            storeHabitEvents.get(); // wait for thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Delay(1000); // Wait for server to catch up

        getHabitEvents.execute(searchString);
        try {
            habitHistory = getHabitEvents.get();
        } catch (Exception e) {
            Log.i("Error", "Failed to get the habits from the async object");
        }

        assertNotNull("Habit History from server was null", habitHistory);

        HabitEvent[] toDelete = new HabitEvent[habitHistory.size()];
        for (int i = 0; i < habitHistory.size(); i++) {
            try {
                assertTrue("HabitEvent in HabitHistory does not contain search string",
                        habitHistory.get(i).getComment().toLowerCase().contains(searchString));
            } catch (HabitEventCommentTooLongException e) {
                e.printStackTrace();
            }
            toDelete[i] = habitHistory.get(i);
        }

        assertTrue(habitHistory.contains(habitEvent1));
        assertTrue(habitHistory.contains(habitEvent2));

        // Delete all the matching habits from the server
        try {
            deleteHabitEvents.execute(toDelete);
            deleteHabitEvents.get(); // wait for thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delay for this many milliseconds
     * @param ms milliseconds to delay
     */
    private void Delay(int ms){
        // Delay 1 second for transaction to finish
        long currentTime = Calendar.getInstance().getTimeInMillis();
        while((Calendar.getInstance().getTimeInMillis() - currentTime) < ms ){}
    }
}