package com.wsfmn.habittracker;

/**
 * Created by musaed on 2017-10-31.
 */

public class DateNotValidException extends Exception {

    public DateNotValidException(){
        super("Date must be current date or a future date.");
    }
}
