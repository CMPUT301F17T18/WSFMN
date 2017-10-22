package com.wsfmn.habittracker;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by Fredric on 2017-10-21.
 */

public class OfflineTest extends ActivityInstrumentationTestCase2 {

    public OfflineTest() {
        super(Offline.class);
    }


    // hard to test needs to already have something stored in Local
    public void testgetLocal(){
        // practice test for a habit events list.
        HabitHistory habitEvent = new HabitHistory();
        Offline offline = new Offline();

    }

    public void teststoreLocal(){
        final String FILENAME = "file.sav";

    }
}
