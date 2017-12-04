package com.wsfmn.view.view;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;
import com.wsfmn.model.Date;
import com.wsfmn.model.Habit;
import com.wsfmn.model.HabitEvent;
import com.wsfmn.controller.HabitHistoryController;
import com.wsfmn.controller.HabitListController;
import com.wsfmn.view.HabitHistoryDetailActivity;
import com.wsfmn.view.R;

/**
 * Created by siddhant on 2017-11-13.
 */

public class HabitHistoryDetailActivityTest extends ActivityInstrumentationTestCase2<HabitHistoryDetailActivity> {
    private Solo solo;

    public HabitHistoryDetailActivityTest(){
        super(HabitHistoryDetailActivity.class);}

    protected void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }


    public void testAddHabitEvent() throws Exception {

        Habit habit =  new Habit("Gym", "lose Weight", new Date());
        HabitListController c = HabitListController.getInstance();
        c.addHabit(habit);
        c.store();
        HabitEvent event = new HabitEvent(habit, "HabitEvent", "Comment", "/Storage/Space",
                null,null);
        HabitHistoryController c2 = HabitHistoryController.getInstance();
        c2.add(event);
        c2.store();

        solo.assertCurrentActivity("Could not open HabitEventDetail", HabitHistoryDetailActivity.class);
        //  Needs the right UI element ID.
        //solo.clearEditText((EditText) solo.getView(R.id.nameEvent2));
        //solo.enterText((EditText) solo.getView(R.id.nameEvent2), "Swimming Competition");
        solo.clickOnButton("Change Habit");
        solo.clickInList(0);
        solo.clearEditText((EditText)solo.getView(R.id.hd_editComment));
        solo.enterText((EditText)solo.getView(R.id.hd_editComment), "100 m");

        solo.clickOnButton("Confirm");
        HabitHistoryController control = HabitHistoryController.getInstance();
        HabitEvent habitE = control.get(0);
        solo.sleep(5000);
        assertEquals("Habit Event not Modified", "Swimming Competition", habitE.getHabitEventTitle());

        final int size = control.size();
        solo.clickInList(0);
        solo.clickOnButton("DELETE");
        solo.sleep(5000);
        int size2 = control.size();
        assertEquals("Delete Habit Event did not occur", size-1, size2);
        c.deleteHabit(habit);
        c.store();
    }
}