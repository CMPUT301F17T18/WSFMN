package com.wsfmn.view.controller;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;


import com.wsfmn.model.Date;
import com.wsfmn.exceptions.DateNotValidException;
import com.wsfmn.model.Habit;
import com.wsfmn.exceptions.HabitCommentTooLongException;
import com.wsfmn.model.HabitEvent;
import com.wsfmn.model.HabitHistory;
import com.wsfmn.model.HabitList;
import com.wsfmn.exceptions.HabitTitleTooLongException;
import com.wsfmn.model.ProfileName;
import com.wsfmn.controller.OfflineController;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

/**
 * Created by Fredric on 2017-10-21.
 * Edited by nmayne on 2017-10-25.
 *
 */


public class OfflineControllerTest extends ActivityInstrumentationTestCase2 {

    public OfflineControllerTest() {
        super(OfflineController.class);
    }


    public void testHabitList() {
        HabitList habitList = new HabitList();

        try {
            Habit myHabit = new Habit("My Habit", new Date());
            habitList.addHabit(myHabit);

            OfflineController.StoreHabitList storeHabitListOffline = new OfflineController.StoreHabitList();
            storeHabitListOffline.execute(habitList);

            OfflineController.GetHabitList getHabitListOffline = new OfflineController.GetHabitList();
            getHabitListOffline.execute();

            HabitList habitListNew = getHabitListOffline.get();

            assertEquals(habitList.getHabit(0).getTitle(), habitListNew.getHabit(0).getTitle());


        } catch (HabitTitleTooLongException e) {
            Log.i("TestStoreGetHabits", e.toString());

        } catch (DateNotValidException e) {
            Log.i("TestStoreGetHabits", e.toString());

        } catch (InterruptedException e) {
            Log.i("TestStoreGetHabits", e.toString());

        } catch (ExecutionException e) {
            Log.i("TestStoreGetHabits", e.toString());

        }
    }


    public void testHabitHistory() throws Exception{
        HabitHistory habitHistory = new HabitHistory();

        try {
            Habit myHabit = new Habit("My Habit", new Date());
            HabitEvent myDoneHabitEvent = new HabitEvent(myHabit, "Title", "Did my habit!", null,
                    null, new Date());
            HabitEvent myNotDoneHabitEvent = new HabitEvent(myHabit, "Title", "Did my habit!", null,
                    null, new Date());

            habitHistory.add(myDoneHabitEvent);
            habitHistory.add(myNotDoneHabitEvent);

            assertTrue(habitHistory.contains(myDoneHabitEvent));
            assertTrue(habitHistory.contains(myNotDoneHabitEvent));

            OfflineController.StoreHabitHistory storeHabitHistoryOffline = new OfflineController.StoreHabitHistory();
            storeHabitHistoryOffline.execute(habitHistory);

            OfflineController.GetHabitHistory getHabitHistoryOffline = new OfflineController.GetHabitHistory();
            getHabitHistoryOffline.execute();

            HabitHistory habitHistoryNew = getHabitHistoryOffline.get();



        } catch (HabitTitleTooLongException e) {
            Log.i("TestStoreGetHabits", e.toString());

        } catch (DateNotValidException e){
            Log.i("TestStoreGetHabits", e.toString());

        } catch (InterruptedException e) {
            Log.i("TestStoreGetHabits", e.toString());

        } catch (ExecutionException e) {
            Log.i("TestStoreGetHabits", e.toString());
        }
        catch (HabitCommentTooLongException e) {
            Log.i("TestStoreGetHabits", e.toString());

        }
    }


    /**
     *
     */
    public void testUserProfile() {
        OfflineController.StoreUserProfile storeUserProfile =
                new OfflineController.StoreUserProfile();

        OfflineController.GetUserProfile getUserProfile =
                new OfflineController.GetUserProfile();

        ProfileName profileName = new ProfileName("USERNAME");

        ProfileName retrievedProfile = new ProfileName();

        assertEquals(retrievedProfile.getName(), "");


        storeUserProfile.execute(profileName);
        try {
            getUserProfile.execute();
            retrievedProfile = getUserProfile.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        assertEquals(retrievedProfile.getName(), profileName.getName());


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
