package com.wsfmn.habittracker;

import java.util.Date;

/**
 * Created by musaed on 2017-10-16.
 */

public class Habit{

    private String id;
    private String title;
    private String reason;
    private Date toStart;
    private WeekDays weekDays;
    private HabitHistory habitHistory;


    public Habit(String title, Date date) throws HabitTitleTooLongException {
        this.id = null;
        this.toStart = date;
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

    public Date getToStart() {
        return toStart;
    }

    public void setToStart(Date toStart) {
        this.toStart = toStart;
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

    public HabitHistory getHabitHistory() {
        return habitHistory;
    }

    public void setHabitHistory(HabitHistory habitHistory) {
        this.habitHistory = habitHistory;
    }
}
