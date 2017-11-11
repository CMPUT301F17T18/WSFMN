package com.wsfmn.habitcontroller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;
import com.wsfmn.habit.Date;
import com.wsfmn.habit.Habit;
import com.wsfmn.habit.HabitEvent;
import com.wsfmn.habit.HabitHistory;
import com.wsfmn.habit.HabitList;
import com.wsfmn.habittracker.App;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;



/**
 * A controller for managing Habit and HabitEvent model data stored on an ElasticSearch server.
 */
public class OnlineController {
    private static final String SERVER_URL = "http://cmput301.softwareprocess.es:8080";
    private static final String INDEX_BASE = "team18_";
    private static final String ID_TAG = "_id";
    private static final int ID_TAG_OFFSET = 6;
    private static final int ID_LENGTH = 20;
    private static String USERNAME = "";    // need to get the username form the ProfileController
    private static JestDroidClient client;

    /**
     * When StoreHabits.execute(Habit... habits) is called on a StoreHabits object,
     * this method will proceed if the device is connected to the internet and will store the
     * given habits on an ElasticSearch DB.
     *
     * If the device is not connected to the internet this method fails silently.
     *
     * When each Habit is stored, an ElasticSearch ID is returned and the local Habit ID attribute
     * is updated with this value. This ID is used to update and delete the remote copy of each
     * Habit passed to StoreHabits.execute(Habit... habits).
     *
     * Created by romansky on 10/20/16. Customized by nmayne 10/22/17.
     */
    public static class StoreHabits extends AsyncTask<Habit, Void, Void> {

        @Override
        protected Void doInBackground(Habit... habits) {
            if (isConnected()) {
                verifySettings();
                for (Habit habit : habits) {
                    Index index;
                    // If the habit has been stored already it will have a non-null ID,
                    // in this case the Index command will update the existing habit at ID
                    // otherwise Index will store a new habit and ElasticSearch will return
                    // the auto-generated ID which is then attributed to habit for future use.
                    if (habit.getId() != null) {
                        index = new Index.Builder(habit)
                                .index(INDEX_BASE + USERNAME)
                                .type("habit")
                                .id(habit.getId())
                                .build();
                    } else {
                        index = new Index.Builder(habit)
                                .index(INDEX_BASE + USERNAME)
                                .type("habit")
                                .build();
                    }

                    try {
                        DocumentResult result = client.execute(index);
                        if (result.isSucceeded())
                            habit.setId(result.getId().toString());
                        else
                            Log.i("Error", "Elasticsearch was not able to add the habit");
                    } catch (Exception e) {
                        Log.i("Error", "Habit Tracker failed to build and send the habits");
                    }
                }
            }
            return null;
        }
    }

    /**
     * When DeleteHabits.execute(Habit... habits) is called on a DeleteHabits object,
     * this method will proceed if the device is connected to the internet and will delete the
     * given habits on an ElasticSearch DB.
     *
     * If the device is not connected to the internet this method fails silently.
     *
     * The delete functionality depends upon there being a non-null id for the habit being deleted.
     * If a habit has not yet been stored on ElasticSearch then it's ID will be null and nothing
     * will happen.
     *
     * Created by nmayne 11/07/17.
     */
    public static class DeleteHabits extends AsyncTask<Habit, Void, Void> {
        @Override
        protected Void doInBackground(Habit... habits) {
            if (isConnected()) {
                verifySettings();
                for (Habit h : habits) {
                    Delete delete = new Delete.Builder(h.getId())
                            .index(INDEX_BASE + USERNAME)
                            .type("habit")
                            .build();
                    try {
                        client.execute(delete);
                    } catch (IOException e) {
                        Log.i("Error", "Delete Habit failed");
                    }
                }
            }
            return null;
        }
    }

    /**
     * When GetHabits.execute(String... search_params) is called on a GetHabits object, this method
     * will proceed if the device is connected to the internet and currently will return a
     * HabitList object containing at most 10 Habit objects that match the search parameter.
     *
     * If the device is not connected to the internet this method fails silently and returns null
     *
     * Created by romansky on 10/20/16. Customized by nmayne 10/22/17.
     */
    public static class GetHabits extends AsyncTask<String, Void, HabitList> {
        @Override
        protected HabitList doInBackground(String... search_parameters) {
            HabitList habitList = null;

            if (isConnected()) {
                verifySettings();
                habitList = new HabitList();

                String query = "{ \"query\": { \"term\": { \"title\": \""
                        + search_parameters[0] + "\" } } }\n";

                Search search = new Search.Builder(query)
                        .addIndex(INDEX_BASE + USERNAME)
                        .addType("habit")
                        .build();
                try {
                    SearchResult result = client.execute(search);
                    if (result.isSucceeded()) {
                        int idx = 0;
                        String JsonString = result.getJsonString();
                        for (SearchResult.Hit hit : result.getHits(Habit.class)) {
                            Habit habit = (Habit) hit.source;
                            idx = JsonString.indexOf(ID_TAG, idx) + ID_TAG_OFFSET;
                            habit.setId(JsonString.substring(idx, idx + ID_LENGTH));
                            habitList.addHabit(habit);
                        }
                    } else {
                        Log.i("Error", "The search query failed");
                    }
                } catch (Exception e) {
                    Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                }
            }
            return habitList;
        }
    }

