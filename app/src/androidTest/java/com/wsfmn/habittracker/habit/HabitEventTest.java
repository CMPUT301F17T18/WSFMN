package com.wsfmn.habittracker.habit;

import android.test.ActivityInstrumentationTestCase2;

import com.wsfmn.habit.Date;
import com.wsfmn.habit.Habit;
import com.wsfmn.habit.HabitEvent;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by siddhant on 2017-11-12.
 */
public class HabitEventTest extends ActivityInstrumentationTestCase2{

    public HabitEventTest(){super(HabitEvent.class);}

    @Test
    public void getDate() throws Exception {
        Habit habit = new Habit("Swimming",
                "To spend time with friends", new Date());
        String testDate = "13/11/2017,00:01";
        HabitEvent he = new HabitEvent(habit, "HabitEvent","Comment","/Storage/Space"
                , testDate);
        String dateValue = he.getDate();

        assertEquals("Correct Date Received ", testDate, dateValue);
    }

    @Test
    public void getCurrentPhotoPath() throws Exception {
    }

    @Test
    public void getHabitFromEvent() throws Exception {
    }

    @Test
    public void getHabitTitle() throws Exception {
    }

    @Test
    public void setHabit() throws Exception {
    }

    @Test
    public void getId() throws Exception {
    }

    @Test
    public void setId() throws Exception {
    }

    @Test
    public void getHabit() throws Exception {
    }

    @Test
    public void getHabitEventTitle() throws Exception {
    }

    @Test
    public void setTitle() throws Exception {
    }

    @Test
    public void getComment() throws Exception {
    }

    @Test
    public void setComment() throws Exception {
    }

}