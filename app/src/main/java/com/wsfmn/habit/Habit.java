package com.wsfmn.habit;


import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by musaed on 2017-10-16.
 */

public class Habit{


    private String id;
    private String title;
    private String reason;
<<<<<<< HEAD
    private Date date;
    private WeekDays weekDays;
    private int[] stats;

    public Habit(){
        date = new Date(2017, 10, 26);
        weekDays = new WeekDays();
        title = "title";
        reason = "reason";
        id = null;
        stats = new int[2];
=======
    protected Date date = null;
    protected WeekDays weekDays;



    //  these attributes are used calculate possible times the habit could occur,
    //  taking into consideration that the user might change the Habit plan.


    protected int oldOcc = 0;
    protected int occ = 0;
    protected int tempOcc = 0;
    protected boolean hasChanged = false;
    protected Date currDate;  //  currDate is set to today when any change to the plan occurs

    //  default constructor: required for MockHabit
    public Habit(){};

    //  custom constructor
    //  used for testing totalOccurrence
    public Habit(Date date, WeekDays weekDays){
        this.currDate = date;
        this.date = date;
        this.weekDays = weekDays;
>>>>>>> a8dcb796c7cf68f71f53f3868ca229daed2fbd5c
    }


    public Habit(String title, Date date) throws HabitTitleTooLongException,
                                                DateNotValidException {
        this.id = null;
        this.setDate(date);
        this.setTitle(title);
        this.weekDays = new WeekDays();
        currDate = date;
    }

    public Habit(String title, String reason, Date date) throws HabitTitleTooLongException,
                                                            HabitReasonTooLongException,
                                                            DateNotValidException{
        this(title, date);
        this.setReason(reason);
    }

    public Habit(String title, String reason, Date date, WeekDays weekDays) throws HabitTitleTooLongException,
                                                            HabitReasonTooLongException,
                                                            DateNotValidException{
        this(title, reason, date);
        this.weekDays = weekDays;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws HabitTitleTooLongException{
        if(title.length() < 1 ||title.length() > 20){
            throw new HabitTitleTooLongException();
        }
        this.title = title;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) throws HabitReasonTooLongException{
        if(reason.length() > 30){
            throw new HabitReasonTooLongException();
        }
        this.reason = reason;
    }

    //  Date object is not accessible outside this class.
    public Date getDate() {
        return date;
    }

    public void setDate(Date toStart) throws DateNotValidException {
        if(this.date == null) {
            if (toStart.compareDate(new Date()) == -1)
                throw new DateNotValidException();
        }
        else{
            if(toStart.compareDate(this.date) == -1)
                throw new DateNotValidException();
        }

        hasChanged = true;
        currDate = toStart;
        this.date = toStart;
    }

    //  WeekDays objects are not accessible outside this class
    public WeekDays getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(WeekDays weekDays) {
        hasChanged = true;
        currDate = new Date();
        this.weekDays = weekDays;
    }

    public void setDay(int day){
        hasChanged = true;
        currDate = new Date();
        weekDays.setDay(day);
    }

    public void unsetDay(int day){
        hasChanged = true;
        currDate = new Date();
        weekDays.unsetDay(day);
    }



    @Override
    public String toString(){
        return title + "    " + date;
    }







    public int getTotalOccurrence(){

        //today
        Date today = new Date();
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

    public int totalOccurrence(Date startDate, Date endDate){
        int totalPossibleOccurrence = 0;
        int tm = endDate.getMonth();
        int dm = startDate.getMonth();


        for(int m = dm; m <= tm; m++){
            Date tempDate = new Date();
            int beg = 0, dayOfWeek = 0, end = 0;

            tempDate.setMonth(m);
            if(tm == dm){  //  current month of habit
                tempDate.setDay(startDate.getDay());
                beg = tempDate.getDay();
                end = endDate.getDay();
            }

            else if(m == dm){
                tempDate.setDay(startDate.getDay());
                beg = tempDate.getDay();
                end = tempDate.getDaysInMonth();
            }

            else if(m == tm){
                tempDate.setDay(1);
                beg = tempDate.getDay();
                end = endDate.getDay();
            }

            else{
                tempDate.setDay(startDate.getDay());
                beg = tempDate.getDay();
                end = tempDate.getDaysInMonth();
            }

            dayOfWeek = tempDate.getDayOfWeek();
            totalPossibleOccurrence += caldays(beg, dayOfWeek, end);
        }

        return totalPossibleOccurrence;
    }

    /**
     *  beg: beg day in month
     *  dayOfWeek: dayOfWeek beg corresponds to
     *  end: end day in month
     */
    public int caldays(int beg, int dayOfWeek, int end){
        int total = 0;

        for(int i = beg; i <= end; i++){

            if(this.getWeekDays().getDay(dayOfWeek-1))
                total++;

            if(++dayOfWeek > 7)
                dayOfWeek = 1;
        }

        return total;
    }
}
