package com.wsfmn.habitcontroller;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;
import com.wsfmn.habit.ProfileName;
import com.wsfmn.habit.Request;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;


public class ProfileOnlineController {

    private static JestDroidClient client;

    public static class SendRequest extends AsyncTask<Request, Void, Void> {

        @Override
        protected Void doInBackground(Request... requests) {
            verifySettings();

            for (Request request : requests) {
                Index index = new Index.Builder(request).index("7f2m").type("request").build();

                try {
                    DocumentResult execute = client.execute(index);

                    if(execute.isSucceeded()) {
                        request.setId(execute.getId());

                    }
                    else
                    {
                        Log.i("Error", "Could not send Tweet");

                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the tweets");
                }

            }
            return null;
        }
    }



    public static class GetRequest extends AsyncTask<String, Void, ArrayList<Request>> {
        @Override
        protected ArrayList<Request> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Request> requests = new ArrayList<Request>();

            // TODO Build the query
            String query = "{\n" + " \"query\": { \"term\": {\"searchName\":\"" + search_parameters[0] + "\"} }\n" + "}";


            Search search = new Search.Builder(query)
                    .addIndex("7f2m")
                    .addType("request")
                    .build();

            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if (result.isSucceeded()){
                    List<Request> foundRequests = result.getSourceAsObjectList(Request.class);

                    requests.addAll(foundRequests);

                }
                else {
                    Log.i("Error", "The search query failed to find any tweets that matched");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return requests;
        }
    }

    public static class CheckUnique extends AsyncTask<String, Void, ProfileName> {
        @Override
        protected ProfileName doInBackground(String... search_parameters) {
            verifySettings();

            ProfileName flag = new ProfileName("failed");

            // TODO Build the query
            String query = "{\n" + " \"query\": { \"term\": {\"name\":\""+ search_parameters[0] + "\"} }\n" + "}";


            Search search = new Search.Builder(query)
                    .addIndex("7f2m")
                    .addType("profilename")
                    .build();

            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if (result.isSucceeded()){
                    try {
                       flag = result.getSourceAsObject(ProfileName.class);

                    }catch(Exception e){
                        Log.i("Error", "The search query failed to find any tweets that matched");
                    }
                }
                else {
                    Log.i("Error", "Could not send profilename");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return flag;
        }
    }

    public static class StoreNameInDataBase extends AsyncTask<ProfileName, Void, Void> {

        @Override
        protected Void doInBackground(ProfileName... names) {
            verifySettings();

            for (ProfileName profileName : names) {
                Index index = new Index.Builder(profileName).index("7f2m").type("profilename").build();

                try {
                    DocumentResult execute = client.execute(index);

                    if(execute.isSucceeded()) {
                        profileName.setId(execute.getId());

                    }
                    else
                    {
                        Log.i("Error", "Could not send ");

                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the tweets");
                }

            }
            return null;
        }
    }


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