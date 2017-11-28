package com.wsfmn.model;

/**
 * Created by musaed on 2017-10-22.
 *
 * Used to throw a HabitTitleTooLongException if the title for a habit does not satisfy a certain
 * constraint.
 */

public class HabitTitleTooLongException extends Exception {

    public HabitTitleTooLongException(){
        super("Title must be between 1 and 20 characters");
    }
}
