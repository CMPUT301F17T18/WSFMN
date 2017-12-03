package com.wsfmn.controller;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wsfmn.model.HabitHistory;
import com.wsfmn.model.HabitList;
import com.wsfmn.model.ProfileName;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.concurrent.ExecutionException;


/**
 * Created by Fredric on 2017-10-16.
 * Updated with GSON by nmayne on 2017-10-25.
 * Added Profile methods, nmayne 201-11-10.
 */

public class OfflineController {
    private static final String PROFILE_FILENAME = "Profile.sav";
    private static final String HABITLIST_FILENAME = "HabitList.sav";
    private static final String HABITHISTORY_FILENAME = "HabitHistory.sav";
    private static final String DELETED_FILENAME = "Deleted.sav";

    /**
     * Store Habit list locally to HabitList.sav
     */
    public static class StoreHabitList extends AsyncTask<HabitList, Void, Void> {

        @Override
        protected Void doInBackground(HabitList... habitList) {
            try {
                FileOutputStream fos = App.CONTEXT.openFileOutput(HABITLIST_FILENAME, 0);
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
            HabitList[] habitList;
            try {
                FileInputStream fis = App.CONTEXT.openFileInput(HABITLIST_FILENAME);
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
            try {
                FileOutputStream fos = App.CONTEXT.openFileOutput(HABITHISTORY_FILENAME, 0);
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
            HabitHistory[] habitHistory;
            try {
                FileInputStream fis = App.CONTEXT.openFileInput(HABITHISTORY_FILENAME);
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
            try {
                FileOutputStream fos = App.CONTEXT.openFileOutput(PROFILE_FILENAME, 0);
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
    public static class GetUserProfile extends AsyncTask<Void, Void, ProfileName> {

        @Override
        protected ProfileName doInBackground(Void... params) {
            ProfileName[] profile;
            try {
                FileInputStream fis = App.CONTEXT.openFileInput(PROFILE_FILENAME);
                BufferedReader in = new BufferedReader(new InputStreamReader(fis));
                Gson gson = new Gson();
                profile = gson.fromJson(in, ProfileName[].class);
            } catch (FileNotFoundException e) {
                profile = new ProfileName[1];
                profile[0] = new ProfileName();
            }
            return profile[0];
        }
    }

    /**
     * Store deleted object ids for online synchronization in Deleted.sav
     */
    public static class StoreDeleted extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... delete) {
            try {
                String[] d = delete;
                FileOutputStream fos = App.CONTEXT.openFileOutput(DELETED_FILENAME, Context.MODE_APPEND);
                OutputStreamWriter writer = new OutputStreamWriter(fos);
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                builder.serializeNulls();
                gson.toJson(d, writer);
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
     * Getting the user's deleted elements from Deleted.sav
     */
    public static class GetDeleted extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            String[] deleted = new String[0];
            try {
                FileInputStream fis = App.CONTEXT.openFileInput(DELETED_FILENAME);
                BufferedReader in = new BufferedReader(new InputStreamReader(fis));
                Gson gson = new Gson();
                deleted = gson.fromJson(in, String[].class);
            } catch (FileNotFoundException e) {
                Log.i("OfflineController","Created " + DELETED_FILENAME);
            }
            return deleted;
        }
    }

    /**
     * Reset Deleted.sav
     */
    public static class ResetDeleted extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                FileOutputStream fos = App.CONTEXT.openFileOutput(DELETED_FILENAME, 0);
                fos.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException();
            } catch (IOException e) {
                throw new RuntimeException();
            }
            return null;
        }
    }
}