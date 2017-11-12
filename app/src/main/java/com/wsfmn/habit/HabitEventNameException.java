package com.wsfmn.habit;

/**
 * Created by siddhant on 2017-11-12.
 */

public class HabitEventNameException extends Exception{
    public HabitEventNameException(){
        super("Habit Event Name should be between 1 to 20 Characters");
    }
}
