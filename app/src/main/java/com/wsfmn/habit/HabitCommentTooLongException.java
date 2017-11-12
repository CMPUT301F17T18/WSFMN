package com.wsfmn.habit;

/**
 * Created by musaed on 2017-10-22.
 */

public class HabitCommentTooLongException extends Exception {

    public HabitCommentTooLongException(){
        super("Habit comment can be at most 20 characters.");
    }
}
