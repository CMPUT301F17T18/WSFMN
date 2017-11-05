package com.wsfmn.habit;

import java.util.Calendar;

/**
 * Created by musaed on 2017-10-26.
 */

public class Date {

    int year;
    int month;
    int day;

    public Date(){
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);
    }

    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getDaysinMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        return calendar.getActualMaximum(Calendar.DATE);
    }

    public int getDayOfWeek(){
        int d = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        switch (d){
            case 1:
                return 7;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            case 5:
                return 4;
            case 6:
                return 5;
            default:
                return 6;
        }
    }

    public boolean equalDate(Date date){
        return this.getYear() == date.getYear() &&
                this.getMonth() == date.getMonth() &&
                this.getDay() == date.getDay();
    }

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

    public String toString(){
        return year + " / " + month + " / " + day;
    }
}
