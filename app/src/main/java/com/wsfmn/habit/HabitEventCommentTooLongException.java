package com.wsfmn.habit;

/**
 * Created by siddhant on 2017-11-11.
 */

/*Exception for Comments which is thrown when the user exceeds
* the 20 character limit for the Habit Event*/
public class HabitEventCommentTooLongException extends Exception {

    public HabitEventCommentTooLongException(){
        super("Habit Event comment can be at most 20 characters.");
    }
}
