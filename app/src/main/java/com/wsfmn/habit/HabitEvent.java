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
    /** Variables For the Habit Event that the user will enter or be created
     * when a user creates a new Habit Event**/
    private String title;
    private Habit habit;
    private String comment;
//    private Bitmap image;
    String id;
    String date;
<<<<<<< HEAD
=======
    //Path of the file Where image is stored
>>>>>>> a8dcb796c7cf68f71f53f3868ca229daed2fbd5c
    String mCurrentPhotoPath;
    //private Date date;
    //change by wei, change location parts
    private Geolocation geolocation;

    //Need to Add Location
<<<<<<< HEAD
=======
    /*Constructor for the Habit Event*/
>>>>>>> a8dcb796c7cf68f71f53f3868ca229daed2fbd5c
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

<<<<<<< HEAD
=======
    /*Get the date of when the HabitEvent was created
    *
    * @return Date: Date of the HabitEvent*/
>>>>>>> a8dcb796c7cf68f71f53f3868ca229daed2fbd5c
    public String getDate(){
        return this.date;
    }

<<<<<<< HEAD
    public String getmCurrentPhotoPath(){
        return mCurrentPhotoPath;
    }

=======
    /*Get the path of the file where image is stored for the habit Event*/
    public String getCurrentPhotoPath(){
        return mCurrentPhotoPath;
    }

    /*Get the Habit the user selects for the HabitEvent*/
>>>>>>> a8dcb796c7cf68f71f53f3868ca229daed2fbd5c
    public Habit getHabitFromEvent(){
        return habit;
    }

    /*Get the Habit title for the Habit Event*/
    public String getHabitTitle(){
        return this.habit.getTitle();
    }

    /*Changes the Habit for the HabitEvent*/
    public void setHabit(Habit habit){
        this.habit = habit;
    }

    /*Get Id for ElasticSearch*/
    public String getId() {return id;}

    /*Set Id for ElasticSearch*/
    public void setId(String id) {
        this.id = id;
    }

    /*Get the Habit for the HabitEvent*/
    public Habit getHabit() {
        return habit;
    }

<<<<<<< HEAD
    public String getHabitEventTitle(){return title;}

    public String setTitle(String title){
=======
    /*Get the Title of the Habit Event*/
    public String getHabitEventTitle() throws HabitEventNameException{
        /*Checks the title length of the HabitEvent*/
        if(title.length() > 20 || title.length()<1){
            /*Throws Exception if violates the condition*/
            throw new HabitEventNameException();
        }
        return title;
    }

    /*Change Title of the HabitEvent*/
    public String setTitle(String title)throws HabitEventNameException{
        /*Checks the length of the title*/
        if(title.length() > 20 || title.length()<1){
            /*Throws this exception*/
            throw new HabitEventNameException();
        }
>>>>>>> a8dcb796c7cf68f71f53f3868ca229daed2fbd5c
        return this.title = title;
    }

    /*Get the comment for HabitEvent that user created*/
    public String getComment() throws HabitEventCommentTooLongException {
        /*If comment larger than 20 characters return and Error*/
        if(comment.length() > 20){
            /*Throw HabitEventCommentTooLongException*/
            throw new HabitEventCommentTooLongException();
        }
        return comment;
    }

<<<<<<< HEAD
    public void setComment(String comment) throws HabitEventCommentTooLongException {
        if(comment.length() >= 20){
=======
    /*Changing the Comment of Habit Event*/
    public void setComment(String comment) throws HabitEventCommentTooLongException {
        /*Checking if comment size does not exceed 20 characters*/
        if(comment.length() > 20){
            /* Throw HabitEventCommentTooLongException*/
>>>>>>> a8dcb796c7cf68f71f53f3868ca229daed2fbd5c
            throw new HabitEventCommentTooLongException();
        }
        this.comment = comment;
    }

//    public Bitmap getImage(){return image;}

//    public void setImage(Bitmap bit) {
//        this.image = bit;
//    }

<<<<<<< HEAD
    public void setImage(Bitmap bit) {
        this.image = bit;
    }

    //Geolocation
    public void setGeolocation(Geolocation geolocation){
        this.geolocation = geolocation;}
    public Geolocation getGeolocation(){return geolocation;}
=======
//    public void setLocation(Geolocation location){
//        this.location = location;
//    }
>>>>>>> a8dcb796c7cf68f71f53f3868ca229daed2fbd5c
//

    @Override
    public String toString(){
        return title + "    " + date;
    }

    /*Displays This when the List is called*/
    @Override
    public String toString(){
        return title + "    " + date;
    }

}
