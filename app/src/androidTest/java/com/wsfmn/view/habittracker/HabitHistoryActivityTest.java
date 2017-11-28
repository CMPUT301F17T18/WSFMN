package com.wsfmn.view.habittracker;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import com.wsfmn.model.Date;
import com.wsfmn.model.Habit;
import com.wsfmn.model.HabitEvent;
import com.wsfmn.controller.HabitHistoryController;
import com.wsfmn.controller.HabitListController;
import com.wsfmn.view.HabitHistoryActivity;

/**
 * Created by siddhant on 2017-11-13.
 */

public class HabitHistoryActivityTest extends ActivityInstrumentationTestCase2<HabitHistoryActivity> {
    private Solo solo;

    public HabitHistoryActivityTest() {
        super(com.wsfmn.view.HabitHistoryActivity.class);
    }

    protected void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testHabitHistory() throws Exception {
        Habit habit =  new Habit("Gym", "lose Weight", new Date());

        HabitEvent event = new HabitEvent(habit, "Gym Event", "Golds Gym",
                "/storage/","2017/11/13,00:01");
//        HabitHistoryController c = HabitHistoryController.getInstance();
        HabitHistoryController.add(event);
        HabitHistoryController.store();

        solo.assertCurrentActivity("Could not open HabitEventActivity", HabitHistoryActivity.class);

        solo.clickOnButton("Add Event");
        solo.sleep(5000);
        solo.goBackToActivity("HabitHistoryActivity");
        solo.sleep(5000);
        solo.clickInList(0);
        solo.sleep(5000);
        solo.goBackToActivity("HabitHistoryActivity");
        solo.sleep(5000);
        HabitHistoryController control = HabitHistoryController.getInstance();
        HabitEvent habitE = control.get(control.size()-1);
        assertNotNull(habitE);
        HabitHistoryController.remove(event);
        HabitHistoryController.store();
        HabitListController c2 = HabitListController.getInstance();
        c2.deleteHabit(habit);
    }
}