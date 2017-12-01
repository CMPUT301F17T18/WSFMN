package com.wsfmn.view.view;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.robotium.solo.Solo;
import com.wsfmn.model.Date;
import com.wsfmn.exceptions.DateNotValidException;
import com.wsfmn.model.Habit;
import com.wsfmn.exceptions.HabitTitleTooLongException;
import com.wsfmn.controller.HabitListController;
import com.wsfmn.view.AddNewHabitActivity;
import com.wsfmn.view.ViewHabitListActivity;

/**
 * Created by musaed on 2017-11-06.
 */

public class ViewHabitListActivityTest extends ActivityInstrumentationTestCase2<ViewHabitListActivity> {

    private Solo solo;

    public ViewHabitListActivityTest() {
        super(ViewHabitListActivity.class);

    }

    public void setUp() throws Exception {
        Log.d("TAG1", "setUp()");
        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void testAddHabitButton(){
        ViewHabitListActivity activity = (ViewHabitListActivity) solo.getCurrentActivity();
        solo.assertCurrentActivity("Wrong Activity", ViewHabitListActivity.class);

        solo.clickOnButton("Add Habit");
        solo.assertCurrentActivity("Wrong Activity", AddNewHabitActivity.class);

        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", ViewHabitListActivity.class);
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


        solo.assertCurrentActivity("Wrong Activity", ViewHabitListActivity.class);

        solo.clickOnButton("Add Habit");
        solo.sleep(5000);

        solo.goBackToActivity("ViewHabitListActivity");

        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", ViewHabitListActivity.class);

        solo.goBackToActivity("ViewHabitListActivity");
    }

}
