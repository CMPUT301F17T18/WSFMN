package com.wsfmn.habittracker;

import android.test.ActivityInstrumentationTestCase2;

import com.wsfmn.habit.Date;


/**
 * Created by musaed on 2017-11-05.
 */

public class DateTest extends ActivityInstrumentationTestCase2 {

    public DateTest() {
        super(Date.class);
    }

    public void testGetDaysOfMonth(){
        Date today = new Date();
        if(today.getMonth() == 11)
            assertEquals(today.getDaysinMonth(), 31);
    }

    public void testGetDayOfWeek(){
        Date today = new Date();
        assertEquals(today.getDayOfWeek(), 7);
    }
}
