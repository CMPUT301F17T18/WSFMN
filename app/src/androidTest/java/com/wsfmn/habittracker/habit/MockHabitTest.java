package com.wsfmn.habittracker.habit;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.wsfmn.habit.Date;

import com.wsfmn.habit.WeekDays;

/**
 * Created by musaed on 2017-11-11.
 */

/*
public class MockHabitTest extends ActivityInstrumentationTestCase2 {

    public MockHabitTest(){
        super(MockHabit.class);
    }

    public void testGetTotalOccurrence(){

        //  initial plan
        Date date = new Date(2017, 10, 20);
        WeekDays weekDays = new WeekDays();
        weekDays.setDay(WeekDays.SATURDAY);
        MockHabit mockHabit = new MockHabit(date, weekDays);

        //  plan is changed
        //habit.setDay(WeekDays.FRIDAY);
        //habit.unsetDay(WeekDays.SATURDAY);
        //habit.setDay(WeekDays.WEDNESDAY);


        //  has to be updated depending on the day, since possible occurrences for a habit
        //  changes depending on the day.
        assertEquals(mockHabit.getTotalOccurrence(), 4);
    }
}
*/