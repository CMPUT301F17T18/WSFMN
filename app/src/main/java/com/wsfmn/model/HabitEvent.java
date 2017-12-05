/*
 * Copyright © 2017 Team 18 (WSFMN), CMPUT301, University of Alberta – All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact nmayne@ualberta.ca.
 *
 *  Team 18 is: Musaed Alsobaie, Siddhant Khanna, Wei Li, Nicholas Mayne, Fredric Mendi.
 *
 *  Code Reuse: https://stackoverflow.com/questions/7620401/how-to-convert-byte-array-to-bitmap
 */

package com.wsfmn.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.wsfmn.exceptions.HabitCommentTooLongException;
import com.wsfmn.exceptions.HabitEventCommentTooLongException;
import com.wsfmn.exceptions.HabitEventNameException;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * A HabitEvent is created when a user does one of their Habits.
 */
public class HabitEvent{

    private String title;                  //Title of habit event, will be the same its habit
    private String title_search;           //Title to be search on ES,
    private String owner;                  //Set the owner for the habit event
    private String photoStringEncoding;    //encoded string for the photo.
    private String photoPath;              // photo path of the picture taken
    private String comment;                // Optional comment for the habit event
    private String id;                     // Id used for ES, will be set once stored.
    private Habit habit;                   // Have the habit included in the habit event
    private Geolocation geolocation;       // Store geolocations, coordinates and address.
    private Date date = null;              // Our own version of Date, to be used for many functions.
    private java.util.Date actualdate;     // Actual date. Coverts our Date class in to java.util.Date


    /**
     * A blank HabitEvent
     */
    public HabitEvent(){
        this.title = "";
        this.title_search = "";
        this.date = new Date();
        IDGenerator idGenerator = new IDGenerator();
        this.id = idGenerator.nextString();
    }

    /**
     * A HabitEvent without a location
     *
     * @param habit Habit associated with the HabitEvent
     * @param title of the Habit/HabitEvent, same value
     * @param comment comment for this HabitEvent
     * @param photoStringEncoding string encoding of a photo for this HabitEvent
     * @param photoPath the local path for the user's photo
     * @param date the date of the HabitEvent
     * @throws HabitCommentTooLongException
     * @throws HabitEventCommentTooLongException
     * @throws ParseException
     */
    public HabitEvent(Habit habit, String title, String comment, String photoStringEncoding,
                      String photoPath, Date date)
            throws HabitCommentTooLongException, HabitEventCommentTooLongException, ParseException {

        this.habit = habit;
        this.title = title;
        setComment(comment);
        this.photoStringEncoding = photoStringEncoding;
        IDGenerator idGenerator = new IDGenerator();
        this.id = idGenerator.nextString();
        this.date = date;
        DateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date adate = formatter.parse(this.date.toDateString());
        this.actualdate = adate;
        this.geolocation = null;
        this.photoPath = photoPath;
    }

    /**
     * A complete HabitEvent
     *
     * @param habit Habit associated with the HabitEvent
     * @param title of the Habit/HabitEvent, same value
     * @param comment comment for this HabitEvent
     * @param photoStringEncoding string encoding of a photo for this HabitEvent
     * @param photoPath the local path for the user's photo
     * @param date the date of the HabitEvent
     * @param geolocation of the HabitEvent
     * @throws HabitCommentTooLongException
     * @throws HabitEventCommentTooLongException
     * @throws ParseException
     */
    public HabitEvent(Habit habit, String title, String comment, String photoStringEncoding,
                      String photoPath, Date date, Geolocation geolocation)
            throws HabitCommentTooLongException, HabitEventCommentTooLongException, ParseException {

        this.habit = habit;
        this.title = title;
        setComment(comment);
        this.photoStringEncoding = photoStringEncoding;
        IDGenerator idGenerator = new IDGenerator();
        this.id = idGenerator.nextString();
        this.date = date;
        DateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date adate = formatter.parse(this.date.toDateString());
        this.actualdate = adate;
        this.geolocation = geolocation;
        this.photoPath = photoPath;
    }

    /**
     * Set the date of when the HabitEvent was created.
     *
     * @return Date of the HabitEvent
     */
    public void setDate(Date date){
        this.date = date;
    }

    /**
     * Get the date Using our date method.
     *
     * @return Date of the HabitEvent
     */
    public Date getDate(){
        return this.date;
    }

