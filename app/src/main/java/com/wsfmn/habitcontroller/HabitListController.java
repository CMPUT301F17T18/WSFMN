package com.wsfmn.habitcontroller;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.wsfmn.habit.Habit;
import com.wsfmn.habit.HabitList;

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
        return INSTANCE;
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


    public void deleteHabit(Habit habit){
        // Added by nmayne on 2017-11-07
        OnlineController.DeleteHabits deleteHabitsOnline =
                new OnlineController.DeleteHabits();
        deleteHabitsOnline.execute(habit);

        habitList.deleteHabit(habit);
    }

    public void deleteHabitAt(int index){
        // Added by nmayne on 2017-11-07
        OnlineController.DeleteHabits deleteHabitsOnline =
                new OnlineController.DeleteHabits();
        deleteHabitsOnline.execute(habitList.getHabit(index));

        habitList.deleteHabitAt(index);
    }

    public int size() {
        return habitList.size();
    }


    public Habit getHabit(int index){
        return habitList.getHabit(index);
    }

    public void setHabit(int index, Habit habit){
        habitList.setHabit(index, habit);
    }


    public boolean hasHabit(Habit habit){
        return habitList.hasHabit(habit);
    }

    public ArrayList<Habit> getHabitList(){
        return  habitList.getHabitList();
    }

    public ArrayList<Habit> getHabitsForToday(){
        return habitList.getHabitsForToday();
    }

    /**
     *  Used to load local data into habitList once it is first created.
     */
    public static void init() {
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


}
