package com.wsfmn.habittracker.habittracker;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;

import com.robotium.solo.Solo;
import com.wsfmn.habit.Habit;
import com.wsfmn.habitcontroller.HabitListController;
import com.wsfmn.habittracker.AddNewHabitActivity;
import com.wsfmn.habittracker.HabitListViewActivity;
import com.wsfmn.habittracker.R;

/**
 * Created by musaed on 2017-11-06.
 */

public class AddNewHabitActivityTest extends ActivityInstrumentationTestCase2<AddNewHabitActivity> {

    private Solo solo;

    public AddNewHabitActivityTest() {
        super(com.wsfmn.habittracker.AddNewHabitActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void setUp() throws Exception {
        Log.d("TAG1", "setUp()");
        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void testConfirmButton(){
        AddNewHabitActivity activity = (AddNewHabitActivity) solo.getCurrentActivity();
        solo.assertCurrentActivity("Wrong Activity", AddNewHabitActivity.class);

        solo.enterText((EditText) solo.getView(R.id.habitTitleEditText), "Swimming");
        solo.clickOnButton("Confirm");

        solo.assertCurrentActivity("Wrong Activity", HabitListViewActivity.class);

        HabitListController c = HabitListController.getInstance();

        Habit habit = c.getHabit(c.size() - 1);
        assertEquals("Swimming", habit.getTitle());


    }
}
