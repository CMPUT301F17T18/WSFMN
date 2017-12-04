package com.wsfmn.controller;

import android.app.Application;
import android.content.Context;

/**
 * This class makes the app's CONTEXT available globally, used in OfflineController.
 * This class also makes the USERNAME available globally, via App.USERNAME.
 *
 * This class gets run via AndroidManifest.
 * https://stackoverflow.com/questions/7144177/getting-the-application-context
 */
public class App extends Application {

    public static Context CONTEXT;
    public static String USERNAME = "";

    /**
     * Set the CONTEXT and USERNAME upon creation.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = getApplicationContext();
        USERNAME = ProfileNameController.getInstance().getProfileName();
    }

    /**
     * Ensure that USERNAME is up-to-date with the locally saved ProfileName.
     */
    public static void reinitialize() {
        USERNAME = ProfileNameController.getInstance().getProfileName();
    }
}