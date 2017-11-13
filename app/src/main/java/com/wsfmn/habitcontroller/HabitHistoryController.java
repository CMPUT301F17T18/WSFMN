package com.wsfmn.habitcontroller;

import android.support.annotation.Nullable;
import android.util.Log;

import com.wsfmn.habit.Habit;
import com.wsfmn.habit.HabitEvent;
import com.wsfmn.habit.HabitHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by nicholasmayne on 2017-11-08.
 */

public class HabitHistoryController {
    private static HabitHistoryController INSTANCE = null;
    private static HabitHistory habitHistory = null;

    /**
     * Instantiate the habitHistory attribute.
     * This pulls the data from the locally saved HabitHistory via OfflineController.
     */
    private HabitHistoryController() {
        habitHistory = new HabitHistory();
        try {
            OfflineController.GetHabitHistory getHabitHistoryOffline =
                    new OfflineController.GetHabitHistory();
            getHabitHistoryOffline.execute();
            habitHistory = getHabitHistoryOffline.get();
        } catch (InterruptedException e) {
            Log.i("Error", e.getMessage());

        } catch (ExecutionException e) {
            Log.i("Error", e.getMessage());
        }

    }

    /**
     * Access the instance of the HabitHistoryController singleton.
     *
     * @return HabitHistoryController the instance of singleton HabitHistoryController
     */
    public static HabitHistoryController getInstance() {
        if(INSTANCE == null){
            INSTANCE = new HabitHistoryController();
        }
        return INSTANCE;
    }

    /**
     * Checks it the HabitHistory is empty.
     *
     * @return Boolean true if HabitHistory is empty
     */
    public static Boolean isEmpty() {
        return habitHistory.isEmpty();
    }

    /**
     * Get the size of the habit history.
     *
     * @return int the number of entries in HabitHistory
     */
    public static int size() {
        return habitHistory.size();
    }

    /**
     * For a given Habit, get its number of HabitEvents in habitHistory.
     *
     * @param h Habit: the Habit to get the number of occurrances of
     * @return int the number of occurances of the given Habit
     */
    public int habitOccurrence(Habit h) {
        return habitHistory.habitOccurrence(h);
    }

    /**
     * Appends a HabitEvent to the end of HabitHistory.
     *
     * @param he HabitEvent: HabitEvent to add to HabitHistory
     */
    public static void add(HabitEvent he) {
        habitHistory.add(he);
    }

