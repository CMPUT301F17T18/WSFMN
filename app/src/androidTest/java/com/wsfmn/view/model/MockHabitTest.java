package com.wsfmn.view.model;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.wsfmn.model.Date;
import com.wsfmn.exceptions.DateNotValidException;
import com.wsfmn.model.MockHabit;
import com.wsfmn.model.WeekDays;

/**
 * Created by musaed on 2017-11-12.
 */

public class MockHabitTest extends ActivityInstrumentationTestCase2{

    public MockHabitTest(){
        super(MockHabit.class);
    }


    public void testSetDatePast(){
        MockHabit habit = new MockHabit();

        Date today = new Date();

        Date past = new Date(today.getYear(), today.getMonth(), today.getDay()-2);

        //  I should not be able to set date in past
        try{
            habit.setDate(past);
            assertNull("Date Was Set In Past", null);
        }
        catch(DateNotValidException e){
            //null
        }
    }

    /**
     * Tests a case where a habit was created in the past but whether can edit it to
     * the past date it was created in, or today, or future.
     */
    public void testSetDatePastTwo(){

        //  new habit
        MockHabit habit = new MockHabit();

        //  date for habit should be null
        assertNull(habit.getDate());

        Date today = new Date();
        Date past = new Date(today.getYear(), today.getMonth(), today.getDay()-2);
        Date future = new Date(today.getYear(), today.getMonth(), today.getDay()+2);

        //  defining today in past
        habit.setToday(past);

        //  setting date for habit in the past
        try{
            habit.setDate(past);
        }
        catch(DateNotValidException e){
            //null
        }

        //  habit date should have been set to past because today is defined at that time
        assertTrue(habit.getDate().equalDate(past));

        //  today now reflects actual today so that habit looks like
        // it created at some point in the past
        habit.setToday(today);

        //  I should still be able to edit habit at that same point in the past
        try{
            habit.setDate(past);
        }
        catch(DateNotValidException e){
            assertNull("Could Not Edit Habit In Past If Created At That Point", null);
        }

        //  I should not be able to edit at some point in past other than the time
        //  it was originally created.
        try{
            Date otherPast = new Date(past.getYear(), past.getMonth(), past.getDay()+1);
            habit.setDate(otherPast);
            assertNull("I was Able to Edit Habit At Point Other Than Past", null);
        }
        catch(DateNotValidException e){
            //null
        }

        //  I should be able to edit habit at today
        try{
            habit.setDate(today);
        }
        catch(DateNotValidException e){
            assertNull("Could Not Edit Habit At Today", null);
        }

        try{
            habit.setDate(future);
        }
        catch (DateNotValidException e){
            assertNull("Could Not Edit Habit At Future", null);
        }


    }


    public void testSetDateFuture(){
        MockHabit habit = new MockHabit();

        Date today = new Date();

        Date future = new Date(today.getYear(), today.getMonth(), today.getDay()+2);

        //  I should be able to set date in future
        try{
            habit.setDate(future);
        }
        catch(DateNotValidException e){
            assertNull("Date Could Not Be Set In Future", null);
        }

        //  I should be able to set date to today
        try{
            habit.setDate(today);
        }
        catch(DateNotValidException e){
            assertNull("Date Could Not Be Set To Today", null);
        }
    }



    /**
     *  Tests getTotalOccurrences under the condition:
     *      No changes to Habit plan has occurred
     */
    public void testPlanNotChanged(){

        //  Initial Dates
        Date today = new Date(2017, 11, 7);
        Date past = new Date(today.getYear(), today.getMonth(), today.getDay()-5);
        Date future = new Date(today.getYear(), today.getMonth(), today.getDay()+5);

        //  initial plan
        Date toStart = new Date(2017, 10, 29);
        WeekDays weekDays = new WeekDays();
        weekDays.setDay(WeekDays.WEDNESDAY);
        MockHabit habit = new MockHabit(toStart, weekDays);
        //  today is first defined in past
        habit.setToday(past);


        assertEquals(1, habit.getTotalOccurrence());
    }

