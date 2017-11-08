package com.wsfmn.habittracker;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.wsfmn.habit.Date;
import com.wsfmn.habit.DateNotValidException;
import com.wsfmn.habit.Habit;
import com.wsfmn.habit.HabitCommentTooLongException;
import com.wsfmn.habit.HabitEvent;
import com.wsfmn.habit.HabitList;
import com.wsfmn.habit.HabitTitleTooLongException;
import com.wsfmn.habitcontroller.OnlineController;

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
    }


    /**
     *
     */
    public void testStoreHabits(){
        long currentTime;
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

        storeHabits.execute(myHabit1, myHabit2);

        // Delay 1 second for transaction to finish
        currentTime = Calendar.getInstance().getTimeInMillis();
        while((Calendar.getInstance().getTimeInMillis() - currentTime) < 1000 ){}

        assertNotNull(myHabit1.getId());
        assertNotNull(myHabit2.getId());

        deleteHabits.execute(myHabit1, myHabit2);

        // Delay 1 second for transaction to finish
        currentTime = Calendar.getInstance().getTimeInMillis();
        while((Calendar.getInstance().getTimeInMillis() - currentTime) < 1000 ){}
    }


    /**
     *
     */
    public void testGetHabits() {
        long currentTime;
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

        storeHabits.execute(myHabit1, myHabit2);

        // Delay for transaction to finish
        currentTime = Calendar.getInstance().getTimeInMillis();
        while((Calendar.getInstance().getTimeInMillis() - currentTime) < 3000 ){}

        getHabits.execute(searchString);
        try {
            habits = getHabits.get();
        } catch (Exception e) {
            Log.i("Error", "Failed to get the habits from the async object");
        }

        assertNotNull("Habit List from server was null", habits);

        Habit[] toDelete = new Habit[habits.getSize()];
        for (int i = 0; i < habits.getSize(); i++) {
            assertTrue("Habit in Habit List does not contain search string",
                    habits.getHabit(i).getTitle().toLowerCase().contains(searchString));
            toDelete[i] = habits.getHabit(i);
        }
        // Delete all the matching habits from the server
        deleteHabits.execute(toDelete);
        // Delay 1 second for transaction to finish
        currentTime = Calendar.getInstance().getTimeInMillis();
        while((Calendar.getInstance().getTimeInMillis() - currentTime) < 3000 ){}

    }

//    public void testStoreHabitEvents() {
//
//        OnlineController.StoreHabitEvents storeHabitEventsOnline =
//                new OnlineController.StoreHabitEvents();
//
//        try {
//            final HabitEvent habitEvent1 =
//                    new HabitEvent(new Habit("Walk the dog", new Date()),
//                            new Date(), true, "I walked the dog it!");
//            final HabitEvent habitEvent2 =
//                    new HabitEvent(new Habit("Feed the cat", new Date()),
//                            new Date(), true, "I fed the cat!");
//
//            assertNull("NewHabitEvent1 ID was not null", habitEvent1.getId());
//            assertNull("NewHabitEvent2 ID was not null", habitEvent2.getId());
//
//
//            storeHabitEventsOnline.execute(habitEvent1, habitEvent2);
//
//            // Delay for transaction to finish
//            long currentTime = Calendar.getInstance().getTimeInMillis();
//            while ((Calendar.getInstance().getTimeInMillis() - currentTime) < 2000) {
//            }
//
//            assertNotNull("NewHabitEvent ID was null", habitEvent1.getId());
//            assertNotNull("NewHabitEvent ID was null", habitEvent1.getId());
//
//        } catch (HabitCommentTooLongException e) {
//            e.printStackTrace();
//
//        } catch (HabitTitleTooLongException e) {
//            e.printStackTrace();
//
//        } catch (DateNotValidException e) {
//            e.printStackTrace();
//        }
//
//    }


//    public void testGetHabitEventsOnline() {
//        HabitHistory habitHistory = null;
//        OnlineController.GetHabitEventsOnline getHabitHistory = new OnlineController.GetHabitEventsOnline();
//        String searchString = "testhabit";
//
//        assertNull("New Habit List was not null", habitHistory);
//        try {
//            getHabitHistory.execute(searchString);
//            habitHistory = getHabitHistory.get();
//        } catch (Exception e) {
//            Log.i("Error", "Failed to get the habits from the async object");
//        }
//
//        // Delay 0.5 second for transaction to finish (usual time is around 200 ms)
//        long currentTime = Calendar.getInstance().getTimeInMillis();
//        while((Calendar.getInstance().getTimeInMillis() - currentTime) < 500 ){}
//
//        assertNotNull("Habit List from server was null", habitHistory);
//
//    }
}