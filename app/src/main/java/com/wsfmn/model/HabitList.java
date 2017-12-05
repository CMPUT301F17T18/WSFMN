/*
 * Copyright © 2017 Team 18 (WSFMN), CMPUT301, University of Alberta – All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact nmayne@ualberta.ca.
 *
 *  Team 18 is: Musaed Alsobaie, Siddhant Khanna, Wei Li, Nicholas Mayne, Fredric Mendi.
 */

package com.wsfmn.model;

import com.wsfmn.controller.HabitHistoryController;

import java.util.ArrayList;
import java.util.List;


/**
 *  A class to store Habits.
 *
 */
public class HabitList {
    private ArrayList<Habit> habits;

    /**
     *  Creates a new habit list object.
     *
     */
    public HabitList() {
        this.habits = new ArrayList<Habit>();
    }

    /**
     *  Creates a new habit list using the array list habits
     *
     * @param habits an array list of type Habit
     */
    public HabitList(ArrayList<Habit> habits) {
        this.habits = habits;
    }

    /**
     *  Adds a new habit to the list of habits
     *
     * @param habit a habit object that we want to add to the list of habits.
     */
    public void addHabit(Habit habit){
        habit.setSearchTitle();
        habits.add(habit);
    }

    /**
     *  Deletes habit from the list of habits.
     *
     * @param habit a habit object that we want to remove from the list of habits.
     */
    public void deleteHabit(Habit habit){
        habits.remove(habit);
    }

    /**
     *  Deletes entry index from the habit list
     *
     * @param index an index in the habit list for an object that we want to delete.
     */
    public void deleteHabitAt(int index){
        habits.remove(index);
    }

    /**
     *  Computes the size of the habit list
     *
     * @return int the size of the habit list
     */
    public int size() {
        return habits.size();
    }

    /**
     *  Adds a list of habits to habit list.
     *
     * @param habitsToAdd a list containing habits that we want to add to the habit list.
     */
    public void addAllHabits(List<Habit> habitsToAdd) {
        for(Habit h: habitsToAdd) {
            h.setSearchTitle();
        }
        habits.addAll(habitsToAdd);
    }

    /**
     *  Retrieves a habit from the habit list.
     *
     * @param index an index for the habit that we want to retrieve form the habit list.
     * @return  Habit a habit from the list of habits.
     */
    public Habit getHabit(int index){
        return habits.get(index);
    }

    /**
     *  Changes the habit object in index to habit.
     *
     * @param index an index for an entry in the habit list
     * @param habit a habit object
     */
    public void setHabit(int index, Habit habit){
        habit.setSearchTitle();
        habits.set(index, habit);
    }

    /**
     *  Verifies whether a habit object is contained in the habit list.
     *
     * @param habit a habit objec to verify if it is contained in the habit list.
     * @return boolean true if the habit object is contained in the habit list,
     *  and false otherwise.
     */
    public boolean hasHabit(Habit habit){
        return habits.contains(habit);
    }


    /**
     *  Returns the array list used to store the habits.
     *
     * @return an array list of type Habit.
     */
    public ArrayList<Habit> getHabitList(){
        return  habits;
    }

    /**
     *  Creates a new array list containing only habits that have Date equal to today
     *
     * @return an array list of type Habit.
     */
    public ArrayList<Habit> getHabitsForToday(){
        ArrayList<Habit> habitsForToday = new ArrayList<Habit>();
        ArrayList<Habit> hl = new ArrayList<Habit>();
        boolean flag = true;

        Date today = new Date();
        int dayOfWeek = today.getDayOfWeek();

        for(int i = 0; i < habits.size(); i++){
            int com = habits.get(i).getDate().compareDate(today);

            if(com == -1 || com == 0){
                if(habits.get(i).getWeekDays().getDay(dayOfWeek-1))
                    hl.add(habits.get(i));
            }
        }

        HabitHistoryController c = HabitHistoryController.getInstance();
        for (int i = 0; i < hl.size(); i++) {
            flag = true;
            for (int j = 0; j < c.size(); j++) {
                if (c.get(j).getHabit().equal(hl.get(i))
                        && c.get(j).getDate().compareDate(new Date()) == 0)
                    flag = false;
            }

            if(flag)
                habitsForToday.add(hl.get(i));
        }

        return habitsForToday;
    }

}