package com.wsfmn.habit;

/**
 * Created by siddhant on 2017-11-11.
 */

<<<<<<< HEAD
=======
/*Exception for Comments which is thrown when the user exceeds
* the 20 character limit for the Habit Event*/
>>>>>>> a8dcb796c7cf68f71f53f3868ca229daed2fbd5c
public class HabitEventCommentTooLongException extends Exception {

    public HabitEventCommentTooLongException(){
        super("Habit Event comment can be at most 20 characters.");
    }
}
