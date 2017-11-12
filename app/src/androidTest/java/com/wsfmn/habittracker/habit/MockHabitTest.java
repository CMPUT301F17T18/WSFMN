package com.wsfmn.habittracker.habit;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.wsfmn.habit.Date;
import com.wsfmn.habit.DateNotValidException;
import com.wsfmn.habit.MockHabit;
import com.wsfmn.habit.WeekDays;

/**
 * Created by musaed on 2017-11-12.
 */

public class MockHabitTest extends ActivityInstrumentationTestCase2{

    public MockHabitTest(){
        super(MockHabit.class);
    }

    /**
     *  Tests getTotalOccurrences under the condition:
     *      No changes to Habit plan has occurred
     */
    public void testPlanNotChanged(){

        //  initial plan
        Date toStart = new Date(2017, 10, 29);
        WeekDays weekDays = new WeekDays();
        weekDays.setDay(WeekDays.WEDNESDAY);
        MockHabit mockHabit = new MockHabit(toStart, weekDays);


        assertEquals(mockHabit.getTotalOccurrence(), 1);
    }

    /**
     *  Tests getTotalOccurrences under the condition:
     *      A change in WeekDays for the habit has occurred
     */
    public void testScheduleChanged(){

        //  initial plan
        Date toStart = new Date(2017, 10, 29);
        WeekDays weekDays = new WeekDays();
        weekDays.setDay(WeekDays.WEDNESDAY);
        MockHabit mockHabit = new MockHabit(toStart, weekDays);

        //  this line is to stimulate a real scenario
        //  this call would have been made if user changed his plan
        mockHabit.getTotalOccurrence();

        //  plan is changed.
        mockHabit.setDay(WeekDays.FRIDAY);


        Log.i("musaed", "second new: " + mockHabit.getTotalOccurrence());
        assertEquals(mockHabit.getTotalOccurrence(), 4);
    }

    /**
     *  Tests getTotalOccurrences under the condition:
     *      A change in date for a habit has occurred
     */
    public void testDateChanged(){

        //  initial plan
        Date toStart = new Date(2017, 10, 22);
        WeekDays weekDays = new WeekDays();
        weekDays.setDay(WeekDays.FRIDAY);
        weekDays.setDay(WeekDays.SATURDAY);
        MockHabit mockHabit = new MockHabit(toStart, weekDays);

        //  this line is to stimulate a real scenario
        //  this call would have been made if user changed his plan
        mockHabit.getTotalOccurrence();

        //  plan is changed.
        try {
            mockHabit.setDate(new Date(2017, 11, 11));
        }
        catch(DateNotValidException e){
            //null
        }


        Log.i("musaed", "second new: " + mockHabit.getTotalOccurrence());
        assertEquals(mockHabit.getTotalOccurrence(), 3);

    }

    /**
     *  Tests getTotalOccurrences under the condition:
     *      A change in both Date and WeekDays has occurred.
     */
    public void testPlanChanged(){

        //  initial plan
        Date toStart = new Date(2017, 10, 22);
        WeekDays weekDays = new WeekDays();
        weekDays.setDay(WeekDays.FRIDAY);
        weekDays.setDay(WeekDays.SATURDAY);
        MockHabit mockHabit = new MockHabit(toStart, weekDays);

        //  this line is to stimulate a real scenario
        //  this call would have been made if user changed his plan
        mockHabit.getTotalOccurrence();

        mockHabit.unsetDay(WeekDays.FRIDAY);
        mockHabit.setDay(WeekDays.SUNDAY);

        //  plan is changed.
        try {
            mockHabit.setDate(new Date(2017, 11, 11));
        }
        catch(DateNotValidException e){
            //null
        }


        Log.i("musaed", "second new: " + mockHabit.getTotalOccurrence());
        assertEquals(mockHabit.getTotalOccurrence(), 4);

    }

}
