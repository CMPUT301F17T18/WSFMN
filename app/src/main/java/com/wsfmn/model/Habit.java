/*
 * Copyright © 2017 Team 18 (WSFMN), CMPUT301, University of Alberta – All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact nmayne@ualberta.ca.
 *
 *  Team 18 is: Musaed Alsobaie, Siddhant Khanna, Wei Li, Nicholas Mayne, Fredric Mendi.
 */

package com.wsfmn.model;

import com.wsfmn.exceptions.DateNotValidException;
import com.wsfmn.exceptions.HabitReasonTooLongException;
import com.wsfmn.exceptions.HabitTitleTooLongException;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by musaed on 2017-10-16.
 *
 *
 * This is an entity class that represents a Habit that the user would like
 * to form.
 *
 * It is responsible for keeping the habits details, including its title,
 * reason, and the date that the habit should start as well as the
 * the days that the habit will be followed.
 *
 */
public class Habit implements Serializable, Comparable<Habit>{


    private String id;              //will set restful client
    private String title;           // Title for the habit
    private String title_search;    // Title that will be used to search for in  ES
    private String reason;          // Optional reason for the user to put
    protected Date date = null;     //  date the habit starts
    protected WeekDays weekDays;    //  days of the week the habit will be done
    private String owner;           // Owner of the habit
    private int score = 0;          // Score of the habit based on habit events.


    //  attributes for calculating statistics about a habit
    //  these attributes are used calculate possible times the habit could occur,
    //  taking into consideration that the user might change the Habit plan.
    protected int oldOcc = 0;
    protected int occ = 0;
    protected int tempOcc = 0;
    protected boolean hasChanged = false;
    protected Date currDate;  //  currDate is set to today when any change to the plan occurs

    //  default constructor: required for MockHabit
    public Habit(){
        date = null;
    }

    /**
     * Create a Habit object.
     *
     * @param date Starting date of the habit
     * @param weekDays A WeekDays list to identify the days of the week the habit will
     *                 occur in
     */
    public Habit(Date date, WeekDays weekDays){
        this.currDate = date;
        this.date = date;
        this.weekDays = weekDays;
    }

    /**
     * Create a Habit object.
     *
     * @param title The title or name of the habit
     * @param date  The starting date of the habit.
     * @throws HabitTitleTooLongException if Habit title is not between 1 and 20 characters.
     * @throws DateNotValidException    if Starting date is not a valid date
     */
    public Habit(String title, Date date) throws HabitTitleTooLongException,
                                                DateNotValidException {
        IDGenerator idGenerator = new IDGenerator();
        this.id = idGenerator.nextString();
        this.setDate(date);
        this.setTitle(title);
        this.weekDays = new WeekDays();
        currDate = date;
    }

    /**
     * Create a Habit object.
     *
     * @param title The title or name of the habit
     * @param reason The reason the habit will be started
     * @param date  Starting date for the habit
     * @throws HabitTitleTooLongException If the habit's title is not between 1 and 20 characters
     * @throws HabitReasonTooLongException If the habit's reason is not between 1 and 30 characters
     * @throws DateNotValidException If the starting date is not valid
     */
    public Habit(String title, String reason, Date date) throws HabitTitleTooLongException,
                                                            HabitReasonTooLongException,
                                                            DateNotValidException{
        this(title, date);
        this.setReason(reason);
    }

    /**
     *  Create a Habit object.
     *
     * @param title The title or name of the habit
     * @param reason The reason the habit will be started
     * @param date Starting date for the habit
     * @param weekDays a WeekDays object that identifies days the habit will occur in
     * @throws HabitTitleTooLongException If the habit's title is not between 1 and 20 characters
     * @throws HabitReasonTooLongException If the habit's reason is not between 1 and 30 characters
     * @throws DateNotValidException If the starting date is not valid
     */
    public Habit(String title, String reason, Date date, WeekDays weekDays) throws HabitTitleTooLongException,
                                                            HabitReasonTooLongException,
                                                            DateNotValidException{
        this(title, reason, date);
        this.weekDays = weekDays;
    }

