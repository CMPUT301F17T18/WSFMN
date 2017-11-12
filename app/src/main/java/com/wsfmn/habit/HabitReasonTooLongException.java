package com.wsfmn.habit;

/**
 * Created by musaed on 2017-10-22.
 */

public class HabitReasonTooLongException extends Exception {

    public HabitReasonTooLongException(){
        super("Habit reason can be at most 30 characters.");
    }
}
