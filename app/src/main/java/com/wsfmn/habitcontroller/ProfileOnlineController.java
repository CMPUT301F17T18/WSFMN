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

import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.DeleteByQuery;



public class ProfileOnlineController {

    private static JestDroidClient client2;

    public static class SendRequest extends AsyncTask<Request, Void, Void> {

        @Override
        protected Void doInBackground(Request... requests) {
            verifySettings();

            for (Request request : requests) {
                Index index = new Index.Builder(request).index("7f2m").type("request").build();
                try {
                    DocumentResult execute = client2.execute(index);

                    if(execute.isSucceeded()) {
                        request.setId(execute.getId());
                    }
                    else
                    {
                        Log.i("Error", "Could not send request");

                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the requests");
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
                SearchResult result = client2.execute(search);
                if (result.isSucceeded()){
                    List<Request> foundRequests = result.getSourceAsObjectList(Request.class);

                    requests.addAll(foundRequests);

                }
                else {
                    Log.i("Error", "The search query failed to find any requests that matched");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return requests;
        }
    }


    public static class DeleteRequest extends AsyncTask<String, Void, ArrayList<Request>> {
        @Override
        protected ArrayList<Request> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Request> requests = new ArrayList<Request>();

            // TODO Build the query
            String query = "{\n" + " \"query\": { \"term\": {\"searchName\":\"" + search_parameters[0] + "\"} }\n" + "}";


            DeleteByQuery delete = new DeleteByQuery.Builder(query)
                    .addIndex("7f2m")
                    .addType("request")
                    .build();

            try {
                // TODO get the results of the query
                JestResult result = client2.execute(delete);
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return requests;
        }
    }

    public static class CheckUnique extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... search_parameters) {
            verifySettings();
            Boolean flag = false;
            // TODO Build the query
            String query = "{\n" + " \"query\": { \"term\": {\"name\":\""+ search_parameters[0] + "\"} }\n" + "}";
            Search search = new Search.Builder(query)
                    .addIndex("7f2m")
                    .addType("profilename")
                    .build();
            try {
                // TODO get the results of the query
                SearchResult result = client2.execute(search);
                if (result.isSucceeded()){
                    String JsonString = result.getJsonString();
                    for (SearchResult.Hit hit : result.getHits(ProfileName.class)) {
                        Log.d("Name Exisits:", "Name already in database");
                        return false;
                    }
                }
                return true;
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
                    DocumentResult result = client2.execute(index);
                    if(result.isSucceeded()) {
                        profileName.setId(result.getId());
                    }
                    else
                    {
                        Log.i("Error", "Could not send name to elasticsearch");

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
        if (client2 == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client2 = (JestDroidClient) factory.getObject();
        }
    }

}