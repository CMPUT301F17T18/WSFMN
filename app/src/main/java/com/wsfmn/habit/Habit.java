package com.wsfmn.habit;


import android.util.Log;

import java.util.Calendar;

/**
 * Created by musaed on 2017-10-16.
 */

public class Habit{

    private String id;
    private String title;
    private String reason;
    private Date date;
    private WeekDays weekDays;

    public Habit(){
        date = new Date(2017, 10, 26);
        weekDays = new WeekDays();
        title = "title";
        reason = "reason";
        id = null;
    }


    public Habit(String title, Date date) throws HabitTitleTooLongException,
                                                DateNotValidException {
        this.id = null;
        this.setDate(date);
        this.setTitle(title);
        this.weekDays = new WeekDays();
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date toStart) throws DateNotValidException {
        if(toStart.compareDate(new Date()) == -1)
            throw new DateNotValidException();
        this.date = toStart;
    }

    public WeekDays getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(WeekDays weekDays) {
        this.weekDays = weekDays;
    }

    public int getHabitOccurrencePercentage(){
        int totalOccurred = 0;
        return (totalOccurred / totalOccurrence()) * 100;
    }

    public int totalOccurrence(){
        int totalPossibleOccurrence = 0;
        Date today = new Date();
        int tm = today.getMonth();
        int dm = this.date.getMonth();


        for(int m = dm; m <= tm; m++){
            Date tempDate = new Date();
            int beg = 0, dayOfWeek = 0, end = 0;

            tempDate.setMonth(m);
            if(tm == dm){  //  current month of habit
                tempDate.setDay(this.date.getDay());
                beg = tempDate.getDay();
                end = today.getDay();
            }

            else if(m == dm){
                tempDate.setDay(this.date.getDay());
                beg = tempDate.getDay();
                end = tempDate.getDaysInMonth();
            }

            else if(m == tm){
                tempDate.setDay(1);
                beg = tempDate.getDay();
                end = today.getDay();
            }

            else{
                tempDate.setDay(this.getDate().getDay());
                beg = tempDate.getDay();
                end = tempDate.getDaysInMonth();
            }
            dayOfWeek = tempDate.getDayOfWeek();


            totalPossibleOccurrence += caldays(beg,
                    dayOfWeek,
                    end);
        }

        return totalPossibleOccurrence;

    }

    /**
     *  beg: day in month
     *  dayOfWeek: dayOfWeek beg corresponds to
     *  end: end of day in month
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

    // nmayne: A local key for a habit, as a combination of title and date... but this
    // is dependent upon Date, which is an issue... probably a better way to do this
    @Override
    public String toString(){
        return title + "    " + date;
    }
}
