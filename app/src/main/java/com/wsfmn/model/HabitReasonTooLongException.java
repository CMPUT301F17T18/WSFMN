package com.wsfmn.model;

/**
 * Created by musaed on 2017-10-22.
 *
 * Used to throw a HabitReasonTooLongException if the reason for a habit does not satisfy a certain
 * constraint.
 */

public class HabitReasonTooLongException extends Exception {

    public HabitReasonTooLongException(){
        super("Habit reason can be at most 30 characters.");
    }
}
