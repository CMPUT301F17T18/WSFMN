package com.wsfmn.habittracker;

import java.util.Date;

/**
 * Created by skhanna1 on 10/16/17.
 */

public class HabitEvent {
    String comment;
    Habit habit;
    Date date;

    public HabitEvent(Habit habit, Date date, String comment) {
        this.habit = habit;
        this.date = date;
        this.comment = comment;
    }

    public void location(){}
    public void AddPic(){}


}
