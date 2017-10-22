package com.wsfmn.habittracker;

import android.media.Image;
import android.widget.ImageView;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Date;

/**
 * Created by skhanna1 on 10/16/17.
 */

public class HabitEvent{
    private String comment;
    private Habit habit;
    private Date date;
    private Boolean done;

    Integer location;
    //Will change to appropriate Data Type when implement it(ImageView).
    String pic;
  
<<<<<<< HEAD
    public HabitEvent(Habit habit, Date date, Boolean done, String comment) throws HabitCommentTooLongException{
        this.habit = habit;
=======
    public HabitEvent(Habit habit, Date date, String comment, Boolean done) {
>>>>>>> d7b6932dd7c07addb7120dfa9edfff350bfbb494
        this.date = date;
        this.done = done;

        if(comment.length() > 20){
            throw new HabitCommentTooLongException();
        }
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

<<<<<<< HEAD
=======
    public void location(){this.location = 5;}
    public void AddPic(){this.pic = "Image";}
    public void updateHabitHistory(){}


>>>>>>> d7b6932dd7c07addb7120dfa9edfff350bfbb494

    public Date getDate(){return date;}

    public void setDate(Date date){this.date = date;}

<<<<<<< HEAD
=======


    public Integer getLocation(){return this.location;}

    public String getPic(){return this.pic;}

>>>>>>> d7b6932dd7c07addb7120dfa9edfff350bfbb494
    public void setDone(Boolean done) {
        this.done = done;
    }

    public Boolean getDone() {
        return done;
    }

    public Integer getLocation(){return this.location;}

    public String getPic(){return this.pic;}

    public void location(){this.location = 5;}

    public void AddPic(){this.pic = "Image";}

    public void updateHabitHistory(){}



}
