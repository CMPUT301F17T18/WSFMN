package com.wsfmn.habittracker.habit;

import android.test.ActivityInstrumentationTestCase2;

import com.wsfmn.habit.Date;
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


        assertEquals(mockHabit.getTotalOccurrence(), 2);
    }

    /**
     *  Tests getTotalOccurrences under the condition:
     *      A change in WeekDays for the habit has occurred
     */
    public void testScheduleChanged(){
    }

    /**
     *  Tests getTotalOccurrences under the condition:
     *      A change in date for a habit has occurred
     */
    public void testDateChanged(){

    }

    /**
     *  Tests getTotalOccurrences under the condition:
     *      A change in both Date and WeekDays has occurred.
     */
    public void testPlanChanged(){

    }

}