    /**
     * When StoreHabitEvents.execute(HabitEvent... habitEvents) is called on a StoreHabitEvents
     * object, this method will proceed if the device is connected to the internet and will store
     * the given habitEvents on an ElasticSearch DB.
     *
     * If the device is not connected to the internet this method fails silently.
     *
     * When each HabitEvent is stored, an ElasticSearch ID is returned and the local HabitEvent ID
     * attribute is updated with this value. This ID is used to update and delete the remote copy of each Habit
     * passed to StoreHabits.execute(Habit... habits).
     * Created by romansky on 10/20/16. Customized by nmayne 11/08/17.
     */
    public static class StoreHabitEvents extends AsyncTask<HabitEvent, Void, Void> {
        @Override
        protected Void doInBackground(HabitEvent... habitEvents) {
            if (isConnected()) {
                verifySettings();
                for (HabitEvent he : habitEvents) {
                    Index index;
                    // If the HabitEvent has been stored already it will have a non-null ID,
                    // in this case the Index command will update the existing HabitEvent at ID
                    // otherwise Index will store a new HabitEvent and ElasticSearch will return
                    // the auto-generated ID which is then attributed to HabitEvent for future use.
                    if (he.getId() != null) {
                        index = new Index.Builder(he)
                                .index(INDEX_BASE + USERNAME)
                                .type("habitevent")
                                .id(he.getId())
                                .build();
                    } else {
                        index = new Index.Builder(he)
                                .index(INDEX_BASE + USERNAME)
                                .type("habitevent")
                                .build();
                    }
                    try {
                        // where is the client?
                        DocumentResult result = client.execute(index);
                        if (result.isSucceeded())
                            he.setId(result.getId().toString());
                        else
                            Log.i("Error", "Elasticsearch was not able to add the habit events");
                    } catch (Exception e) {
                        Log.i("Error", "Habit Tracker failed to build and send the habit events");
                    }
                }
            }
            return null;
        }
    }

    /**
     * When GetHabits.execute(String... search_params) is called on a GetHabits object, this method
     * will proceed if the device is connected to the internet and currently will return a
     * HabitList object containing at most 10 Habit objects that match the search parameter.
     *
     * If the device is not connected to the internet this method fails silently and returns null
     *
     * Created by nmayne 11/07/17.
     */
    public static class DeleteHabitEvents extends AsyncTask<HabitEvent, Void, Void> {
        @Override
        protected Void doInBackground(HabitEvent... habitEvents) {
            if (isConnected()) {
                verifySettings();
                for (HabitEvent he : habitEvents) {
                    Delete delete = new Delete.Builder(he.getId())
                            .index(INDEX_BASE + USERNAME)
                            .type("habitevent")
                            .build();
                    try {
                        client.execute(delete);
                    } catch (IOException e) {
                        Log.i("Error", "Delete Habit Event failed");
                    }
                }
            }
            return null;
        }
    }

    /**
     * Created by romansky on 10/20/16. Customized by nmayne 11/08/17.
     */
    public static class GetHabitEvents extends AsyncTask<String, Void, HabitHistory> {
        @Override
        protected HabitHistory doInBackground(String... search_parameters) {
            HabitHistory habitHistory = null;

            if (isConnected()) {
                verifySettings();

                habitHistory = new HabitHistory();

                String query = "{ \"query\": { \"term\": { \"comment\": \""
                        + search_parameters[0] + "\" } } }\n";

                Search search = new Search.Builder(query)
                        .addIndex(INDEX_BASE + USERNAME)
                        .addType("habitevent")
                        .build();
                try {
                    SearchResult result = client.execute(search);
                    if (result.isSucceeded()) {
                        int idx = 0;
                        String JsonString = result.getJsonString();
                        for (SearchResult.Hit hit : result.getHits(HabitEvent.class)) {
                            HabitEvent he = (HabitEvent) hit.source;
                            idx = JsonString.indexOf(ID_TAG, idx) + ID_TAG_OFFSET;
                            he.setId(JsonString.substring(idx, idx + ID_LENGTH));
                            habitHistory.add(he);
                            Log.d("GotHabit:", he.getComment());
                        }
                    }

                    if (result.isSucceeded()) {
                        List<HabitEvent> foundHabitEvents = result.getSourceAsObjectList(HabitEvent.class);
                        habitHistory.addAllHabitEvents(foundHabitEvents);
                    } else {
                        Log.i("Error", "The search query failed");
                    }
                } catch (Exception e) {
                    Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                }
            }
            return habitHistory;
        }
    }



    /**
     * Created by romansky on 10/20/16. Customized by nmayne 10/22/17.
     */
    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder(SERVER_URL);
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }

    /**
     * Check if the device is connected to the internet via wifi or mobile.
     * https://stackoverflow.com/questions/5474089/how-to-check-currently-internet-connection-is-available-or-not-in-android
     * @return Boolean: true is connection is alive, otherwise false
     */
    @NonNull
    public static Boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) App.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        }
        else
            return false;
    }
}