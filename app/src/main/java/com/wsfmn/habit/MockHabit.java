package com.wsfmn.habit;

/**
 * Created by musaed on 2017-11-11.
 */

import android.util.Log;

/**
 *  A class intended to imitate Habit but customizes some
 *  attributes for testing purposes.
 */
/*
public class MockHabit extends Habit {

    //  we pick what today is so that we test getTotalOccurrence
    private Date today = new Date(2017, 11, 11);

    public MockHabit(Date date, WeekDays weekDays){
        this.date = date;
        this.weekDays = weekDays;
    }

    public Date getDate(){
        return date;
    }

    public void setDate(Date toStart) throws DateNotValidException {
        if(toStart.compareDate(new Date()) == -1)
            throw new DateNotValidException();

        hasChanged = true;
        currDate = toStart;
        this.date = toStart;
    }

    public void setWeekDays(WeekDays weekDays) {
        hasChanged = true;
        currDate = today;
        this.weekDays = weekDays;
    }

    public void setDay(int day){
        hasChanged = true;
        currDate = today;
        weekDays.setDay(day);
    }

    public void unsetDay(int day){
        hasChanged = true;
        currDate = today;
        weekDays.unsetDay(day);
    }

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
        Log.i("musaed", "mockhabit.java got called!!");
        return occ;
    }

}
*/