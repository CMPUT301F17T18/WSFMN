package com.wsfmn.habit;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.wsfmn.habittracker.R;
import android.graphics.Bitmap;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by skhanna1 on 10/16/17.
 */

public class HabitEvent{
    private String title;
    private Habit habit;
    private String comment;
    private Bitmap image;
    String id;
    String date;
    String mCurrentPhotoPath;
    //private Date date;
    //change by wei, change location parts
    private Geolocation geolocation;

    //Need to Add Location
    public HabitEvent(Habit habit, String title, String comment, String mCurrentPhotoPath, String date) throws HabitCommentTooLongException {
        this.habit = habit;
        this.title = title;
        this.comment =comment;
        this.mCurrentPhotoPath = mCurrentPhotoPath;
        this.id = null;
        this.date = date;
    }

//    public HabitEvent(Habit habit, EditText nameHabitEvent, EditText comment, Bitmap image){
//        this.habit = habit;
//        this.image = image;
//        this.comment = "No Comment";
//        this.title = "title";
//    }

    public String getDate(){
        return this.date;
    }

    public String getmCurrentPhotoPath(){
        return mCurrentPhotoPath;
    }

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

    public String getHabitEventTitle(){return title;}

    public String setTitle(String title){
        return this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) throws HabitEventCommentTooLongException {
        if(comment.length() >= 20){
            throw new HabitEventCommentTooLongException();
        }
        this.comment = comment;
    }

    public Bitmap getImage(){return image;}

    public void setImage(Bitmap bit) {
        this.image = bit;
    }

    //Geolocation
    public void setGeolocation(Geolocation geolocation){
        this.geolocation = geolocation;}
    public Geolocation getGeolocation(){return geolocation;}
//

    @Override
    public String toString(){
        return title + "    " + date;
    }

}
