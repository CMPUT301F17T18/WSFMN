/*
 * Copyright © 2017 Team 18 (WSFMN), CMPUT301, University of Alberta – All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact nmayne@ualberta.ca.
 *
 *  Team 18 is: Musaed Alsobaie, Siddhant Khanna, Wei Li, Nicholas Mayne, Fredric Mendi.
 */

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
     *      HabitListController c = HabitListController.getInstance();
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
     *      c.addAndStore();
     *
     *  we call c.addAndStore() to add and store the new habit locally and online.
     */

    private static HabitListController INSTANCE = null;
    private static HabitList habitList = null;

    /**
     * Instantiate the habitList attribute.
     * This pulls the data from the locally saved HabitList via OfflineController using init().
     */
    private HabitListController(){
        habitList = new HabitList();
        init();
    }

    /**
     * Access the instance of HabitListController singleton.
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
     * Check if HabitList is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty(){
        return habitList.size() == 0;
    }

    /**
     * Add a habit to the habit list.
     *
     * @param habit a habit to be added to the habit list
     */
    public void addHabit(Habit habit) {
        habitList.addHabit(habit);
    }

    /**
     * Store a Habit online and offline and then add it to the habit list.
     *
     * @param habit a habit to be added offline, online, and to the habit list
     */
    public void addAndStore(Habit habit){
        addHabit(habit);

        OnlineController.StoreHabits storeHabitsOnline =
                new OnlineController.StoreHabits();
        OfflineController.StoreHabitList storeHabitList =
                new OfflineController.StoreHabitList();

        storeHabitsOnline.execute(habit);
        storeHabitList.execute(habitList);
    }

    /**
     *  Delete a habit both online and offline, if not connected online then the habit deletion
     *  is queued for the next time a user goes online.
     *
     * @param habit to be deleted
     */
    public void deleteHabit(Habit habit){
        // Added by nmayne on 2017-11-07
        if (OnlineController.isConnected()) {
            OnlineController.DeleteHabits deleteHabitsOnline =
                    new OnlineController.DeleteHabits();
            deleteHabitsOnline.execute(habit.getId());
        } else {
            // Store this Habit ID for online deletion upon next connection
            OfflineController.addToOfflineDelete("HAB", habit.getId());
        }

        habitList.deleteHabit(habit);
    }

    /**
     * Delete a habit at a particular index both online and offline,
     * if not connected online then the habit deletion.
     *
     * @param index of Habit to be deleted
     */
    public void deleteHabitAt(int index){
        // Added by nmayne on 2017-11-07
        if (OnlineController.isConnected()) {
            OnlineController.DeleteHabits deleteHabitsOnline =
                    new OnlineController.DeleteHabits();
            deleteHabitsOnline.execute(habitList.getHabit(index).getId());
        } else {
            // Store this Habit ID for online deletion upon next connection
            OfflineController.addToOfflineDelete("HAB", habitList.getHabit(index).getId());
        }

        habitList.deleteHabitAt(index);
    }

    /**
     * Get the size of the HabitList.
     *
     * @return number of entries in HabitList, i.e. its size
     */
    public int size() {
        return habitList.size();
    }

    /**
     * Get a habit at a particular index.
     *
     * @param index of the Habit to get
     * @return the Habit at that index
     */
    public Habit getHabit(int index){
        return habitList.getHabit(index);
    }

    /**
     * Get habit by its ID.
     *
     * @param id of the Habit to get
     * @return the Habit with the given ID, otherwise null
     */
    public Habit getHabit(String id)  {
        for (int i = 0; i<habitList.size(); i++){
            if (getHabit(i).getId().equals(id)){
                return getHabit(i);
            }
        }
        return null;
    }

    /**
     * Set the Habit at a particular index in the HabitList.
     *
     * @param index at which to set the Habit
     * @param habit the Habit to set at that index
     */
    public void setHabit(int index, Habit habit){
        habitList.setHabit(index, habit);
    }

    /**
     * Check if a Habit is in the HabitList.
     *
     * @param habit to check for in HabitList
     * @return true if the Habit is in the list, otherwise false
     */
    public boolean hasHabit(Habit habit){
        return habitList.hasHabit(habit);
    }

    /**
     * Get the underlying ArrayList that stores the Habits.
     *
     * @return the Habits in HabitList
     */
    public ArrayList<Habit> getHabitList(){
        return  habitList.getHabitList();
    }

    /**
     * Get the underlying ArrayList of the Habits scheduled for today.
     *
     * @return the Habits from the HabitList that need to be done today.
     */
    public ArrayList<Habit> getHabitsForToday(){
        return habitList.getHabitsForToday();
    }

    public void updateHabitScore(Habit habit){
        int score = 0;
        float occurred = HabitHistoryController.getInstance().habitOccurrence(habit);
        float occurrence = habit.getTotalOccurrence();



        if(occurrence == 0)
            score = 0;

        else {
            score = (int)((occurred / occurrence) * 100);
            if (score > 100) score = 100;
        }

        habit.setScore(score);
    }

    /**
     * Update the scores for all Habits.
     */
    public void updateAllHabitsScore(){
        for(int i = 0; i < habitList.size(); i++){
            updateHabitScore(habitList.getHabit(i));
        }
    }

    /**
     *  Load locally stored data into habitList when it is first created.
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
     *  Store HabitList data locally.
     */
    public void store(){
        OfflineController.StoreHabitList storeHabitListOffline =
                new OfflineController.StoreHabitList();
        for (Habit h : habitList.getHabitList()) {
            h.setSearchTitle();
        }
        storeHabitListOffline.execute(habitList);
    }

    /**
     * Updates a Habit online.
     *
     * @param h a habit to update online
     */
    public void updateOnline(Habit h) {
        h.setSearchTitle();
        OnlineController.StoreHabits storeHabitsOnline =
                new OnlineController.StoreHabits();
        storeHabitsOnline.execute(h);
    }

    /**
     * Stores HabitList online, and offline.
     *
     */
    public void storeAll() {
        OnlineController.StoreHabits storeHabits =
                new OnlineController.StoreHabits();

        OfflineController.StoreHabitList storeHabitList =
                new OfflineController.StoreHabitList();

        int size = habitList.size();
        Habit[] habits = new Habit[size];

        if (size > 0) {
            for (int i = 0; i < size; i++) {
                Habit h = habitList.getHabit(i);
                h.setSearchTitle();
                habits[i] = h;
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
}