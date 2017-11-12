package com.wsfmn.habit;

/**
 * Created by musaed on 2017-10-22.
 */

public class HabitTitleTooLongException extends Exception {

    public HabitTitleTooLongException(){
        super("Title must be between 1 and 20 characters");
    }
}
