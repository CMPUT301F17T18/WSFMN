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
    private HabitHistory habitHistory;

    public Habit(){

    }

    public Habit(String title, Date date){
        this.id = null;
        this.title = title;
        this.date = date;
    }

    public Habit(String title, String reason, Date date) {
        this.id = null;
        this.title = title;
        this.reason = reason;
        this.date = date;
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

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