    /**
     *  Retrieved the id for a habit.
     *
     * @return the id of the habit
     */
    public String getId() {
        return id;
    }

    /**
     *  Sets the id for a habit.
     *
     * @param id to identify the habit
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *  Gets the title for a habit.
     *
     * @return title of the habit
     */
    public String getTitle() {
        return title;
    }

    /**
     *  Sets the title for a habit if it is between 1 - 20 characters.
     *
     * @param title name or title of the habit.
     * @throws HabitTitleTooLongException thrown if the habit
     *  does not satisfy constraints.
     */
    public void setTitle(String title) throws HabitTitleTooLongException{
        if(title.length() < 1 ||title.length() > 20){
            throw new HabitTitleTooLongException();
        }
        this.title = title;
    }

    /**
     * Get the title used for online searching.
     *
     * @return online search title
     */
    public String getSearchTitle() {
        return title_search.toLowerCase().replaceAll("\\s+", "").replaceAll("[^A-Za-z0-9]", "");
    }

    /**
     * Set the searchable title based upon title.
     *
     */
    public void setSearchTitle() {
        title_search = title.toLowerCase().replaceAll("\\s+", "").replaceAll("[^A-Za-z0-9]", "");
    }

    /**
     * Show the habit's score, It is an indicator on how well you're doing the habit.
     *
     * @return the habit's score
     */
    public int getScore() {
        return score;
    }

    /**
     * Set the habit score.
     *
     * @param score of the habit
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     *  Gets reason for starting the habit.
     *
     * @return reason for starting the habit
     */
    public String getReason() {
        return reason;
    }

    /**
     *  Set the reason for a habit if it is not more than 30 characters.
     *
     * @param reason the reason for the habit
     * @throws HabitReasonTooLongException if reason is too long,
     *  an exception is thrown
     */
    public void setReason(String reason) throws HabitReasonTooLongException{
        if(reason.length() > 30){
            throw new HabitReasonTooLongException();
        }
        this.reason = reason;
    }

    /**
     *  Get the starting date of a habit.
     *
     * @return the date the habit starts
     */
    public Date getDate() {
        return date;
    }

    /**
     *  Sets the starting date of the habit if it is a valid date
     *
     * @param toStart the date the habit will starts
     * @throws DateNotValidException: thrown in the following conditions.
     *         -    if the habit is first created, then date
     *              must be current or future date
     *         -    if habit was created previously, then updating
     *              date is only allowed if is not before current date
     */
    public void setDate(Date toStart) throws DateNotValidException {
        if(this.date == null) {
            if (toStart.compareDate(new Date()) == -1)
                throw new DateNotValidException();
        }
        else{
            if(toStart.compareDate(this.date) == -1 && toStart.compareDate(new Date()) == -1)
                    throw new DateNotValidException();

        }

        if(date == null){
            hasChanged = true;
            currDate = toStart;
            this.date = toStart;
        }
        else if (this.date.compareDate(toStart) != 0) {
            hasChanged = true;
            currDate = toStart;
            this.date = toStart;
        }
    }

    /**
     * Get the days of the week the habit will occur in
     *
     * @return the days of the week the habit is done
     */
    public WeekDays getWeekDays() {
        return weekDays;
    }

    /**
     *  Gets weekDays for a habit.
     *
     * @param weekDays the days of the week the habit is done
     */
    public void setWeekDays(WeekDays weekDays) {
        hasChanged = true;
        currDate = new Date();
        this.weekDays = weekDays;
    }

    /**
     *  Access the weekDays attribute to set a day
     *  that the habit is expected to be done in.
     *
     * @param day the days that the habit will be done
     */
    public void setDay(int day){
        if(!weekDays.getDay(day)) {
            hasChanged = true;
            currDate = new Date();
        }
        weekDays.setDay(day);
    }

    /**
     *  Access the weekDays attribute to unset a day
     *  that the habit is not expected to be done in.
     *
     * @param day
     */
    public void unsetDay(int day){
        if(weekDays.getDay(day)) {
            hasChanged = true;
            currDate = new Date();
        }
        weekDays.unsetDay(day);
    }

