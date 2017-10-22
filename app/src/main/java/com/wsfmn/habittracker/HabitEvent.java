package com.wsfmn.habittracker;

import java.util.Date;

/**
 * Created by skhanna1 on 10/16/17.
 */

public class HabitEvent{
    private String comment;
    private Habit habit;
    private Date date;
    private Boolean done;

    public HabitEvent(Habit habit, Date date, Boolean done, String comment) {
        this.date = date;
        this.done = done;
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

    public void setComment(String comment) throws HabitCommentTooLongException {
        if(comment.length() > 20){
            throw new HabitCommentTooLongException();
        }
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Boolean getDone() {
        return done;
    }

    public void location(){}
    public void AddPic(){}


}
