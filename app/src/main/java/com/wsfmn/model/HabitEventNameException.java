package com.wsfmn.model;

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
