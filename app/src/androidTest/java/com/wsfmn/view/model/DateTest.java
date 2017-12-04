package com.wsfmn.view.model;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.wsfmn.model.Date;

import java.util.Calendar;

import static java.lang.Thread.sleep;


/**
 * Created by musaed on 2017-11-05.
 */

public class DateTest extends ActivityInstrumentationTestCase2 {

    public DateTest() {
        super(Date.class);
    }

    /**
     *  Tests getting the number of days in a month that the Date object is defined with.
     *
     */
    public void testGetDaysInMonth(){
        Date date1 = new Date(2017, 11, 11);
        Date date2 = new Date(2017, 10, 10);
        Date date3 = new Date(2016, 3, 2);

        assertEquals(date1.getDaysInMonth(), 30);
        assertEquals(date2.getDaysInMonth(), 31);
        assertEquals(date3.getDaysInMonth(), 31);
    }


    /**
     *  Tests getting the day of the week of the Date object.
     *
     */
    public void testGetDayOfWeek(){
        Date date1 = new Date(2017, 11, 7);
        Date date2 = new Date(2017, 11, 6);
        Date date3 = new Date(2017, 10, 15);

        assertEquals(date1.getDayOfWeek(), 2);
        assertEquals(date2.getDayOfWeek(), 1);
        assertEquals(date3.getDayOfWeek(), 7);
    }

    /**
     *  Tests comparing two Date objects.
     *
     */
    public void testCompareDate(){
        Date date1 = new Date(2017, 11, 11);
        Date date2 = new Date(2017, 11, 10);
        Date date3 = new Date(2017, 10, 11);
        Date date4 = new Date(2017, 11, 11);
        Date date5 = new Date(2016, 11, 11);

        assertEquals(date1.compareDate(date2), 1);
        assertEquals(date1.compareDate(date3), 1);

        assertEquals(date1.compareDate(date4), 0);
        assertEquals(date1.compareDate(date5), 1);
    }

    /**
     *  Tests comparing whether two Date objects are equal or not.
     *
     */
    public void testEqualDate(){
        Date date1 = new Date(2017, 11, 11);
        Date date2 = new Date(2017, 11, 11);
        Date date3 = new Date(2017, 11, 10);
        Date date4 = new Date(2017, 10, 11);
        Date date5 = new Date(2016, 11, 11);

        assertTrue(date1.equalDate(date2));

        assertFalse(date1.equalDate(date3));
        assertFalse(date1.equalDate(date4));
        assertFalse(date1.equalDate(date5));
    }


    /**
     *  Tests comparing Date objects that were defined using the constructor with parameter t.
     *
     * @throws InterruptedException
     */
    public void testHDate() throws InterruptedException{
        Date date = new Date(0);

        sleep(2000);

        Date date2 = new Date(0);

        assertEquals(-1, date.compareDate(date2));
    }

}
