package com.wsfmn.habit;


/**
 * Created by musaed on 2017-11-12.
 */

import android.util.Log;

/**
 *  This class imitates Habit, but with customized attributes to test
 *  getTotalOccurrences. The particular purpose of this class is to see
 *  whether getTotalOccurrences counts total possible occurrences for a habit,
 *  even if the plan changes.
 *
 *  getTotalOccurrences is defined in habit but is only tested using this class.
 */
public class MockHabit extends Habit {

    //  as long as no changes happen to Habit plan, this would be considered today
    //private Date todayBeforeChange = new Date(2017, 11, 2);

    //  once a change happens to Habit plan, we change today to a later date to test whether
    //  getTotalOccurrence takes into consideration the new changes in  the plan when it counts
    //  total possible occurrences for a habit.
    //private Date todayAfterChange = new Date(2017, 11, 12);

    //  this defines what today is when the getTotalOccurrence, or other methods are used to
    //  calculate total possible occurrences for a habit.
    //private Date today = todayBeforeChange;
    private Date today = new Date();

    public MockHabit(){};

    public MockHabit(Date date, WeekDays weekDays){
        this.date = date;
        this.currDate = date;
        this.weekDays = weekDays;
    }

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

    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }

    public void setWeekDays(WeekDays weekDays) {
        hasChanged = true;
        currDate = today;
        //today = todayAfterChange;
        this.weekDays = weekDays;
    }

    public void setDay(int day){
        hasChanged = true;
        currDate = today;
        //today = todayAfterChange;
        weekDays.setDay(day);
    }

    public void unsetDay(int day){
        hasChanged = true;
        currDate = today;
        //today = todayAfterChange;
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

        return occ;
    }

}
