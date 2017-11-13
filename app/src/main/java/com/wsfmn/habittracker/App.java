package com.wsfmn.habittracker;

import android.app.Application;
import android.content.Context;

/**
 * Created by nicholasmayne on 2017-10-25.
 * This class makes the app's Context available everywhere which is useful at least in OfflineController
 * This class gets run via AndroidManifest.
 * https://stackoverflow.com/questions/7144177/getting-the-application-context
 */

public class App extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }


}
