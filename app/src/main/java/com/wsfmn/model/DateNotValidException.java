package com.wsfmn.model;

/**
 * Created by musaed on 2017-10-31.
 *
 * Used to throw an exception if a Date object does not satisfy a certain constraint.
 */

public class DateNotValidException extends Exception {

    public DateNotValidException(){
        super("Date must be current date or a future date.");
    }
}
