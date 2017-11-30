package com.wsfmn.controller;

import android.util.Log;

import com.wsfmn.model.Habit;
import com.wsfmn.model.HabitEvent;
import com.wsfmn.model.HabitList;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by musaed on 2017-11-04.
 */

public class HabitListController{

    
    /**
     *  https://drive.google.com/file/d/0B-dXFEI__NgkZ2F5Y2ZXWlY1cWM/view
     *
     *  This controller uses Singleton design pattern.
     *  One instance of the model HabitList is created
     *  during the duration of the program and can be
     *  accessed anywhere in the program by creating a
     *  HabitListController object (we don't use intents
     *  to pass model data between activities)
     *
     *
     *      HabitListController c = new HabitListController();
     *
     *
     *  When this is first called, a new model is initialized
     *  and we call init to populate the model with data from local
     *  storage
     *  (you don't need to do this, this is done by the constructor)
     *
     *  Everything in the model is updated by calling methods
     *  from the controller
     *
     *      c.addHabit(Habit);
     *      c.store();
     *
     *  we call c.store to store the new habit locally.
     */

    private static HabitListController INSTANCE = null;
    private static HabitList habitList = null;

    /**
     * Instantiates the habitList attribute.
     * This pulls the data from the locally saved HabitList via OfflineController using init().
     */
    private HabitListController(){
        habitList = new HabitList();
        init();
    }

    /**
     * Accesses the instance of HabitListController singleton
     *
     * @return HabitListController the instance of singleton controller
     */
    public static HabitListController getInstance() {
        if(INSTANCE == null){
            INSTANCE = new HabitListController();
        }

        INSTANCE.updateAllHabitsScore();
        return INSTANCE;
    }

    /**
     * Checks if HabitList is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty(){
        return habitList.size() == 0;
    }

    /**
     * Adds a habit to the habit list
     *
     * @param habit a habit to be added to the habit list
     */
    public void addHabit(Habit habit) {
        habitList.addHabit(habit);
    }

    /**
     *  Stores a Habit online and offline and then adds it to the habit list
     *
     * @param habit a habit to be added offline, online, and to the habit list
     */
    public void addAndStore(Habit habit){
        OnlineController.StoreHabits storeHabitsOnline =
                new OnlineController.StoreHabits();
        OfflineController.StoreHabitList storeHabitList =
                new OfflineController.StoreHabitList();

        storeHabitsOnline.execute(habit);
        storeHabitList.execute(habitList);
        addHabit(habit);
    }

    /**
     *
     * @param habit
     */
    public void deleteHabit(Habit habit){
        // Added by nmayne on 2017-11-07
        OnlineController.DeleteHabits deleteHabitsOnline =
                new OnlineController.DeleteHabits();
        deleteHabitsOnline.execute(habit);

        habitList.deleteHabit(habit);
    }

    /**
     *
     * @param index
     */
    public void deleteHabitAt(int index){
        // Added by nmayne on 2017-11-07
        OnlineController.DeleteHabits deleteHabitsOnline =
                new OnlineController.DeleteHabits();
        deleteHabitsOnline.execute(habitList.getHabit(index));

        habitList.deleteHabitAt(index);
    }

    /**
     * Get the size of the habit history.
     *
     * @return number of entries in HabitList
     */
    public int size() {
        return habitList.size();
    }

    /**
     *
     * @param index
     * @return
     */
    public Habit getHabit(int index){
        return habitList.getHabit(index);
    }

    /**
     *
     * @param index
     * @param habit
     */
    public void setHabit(int index, Habit habit){
        habitList.setHabit(index, habit);
    }

    /**
     *
     * @param habit
     * @return
     */
    public boolean hasHabit(Habit habit){
        return habitList.hasHabit(habit);
    }

    /**
     *
     * @return
     */
    public ArrayList<Habit> getHabitList(){
        return  habitList.getHabitList();
    }

    /**
     *
     * @return
     */
    public ArrayList<Habit> getHabitsForToday(){
        return habitList.getHabitsForToday();
    }

    public void updateHabitScore(Habit habit){
        int score = 0;
        float occurred = HabitHistoryController.getInstance().habitOccurrence(habit);
        float occurrence = habit.getTotalOccurrence();

        score = (int)((occurred / occurrence) * 100);

        if(occurrence == 0)
            score = 0;

        else if(score > 100)
            score = 100;

        habit.setScore(score);
    }

    public void updateAllHabitsScore(){
        for(int i = 0; i < habitList.size(); i++){
            updateHabitScore(habitList.getHabit(i));
        }
    }

    /**
     *  Used to load local data into habitList once it is first created.
     */
    private void init() {
        try {
            OfflineController.GetHabitList getHabitListOffline =
                    new OfflineController.GetHabitList();
            getHabitListOffline.execute();
            habitList = getHabitListOffline.get();
        } catch (InterruptedException e) {
            Log.i("Error", e.getMessage());

        } catch (ExecutionException e) {
            Log.i("Error", e.getMessage());
        }
    }
    
    /**
     *  Stores HabitList data locally.
     */
    public void store(){
        OfflineController.StoreHabitList storeHabitListOffline =
                new OfflineController.StoreHabitList();
        storeHabitListOffline.execute(habitList);
    }

    /**
     * Updates a Habit online
     * @param h a habit to update online
     */
    public void updateOnline(Habit h) {
        OnlineController.StoreHabits storeHabitsOnline =
                new OnlineController.StoreHabits();
        storeHabitsOnline.execute(h);
    }

    /**
     * Stores HabitHistory online, and offline.
     */
    public void storeAll() {
        OnlineController.StoreHabits storeHabits =
                new OnlineController.StoreHabits();

        OfflineController.StoreHabitList storeHabitList =
                new OfflineController.StoreHabitList();

        int size = habitList.size();
        Habit[] habits = new Habit[size];

        for (int i = 0; i < size; i++){
            habits[i] = habitList.getHabit(i);
        }

        try {
            storeHabits.execute(habits).get();
            storeHabitList.execute(habitList).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
