/*
 * Copyright © 2017 Team 18 (WSFMN), CMPUT301, University of Alberta – All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact nmayne@ualberta.ca.
 *
 *  Team 18 is: Musaed Alsobaie, Siddhant Khanna, Wei Li, Nicholas Mayne, Fredric Mendi.
 */

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