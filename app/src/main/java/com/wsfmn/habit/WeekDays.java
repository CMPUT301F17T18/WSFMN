package com.wsfmn.habit;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by musaed on 2017-10-22.
 */

public class WeekDays {

    public static final int MONDAY = 0;
    public static final int TUESDAY = 1;
    public static final int WEDNESDAY = 2;
    public static final int THURSDAY = 3;
    public static final int FRIDAY = 4;
    public static final int SATURDAY = 5;
    public static final int SUNDAY = 6;

    private boolean[] weekDays;

    public WeekDays(){
        weekDays = new boolean[7];
        for(boolean day: weekDays){
            day = false;
        }
    }

    public boolean getDay(int day){
        return weekDays[day];
    }

    public void setDay(int day){
        weekDays[day] = true;
    }

    public void unsetDay(int day){
        weekDays[day] = false;
    }

    public WeekDays copy(){
        WeekDays copy = new WeekDays();
        for(int i = 0; i < 7; i++){
            if(this.getDay(i))
                copy.setDay(i);
        }
        return copy;
    }

}
