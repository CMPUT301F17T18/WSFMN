package com.wsfmn.habit;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.wsfmn.habittracker.R;
import android.graphics.Bitmap;
import android.widget.EditText;

import org.apache.commons.lang3.ObjectUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by skhanna1 on 10/16/17.
 */

public class HabitEvent{
    /**
     * Variables For the Habit Event that the user will enter or be created
     * when a user creates a new Habit Event
     */
    private String title;
    private Habit habit;
    private String comment;
    String id;
    String date;
    //Path of the file Where image is stored
    String CurrentPhotoPath;

    //private Bitmap image;
    //private Date date;
    //change by wei, change location parts
    private Geolocation geolocation;

    //Need to Add Location

    /**
     * Constructor for the Habit Event
     * @param habit
     * @param title
     * @param comment
     * @param CurrentPhotoPath
     * @param date
     * @throws HabitCommentTooLongException
     */
    public HabitEvent(Habit habit, String title, String comment, String CurrentPhotoPath, String date) throws HabitCommentTooLongException {
        this.habit = habit;
        this.title = title;
        this.comment =comment;
        this.CurrentPhotoPath = CurrentPhotoPath;
        this.id = null;
        this.date = date;
    }

//    public HabitEvent(Habit habit, EditText nameHabitEvent, EditText comment, Bitmap image){
//        this.habit = habit;
//        this.image = image;
//        this.comment = "No Comment";
//        this.title = "title";
//    }

    /**
     * Get the date of when the HabitEvent was created
     * @return Date: Date of the HabitEvent
     */
    public String getDate(){
        return this.date;
    }

    /**
     * Get the path of the file where image is stored for the habit Event
     * @return mCurrentPhotoPath: filename of the image
     */
    public String getCurrentPhotoPath(){
        return CurrentPhotoPath;
    }

    /**
     * Get the Habit the user selects for the HabitEvent
     * @return Habit
     */
    public Habit getHabitFromEvent(){
        return habit;
    }

    /**
     * Get the Habit title for the Habit Event
     * @return habit Title
     */
    public String getHabitTitle(){
        return this.habit.getTitle();
    }

    /**
     * /*Changes the Habit for the HabitEvent
     * @param habit
     */
    public void setHabit(Habit habit){
        this.habit = habit;
    }

    /**
     * Get Id for ElasticSearch
     * @return Id
     */
    public String getId() {return id;}

    /**
     * Set Id for ElasticSearch
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /*Get the Habit for the HabitEvent*/
    public Habit getHabit() {
        return habit;
    }

    /*Get the Title of the Habit Event*/
    public String getHabitEventTitle() throws HabitEventNameException{
        /*Checks the title length of the HabitEvent*/
        if(title.length() > 35 || title.length()<1){
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

    /*Changing the Comment of Habit Event*/
    public void setComment(String comment) throws HabitEventCommentTooLongException {
        /*Checking if comment size does not exceed 20 characters*/
        if(comment.length() > 20){
            /* Throw HabitEventCommentTooLongException*/
            throw new HabitEventCommentTooLongException();
        }
        this.comment = comment;
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
