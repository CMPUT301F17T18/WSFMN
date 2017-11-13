package com.wsfmn.habit;

/**
 * Created by musaed on 2017-10-22.
 *
 * Used to throw a HabitCommentTooLongException if it does not satisfy a certain constraint
 */

public class HabitCommentTooLongException extends Exception {

    public HabitCommentTooLongException(){
        super("Habit comment can be at most 20 characters.");
    }
}
