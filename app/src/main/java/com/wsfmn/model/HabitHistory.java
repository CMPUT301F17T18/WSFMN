/*
 * Copyright © 2017 Team 18 (WSFMN), CMPUT301, University of Alberta – All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact nmayne@ualberta.ca.
 *
 *  Team 18 is: Musaed Alsobaie, Siddhant Khanna, Wei Li, Nicholas Mayne, Fredric Mendi.
 */

package com.wsfmn.model;

import java.util.ArrayList;
import java.util.List;


/**
 * A class to store the HabitEvents of all Habits and the number of
 * History refers to this list of all past HabitEvents.
 */
public class HabitHistory {
    private ArrayList<HabitEvent> habitHistory;

    /**
     * Construct a new Habit History to store Habit Events.
     */
    public HabitHistory(){
        this.habitHistory = new ArrayList<HabitEvent>();
    }

    /**
     * Checks if the HabitHistory is empty.
     *
     * @return Boolean true if HabitHistory is empty
     */
    public Boolean isEmpty() {
        return habitHistory.isEmpty();
    }

    /**
     * Appends a HabitEvent to the end of HabitHistory.
     *
     * @param habitEvent HabitEvent: HabitEvent to add to HabitHistory
     */
    public void add(HabitEvent habitEvent) {
        habitEvent.setSearchTitle();
        habitHistory.add(habitEvent);
    }

    /**
     * Get the HabitEvent at a given index in the HabitHistory.
     *
     * @param index int: the index of the HabitEvent to get
     * @return HabitEvent at the specified index
     * @throws IndexOutOfBoundsException
     */
    public HabitEvent get(int index) throws IndexOutOfBoundsException {
            return habitHistory.get(index);
    }

    /**
     * Removes and returns a HabitEvent at the specified index in HabitHistory,
     * and decrements the HabitHistory indices that follow it.
     *
     * @param index int: the index of the HabitEvent to remove
     * @return HabitEvent removed from the specified index
     * @throws IndexOutOfBoundsException
     */
    public HabitEvent remove(int index) throws IndexOutOfBoundsException{
        return habitHistory.remove(index);
    }

    /**
     * Check to see it a HabitEvent is in HabitHistory.
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
     * @param habitEvent HabitEvent: get the first index of this HabitEvent
     * @return int first index of the specified HabitEvent, otherwise -1
     */
    public int indexOf(HabitEvent habitEvent){
        for (int i = 0; i < habitHistory.size(); i++) {
            if (habitEvent.getId().equals(habitHistory.get(i).getId())){
                return i;
            }
        }
        return -1;
    }

    /**
     * Adds a full list of HabitEvents to the HabitHistory.
     *
     * @param habitEvents
     */
    public void addAllHabitEvents(List<HabitEvent> habitEvents) {
        for (HabitEvent he: habitEvents) {
            he.setSearchTitle();
        }
        this.habitHistory.addAll(habitEvents);
    }

    /**
     * Get the size of the habit history.
     *
     * @return int the number of entries in HabitHistory
     */
    public int size() {
        return habitHistory.size();
    }

    /**
     * For a given Habit, get its number of HabitEvents in habitHistory.
     *
     * @param h Habit: the Habit to get the number of occurrances of
     * @return int the number of occurances of the given Habit
     */
    public int habitOccurrence(Habit h) {
        int num = 0;
        for (HabitEvent he : habitHistory) {
            if (he.getHabit().equal(h)) {
                num++;
            }
        }
        return num;
    }

    /**
     *  Filters habit history list based on title
     *
     * @param title a habit used to filter habit history list
     */
    public void filterByTitle(String title){

        for(int i = 0; i < habitHistory.size(); i++) {
            if (!habitHistory.get(i).getHabitTitle().equals(title))
                habitHistory.remove(i--);
        }
    }

    /**
     *  Filters habit history list based on habit events containing comment
     *
     * @param comment a string that we use to filter habit history list
     */
    public void filterByComment(String comment) {

        for(int i = 0; i < habitHistory.size(); i++){
            if(!habitHistory.get(i).getComment().contains(comment))
                habitHistory.remove(i--);
        }
    }

    /**
     * Sorts a habit history list based on Date, most recent coming first.
     *
     */
    public void sortHabitHistory(){
        HabitEvent he = null;
        HabitEvent tempHe = null;

        for(int i = 0; i < habitHistory.size(); i++){
            for(int j = i; j < habitHistory.size(); j++){
                if(habitHistory.get(i).compareDate(habitHistory.get(j).getDate()) == -1) {
                    he = habitHistory.get(j);

                    tempHe = habitHistory.get(i);
                    habitHistory.set(i, he);
                    habitHistory.set(j, tempHe);
                }
            }

        }
    }

    /**
     * Get the Habit
     * @return
     */
    public ArrayList<HabitEvent> getHabitEventList(){
        return  habitHistory;
    }
}

