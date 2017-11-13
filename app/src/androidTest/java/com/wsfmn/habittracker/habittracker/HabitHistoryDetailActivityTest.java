package com.wsfmn.habittracker.habittracker;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;
import com.wsfmn.habit.Date;
import com.wsfmn.habit.DateNotValidException;
import com.wsfmn.habit.Habit;
import com.wsfmn.habit.HabitCommentTooLongException;
import com.wsfmn.habit.HabitEvent;
import com.wsfmn.habit.HabitEventNameException;
import com.wsfmn.habit.HabitReasonTooLongException;
import com.wsfmn.habit.HabitTitleTooLongException;
import com.wsfmn.habitcontroller.HabitHistoryController;
import com.wsfmn.habitcontroller.HabitListController;
import com.wsfmn.habittracker.HabitHistoryActivity;
import com.wsfmn.habittracker.R;
import com.wsfmn.habittracker.habitHistoryDetailActivity;

/**
 * Created by siddhant on 2017-11-13.
 */

public class HabitHistoryDetailActivityTest extends ActivityInstrumentationTestCase2<habitHistoryDetailActivity> {
    private Solo solo;

    public HabitHistoryDetailActivityTest(){
        super(com.wsfmn.habittracker.habitHistoryDetailActivity.class);}

    protected void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

<<<<<<< HEAD
    public void testAddHabitEvent() throws HabitEventNameException {
=======
    public void testAddHabitEvent() throws HabitEventNameException, HabitCommentTooLongException, DateNotValidException, HabitTitleTooLongException, HabitReasonTooLongException {

        Habit habit =  new Habit("Gym", "lose Weight", new Date());
        HabitListController c = HabitListController.getInstance();
        c.addAndStore(habit);
        HabitEvent event = new HabitEvent(habit, "Gym Event", "Golds Gym",
                "/storage/","2017/11/13,00:01");
        HabitHistoryController.getInstance();
        HabitHistoryController.addAndStore(event);
>>>>>>> 2dcff8326073803d55fbe93a9623d71fa165586c

        solo.assertCurrentActivity("Could not open HabitEventDetail", habitHistoryDetailActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.nameEvent2));
        solo.enterText((EditText) solo.getView(R.id.nameEvent2), "Swimming Competition");
        solo.clickOnButton("Change Habit");
        solo.clickInList(0);
        solo.clearEditText((EditText)solo.getView(R.id.Comment2));
        solo.enterText((EditText)solo.getView(R.id.Comment2), "100 m");

        solo.clickOnButton("Confirm");
        HabitHistoryController control = HabitHistoryController.getInstance();
        HabitEvent habitE = control.get(0);
        assertEquals("Habit Event not Modified", "Swimming Competition", habitE.getHabitEventTitle());

        int size = control.size();
        solo.clickOnButton("DELETE");
        int size2 = control.size();
        size = size;
        assertEquals("Delete Habit Event did not occur", size-1, size2);
        c.deleteHabit(habit);
    }
}