/*
 * Copyright © 2017 Team 18 (WSFMN), CMPUT301, University of Alberta – All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact nmayne@ualberta.ca.
 *
 *  Team 18 is: Musaed Alsobaie, Siddhant Khanna, Wei Li, Nicholas Mayne, Fredric Mendi.
 */

package com.wsfmn.exceptions;

/**
 * Created by siddhant on 2017-11-11.
 */

public class HabitEventCommentTooLongException extends Exception {

    public HabitEventCommentTooLongException(){
        super("Habit Event comment can be at most 20 characters.");
    }
}