    /**
     * get the actual Date using java.util.Date
     *
     * @return the actualDate
     * @throws ParseException
     */
    public java.util.Date getActualDate() throws ParseException {
        return this.actualdate;
    }

    /**
     * The string-encoding of the photo for this HabitEvent.
     *
     * @return the stored photo as a String encoding
     */
    public String getPhotoStringEncoding(){
        return photoStringEncoding;
    }

    /**
     * Set the String encoded for the Habitevent.
     *
     * @param photoStringEncoding the String encoding of the photo for this HabitEvent
     */
    public void setPhotoStringEncoding(String photoStringEncoding){
        this.photoStringEncoding = photoStringEncoding;
    }

    /**
     * Get the photo path from the picture taken on the phone/emulator.
     *
     * @return the local path to the original user's photo
     */
    public String getPhotoPath(){return photoPath;}

    /**
     * Set the photo path into HabitEvent.
     *
     * @param photoPath the local path to the original user's photo
     */
    public void setPhotoPath(String photoPath){this.photoPath = photoPath;}

    /**
     * Get the Habit the user selects for the HabitEvent.
     *
     * @return Habit
     */
    public Habit getHabitFromEvent(){
        return habit;
    }

    /**
     * Get the Habit title for the Habit Event.
     *
     * @return habit Title
     */
    public String getHabitTitle(){
        return this.habit.getTitle();
    }

    /**
     * Set the Habit for the HabitEvent.
     *
     * @param habit
     */
    public void setHabit(Habit habit){
        this.habit = habit;
    }

    /**
     * Get Id for ElasticSearch.
     *
     * @return Id for the HabitEvent
     */
    public String getId() {return id;}

    /**
     * Set Id for ElasticSearch.
     *
     * @param id for the HabitEvent
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the Habit of the HabitEvent.
     *
     * @return habit of the Habit event
     */
    public Habit getHabit() {
        return habit;
    }

    /**
     * Get the Title of the Habit Event.
     *
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
     * Get the title of the habit event.
     *
     * @return the HabitEvent title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of the HabitEvent
     *
     * @param title of the HabitEvent
     * @throws HabitEventNameException constrained by a char limit of 35
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
     * Get the title used for online searching.
     *
     * @return online search title
     */
    public String getSearchTitle() {
        return title_search;
    }

    /**
     * Set the searchable title based upon title.
     */
    public void setSearchTitle() {
        title_search = title.toLowerCase().replaceAll("\\s+", "").replaceAll("[^A-Za-z0-9]", "");
    }

    /**
     * Get the comment for HabitEvent that user created.
     *
     * @return comment of the HabtiEvent
     */
    public String getComment(){
        return comment;
    }

    /**
     * Set the Comment of HabitEvent.
     *
     * @param comment of the HabitEvent
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
     * Set the Geolocation for the HabitEvent.
     * This will add coordinates and an address String.
     *
     * @param geolocation of the HabitEvent
     */
    public void setGeolocation(Geolocation geolocation){
        this.geolocation = geolocation;
    }

    /**
     * Get the HabitEvent Geolocation.
     *
     * @return HabitEvent Geolocation
     */
    public Geolocation getGeolocation(){
        return geolocation;
    }

    /**
     * Get the owner of habit event.
     *
     * @return the owner of the HabitEvent
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Set the owner of the habit event.
     *
     * @param owner of the HabitEvent
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     *  Compare two String Dates
     *
     * @param otherDate the other date that compare with the calling objects' ate
     * @return int 0 if equal, -1 if the calling object's date is smaller than otherDate,
     *  1 otherwise.
     */
    public int compareDate(Date otherDate){
        return date.compareDate(otherDate);
    }

    /**
     * String representation of a HabitEvent.
     *
     * @return HabitEvent as a string
     */
    @Override
    public String toString(){
        return title + "    " + this.date;
    }

    /**
     * Code Reuse: https://stackoverflow.com/questions/7620401/how-to-convert-byte-array-to-bitmap
     *
     * @return the Image as a Bitmap
     */
    public Bitmap getImageBitmap() {
        if(photoStringEncoding!=null) {
            byte[] decodedString = Base64.decode(this.photoStringEncoding, Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            return decodedImage;
        }
        return null;
    }
}