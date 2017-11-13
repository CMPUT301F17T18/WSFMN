package com.wsfmn.habittracker.habittracker;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import com.wsfmn.habit.Date;
import com.wsfmn.habit.DateNotValidException;
import com.wsfmn.habit.Habit;
import com.wsfmn.habit.HabitCommentTooLongException;
import com.wsfmn.habit.HabitEvent;
import com.wsfmn.habit.HabitReasonTooLongException;
import com.wsfmn.habit.HabitTitleTooLongException;
import com.wsfmn.habitcontroller.HabitHistoryController;
import com.wsfmn.habitcontroller.HabitListController;
import com.wsfmn.habittracker.HabitHistoryActivity;

/**
 * Created by siddhant on 2017-11-13.
 */

public class HabitHistoryActivityTest extends ActivityInstrumentationTestCase2<HabitHistoryActivity> {
    private Solo solo;

    public HabitHistoryActivityTest() {
        super(com.wsfmn.habittracker.HabitHistoryActivity.class);
    }

    protected void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testHabitHistory() throws DateNotValidException, HabitTitleTooLongException, HabitReasonTooLongException, HabitCommentTooLongException {
        Habit habit =  new Habit("Gym", "lose Weight", new Date());

        HabitEvent event = new HabitEvent(habit, "Gym Event", "Golds Gym",
                "/storage/","2017/11/13,00:01");
        HabitHistoryController.getInstance();
        HabitHistoryController.add(event);

        solo.assertCurrentActivity("Could not open HabitEventActivity", HabitHistoryActivity.class);
//        solo.assertCurrentActivity("Could not open HabitEventActivity", HabitHistoryActivity.class);
//        solo.sleep(5000);
        solo.clickOnButton("Add Event");
        solo.sleep(5000);
        solo.goBackToActivity("HabitHistoryActivity");
        solo.clickInList(0);
        solo.goBackToActivity("HabitHistoryActivity");

        HabitHistoryController control = HabitHistoryController.getInstance();
        HabitEvent habitE = control.get(control.size()-1);
        assertNotNull(habitE);
        HabitHistoryController.removeAndStore(event);
        HabitListController c2 = HabitListController.getInstance();
        c2.deleteHabit(habit);
    }
}