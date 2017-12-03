package com.wsfmn.controller;

import android.support.annotation.Nullable;
import android.util.Log;

import com.wsfmn.model.Habit;
import com.wsfmn.model.HabitEvent;
import com.wsfmn.model.HabitHistory;
import com.wsfmn.model.HabitList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * Controller class for Habit History model class.
 *
 */
public class HabitHistoryController {
    private static HabitHistoryController INSTANCE = null;
    private static HabitHistory habitHistory = null;

    //  Added by alsobaie on 2017/11/22
    //  Used for user to search habit history list.
    //  Gets reloaded (by copying values from original habitHistory)
    //  then filtered each time the user does a search
    private static HabitHistory habitHistoryFilter = null;


    /**
     * Instantiates the habitHistory attribute.
     * This pulls the data from the locally saved HabitHistory via OfflineController using init().
     */
    private HabitHistoryController() {
        habitHistory = new HabitHistory();
        init();
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
     *  Added by alsobaie on 2017/11/22
     *  Instantiates the filtered habit history list.
     *
     * @return HabitHistory, a filtered habit history list
     */
    public HabitHistory getFilteredInstance(){
        getInstance();

        if(habitHistoryFilter == null)
            habitHistoryFilter = new HabitHistory();

        reloadFilter();
        habitHistoryFilter.sortHabitHistory();
        return habitHistoryFilter;
    }

    /**
     *  Added by alsobaie on 2017/11/22
     *  Copies values from habit history list to filtered habit history list.
     *
     */
    public void reloadFilter(){
        habitHistoryFilter.getHabitEventList().clear();
        for(int i = 0; i < habitHistory.size(); i++){
            habitHistoryFilter.add(habitHistory.get(i));
        }
    }

    /**
     *
     */
    public HabitHistory getFilteredHabitHistory(){
        return habitHistoryFilter;
    }

    /**
     * Checks if HabitHistory is empty.
     *
     * @return true if empty, false otherwise
     */
    public Boolean isEmpty() {
        return habitHistory.isEmpty();
    }

    /**
     * Gets the size of the habit history.
     *
     * @return number of entries in HabitHistory
     */
    public int size() {
        return habitHistory.size();
    }

    /**
     * For a given Habit, gets its number of HabitEvents in habitHistory.
     *
     * @param h Habit to get the number of occurrences of
     * @return the number of occurrences of the given Habit
     */
    public int habitOccurrence(Habit h) {
        return habitHistory.habitOccurrence(h);
    }

    /**
     * Appends a HabitEvent to the end of HabitHistory.
     *
     * @param he HabitEvent to add to HabitHistory
     */
    public void add(HabitEvent he) {
        habitHistory.add(he);
    }

    /**
     *  Sorts habit history list based on Date, most recent coming first.
     *
     */
    public void sortHabitHistory(){
        habitHistory.sortHabitHistory();
    }

    /**
     * Stores a HabitEvent online, locally (appended to HabitHistory), and offline.
     *
     * @param he HabitEvent to add to HabitHistory
     */
    public void addAndStore(HabitEvent he) {
        OnlineController.StoreHabitEvents storeHabitEvents =
                new OnlineController.StoreHabitEvents();
        OfflineController.StoreHabitHistory storeHabitHistory =
                new OfflineController.StoreHabitHistory();
        try {
            storeHabitEvents.execute(he).get(); // .get() waits for this task to complete
            add(he);
            storeHabitHistory.execute(habitHistory).get(); // .get() waits for this task to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //  added by alsobaie on 2017/11/29
        HabitListController.getInstance().updateHabitScore(he.getHabit());
    }

    /**
     * Returns the HabitEvent at a particular index.
     *
     * @param idx index of the HabitEvent to get
     * @return HabitEvent at the specified index
     * @throws IndexOutOfBoundsException
     */
    public HabitEvent get(int idx) throws IndexOutOfBoundsException {
        return habitHistory.get(idx);
    }


    /**
     * Get habit by it's id
     * @param id
     * @return
     */
    public HabitEvent get(String id)  {
        for (int i = 0; i<habitHistory.size(); i++){
            if (habitHistory.get(i).getId().equals(id)){
                return habitHistory.get(i);
            }
        }
        return null;
    }

    /**
     * Removes and returns a HabitEvent at the specified index in HabitHistory.
     * NOTE: To save this change and update online instead use, removeAndStore(HabitEvent he).
     *
     * @param idx int: the index of the HabitEvent to remove
     * @return HabitEvent removed from the specified index
     * @throws IndexOutOfBoundsException
     */
    public HabitEvent remove(int idx) throws IndexOutOfBoundsException{
        return habitHistory.remove(idx);
    }

    /**
     * Removes and returns a HabitEvent from HabitHistory.
     * NOTE: To save this change and update online instead use, removeAndStore(HabitEvent he).
     *
     * @param he HabitEvent: the HabitEvent to remove
     * @return HabitEvent removed from HabitHistory or null if in HabitHistory
     * @throws IndexOutOfBoundsException
     */
    @Nullable
    public HabitEvent remove(HabitEvent he) throws IndexOutOfBoundsException{
        int idx = habitHistory.indexOf(he);
        if (idx != -1) {
            return habitHistory.remove(idx);
        } else {
            return null;
        }
    }

    /**
     *  Returns a copy of habit history list with only habit events belonging to h
     *
     * @param title a habit used to filter habit history list
     * @return HabitHistory a filtered copy of habit history list based on h
     */
    public void filterByTitle(String title){
        habitHistory.filterByTitle(title);
    }

    /**
     * Returns a copy of habit history list with only habit events containing
     * comment in their comment String
     *
     * @param comment a string that we use to filter habit history list
     * @return HabitHistory a filtered copy of habit history list based on comment
     * @throws Exception
     */
    public void filterByComment(String comment) {
        habitHistory.filterByComment(comment);
    }

    /**
     * Removes and returns a HabitEvent from HabitHistory, and online,
     * and decrements the HabitHistory indices that follow it, saving all changes.
     *
     * @param he HabitEvent: the HabitEvent to remove
     * @return HabitEvent removed from HabitHistory or null if not in HabitHistory
     * @throws IndexOutOfBoundsException
     */
    @Nullable
    public HabitEvent removeAndStore(HabitEvent he) throws IndexOutOfBoundsException{
        int idx = habitHistory.indexOf(he);
        if (idx != -1) {
            if (OnlineController.isConnected()) {
                OnlineController.DeleteHabitEvents deleteHabitEventsOnline =
                        new OnlineController.DeleteHabitEvents();
                deleteHabitEventsOnline.execute(he.getId());

            } else {
                // Store this Habit Event ID for online deletion upon next connection
                OfflineController.addToOfflineDelete("HEV", he.getId());
            }
            HabitEvent removed = habitHistory.remove(idx);
            store();

            //  added by alsobaie on 2017/11/29
            HabitListController.getInstance().updateHabitScore(removed.getHabit());

            return removed;
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
    public Boolean contains(HabitEvent habitEvent) {
        return habitHistory.contains(habitEvent);
    }

    /**
     * Get the first index of the specified HabitEvent, if it is in HabitHistory.
     *
     * @param habitEvent HabitEvent: return the first index of this HabitEvent
     * @return int first index of the specified HabitEvent
     */
    public int indexOf(HabitEvent habitEvent){
        return habitHistory.indexOf(habitEvent);
    }

    /**
     * Add a List of HabitEvents to the HabitHistory.
     *
     * @param habitEvents List<HabitEvent>: a List of HabitEvents to add to HabitHistory
     */
    public void addAllHabitEvents(List<HabitEvent> habitEvents) {
        habitHistory.addAllHabitEvents(habitEvents);
    }

    /**
     * Stores HabitHistory online, and offline.
     */
    public void storeAll() {
        OnlineController.StoreHabitEvents storeHabitEvents =
                new OnlineController.StoreHabitEvents();

        OfflineController.StoreHabitHistory storeHabitHistory =
                new OfflineController.StoreHabitHistory();

        int size = habitHistory.size();
        HabitEvent[] habitEvents = new HabitEvent[size];

        if (size > 0) {
            for (int i = 0; i < size; i++) {
                habitEvents[i] = habitHistory.get(i);
            }

            try {
                storeHabitEvents.execute(habitEvents).get();
                storeHabitHistory.execute(habitHistory).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  Stores HabitHistory data locally.
     */
    public void store(){
        OfflineController.StoreHabitHistory storeHabitHistoryOffline =
                new OfflineController.StoreHabitHistory();
        try {
            storeHabitHistoryOffline.execute(habitHistory).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates a Habit online
     * @param he a HabitEvent to update online
     */
    public void updateOnline(HabitEvent he) {
        OnlineController.StoreHabitEvents storeHabitEventsOnline =
                new OnlineController.StoreHabitEvents();
        try {
            storeHabitEventsOnline.execute(he).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Store the changes to HabitHistory and update the HabitEvent online
     * @param he a HabitEvent to update online
     */
    public void storeAndUpdate(HabitEvent he) {
        OfflineController.StoreHabitHistory storeHabitHistoryOffline =
                new OfflineController.StoreHabitHistory();

        OnlineController.StoreHabitEvents storeHabitEventsOnline =
                new OnlineController.StoreHabitEvents();
        try {
            storeHabitHistoryOffline.execute(habitHistory).get();
            storeHabitEventsOnline.execute(he).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return
     */
    public ArrayList<HabitEvent> getHabitEventList(){
        return  habitHistory.getHabitEventList();
    }

    /**
     *  Initializes the model with data from local storage
     *
     */
    private void init(){
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