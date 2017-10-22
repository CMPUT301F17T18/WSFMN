package com.wsfmn.habittracker;

import android.media.Image;
import android.widget.ImageView;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Date;

/**
 * Created by skhanna1 on 10/16/17.
 */

public class HabitEvent {
    String comment;
    Habit habit;
    Date date;

    Integer location;
    //Will change to appropriate Data Type when implement it(ImageView).
    String pic;

    public HabitEvent(Habit habit, Date date, String comment) {
        this.habit = habit;
        this.date = date;
        this.comment = comment;
    }

    public void location(){this.location = 5;}
    public void AddPic(){this.pic = "Image";}
    public void updateHabitHistory(){}

    public String getComment(){return comment;}

    public void setComment(String comment){this.comment = comment;}

    public Date getDate(){return date;}

    public void setDate(Date date){this.date = date;}


    public Habit getHabit(){return habit;}

    public Integer getLocation(){return this.location;}

    public String getPic(){return this.pic;}


}
