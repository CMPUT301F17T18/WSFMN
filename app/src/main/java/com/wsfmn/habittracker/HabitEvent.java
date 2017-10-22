package com.wsfmn.habittracker;

import java.util.Date;

/**
 * Created by skhanna1 on 10/16/17.
 */

public class HabitEvent extends Habit{
    private String comment;
    private Habit habit;
    private Date date;

    public HabitEvent(Habit habit, Date date, String comment) {
        this.date = date;
        this.comment = comment;
    }

    public Habit getHabit() {
        return habit;
    }

    public void setHabit(Habit habit) {
        this.habit = habit;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    public void location(){}
    public void AddPic(){}


}
