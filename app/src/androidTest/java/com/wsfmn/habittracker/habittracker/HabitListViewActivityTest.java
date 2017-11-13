package com.wsfmn.habittracker.habittracker;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.robotium.solo.Solo;
import com.wsfmn.habit.Date;
import com.wsfmn.habit.DateNotValidException;
import com.wsfmn.habit.Habit;
import com.wsfmn.habit.HabitTitleTooLongException;
import com.wsfmn.habitcontroller.HabitListController;
import com.wsfmn.habittracker.AddNewHabitActivity;
import com.wsfmn.habittracker.HabitListViewActivity;
import com.wsfmn.habittracker.HabitListViewDetailActivity;
import com.wsfmn.habittracker.R;

/**
 * Created by musaed on 2017-11-06.
 */

public class HabitListViewActivityTest extends ActivityInstrumentationTestCase2<HabitListViewActivity> {

    private Solo solo;

    public HabitListViewActivityTest() {
        super(com.wsfmn.habittracker.HabitListViewActivity.class);

    }

    public void setUp() throws Exception {
        Log.d("TAG1", "setUp()");
        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void testAddHabitButton(){
        HabitListViewActivity activity = (HabitListViewActivity) solo.getCurrentActivity();
        solo.assertCurrentActivity("Wrong Activity", HabitListViewActivity.class);

        solo.clickOnButton("Add Habit");
        solo.assertCurrentActivity("Wrong Activity", AddNewHabitActivity.class);

        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", HabitListViewActivity.class);
    }

    public void testClickHabit(){
        HabitListController c = HabitListController.getInstance();
        Habit habit = null;

        try{
            habit = new Habit("title", new Date());
        }
        catch(HabitTitleTooLongException e){
            //null
        }
        catch(DateNotValidException e){
            //null
        }


        solo.assertCurrentActivity("Wrong Activity", HabitListViewActivity.class);

        solo.clickOnButton("Add Habit");
        solo.sleep(5000);

        solo.goBackToActivity("HabitListViewActivity");

        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", HabitListViewActivity.class);

        solo.goBackToActivity("HabitListViewActivity");
    }

}
