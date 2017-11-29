package com.wsfmn.model;

import com.wsfmn.exceptions.HabitCommentTooLongException;
import com.wsfmn.exceptions.HabitEventCommentTooLongException;
import com.wsfmn.exceptions.HabitEventNameException;

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


    public HabitEvent(){
        this.title = "";
    }

    /**
     * Constructor for the Habit Event
     * @param habit
     * @param title
     * @param comment
     * @param CurrentPhotoPath
     * @param date
     *
     */
    public HabitEvent(Habit habit, String title, String comment, String CurrentPhotoPath, String date) throws HabitCommentTooLongException,
                                                        HabitEventCommentTooLongException{
        this.habit = habit;
        this.title = title;
        setComment(comment);
        this.CurrentPhotoPath = CurrentPhotoPath;
        this.id = null;
        this.date = date;

    }


    /**
     *
     * @param habit
     * @param title
     * @param comment
     * @param CurrentPhotoPath
     * @param geolocation
     * @param date
     * @throws HabitCommentTooLongException
     * @throws HabitEventCommentTooLongException
     */
    public HabitEvent(Habit habit, String title, String comment, String CurrentPhotoPath, Geolocation geolocation, String date)
            throws HabitCommentTooLongException, HabitEventCommentTooLongException{
        this.habit = habit;
        this.title = title;
        setComment(comment);
        this.CurrentPhotoPath = CurrentPhotoPath;
        this.id = null;
        this.date = date;
        this.geolocation = geolocation;
    }



///// SID, Delete this unused constructor? From: nmayne
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

    /**
     * Get the Habit for the HabitEvent
     * @return habit
     */
    public Habit getHabit() {
        return habit;
    }

    /**
     * Get the Title of the Habit Event
     * @return title of the HabitEvent
     * @throws HabitEventNameException
     */
    public String getHabitEventTitle() throws HabitEventNameException{
        /*Checks the title length of the HabitEvent*/
        if(title.length() > 35 || title.length()<1){
            /*Throws Exception if violates the condition*/
            throw new HabitEventNameException();
        }
        return title;
    }

    /**
     * Change Title of the HabitEvent
     * @param title
     * @throws HabitEventNameException
     */
    public String setTitle(String title)throws HabitEventNameException{
        /*Checks the length of the title*/
        if(title.length() > 35 || title.length()<1){
            /*Throws this exception*/
            throw new HabitEventNameException();
        }
        return this.title = title;
    }

    /**
     * Get the comment for HabitEvent that user created
     * @return comment
     */
    public String getComment(){
        return comment;
    }

    /**
     * /*Changing the Comment of Habit Event
     * @param comment
     * @throws HabitEventCommentTooLongException
     */
    public void setComment(String comment) throws HabitEventCommentTooLongException {
        /*Checking if comment size does not exceed 20 characters*/
        if(comment != null) {
            if (comment.length() > 20) {
            /* Throw HabitEventCommentTooLongException*/
                throw new HabitEventCommentTooLongException();
            }
        }
        this.comment = comment;
    }

    /**
     *
     * @param geolocation
     */
    public void setGeolocation(Geolocation geolocation){
        this.geolocation = geolocation;
    }

    /**
     *
     * @return
     */
    public Geolocation getGeolocation(){
        return geolocation;
    }

    /**
     *  Compares two String Dates
     *
     * @param otherDate the other date that compare with the calling objects' ate
     * @return int 0 if equal, -1 if the calling object's date is smaller than otherDate,
     *  1 otherwise.
     */
    public int compareDate(String otherDate){
        String[] list1 = this.date.split("/");
        String[] list2 = otherDate.split("/");

        String[] last1 = list1[2].substring(6).split(":");
        list1[2] = list1[2].substring(0, 4);

        int hour1 = Integer.parseInt(last1[0]);
        int minute1 = Integer.parseInt(last1[1]);

        String[] last2 = list2[2].substring(6).split(":");
        list2[2] = list2[2].substring(0, 4);

        int hour2 = Integer.parseInt(last2[0]);
        int minute2 = Integer.parseInt(last2[1]);

        if(Integer.parseInt(list1[2]) < Integer.parseInt(list2[2]))
            return -1;
        else if(Integer.parseInt(list1[2]) > Integer.parseInt(list2[2]))
            return 1;
        else if(Integer.parseInt(list1[1]) < Integer.parseInt(list2[1]))
            return -1;
        else if(Integer.parseInt(list1[1]) > Integer.parseInt(list2[1]))
            return 1;
        else if(Integer.parseInt(list1[0]) < Integer.parseInt(list2[0]))
            return -1;
        else if(Integer.parseInt(list1[0]) > Integer.parseInt(list2[0]))
            return 1;
        else if(hour1 < hour2)
            return -1;
        else if(hour1 > hour2)
            return 1;
        else if(minute1 < minute2)
            return -1;
        else if(minute1 > minute2)
            return 1;
        else
            return 0;

    }

    @Override
    public String toString(){
        return title + "    " + date;
    }

}
