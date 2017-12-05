/*
 * Copyright © 2017 Team 18 (WSFMN), CMPUT301, University of Alberta – All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact nmayne@ualberta.ca.
 *
 *  Team 18 is: Musaed Alsobaie, Siddhant Khanna, Wei Li, Nicholas Mayne, Fredric Mendi.
 */

package com.wsfmn.exceptions;

/**
 * Created by siddhant on 2017-11-12.
 */
/*Exception is thrown when Habit Event Name exceeds 20
* characters or is less than 1 character that the user
* Enters*/
public class HabitEventNameException extends Exception{
    public HabitEventNameException(){
        super("Habit Event Name should be between 1 to 35 Characters");
    }
}
