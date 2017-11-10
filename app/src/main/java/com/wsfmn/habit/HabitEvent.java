package com.wsfmn.habit;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.wsfmn.habittracker.R;
import android.graphics.Bitmap;
import android.widget.EditText;
/**
 * Created by skhanna1 on 10/16/17.
 */

public class HabitEvent{
    private String title;
    private Habit habit;
    private String comment;
    private Bitmap image;
    String id;
    //private Date date;
    //change by wei, change location parts
    //private Geolocation location;

    //Need to Add Location
    public HabitEvent(Habit habit, String title, String comment, Bitmap image) throws HabitCommentTooLongException {
        this.habit = habit;
        this.title = title;
        this.comment =comment;
        this.image = image;
        this.id = null;
    }

//    public HabitEvent(Habit habit, EditText nameHabitEvent, EditText comment, Bitmap image){
//        this.habit = habit;
//        this.image = image;
//        this.comment = "No Comment";
//        this.title = "title";
//    }

    public Habit getHabitFromEvent(){
        return habit;
    }

    public String getHabitTitle(){
        return this.habit.getTitle();
    }

    public void setHabit(Habit habit){
        this.habit = habit;
    }
    public String getId() {return id;}

    public void setId(String id) {
        this.id = id;
    }

    public Habit getHabit() {
        return habit;
    }

    public String getTitle(){return title;}

    public String setTitle(String title) throws HabitTitleTooLongException{
        if (title.length() < 1 || title.length() > 20) {
            throw new HabitTitleTooLongException();
        }
        return this.title = title;
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

    public Bitmap getImage(){return image;}

//    public void setLocation(Geolocation location){
//        this.location = location;
//    }
//
//    public Geolocation getLocation() {return this.location;}

//    public void location(){this.location = 5;}

}
