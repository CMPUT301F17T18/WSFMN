package com.wsfmn.habittracker;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import java.util.Calendar;


/**
 * Created by nicholasmayne on 2017-10-17.
 */

public class OnlineControllerTest extends ActivityInstrumentationTestCase2 {

    public OnlineControllerTest() {
        super(MainActivity.class);
    }


    public void testStoreHabitsOnline(){

        try {
            final Habit newHabit = new Habit("HabitTitle", new Date());

            assertNull("New habit ID was not null", newHabit.getId());

            OnlineController.StoreHabits storeHabitsOnline =
                    new OnlineController.StoreHabits();
            storeHabitsOnline.execute(newHabit);

            // Delay 0.5 second for transaction to finish (usual time is around 200 ms)
            long currentTime = Calendar.getInstance().getTimeInMillis();
            while((Calendar.getInstance().getTimeInMillis() - currentTime) < 500 ){}

            assertNotNull("New habit ID was null", newHabit.getId());
        }
        catch(HabitTitleTooLongException e){
            // TODO: handle exception
        }

    }


    public void testGetHabitsOnline() {
        HabitList habits = null;
        OnlineController.GetHabits getHabitList = new OnlineController.GetHabits();
        String searchString = "testhabit";

        assertNull("New Habit List was not null", habits);
        try {
            getHabitList.execute(searchString);
            habits = getHabitList.get();
        } catch (Exception e) {
            Log.i("Error", "Failed to get the habits from the async object");
        }

        // Delay 0.5 second for transaction to finish (usual time is around 200 ms)
        long currentTime = Calendar.getInstance().getTimeInMillis();
        while((Calendar.getInstance().getTimeInMillis() - currentTime) < 500 ){}

        assertNotNull("Habit List from server was null", habits);
        assertTrue("Habit in Habit List does not contain search string",
                habits.getHabit(0).getTitle().toLowerCase().contains(searchString));
    }


//    public void testAddHabitEventsOnline(){
//
//        try {
//            final HabitEvent newHabitEvent =
//                    new HabitEvent(new Habit("testHabit", new Date()), new Date(), true, "I did it!" );
//
//            assertNull("NewHabitEvent ID was not null", newHabitEvent.getId());
//
//            OnlineController.StoreHabitEventsOnline storeHabitEventsOnline =
//                    new OnlineController.StoreHabitEventsOnline();
//            storeHabitEventsOnline.execute(newHabitEvent);
//
//            // Delay 0.5 second for transaction to finish (usual time is around 200 ms)
//            long currentTime = Calendar.getInstance().getTimeInMillis();
//            while((Calendar.getInstance().getTimeInMillis() - currentTime) < 500 ){}
//
//            assertNotNull("NewHabitEvent ID was null", newHabitEvent.getId());
//        }
//        catch(HabitCommentTooLongException e){
//            // TODO: handle exception
//        }
//        catch (HabitTitleTooLongException e){
//
//        }
//
//    }
//

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