package com.wsfmn.habitcontroller;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;
import com.wsfmn.habit.Date;
import com.wsfmn.habit.Habit;
import com.wsfmn.habit.HabitEvent;
import com.wsfmn.habit.HabitHistory;
import com.wsfmn.habit.HabitList;

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
 * Created by nicholasmayne on 2017-10-16.
 */


public class OnlineController {
    private static final String INDEX_BASE = "team18";
    private static final int ID_LENGTH = 20;
    private static final int ID_TAG_OFFSET = 6;
    private static final String ID_TAG = "_id";
    private static String USERNAME = "";
    private static JestDroidClient client;

    /**
     * Created by romansky on 10/20/16. Customized by nmayne 10/22/17.
     */
    public static class StoreHabits extends AsyncTask<Habit, Void, Void> {

        @Override
        protected Void doInBackground(Habit... habits) {
            verifySettings();

            for (Habit habit : habits) {
                Index index;
                // If the habit has been stored already it will have a non-null ID,
                // in this case the Index command will update the existing habit at ID
                // otherwise Index will store a new habit and ElasticSearch will return
                // the auto-generated ID which is then attributed to habit for future use.
                if (habit.getId() != null) {
                    index = new Index.Builder(habit)
                            .index(INDEX_BASE+USERNAME)
                            .type("habit")
                            .id(habit.getId())
                            .build();
                } else {
                    index = new Index.Builder(habit)
                            .index(INDEX_BASE+USERNAME)
                            .type("habit")
                            .build();
                }

                try {
                    DocumentResult result = client.execute(index);
                    if(result.isSucceeded())
                        habit.setId(result.getId().toString());
                    else
                        Log.i("Error", "Elasticsearch was not able to add the habit");
                }
                catch (Exception e) {
                    Log.i("Error", "Habit Tracker failed to build and send the habits");
                }

            }
            return null;
        }
    }

    /**
     * Created by nmayne 11/07/17.
     */
    public static class DeleteHabits extends AsyncTask<Habit, Void, Void> {
        @Override
        protected Void doInBackground(Habit... habits) {
            verifySettings();
            for (Habit h : habits) {
                Delete delete = new Delete.Builder(h.getId())
                        .index(INDEX_BASE+USERNAME)
                        .type("habit")
                        .build();
                try {
                    client.execute(delete);
                } catch (IOException e) {
                    Log.i("Error", "Delete Habit failed");
                }
            }
            return null;
        }
    }

    /**
     * Created by romansky on 10/20/16. Customized by nmayne 10/22/17.
     */
    public static class GetHabits extends AsyncTask<String, Void, HabitList> {
        @Override
        protected HabitList doInBackground(String... search_parameters) {
            verifySettings();
            HabitList habitList = new HabitList();

            String query = "{ \"query\": { \"term\": { \"title\": \""
                    + search_parameters[0] + "\" } } }\n";

            Search search = new Search.Builder(query)
                    .addIndex(INDEX_BASE+USERNAME)
                    .addType("habit")
                    .build();
            try {
                SearchResult result = client.execute(search);
                if(result.isSucceeded())
                {
                    int idx = 0;
                    String JsonString = result.getJsonString();
                    for (SearchResult.Hit hit : result.getHits(Habit.class)) {
                        Habit habit = (Habit) hit.source;
                        idx = JsonString.indexOf(ID_TAG, idx) + ID_TAG_OFFSET;
                        habit.setId(JsonString.substring(idx, idx+ID_LENGTH));
                        habitList.addHabit(habit);
                    }
                }
                else
                {
                    Log.i("Error","The search query failed");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return habitList;
        }
    }

    /**
     * Created by romansky on 10/20/16. Customized by nmayne 11/08/17.
     */
    public static class StoreHabitEvents extends AsyncTask<HabitEvent, Void, Void> {
        @Override
        protected Void doInBackground(HabitEvent... habitEvents) {
            verifySettings();
            for (HabitEvent he : habitEvents) {
                Index index;
                // If the HabitEvent has been stored already it will have a non-null ID,
                // in this case the Index command will update the existing HabitEvent at ID
                // otherwise Index will store a new HabitEvent and ElasticSearch will return
                // the auto-generated ID which is then attributed to HabitEvent for future use.
                if (he.getId() != null) {
                    index = new Index.Builder(he)
                            .index(INDEX_BASE+USERNAME)
                            .type("habitevent")
                            .id(he.getId())
                            .build();
                } else {
                    index = new Index.Builder(he)
                            .index(INDEX_BASE+USERNAME)
                            .type("habitevent")
                            .build();
                }
                try {
                    // where is the client?
                    DocumentResult result = client.execute(index);
                    if(result.isSucceeded())
                        he.setId(result.getId().toString());
                    else
                        Log.i("Error", "Elasticsearch was not able to add the habit events");
                }
                catch (Exception e) {
                    Log.i("Error", "Habit Tracker failed to build and send the habit events");
                }
            }
            return null;
        }
    }

    /**
     * Created by nmayne 11/07/17.
     */
    public static class DeleteHabitEvents extends AsyncTask<HabitEvent, Void, Void> {
        @Override
        protected Void doInBackground(HabitEvent... habitEvents) {
            verifySettings();
            for (HabitEvent he : habitEvents) {
                Delete delete = new Delete.Builder(he.getId())
                        .index(INDEX_BASE+USERNAME)
                        .type("habitevent")
                        .build();
                try {
                    client.execute(delete);
                } catch (IOException e) {
                    Log.i("Error", "Delete Habit Event failed");
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
            verifySettings();

            HabitHistory habitHistory = new HabitHistory();

            String query = "{ \"query\": { \"term\": { \"comment\": \""
                    + search_parameters[0] + "\" } } }\n";

            Search search = new Search.Builder(query)
                    .addIndex(INDEX_BASE+USERNAME)
                    .addType("habitevent")
                    .build();
            try {
                SearchResult result = client.execute(search);
                if(result.isSucceeded())
                {
                    int idx = 0;
                    String JsonString = result.getJsonString();
                    for (SearchResult.Hit hit : result.getHits(HabitEvent.class)) {
                        HabitEvent he = (HabitEvent) hit.source;
                        idx = JsonString.indexOf(ID_TAG, idx) + ID_TAG_OFFSET;
                        he.setId(JsonString.substring(idx, idx+ID_LENGTH));
                        habitHistory.add(he);
                        Log.d("GotHabit:", he.getComment());
                    }
                }

                if(result.isSucceeded())
                {
                    List<HabitEvent> foundHabitEvents = result.getSourceAsObjectList(HabitEvent.class);
                    habitHistory.addAllHabitEvents(foundHabitEvents);
                }
                else
                {
                    Log.i("Error","The search query failed");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return habitHistory;
        }
    }

    /**
     * Created by romansky on 10/20/16. Customized by nmayne 10/22/17.
     */
    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }

}