    /**
     *  Tests getTotalOccurrences under the condition:
     *      A change in WeekDays for the habit has occurred
     */
    public void testScheduleChanged(){

        //  Initial Dates
        Date today = new Date(2017, 11, 7);
        Date past = new Date(today.getYear(), today.getMonth(), today.getDay()-5);
        Date future = new Date(today.getYear(), today.getMonth(), today.getDay()+5);

        //  initial plan
        Date toStart = new Date(2017, 10, 29);
        WeekDays weekDays = new WeekDays();
        weekDays.setDay(WeekDays.WEDNESDAY);
        MockHabit habit = new MockHabit(toStart, weekDays);
        //  today is first defined in past
        habit.setToday(past);

        //  this line is to stimulate a real scenario
        //  this call would have been made if user changed his plan
        habit.getTotalOccurrence();

        //  plan is changed.
        habit.setDay(WeekDays.FRIDAY);
        //  today is changes to future
        habit.setToday(future);

        assertEquals(4, habit.getTotalOccurrence());
    }

    /**
     *  Tests getTotalOccurrences under the condition:
     *      A change in date for a habit has occurred
     */
    public void testDateChanged(){

        //  Initial Dates
        Date today = new Date(2017, 11, 7);
        Date past = new Date(today.getYear(), today.getMonth(), today.getDay()-5);
        Date future = new Date(today.getYear(), today.getMonth(), today.getDay()+5);

        //  initial plan
        Date toStart = new Date(2017, 10, 22);
        WeekDays weekDays = new WeekDays();
        weekDays.setDay(WeekDays.FRIDAY);
        weekDays.setDay(WeekDays.SATURDAY);
        MockHabit habit = new MockHabit(toStart, weekDays);
        //  today is first defined in past
        habit.setToday(past);

        //  this line is to stimulate a real scenario
        //  this call would have been made if user changed his plan
        habit.getTotalOccurrence();

        //  plan is changed.
        try {
            habit.setDate(new Date(2017, 11, 11));
        }
        catch(DateNotValidException e){
            //null
        }

        //  Today is changed to future
        habit.setToday(future);

        Log.i("musaed", "second new: " + habit.getTotalOccurrence());
        assertEquals(habit.getTotalOccurrence(), 3);

    }

    /**
     *  Tests getTotalOccurrences under the condition:
     *      A change in both Date and WeekDays has occurred.
     */
    public void testPlanChanged(){

        //  Initial Dates
        Date today = new Date(2017, 11, 7);
        Date past = new Date(today.getYear(), today.getMonth(), today.getDay()-5);
        Date future = new Date(today.getYear(), today.getMonth(), today.getDay()+5);

        //  initial plan
        Date toStart = new Date(2017, 10, 22);
        WeekDays weekDays = new WeekDays();
        weekDays.setDay(WeekDays.FRIDAY);
        weekDays.setDay(WeekDays.SATURDAY);
        MockHabit habit = new MockHabit(toStart, weekDays);
        //  today is first defined in past
        habit.setToday(past);

        //  this line is to stimulate a real scenario
        //  this call would have been made if user changed his plan
        habit.getTotalOccurrence();

        //  Plan is changed
        habit.unsetDay(WeekDays.FRIDAY);
        habit.setDay(WeekDays.SUNDAY);

        try {
            habit.setDate(new Date(2017, 11, 11));
        }
        catch(DateNotValidException e){
            //null
        }

        //  Today is changed to future
        habit.setToday(future);

        assertEquals(4, habit.getTotalOccurrence());

    }

    public void testPlanChangedOne(){

        //  Initial Dates
        Date today = new Date(2017, 11, 7);
        Date past = new Date(today.getYear(), today.getMonth(), today.getDay()-5);
        Date future = new Date(today.getYear(), today.getMonth(), today.getDay()+5);

        //  initial plan
        WeekDays weekDays = new WeekDays();
        weekDays.setDay(WeekDays.TUESDAY);
        MockHabit habit = new MockHabit(today, weekDays);
        //  today is first defined
        habit.setToday(today);

        assertEquals(1, habit.getTotalOccurrence());

        habit.setDay(WeekDays.FRIDAY);

        assertEquals(2, habit.getTotalOccurrence());

    }

}