    /**
     * Create a string representing a habit.
     *
     * @return a string representation for a habit
     */
    @Override
    public String toString(){
        return title + "    " + date + "   Score: %" + score;
    }

    /**
     * Check if habit is equal calling object.
     *
     * @param habit a habit to check if it is equal to calling object
     * @return boolean true if equal and false otherwise.
     */
    public boolean equal(Habit habit){
        return this.getTitle().equals(habit.getTitle());
    }

    /**
     *  Calculate the total number of times that the habit could have
     *  occurred in if it was followed according to schedule.
     *
     * @return  the total number of times that the habit could have occurred in
     *  if it was followed according to schedule
     */
    public int getTotalOccurrence(){
        //today
        Date today = new Date();
        if(!hasChanged){
            tempOcc = totalOccurrence(currDate, today);
            occ = tempOcc + oldOcc;
        }

        else{
            oldOcc = occ;
            tempOcc = totalOccurrence(currDate, today);
            occ = oldOcc + tempOcc;
            hasChanged = false;
        }

        return occ;
    }

    /**
     *  Calculate total times a habit could have occured in if it was
     *  followed according to schedule from startDate to endDate.
     *
     * @param startDate the start date for the habit that we want to use
     * @param endDate   the end date for a habit that we want to do
     * @return total times a habit could have occurred in if it was followed
     *  according to schedule from startDate to endDate.
     */
    public int totalOccurrence(Date startDate, Date endDate){
        int totalPossibleOccurrence = 0;
        int tm = endDate.getMonth();
        int dm = startDate.getMonth();


        for(int m = dm; m <= tm; m++){
            Date tempDate = new Date();
            int beg = 0, dayOfWeek = 0, end = 0;

            tempDate.setMonth(m);
            if(tm == dm){  //  current month of habit
                tempDate.setDay(startDate.getDay());
                beg = tempDate.getDay();
                end = endDate.getDay();
            }

            else if(m == dm){
                tempDate.setDay(startDate.getDay());
                beg = tempDate.getDay();
                end = tempDate.getDaysInMonth();
            }

            else if(m == tm){
                tempDate.setDay(1);
                beg = tempDate.getDay();
                end = endDate.getDay();
            }

            else{
                tempDate.setDay(startDate.getDay());
                beg = tempDate.getDay();
                end = tempDate.getDaysInMonth();
            }

            dayOfWeek = tempDate.getDayOfWeek();
            totalPossibleOccurrence += caldays(beg, dayOfWeek, end);
        }

        return totalPossibleOccurrence;
    }

    /**
     *  In a given month, return the number of times a habit could have occurred
     *  if it was followed according to schedule.
     *
     * @param beg we identify whent he month should start
     * @param dayOfWeek we pass the day of the week that beg corresponds to
     * @param end we the end day of the month that we want to use
     * @return In a given month identified by the parameters, returns the number of
     *  times a habit could have occurred if it was followed according to schedule
     */
    public int caldays(int beg, int dayOfWeek, int end){
        int total = 0;

        for(int i = beg; i <= end; i++){

            if(this.getWeekDays().getDay(dayOfWeek-1))
                total++;

            if(++dayOfWeek > 7)
                dayOfWeek = 1;
        }

        return total;
    }

    /**
     * Get the user name of the owner of the habit. For elastic search use and for FriendAdapter use.
     *
     * @return String the owner for a habit.
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Set the owner for which the habit belongs to. For elastic search use and for FriendAdapter.
     *
     * @param owner String the owner for the habit.
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Override compareTo method, Will be able to order a list by User name first then by
     * Habit title name.
     *
     * @param other the other habit that is compared with the calling object.
     * @return int
     */
    public int compareTo(Habit other)
    {
        int res =  this.getOwner().compareToIgnoreCase(other.getOwner());
        if (res != 0)
            return res;
        return this.getTitle().compareToIgnoreCase(other.getTitle());
    }
}
