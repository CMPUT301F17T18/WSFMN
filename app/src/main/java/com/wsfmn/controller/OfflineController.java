package com.wsfmn.controller;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wsfmn.model.HabitHistory;
import com.wsfmn.model.HabitList;
import com.wsfmn.model.Profile;
import com.wsfmn.model.ProfileName;
import com.wsfmn.view.App;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;



/**
 * Created by Fredric on 2017-10-16.
 * Updated with GSON by nmayne on 2017-10-25.
 * Added Profile methods, nmayne 201-11-10.
 */

public class OfflineController {
    private static final String PROFILE_FILENAME = "Profile.sav";
    private static final String HABITLIST_FILENAME = "HabitList.sav";
    private static final String HABITHISTORY_FILENAME = "HabitHistory.sav";


    /**
     * Store Habit list locally to HabitList.sav
     */
    public static class StoreHabitList extends AsyncTask<HabitList, Void, Void> {

        @Override
        protected Void doInBackground(HabitList... habitList) {
            Context context = App.context;
            try {
                FileOutputStream fos = context.openFileOutput(HABITLIST_FILENAME, 0);
                OutputStreamWriter writer = new OutputStreamWriter(fos);
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                builder.serializeNulls();
                gson.toJson(habitList, writer);
                writer.flush();
            } catch (FileNotFoundException e) {
                throw new RuntimeException();
            } catch (IOException e) {
                throw new RuntimeException();
            }
            return null;
        }
    }

    /**
     * Retrieve the Habit list Locally from HabitList.sav
     */
    public static class GetHabitList extends AsyncTask<Void, Void, HabitList> {

        @Override
        protected HabitList doInBackground(Void... params) {
            Context context = App.context;
            HabitList[] habitList;
            try {
                FileInputStream fis = context.openFileInput(HABITLIST_FILENAME);
                BufferedReader in = new BufferedReader(new InputStreamReader(fis));
                Gson gson = new Gson();
                habitList = gson.fromJson(in, HabitList[].class);
            } catch (FileNotFoundException e) {
                Log.d("FileNotfound", e.getMessage());
                habitList = new HabitList[1];
                habitList[0] = new HabitList();
            }
            return habitList[0];
        }
    }

    /**
     * Storing habit events from Habit History to HabitHistory.sav
     */
    public static class StoreHabitHistory extends AsyncTask<HabitHistory, Void, Void> {

        @Override
        protected Void doInBackground(HabitHistory... habitHistory) {
            Context context = App.context;
            try {
                FileOutputStream fos = context.openFileOutput(HABITHISTORY_FILENAME, 0);
                OutputStreamWriter writer = new OutputStreamWriter(fos);
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                builder.serializeNulls();
                gson.toJson(habitHistory, writer);
                writer.flush();
            } catch (FileNotFoundException e) {
                throw new RuntimeException();
            } catch (IOException e) {
                throw new RuntimeException();
            }
            return null;
        }
    }

    /**
     * Getting Habit events for Habit History in HabitHistory.sav
     */
    public static class GetHabitHistory extends AsyncTask<Void, Void, HabitHistory> {

        @Override
        protected HabitHistory doInBackground(Void... params) {
            Context context = App.context;
            HabitHistory[] habitHistory;
            try {
                FileInputStream fis = context.openFileInput(HABITHISTORY_FILENAME);
                BufferedReader in = new BufferedReader(new InputStreamReader(fis));
                Gson gson = new Gson();
                habitHistory = gson.fromJson(in, HabitHistory[].class);
            } catch (FileNotFoundException e) {
                habitHistory = new HabitHistory[1];
                habitHistory[0] = new HabitHistory();

            }
            return habitHistory[0];
        }
    }

    /**
     * Storing the user's profilename locally in Profile.sav
     */
    public static class StoreUserProfile extends AsyncTask<ProfileName, Void, Void> {
        @Override
        protected Void doInBackground(ProfileName... profilename) {
            Context context = App.context;
            try {
                FileOutputStream fos = context.openFileOutput(PROFILE_FILENAME, 0);
                OutputStreamWriter writer = new OutputStreamWriter(fos);
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                builder.serializeNulls();
                gson.toJson(profilename, writer);
                writer.flush();
            } catch (FileNotFoundException e) {
                throw new RuntimeException();
            } catch (IOException e) {
                throw new RuntimeException();
            }
            return null;
        }
    }

    /**
     * Getting the user's profilename from Profile.sav
     */
    public static class GetUserProfile extends AsyncTask<Void, Void, Profile> {

        @Override
        protected Profile doInBackground(Void... params) {
            Context context = App.context;
            Profile[] profile;
            try {
                FileInputStream fis = context.openFileInput(PROFILE_FILENAME);
                BufferedReader in = new BufferedReader(new InputStreamReader(fis));
                Gson gson = new Gson();
                profile = gson.fromJson(in, Profile[].class);
            } catch (FileNotFoundException e) {
                profile = new Profile[1];
                profile[0] = new Profile();

            }
            return profile[0];
        }
    }
}
