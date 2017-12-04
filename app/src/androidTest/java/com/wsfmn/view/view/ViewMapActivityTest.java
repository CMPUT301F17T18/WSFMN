package com.wsfmn.view.view;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import com.wsfmn.view.ViewHabitListActivity;
import com.wsfmn.view.ViewMapActivity;

/**
 * Created by ${WeiLi5} on ${12}.
 */

public class ViewMapActivityTest extends ActivityInstrumentationTestCase2<ViewMapActivity> {
    private Solo solo;

    public ViewMapActivityTest(){
        super(ViewMapActivity.class);
    }

    protected void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testMyEventsButton(){
        solo.assertCurrentActivity("Wrong Activity", ViewMapActivity.class);
        solo.clickOnButton("User's habit events");
    }

    public void testFriendsEventButton(){
        solo.assertCurrentActivity("Wrong Activity", ViewMapActivity.class);
        solo.clickOnButton("Friends' habit events");
    }

    public void testNearMeButton(){
        solo.assertCurrentActivity("Wrong Activity", ViewMapActivity.class);
        solo.clickOnButton("Show nearby habit events");
    }


}