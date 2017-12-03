package com.wsfmn.view.view;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import com.wsfmn.model.Date;
import com.wsfmn.model.Habit;
import com.wsfmn.model.HabitEvent;
import com.wsfmn.controller.HabitHistoryController;
import com.wsfmn.controller.HabitListController;
import com.wsfmn.view.ViewHabitHistoryActivity;

/**
 * Created by siddhant on 2017-11-13.
 */

public class ViewHabitHistoryActivityTest extends ActivityInstrumentationTestCase2<ViewHabitHistoryActivity> {
    private Solo solo;

    public ViewHabitHistoryActivityTest() {
        super(ViewHabitHistoryActivity.class);
    }

    protected void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testHabitHistory() throws Exception {
        Habit habit =  new Habit("Gym", "lose Weight", new Date());

        HabitEvent event = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space",
                null,null);
//        HabitHistoryController c = HabitHistoryController.getInstance();
        HabitHistoryController.getInstance().add(event);
        HabitHistoryController.getInstance().store();

        solo.assertCurrentActivity("Could not open AddNewHabitEventActivity", ViewHabitHistoryActivity.class);

        solo.clickOnButton("Add Event");
        solo.sleep(5000);
        solo.goBackToActivity("ViewHabitHistoryActivity");
        solo.sleep(5000);
        solo.clickInList(0);
        solo.sleep(5000);
        solo.goBackToActivity("ViewHabitHistoryActivity");
        solo.sleep(5000);
        HabitHistoryController control = HabitHistoryController.getInstance();
        HabitEvent habitE = control.get(control.size()-1);
        assertNotNull(habitE);
        HabitHistoryController.getInstance().remove(event);
        HabitHistoryController.getInstance().store();
        HabitListController c2 = HabitListController.getInstance();
        c2.deleteHabit(habit);
    }
}