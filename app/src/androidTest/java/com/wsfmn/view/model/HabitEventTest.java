package com.wsfmn.view.model;

import android.test.ActivityInstrumentationTestCase2;

import com.wsfmn.exceptions.DateNotValidException;
import com.wsfmn.exceptions.HabitReasonTooLongException;
import com.wsfmn.exceptions.HabitTitleTooLongException;
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
        Date date = new Date(0);
        HabitEvent he = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space",
                null, date);

        assertEquals("Correct Date Not Received ", date, he.getDate());
    }

    @Test
    public void testSetDate() throws Exception{
        Habit habit = new Habit("Swimming",
                "To spend time with friends", new Date());
        Date date = new Date(0);
        HabitEvent he = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space",
                null, date);
        Date date2 = new Date(0);
        he.setDate(date2);
        assertEquals("Date not set", date2, he.getDate());
    }

    @Test
    public void testGetPhotoStringEncoding() throws Exception{
        Habit habit = new Habit("Swimming",
                "To spend time with friends", new Date());
        Date date = new Date(0);
        HabitEvent he = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space",
                null, date);

        assertEquals("Not same Image folder", "/Storage/Space",he.getPhotoStringEncoding());
    }

    @Test
    public void testSetPhotoStringEncoding() throws Exception {
        Habit habit = new Habit("Swimming",
                "To spend time with friends", new Date());
        Date date = new Date(0);
        HabitEvent he = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space",
                null, date);
        he.setPhotoStringEncoding("/Storage/Space/2");
        assertEquals("Photo Path Changed", "/Storage/Space/2",he.getPhotoStringEncoding());
    }

    @Test
    public void testGetPhotoPath() throws Exception{
        Habit habit = new Habit("Swimming",
                "To spend time with friends", new Date());
        Date date = new Date(0);
        HabitEvent he = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space",
                null, date);
        assertNull(he.getPhotoPath());
    }

    @Test
    public void testSetPhotoPath() throws Exception{
        Habit habit = new Habit("Swimming",
                "To spend time with friends", new Date());
        Date date = new Date(0);
        HabitEvent he = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space",
                null, date);
        he.setPhotoPath("/Storage/Check");
        assertEquals("PhotoPath set", "/Storage/Check", he.getPhotoPath());
    }

    @Test
    public void testGetHabitFromEvent() throws Exception {
        Habit habit = new Habit("Swimming",
                "To spend time with friends", new Date());
        Date date = new Date(0);
        HabitEvent he = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space",
                null, date);
        Habit habitOfEvent = he.getHabitFromEvent();
        assertNotNull(habitOfEvent);
        assertEquals("Getting the Habit", habit, habitOfEvent);

    }

    @Test
    public void testGetHabitTitle() throws Exception {
        Habit habit = new Habit("Swimming",
                "To spend time with friends", new Date());
        Date date = new Date(0);
        HabitEvent he = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space",
                null, date);
        Habit habitOfEvent = he.getHabitFromEvent();
        assertNotNull(habitOfEvent);
        assertEquals("Title are not equal", "Swimming", he.getHabitTitle());
    }

    @Test
    public void testSetHabit() throws Exception {
        Habit habit = new Habit("Swimming",
                "To spend time with friends", new Date());
        Date date = new Date(0);
        HabitEvent he = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space",
                null,date);
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
        Date date = new Date(0);
        HabitEvent he = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space",
                null,date);
        assertNotNull(he.getId());
    }

    @Test
    public void setId() throws Exception {
        Habit habit = new Habit("Swimming",
                "To spend time with friends", new Date());
        Date date = new Date(0);
        HabitEvent he = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space",
                null,date);
        assertNull(he.getId());
        he.setId("Unique ID");
        assertEquals("ID not set","Unique ID", he.getId());
    }

    @Test
    public void testGetHabit() throws Exception {
        Habit habit = new Habit("Swimming",
                "To spend time with friends", new Date());
        Date date = new Date(0);
        HabitEvent he = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space",
                null,date);
        Habit habitOfEvent = he.getHabit();
        assertNotNull(habitOfEvent);
        assertEquals("Getting the Habit", habit, habitOfEvent);
    }

    @Test
    public void testGetHabitEventTitle() throws Exception {
        Habit habit = new Habit("Swimming",
                "To spend time with friends", new Date());
        Date date =  new Date(0);
        HabitEvent he = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space",
                null,date);
        String string2 = he.getHabitEventTitle();
        assertEquals("Habit Title not correct", "HabitEvent", string2);
    }

    @Test
    public void testSetTitle() throws Exception {
        Habit habit = new Habit("Swimming",
                "To spend time with friends", new Date());
        HabitEvent he = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space",
                null,new Date(0));
        he.setTitle("HabitEventTest");
        assertNotNull(he);
        assertEquals("Habit Event title not changed", "HabitEventTest", he.getHabitEventTitle());
    }

    @Test
    public void testGetComment() throws Exception {
        Habit habit = new Habit("Swimming",
                "To spend time with friends", new Date());
        HabitEvent he = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space",
                null,new Date(0));
        assertNotNull(he);
        assertEquals("Did not get title of Habit Event","Comment", he.getComment());
    }

    @Test
    public void testSetComment() throws Exception {
        Habit habit = new Habit("Swimming",
                "To spend time with friends", new Date());
        HabitEvent he = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space",
                null,new Date(0));
        assertNotNull(he);
        he.setComment("CommentTest");
        assertEquals("Comment not Changed", "CommentTest", he.getComment());
    }

    public void testCompareDate() throws Exception{
        Habit habit = new Habit("Swimming",
                "To spend time with friends", new Date());
        HabitEvent he = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space",
                null,new Date(0));

        int string1 = he.compareDate(new Date(0));
        assertEquals(0, he.compareDate(new Date(0)));
    }

}