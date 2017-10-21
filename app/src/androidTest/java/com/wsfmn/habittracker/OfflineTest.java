package com.wsfmn.habittracker;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Fredric on 2017-10-21.
 */

public class OfflineTest extends ActivityInstrumentationTestCase2 {

    public OfflineTest() {
        super(MainActivity.class);
    }

    public void testgetLocal(){
        final String FILENAME = "file.sav";
    }

    public void teststoreLocal(){
        final String FILENAME = "file.sav";

    }
}
