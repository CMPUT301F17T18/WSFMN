/*
 * Copyright © 2017 Team 18 (WSFMN), CMPUT301, University of Alberta – All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact nmayne@ualberta.ca.
 *
 *  Team 18 is: Musaed Alsobaie, Siddhant Khanna, Wei Li, Nicholas Mayne, Fredric Mendi.
 */

package com.wsfmn.exceptions;

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
