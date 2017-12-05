/*
 * Copyright © 2017 Team 18 (WSFMN), CMPUT301, University of Alberta – All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact nmayne@ualberta.ca.
 *
 *  Team 18 is: Musaed Alsobaie, Siddhant Khanna, Wei Li, Nicholas Mayne, Fredric Mendi.
 */

package com.wsfmn.model;


/**
 * Created by musaed on 2017-11-12.
 */

import com.wsfmn.exceptions.DateNotValidException;

/**
 *  This class imitates Habit, but with customized attributes to test
 *  getTotalOccurrences. The particular purpose of this class is to see
 *  whether getTotalOccurrences counts total possible occurrences for a habit,
 *  even if the plan changes.
 *
 *  getTotalOccurrences is defined in habit but is only tested using this class.
 */
public class MockHabit extends Habit {


    private Date today = new Date();

    /**
     *  Creates a new MockHabit object.
     *
     */
    public MockHabit(){};

    /**
     *  Creates a new MockHabit object.
     *
     * @param date the for the MockHabit object.
     * @param weekDays the Days of the week this object is set to occur in.
     */
    public MockHabit(Date date, WeekDays weekDays){
        this.date = date;
        this.currDate = date;
        this.weekDays = weekDays;
    }

    /**
     *  Sets the start date for MockHabit if it is not smaller than today.
     *
     * @param toStart the date the habit will starts
     * @throws DateNotValidException
     */
    public void setDate(Date toStart) throws DateNotValidException {

        if(this.date == null) {
            if (toStart.compareDate(today) == -1)
                throw new DateNotValidException();
        }
        else{
            if(toStart.compareDate(this.date) != 0 && toStart.compareDate(today) == -1)
                throw new DateNotValidException();

        }

        hasChanged = true;
        currDate = toStart;
        this.date = toStart;
    }

    /**
     *  Gets the today Date object.
     *
     * @return
     */
    public Date getToday() {
        return today;
    }

    /**
     *  Sets the today Date object.
     *
     * @param today a Date object
     */
    public void setToday(Date today) {
        this.today = today;
    }

    /**
     *  Sets weekDays for MockHabit
     *
     * @param weekDays the days of the week the habit is done
     */
    public void setWeekDays(WeekDays weekDays) {
        hasChanged = true;
        currDate = today;
        //today = todayAfterChange;
        this.weekDays = weekDays;
    }

    /**
     *  Sets the weekDays with entry day for a MockHabit object.
     *
     * @param day the day that the habit will be done in.
     */
    public void setDay(int day){
        hasChanged = true;
        currDate = today;
        //today = todayAfterChange;
        weekDays.setDay(day);
    }

    /**
     *  Unsets the weekDays with entry day for a MockHabit object.
     *
     * @param day the day that the habit will not be done in.
     */
    public void unsetDay(int day){
        hasChanged = true;
        currDate = today;
        //today = todayAfterChange;
        weekDays.unsetDay(day);
    }

    /**
     *  Retrieves the total possible numbers that the habit could occur.
     *
     * @return int the total possible times the habit could occur.
     */
    public int getTotalOccurrence(){

        if(!hasChanged){
            tempOcc = totalOccurrence(currDate, today);
            occ = tempOcc + oldOcc;
        }

        else{
            oldOcc = occ;
            tempOcc = totalOccurrence(currDate, today);
            occ = oldOcc + tempOcc;
            hasChanged = false;
        }

        return occ;
    }

}
