package com.wsfmn.habittracker;

/**
 * Created by musaed on 2017-10-22.
 */

public class HabitTitleTooLongException extends Exception {

    public HabitTitleTooLongException(){
        super("Title can be at most 20 characters.");
    }
}
