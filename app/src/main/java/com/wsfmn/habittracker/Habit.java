package com.wsfmn.habittracker;

import java.util.Date;

/**
 * Created by musaed on 2017-10-16.
 */

public class Habit{

    private String title;
    private String reason;
    private Date date;

    public Habit(String title, Date date){
        this.title = title;
        this.date = date;
    }

    public Habit(String title, String reason, Date date) {
        this.title = title;
        this.reason = reason;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
