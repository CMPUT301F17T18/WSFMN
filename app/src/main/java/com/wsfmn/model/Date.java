package com.wsfmn.model;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by musaed on 2017-10-26.
 *
 * This class represents a Date object that is used by classes that need to keep Date
 * information.
 */

public class Date implements Serializable{

    int year;
    int month;
    int day;

    /**
     *  Creates a Date object with year, month, and day corresponding to the date the
     *  object was created in.
     *
     */
    public Date(){
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Creates a Date object where the user passes year, month, and day
     *
     * @param year the year attribute is set to year
     * @param month the month attribute is set to month
     * @param day the day attribute is set to day
     */
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     *  Returns the year part of Date
     *
     * @return int the year of date
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets year for a date
     *
     * @param year a year that we use to set the date's year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     *  Returns the month of Date
     *
     * @return int the month of date
     */
    public int getMonth() {
        return month;
    }

    /**
     * Sets the month for date
     *
     * @param month a month that we use to set the date's month
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     *  Returns the day of Date
     *
     * @return int the day of date
     */
    public int getDay() {
        return day;
    }

    /**
     * Sets day for date
     *
     * @param day a day that we use to set the date's day
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     *  According to the current year, month, and day for the Date object,
     *  returns the total number of days for a month
     *
     * @return int the total number of days for a month, depending on the value of month
     *  of the calling object
     */
    public int getDaysInMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, getMonth()-1);
        return calendar.getActualMaximum(Calendar.DATE);
    }

    /**
     * Returns the day of the week that the day value of this calling objects corresponds to
     *
     * @return int the day of the week that the day value of this calling object
     *  corresponds to.
     */
    public int getDayOfWeek(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, getDay());
        c.set(Calendar.MONTH, getMonth()-1);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        int d = c.get(Calendar.DAY_OF_WEEK) - 1;

        if(d == 0)
            return 7;
        return d;
    }

    /**
     *  Compare two Dates to see if they are equal or not
     *
     * @param date a date to compare with the calling object.
     * @return boolean true if the two dates are equal and false otherwise
     */
    public boolean equalDate(Date date){
        return this.getYear() == date.getYear() &&
                this.getMonth() == date.getMonth() &&
                this.getDay() == date.getDay();
    }

    /**
     * Compares two Dates to see if one is larger than the other of
     * if they are equal
     *
     * @param date a date to compare with the calling object
     * @return int 0 if the two dates are equal.
     *          -1 if the calling object is smaller
     *          1 if the calling object is greater than date
     */
    public int compareDate(Date date){

        if(this.getYear() < date.getYear())
            return -1;

        else if(this.getYear() > date.getYear())
            return 1;

        else if(this.getMonth() < date.getMonth())
            return -1;

        else if(this.getMonth() > date.getMonth())
            return 1;

        else if(this.getDay() < date.getDay())
            return -1;

        else if(this.getDay() > date.getDay())
            return 1;

        else
            return 0;
    }

    /**
     *  Creates a string representation of a Date object, which consists of year,
     *  month, and day
     *
     * @return string a string representation of a Date object, wihch contains the year,
     * month, and day of the Date object.
     */
    public String toString(){
        return year + " / " + month + " / " + day;
    }
    public String toDateString(){
        String syear = Integer.toString(year);
        String smonth = Integer.toString(month);
        String sday = Integer.toString(day);
        return syear  + smonth  + sday;
    }

}
