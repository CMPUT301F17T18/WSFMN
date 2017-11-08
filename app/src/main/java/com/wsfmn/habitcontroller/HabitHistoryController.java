package com.wsfmn.habitcontroller;

import android.util.Log;

import com.wsfmn.habit.HabitEvent;
import com.wsfmn.habit.HabitHistory;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by nicholasmayne on 2017-11-08.
 */

class HabitHistoryController {
    private static final HabitHistoryController ourInstance = new HabitHistoryController();
    private static HabitHistory habitHistory = null;


    private HabitHistoryController() {
        getInstance();
    }


    static HabitHistoryController getInstance() {
        if (habitHistory == null) {
            habitHistory = new HabitHistory();
            init();
        }
        return ourInstance;
    }


    /**
     * Checks it the HabitHistory is empty
     * @return Boolean true if HabitHistory is empty
     */
    public static Boolean isEmpty() {
        return habitHistory.isEmpty();
    }


    /**
     * Appends a HabitEvent to the end of HabitHistory
     * @param habitEvent HabitEvent: HabitEvent to add to HabitHistory
     */
    public static void add(HabitEvent habitEvent) {
        habitHistory.add(habitEvent);
    }


    /**
     *
     * @param index int: the index of the HabitEvent to get
     * @return HabitEvent at the specified index
     * @throws IndexOutOfBoundsException
     */
    public static HabitEvent get(int index) throws IndexOutOfBoundsException {
        return habitHistory.get(index);
    }


    /**
     * Removes and returns a HabitEvent at the specified index in HabitHistory,
     * and decrements the HabitHistory indices that follow it.
     * @param index int: the index of the HabitEvent to remove
     * @return HabitEvent remove from the specified index
     * @throws IndexOutOfBoundsException
     */
    public static HabitEvent remove(int index) throws IndexOutOfBoundsException{
        return habitHistory.remove(index);
    }


    /**
     * Check to see it a HabitEvent is in HabitHistory
     * @param habitEvent HabitEvent: check HabitHistory for this HabitEvent
     * @return Boolean true if the HabitEvent is in HabitHistory
     */
    public static Boolean contains(HabitEvent habitEvent) {
        return habitHistory.contains(habitEvent);
    }


    /**
     * Get the first index of the specified HabitEvent, if it is in HabitHistory
     * @param habitEvent HabitEvent: return the first index of this HabitEvent
     * @return int first index of the specified HabitEvent
     */
    public static int indexOf(HabitEvent habitEvent){
        return habitHistory.indexOf(habitEvent);
    }


    /**
     * Add a List of HabitEvents to the HabitHistory
     * @param habitEvents List<HabitEvent>: a List of HabitEvents to add to HabitHistory
     */
    public static void addAllHabitEvents(List<HabitEvent> habitEvents) {
        habitHistory.addAllHabitEvents(habitEvents);
    }


    /**
     *  Used to load local data into habitHistory once it is first created.
     */
    public static void init() {
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
}