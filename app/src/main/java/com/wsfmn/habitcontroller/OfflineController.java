package com.wsfmn.habitcontroller;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wsfmn.habit.HabitHistory;
import com.wsfmn.habit.HabitList;
import com.wsfmn.habittracker.App;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;



/**
 * Created by Fredric on 2017-10-16.
 * Updated with GSON by Nicholas on 2017-10-25.
 */

public class OfflineController {
    private static final String HABITLIST_FILENAME = "HabitList.sav";
    private static final String HABITHISTORY_FILENAME = "HabitHistory.sav";


    /**
     *
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
                // TODO Auto-generated catch block
                throw new RuntimeException();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                throw new RuntimeException();
            }
            return null;
        }
    }

    /**
     *
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
                // TODO Auto-generated catch block
                Log.d("FileNotfound", e.getMessage());
                habitList = new HabitList[1];
                habitList[0] = new HabitList();
            }
            return habitList[0];
        }
    }

    /**
     *
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
                // TODO Auto-generated catch block
                throw new RuntimeException();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                throw new RuntimeException();
            }
            return null;
        }
    }

    /**
     *
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
                // TODO Auto-generated catch block
                habitHistory = new HabitHistory[1];
            }
            return habitHistory[0];
        }
    }

}