    /**
     * Stores a HabitEvent online, locally (appended to HabitHistory), and offline.
     *
     * @param he HabitEvent: HabitEvent to add to HabitHistory
     */
    public static void addAndStore(HabitEvent he) {
        OnlineController.StoreHabitEvents storeHabitEvents =
                new OnlineController.StoreHabitEvents();
        OfflineController.StoreHabitHistory storeHabitHistory =
                new OfflineController.StoreHabitHistory();

        try {
            storeHabitEvents.execute(he).get(); // .get() waits for this task to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        add(he);
        storeHabitHistory.execute(habitHistory);
    }

    /**
     * Return the HabitEvent at a particular index.
     *
     * @param idx int: the index of the HabitEvent to get
     * @return HabitEvent at the specified index
     * @throws IndexOutOfBoundsException
     */
    public static HabitEvent get(int idx) throws IndexOutOfBoundsException {
        return habitHistory.get(idx);
    }

    /**
     * Removes and returns a HabitEvent at the specified index in HabitHistory
     * To save this change and update online use, removeAndStore(int idx).
     *
     * @param idx int: the index of the HabitEvent to remove
     * @return HabitEvent removed from the specified index
     * @throws IndexOutOfBoundsException
     */
    public static HabitEvent remove(int idx) throws IndexOutOfBoundsException{
        return habitHistory.remove(idx);
    }

    /**
     * Removes and returns a HabitEvent from HabitHistory.
     * To save this change and update online use, removeAndStore(HabitEvent he).
     *
     * @param he HabitEvent: the HabitEvent to remove
     * @return HabitEvent removed from HabitHistory or null if in HabitHistory
     * @throws IndexOutOfBoundsException
     */
    @Nullable
    public static HabitEvent remove(HabitEvent he) throws IndexOutOfBoundsException{
        int idx = habitHistory.indexOf(he);
        if (idx != -1) {
            return habitHistory.remove(idx);
        } else {
            return null;
        }
    }

    //TODO: Handle the case where removing a habit but the user is offline and we need
    // to remove it later online when they are connected again
    /**
     * Removes and returns a HabitEvent at the specified index in HabitHistory, and online,
     * and decrements the HabitHistory indices that follow it, saving all changes.
     *
     * @param idx int: the index of the HabitEvent to remove
     * @return HabitEvent removed from HabitHistory
     * @throws IndexOutOfBoundsException
     */
    public static HabitEvent removeAndStore(int idx) throws IndexOutOfBoundsException{
        HabitEvent removed = habitHistory.remove(idx);

        OnlineController.DeleteHabitEvents deleteHabitEventsOnline =
                new OnlineController.DeleteHabitEvents();
        deleteHabitEventsOnline.execute(removed);
        store();

        return removed;
    }

    //TODO: Handle the case where removing a habit but the user is offline and we need
    // to remove it later online when they are connected again
    /**
     * Removes and returns a HabitEvent from HabitHistory, and online,
     * and decrements the HabitHistory indices that follow it, saving all changes.
     *
     * @param he HabitEvent: the HabitEvent to remove
     * @return HabitEvent removed from HabitHistory or null if in HabitHistory
     * @throws IndexOutOfBoundsException
     */
    @Nullable
    public static HabitEvent removeAndStore(HabitEvent he) throws IndexOutOfBoundsException{
        int idx = habitHistory.indexOf(he);
        if (idx != -1) {
            OnlineController.DeleteHabitEvents deleteHabitEventsOnline =
                    new OnlineController.DeleteHabitEvents();
            deleteHabitEventsOnline.execute(he);
            store();
            return habitHistory.remove(idx);
        } else {
            return null;
        }
    }

    /**
     * Check to see if a HabitEvent is in HabitHistory.
     *
     * @param habitEvent HabitEvent: check HabitHistory for this HabitEvent
     * @return Boolean true if the HabitEvent is in HabitHistory
     */
    public static Boolean contains(HabitEvent habitEvent) {
        return habitHistory.contains(habitEvent);
    }

    /**
     * Get the first index of the specified HabitEvent, if it is in HabitHistory.
     *
     * @param habitEvent HabitEvent: return the first index of this HabitEvent
     * @return int first index of the specified HabitEvent
     */
    public static int indexOf(HabitEvent habitEvent){
        return habitHistory.indexOf(habitEvent);
    }

    /**
     * Add a List of HabitEvents to the HabitHistory.
     *
     * @param habitEvents List<HabitEvent>: a List of HabitEvents to add to HabitHistory
     */
    public static void addAllHabitEvents(List<HabitEvent> habitEvents) {
        habitHistory.addAllHabitEvents(habitEvents);
    }

    /**
     * Stores HabitHistory online, and offline.
     */
    public static void storeAll() {
        OnlineController.StoreHabitEvents storeHabitEvents =
                new OnlineController.StoreHabitEvents();

        OfflineController.StoreHabitHistory storeHabitHistory =
                new OfflineController.StoreHabitHistory();

        int size = habitHistory.size();
        HabitEvent[] habitEvents = new HabitEvent[size];

        for (int i = 0; i < size; i++){
            habitEvents[i] = habitHistory.get(i);
        }

        try {
            storeHabitEvents.execute(habitEvents).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        storeHabitHistory.execute(habitHistory);
    }

    /**
     *  Stores HabitHistory data locally.
     */
    public static void store(){
        OfflineController.StoreHabitHistory storeHabitHistoryOffline =
                new OfflineController.StoreHabitHistory();
        storeHabitHistoryOffline.execute(habitHistory);
    }

    /**
     * Updates a Habit online
     * @param he a HabitEvent to update online
     */
    public static void updateOnline(HabitEvent he) {
        OnlineController.StoreHabitEvents storeHabitEventsOnline =
                new OnlineController.StoreHabitEvents();
        storeHabitEventsOnline.execute(he);
    }

    /**
     * Store the changes to HabitHistory and update the HabitEvent online
     * @param he a HabitEvent to update online
     */
    public static void storeAndUpdate(HabitEvent he) {
        OfflineController.StoreHabitHistory storeHabitHistoryOffline =
                new OfflineController.StoreHabitHistory();
        storeHabitHistoryOffline.execute(habitHistory);

        OnlineController.StoreHabitEvents storeHabitEventsOnline =
                new OnlineController.StoreHabitEvents();
        storeHabitEventsOnline.execute(he);
    }

    /**
     *
     * @return
     */
    public static ArrayList<HabitEvent> getHabitEventList(){
        return  habitHistory.getHabitEventList();
    }
}