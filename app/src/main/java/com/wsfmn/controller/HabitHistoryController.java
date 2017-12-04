package com.wsfmn.controller;

import android.support.annotation.Nullable;
import android.util.Log;

import com.wsfmn.exceptions.HabitEventNameException;
import com.wsfmn.model.Habit;
import com.wsfmn.model.HabitEvent;
import com.wsfmn.model.HabitHistory;
import com.wsfmn.model.HabitList;

import java.io.File;
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
     * @return a filtered habit history list
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
     *  Copy values from habit history list to filtered habit history list.
     *
     */
    public void reloadFilter(){
        habitHistoryFilter.getHabitEventList().clear();
        for(int i = 0; i < habitHistory.size(); i++){
            habitHistoryFilter.add(habitHistory.get(i));
        }
    }

    /**
     * Get the current version of the filtered Habit History.
     *
     * @return a filtered version of the Habit History
     */
    public HabitHistory getFilteredHabitHistory(){
        return habitHistoryFilter;
    }

    /**
     * Check if HabitHistory is empty.
     *
     * @return true if empty, false otherwise
     */
    public Boolean isEmpty() {
        return habitHistory.isEmpty();
    }

    /**
     * Get the size of the habit history.
     *
     * @return number of entries in HabitHistory
     */
    public int size() {
        return habitHistory.size();
    }

    /**
     * For a given Habit, get the number of HabitEvents that reference it.
     *
     * @param h Habit to get the number of occurrences of
     * @return the number of occurrences of the given Habit
     */
    public int habitOccurrence(Habit h) {
        return habitHistory.habitOccurrence(h);
    }

    /**
     * Append a HabitEvent to the end of HabitHistory.
     *
     * @param he HabitEvent to add to HabitHistory
     */
    public void add(HabitEvent he) {
        habitHistory.add(he);
    }

    /**
     *  Sort habit history based on Date, most recent coming first.
     *
     */
    public void sortHabitHistory(){
        habitHistory.sortHabitHistory();
    }

    /**
     * Store a HabitEvent online, offline, and locally (appended to HabitHistory in memory).
     * Also updates the score for the user that is related to this HabitEvent.
     *
     * @param he HabitEvent to add to HabitHistory
     */
    public void addAndStore(HabitEvent he) {
        add(he);

        OnlineController.StoreHabitEvents storeHabitEvents =
                new OnlineController.StoreHabitEvents();
        OfflineController.StoreHabitHistory storeHabitHistory =
                new OfflineController.StoreHabitHistory();

        storeHabitEvents.execute(he);
        storeHabitHistory.execute(habitHistory);

        //  added by alsobaie on 2017/11/29
        HabitListController.getInstance().updateHabitScore(he.getHabit());
    }

    /**
     * Return the HabitEvent at a particular index.
     *
     * @param idx index of the HabitEvent to get
     * @return HabitEvent at the specified index
     * @throws IndexOutOfBoundsException
     */
    public HabitEvent get(int idx) throws IndexOutOfBoundsException {
        return habitHistory.get(idx);
    }


    /**
     * Return a HabitEvent by its ID.
     *
     * @param id of the Habit
     * @return the HabitEvent with that ID, if it exists, otherwise null
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
     * Remove and return a HabitEvent at the specified index in HabitHistory.
     * NOTE: To save this change and update online and offline use: removeAndStore(HabitEvent he).
     *
     * @param idx index of the HabitEvent to remove
     * @return HabitEvent removed from the specified index or null if not in HabitHistory
     * @throws IndexOutOfBoundsException
     */
    public HabitEvent remove(int idx) throws IndexOutOfBoundsException{
        return habitHistory.remove(idx);
    }

    /**
     * Remove and return a HabitEvent from HabitHistory.
     * NOTE: To save this change and update online and offline use: removeAndStore(HabitEvent he).
     *
     * @param he the HabitEvent to remove
     * @return HabitEvent removed from HabitHistory or null if not in HabitHistory
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
     * Filter the habit history by the title of the Habit/HabitEvent.
     *
     * @param title used to filter habit history by title attribute
     */
    public void filterByTitle(String title){
        habitHistory.filterByTitle(title);
    }

    /**
     * Filter the habit history by the comment in a HabitEvent.
     *
     * @param comment used to filter the habit history by comment attribute
     */
    public void filterByComment(String comment) {
        habitHistory.filterByComment(comment);
    }

    /**
     * Remove and return a HabitEvent from HabitHistory, done both online and offline,
     * decrements the HabitHistory indices that follow it, and removes the photos
     * from the file system, saving all changes and updating the user's score as appropriate.
     *
     * @param he the HabitEvent to remove
     * @return the HabitEvent removed from HabitHistory or null if not in HabitHistory
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
            // Delete the photo from the filesystem
            ImageController.getInstance().deleteImage(he.getPhotoPath());

            // Remove HabitEvent from HabitHistory
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
     * @param habitEvent check HabitHistory for this HabitEvent
     * @return Boolean true if the HabitEvent is in HabitHistory, otherwise false
     */
    public Boolean contains(HabitEvent habitEvent) {
        return habitHistory.contains(habitEvent);
    }

    /**
     * Get the first index of the specified HabitEvent, if it is in HabitHistory.
     *
     * @param habitEvent HabitEvent: return the first index of this HabitEvent
     * @return int first index of the specified HabitEvent, otherwise -1
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
     * Store HabitHistory online, and offline.
     *
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
     * Store HabitHistory offline.
     *
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
     * Update a HabitEvent online.
     *
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
     * Update the Habit inside the HabitEvents that reference it.
     *
     * @param h the habit to update within all HabitEvents
     */
    public void pushHabitChangesToHabitEvents(Habit h) {
        for (HabitEvent he : habitHistory.getHabitEventList()) {
            if (he.getHabit().getId().equals(h.getId())) {
                he.setHabit(h);
                try {
                    he.setTitle(h.getTitle());
                } catch (HabitEventNameException e) {
                    e.printStackTrace();
                }
                HabitHistoryController.getInstance();
                updateOnline(he);
            }
        }
        store();
    }

    /**
     * Store the changes to HabitHistory and update the HabitEvent online
     *
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
     * Get the HabitHistory as an ArrayList of HabitEvents
     *
     * @return the underlying List of HabitHistory
     */
    public ArrayList<HabitEvent> getHabitEventList(){
        return  habitHistory.getHabitEventList();
    }

    /**
     *  Initialize the model with data from local storage.
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