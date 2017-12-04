/*
 * Copyright © 2017 Team 18 (WSFMN), CMPUT301, University of Alberta – All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact nmayne@ualberta.ca.
 *
 *  Team 18 is: Musaed Alsobaie, Siddhant Khanna, Wei Li, Nicholas Mayne, Fredric Mendi.
 */

package com.wsfmn.model;

import java.io.Serializable;

/**
 * Created by musaed on 2017-10-22.
 *
 * This class is a list data structure of type boolean and size 7 used to store days of the week
 * that the habit will occurr in. Each entry represents a day, and we use the defines constants
 * as indexes to access the list's entries. An entry containing true means that the habit will
 * occur in this day, and false if not.
 *
 */

public class WeekDays implements Serializable {

    public static final int MONDAY = 0;
    public static final int TUESDAY = 1;
    public static final int WEDNESDAY = 2;
    public static final int THURSDAY = 3;
    public static final int FRIDAY = 4;
    public static final int SATURDAY = 5;
    public static final int SUNDAY = 6;

    private boolean[] weekDays;

    /**
     *  Creates a WeekDays object and initializes all entries to false.
     */
    public WeekDays(){
        weekDays = new boolean[7];
        for(boolean day: weekDays){
            day = false;
        }
    }

    /**
     * Gets the value for an entry according to the index day, either true or false
     *
     * @param day an index that identifies a day in a week
     * @return the value of the entry accessed by day, either true or false
     */
    public boolean getDay(int day){
        return weekDays[day];
    }

    /**
     * Sets the value for an entry according to the index day to true
     *
     * @param day an index that identifies a day in a week
     */
    public void setDay(int day){
        weekDays[day] = true;
    }

    /**
     * Sets the value for an entry according to the index day to false
     *
     * @param day an index that identifies a day in a week
     */
    public void unsetDay(int day){
        weekDays[day] = false;
    }

    /**
     * Creates a different object with values copied from calling object.
     *
     * @return A different object with a copy of the values of the calling object.
     */
    public WeekDays copy(){
        WeekDays copy = new WeekDays();
        for(int i = 0; i < 7; i++){
            if(this.getDay(i))
                copy.setDay(i);
        }
        return copy;
    }

}
