package com.wsfmn.view.habitcontrollers;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.wsfmn.model.Date;
import com.wsfmn.model.DateNotValidException;
import com.wsfmn.model.Habit;
import com.wsfmn.model.HabitCommentTooLongException;
import com.wsfmn.model.HabitEvent;
import com.wsfmn.model.HabitEventCommentTooLongException;
import com.wsfmn.model.HabitHistory;
import com.wsfmn.model.HabitList;
import com.wsfmn.model.HabitTitleTooLongException;
import com.wsfmn.model.ProfileName;
import com.wsfmn.controller.OnlineController;
import com.wsfmn.view.MainActivity;

import java.util.concurrent.ExecutionException;

import static java.lang.Thread.sleep;


/**
 * Created by nicholasmayne on 2017-10-17.
 */

public class OnlineControllerTest extends ActivityInstrumentationTestCase2 {

    /**
     *
     */
    public OnlineControllerTest() {
        super(MainActivity.class);
    }

    /**
     * Test the online connection
     */
    public void testIsConnected() {
        assertTrue(OnlineController.isConnected());
    }

    /**
     * Test that Habits can be successfully stored via ElasticSearch
     */
    public void testStoreHabits() {
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
    public void testStoreHabitEvents() throws Exception{
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
    public void testGetHabitEvents() throws HabitEventCommentTooLongException {
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

        getHabitEvents.execute(searchString);
        try {
            habitHistory = getHabitEvents.get();
        } catch (Exception e) {
            Log.i("Error", "Failed to get the habits from the async object");
        }

        assertNotNull("Habit History from server was null", habitHistory);

        HabitEvent[] toDelete = new HabitEvent[habitHistory.size()];
        for (int i = 0; i < habitHistory.size(); i++) {
            assertTrue("HabitEvent in HabitHistory does not contain search string",
                    habitHistory.get(i).getComment().toLowerCase().contains(searchString));
            toDelete[i] = habitHistory.get(i);
        }

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
     * Test if we can store a profilename in ElasticSearch
     */
    public void testStoreNameinDataBase() {
        OnlineController check = new OnlineController();
        OnlineController.StoreNameInDataBase store = new OnlineController.StoreNameInDataBase();
        OnlineController.DeleteProfileName delete = new OnlineController.DeleteProfileName();

        ProfileName profilename = new ProfileName("junit");
        assertEquals(true, check.checkName("junit"));
        store.execute(profilename);
        try {
            store.get();
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        assertEquals(false, check.checkName("junit"));

        try {
            delete.execute("junit");
            delete.get(); // wait for thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        assertEquals(true, check.checkName("junit"));

    }

//    /**
//     * Delay for this many milliseconds
//     * @param ms milliseconds to delay
//     */
//    private void Delay(int ms){
//        // Delay 1 second for transaction to finish
//        long currentTime = Calendar.getInstance().getTimeInMillis();
//        while((Calendar.getInstance().getTimeInMillis() - currentTime) < ms ){}
//    }
}