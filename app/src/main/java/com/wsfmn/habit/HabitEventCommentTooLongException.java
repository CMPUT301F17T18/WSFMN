package com.wsfmn.habit;

/**
 * Created by siddhant on 2017-11-11.
 */

public class HabitEventCommentTooLongException extends Exception {

    public HabitEventCommentTooLongException(){
        super("Habit Event comment can be at most 20 characters.");
    }
}
