package com.wsfmn.habittracker.habit;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.wsfmn.habit.Date;
import com.wsfmn.habit.DateNotValidException;
import com.wsfmn.habit.Habit;
import com.wsfmn.habit.HabitReasonTooLongException;
import com.wsfmn.habit.HabitTitleTooLongException;
import com.wsfmn.habit.WeekDays;


/**
 * Created by musaed on 2017-10-21.
 */

public class HabitTest extends ActivityInstrumentationTestCase2 {

    public HabitTest(){
        super(Habit.class);
    }

    public void testGetId(){
        Habit habit = null;
        try{
            habit = new Habit("title", "reason", new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(HabitReasonTooLongException e){

        }
        catch (DateNotValidException e){
            //null
        }

        assertNull("Habit ID was not null", habit.getId());
    }

    public void testSetId(){
        Habit habit = null;
        try{
            habit = new Habit("title", "reason", new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(HabitReasonTooLongException e){

        }
        catch (DateNotValidException e){
            //null
        }

        habit.setId("My Unique ID");
        assertNotNull("Habit ID was null", habit.getId());
        assertEquals("Habit ID was not equal to the one set.", habit.getId(), "My Unique ID");
    }

    public void testGetTitle(){
        Habit habit = null;

        try {
            habit = new Habit("title", new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(DateNotValidException e){
            //null
        }

        assertEquals(habit.getTitle(), "title");
    }

    public void testSetTitle(){
        Habit habit = null;


        try{
            habit = new Habit("title1", new Date());
            habit.setTitle("title2");
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(DateNotValidException e){
            //null
        }


        assertEquals(habit.getTitle(), "title2");
    }

    public void testGetReason(){ 
        Habit habit = null;

        try{
            habit = new Habit("title", "reason", new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(HabitReasonTooLongException e){
            //null
        }
        catch (DateNotValidException e){
            //null
        }

        assertEquals(habit.getReason(), "reason");
    }

    public void testSetReason(){
        Habit habit = null;


        try{
            habit = new Habit("title", "reason1", new Date());
            habit.setReason("reason2");
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(HabitReasonTooLongException e){
            //null
        }
        catch(DateNotValidException e){
            //null
        }


        assertEquals(habit.getReason(), "reason2");
    }

    public void testGetDate(){
        Date date = new Date();
        Habit habit = null;

        try {
            habit = new Habit("title", date);
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(DateNotValidException e){
            //null
        }


        assertTrue(habit.getDate().equalDate(date));
    }

    public void testSetDate(){
        Date date1 = new Date();
        Date date2 = new Date(date1.getYear(),
                date1.getMonth(),
                date1.getDay()+1);
        Habit habit = null;

        try {
            habit = new Habit("title", date1);
            habit.setDate(date2);

        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(DateNotValidException e){
            //null
        }

        assertTrue(habit.getDate().equalDate(date2));
    }

    public void testGetWeekDays(){
        Habit habit = null;

        try{
            habit = new Habit("title", "reason", new Date(), new WeekDays());
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(HabitReasonTooLongException e){
            //null
        }
        catch(DateNotValidException e){
            //null
        }

        assertNotNull(habit.getWeekDays());
    }

    public void testSetWeekDays(){
        Habit habit = null;

        try{
            habit = new Habit("title", "reason", new Date(), new WeekDays());
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(HabitReasonTooLongException e){
            //null
        }
        catch(DateNotValidException e){
            //null
        }

        WeekDays weekDays = new WeekDays();
        weekDays.setDay(WeekDays.MONDAY);

        habit.setWeekDays(weekDays);

        assertNotNull(habit.getWeekDays());
        assertEquals(habit.getWeekDays().getDay(WeekDays.MONDAY), true);

        for(int i = 1; i < 7; i++){
            assertEquals(habit.getWeekDays().getDay(i), false);
        }

    }

    public void testToString() {
        Habit habit = null;
        Date date = new Date();
        try{
            habit = new Habit("title", "reason", new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(HabitReasonTooLongException e){

        }
        catch (DateNotValidException e){
            //null
        }


        assertTrue("Habit as a string was incorrect", habit.toString().contains("title"+"    "+date));
    }

    public void testSetTitleException(){
        Habit habit = null;

        try{
            habit = new Habit("a title that is more than twenty characters", new Date());
        }
        catch(HabitTitleTooLongException e) {
            assertNull("Title Constraint Enforcement Failed", habit);
        }
        catch(DateNotValidException e){
            //null
        }

    }

    public void testSetReasonException(){
        Habit habit = null;

        try{
            habit = new Habit("title",
                    "a reason that contains more than thirty characters",
                    new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(HabitReasonTooLongException e){
            assertNull("Reason Constraint Enforcement Failed", habit);
        }
        catch(DateNotValidException e){
            //null
        }
    }

    public void testSetDateException(){
        Habit habit = null;

        try{
            habit = new Habit("title", "reason", new Date(2017, 10, 10));
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(HabitReasonTooLongException e){
            //null
        }
        catch(DateNotValidException e){
            assertNull("Date Constraint Enforcement Failed", habit);
        }
    }

    public void testCalDay(){
        /*Habit habit = new Habit();
        int result = 0;

        habit.getDate().setMonth(10);
        habit.getDate().setDay(26);
        habit.getWeekDays().setDay(WeekDays.FRIDAY);
        habit.getWeekDays().setDay(WeekDays.MONDAY);
        habit.getWeekDays().setDay(WeekDays.TUESDAY);

        result = habit.caldays(habit.getDate().getDay(),
                habit.getDate().getDayOfWeek(),
                habit.getDate().getDaysInMonth());

        assertEquals(result, 3);*/
    }


    public void testTotalOccurrence(){

        Date start = new Date(2017, 10, 20);
        Date end = new Date(2017, 11, 10);
        WeekDays weekDays = new WeekDays();
        weekDays.setDay(WeekDays.SATURDAY);
        weekDays.setDay(WeekDays.SUNDAY);
        Habit habit = new Habit(start, weekDays);

        assertEquals(habit.totalOccurrence(start, end), 6);
    }

    public void testTotalOccurrenceSameDate(){

        Date start = new Date(2017, 11, 11);
        Date end = new Date(2017, 11, 11);
        WeekDays weekDays = new WeekDays();
        weekDays.setDay(WeekDays.SATURDAY);
        Habit habit = new Habit(start, weekDays);

        assertEquals(habit.totalOccurrence(start, end), 1);
    }

    public void testGetTotalOccurrence(){

        //  initial plan
        Date date = new Date(2017, 10, 20);
        WeekDays weekDays = new WeekDays();
        weekDays.setDay(WeekDays.SATURDAY);
        Habit habit = new Habit(date, weekDays);

        //  to make a change, this call would have been made.
        habit.getTotalOccurrence();

        //  plan is changed
        habit.setDay(WeekDays.FRIDAY);
        habit.unsetDay(WeekDays.SATURDAY);
        habit.setDay(WeekDays.WEDNESDAY);


        //  has to be updated depending on the day, since possible occurrences for a habit
        //  changes depending on the day.
        Log.i("musaed", "result = " + habit.getTotalOccurrence());
        assertEquals(habit.getTotalOccurrence(), 4);
    }



}
