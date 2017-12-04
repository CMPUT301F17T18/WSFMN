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
 *
 */
public class HabitList {
    private ArrayList<Habit> habits;

    /**
     *
     */
    public HabitList() {
        this.habits = new ArrayList<Habit>();
    }

    /**
     *
     *
     * @param habits
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
     *
     * @param index
     * @return
     */
    public Habit getHabit(int index){
        return habits.get(index);
    }

    /**
     *
     * @param index
     * @param habit
     */
    public void setHabit(int index, Habit habit){
        habit.setSearchTitle();
        habits.set(index, habit);
    }

    /**
     *
     * @param habit
     * @return
     */
    public boolean hasHabit(Habit habit){
        return habits.contains(habit);
    }

    // TODO alsobaie: added this, needs testing

    /**
     *
     * @return
     */
    public ArrayList<Habit> getHabitList(){
        return  habits;
    }

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