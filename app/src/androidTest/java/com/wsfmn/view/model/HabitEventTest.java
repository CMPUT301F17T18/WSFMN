package com.wsfmn.view.model;

import android.test.ActivityInstrumentationTestCase2;

import com.wsfmn.model.Date;
import com.wsfmn.model.Habit;
import com.wsfmn.model.HabitEvent;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by siddhant on 2017-11-12.
 */
public class HabitEventTest extends ActivityInstrumentationTestCase2{

    public HabitEventTest(){super(HabitEvent.class);}

    @Test
    public void testGetDate() throws Exception {
        Habit habit = new Habit("Swimming",
                    "To spend time with friends", new Date());
        HabitEvent he = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space"
                    , "13/11/2017,00:01");

        assertEquals("Correct Date Not Received ", "13/11/2017,00:01", he.getDate());
    }

    @Test
    public void testGetCurrentPhotoPath() throws Exception {
        Habit habit = new Habit("Swimming",
                "To spend time with friends", new Date());
        HabitEvent he = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space"
                , "13/11/2017,00:01");
        assertEquals("Incorrect Path of Image File", "/Storage/Space", he.getCurrentPhotoPath());

    }

    @Test
    public void testGetHabitFromEvent() throws Exception {
        Habit habit = new Habit("Swimming",
                "To spend time with friends", new Date());
        HabitEvent he = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space"
                , "13/11/2017,00:01");
        Habit habitOfEvent = he.getHabitFromEvent();
        assertNotNull(habitOfEvent);
        assertEquals("Getting the Habit", habit, habitOfEvent);

    }

    @Test
    public void testGetHabitTitle() throws Exception {
        Habit habit = new Habit("Swimming",
                "To spend time with friends", new Date());
        HabitEvent he = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space"
                , "13/11/2017,00:01");
        Habit habitOfEvent = he.getHabitFromEvent();
        assertNotNull(habitOfEvent);
        assertEquals("Title are not equal", "Swimming", he.getHabitTitle());
    }

    @Test
    public void testSetHabit() throws Exception {
        Habit habit = new Habit("Swimming",
                "To spend time with friends", new Date());
        HabitEvent he = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space"
                , "13/11/2017,00:01");
        Habit habitOfEvent = he.getHabitFromEvent();
        assertNotNull(habitOfEvent);
        Habit habit2 = new Habit("Running",
                "Lose Weight", new Date());
        he.setHabit(habit2);
        assertNotNull(habit2);
        assertNotNull(he.getHabitFromEvent());
        assertEquals("New habit is not been set", habit2, he.getHabitFromEvent());
    }

    @Test
    public void testGetId() throws Exception {
        Habit habit = new Habit("Swimming",
                "To spend time with friends", new Date());
        HabitEvent he = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space"
                , "13/11/2017,00:01");
        assertNull(he.getId());
    }

    @Test
    public void setId() throws Exception {
        Habit habit = new Habit("Swimming",
                "To spend time with friends", new Date());
        HabitEvent he = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space"
                , "13/11/2017,00:01");
        assertNull(he.getId());
        he.setId("Unique ID");
        assertEquals("ID not set","Unique ID", he.getId());
    }

    @Test
    public void testGetHabit() throws Exception {
        Habit habit = new Habit("Swimming",
                "To spend time with friends", new Date());
        HabitEvent he = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space"
                , "13/11/2017,00:01");
        Habit habitOfEvent = he.getHabit();
        assertNotNull(habitOfEvent);
        assertEquals("Getting the Habit", habit, habitOfEvent);
    }

    @Test
    public void testGetHabitEventTitle() throws Exception {
        Habit habit = new Habit("Swimming",
                "To spend time with friends", new Date());
        HabitEvent he = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space"
                , "13/11/2017,00:01");
        assertEquals("Habit Title not correct", "HabitEvent", he.getHabitEventTitle());
    }

    @Test
    public void testSetTitle() throws Exception {
        Habit habit = new Habit("Swimming",
                "To spend time with friends", new Date());
        HabitEvent he = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space"
                , "13/11/2017,00:01");
        he.setTitle("HabitEventTest");
        assertNotNull(he);
        assertEquals("Habit Event title not changed", "HabitEventTest", he.getHabitEventTitle());
    }

    @Test
    public void testGetComment() throws Exception {
        Habit habit = new Habit("Swimming",
                "To spend time with friends", new Date());
        HabitEvent he = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space"
                , "13/11/2017,00:01");
        assertNotNull(he);
        assertEquals("Did not get title of Habit Event","Comment", he.getComment());
    }

    @Test
    public void testSetComment() throws Exception {
        Habit habit = new Habit("Swimming",
                "To spend time with friends", new Date());
        HabitEvent he = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space"
                , "13/11/2017,00:01");
        assertNotNull(he);
        he.setComment("CommentTest");
        assertEquals("Comment not Changed", "CommentTest", he.getComment());
    }

    public void testCompareDate() throws Exception{
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
        String timeStamp = df.format(Calendar.getInstance().getTime());

        Habit habit = new Habit("Swimming",
                "To spend time with friends", new Date());
        HabitEvent he = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space"
                , timeStamp);


        assertEquals(0, he.compareDate(timeStamp));
        assertEquals(-1, he.compareDate("13/12/2018, 00:01"));
        assertEquals(1, he.compareDate("13/11/2017,00:01"));


    }

}