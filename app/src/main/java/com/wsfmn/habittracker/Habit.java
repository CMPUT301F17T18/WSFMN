package com.wsfmn.habittracker;

import java.util.Date;

/**
 * Created by musaed on 2017-10-16.
 */

public class Habit{

    private String id;
    private String title;
    private String reason;
    private Date date;
    private WeekDays weekDays;


    public Habit(String title, Date date) throws HabitTitleTooLongException {
        this.id = null;
        this.date = date;
        this.setTitle(title);
    }

    public Habit(String title, String reason, Date date) throws HabitTitleTooLongException,
                                                            HabitReasonTooLongException{
        this(title, date);
        this.setReason(reason);
    }

    public Habit(String title, String reason, Date date, WeekDays weekDays) throws HabitTitleTooLongException,
                                                            HabitReasonTooLongException{
        this(title, reason, date);
        this.weekDays = weekDays;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws HabitTitleTooLongException{
        if(title.length() > 20){
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

    public void setDate(Date toStart) {
        this.date = toStart;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public WeekDays getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(WeekDays weekDays) {
        this.weekDays = weekDays;
    }

    // nmayne: A local key for a habit, as a combination of title and date... but this
    // is dependent upon Date, which is an issue... probably a better way to do this
    @Override
    public String toString(){
        return title + " " + date;
    }
}
