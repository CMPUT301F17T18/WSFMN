package com.wsfmn.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wsfmn.controller.App;
import com.wsfmn.controller.HabitHistoryController;
import com.wsfmn.controller.HabitListController;
import com.wsfmn.controller.ProfileNameController;
import com.wsfmn.model.ProfileName;
import com.wsfmn.model.Request;
import com.wsfmn.model.RequestAdapter;
import com.wsfmn.model.RequestList;
import com.wsfmn.controller.OfflineController;
import com.wsfmn.controller.OnlineController;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;


/**
 * Represents Profiling Activity, Allowing the user to follow, share,
 * and view other friends' events
 *
 * @version 1.0
 * @see Activity
 */
public class ProfileActivity extends Activity {

    private static final String USER_FILENAME = "username.sav";
    private String profileName = "";
    private boolean flag = false;

    private TextView userName;
    private TextView yourName;
    private ListView requestsFromUser;
    private RequestList requestsList = new RequestList();
    RequestAdapter adapter = new RequestAdapter(requestsList, this);
    private OnlineController online = new OnlineController();
    private OfflineController offline = new OfflineController();

    /**
     * Creates variables and activities
     *
     * @param savedInstanceState saves the state of app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userName = (EditText) findViewById(R.id.userName);
        requestsFromUser = (ListView) findViewById(R.id.requestStuff);
        yourName = (TextView) findViewById(R.id.showName);

    }

    /**
     *  Button method to Share events with a user. Searching for user with textbox input
     * @param view
     */
    public void shareOnClick(View view){
        String text = userName.getText().toString().toLowerCase().replaceAll("\\s+","");
        Request newRequest = new Request(profileName, text, "share");
        flag = online.checkRequest(profileName, "share", text);
        if (flag == false){
            Toast.makeText(ProfileActivity.this, "Request Already Sent!",
                    Toast.LENGTH_LONG).show();
        }
        //requestsList.add(newRequest);
        else {
            OnlineController.SendRequest sendRequest = new OnlineController.SendRequest();
            sendRequest.execute(newRequest);
            adapter.notifyDataSetChanged();
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * Button method to Follow events from user. Searching for user with textbox input
     * @param view
     */
    public void followOnClick(View view){
        String text = userName.getText().toString().toLowerCase().replaceAll("\\s+","");
        Request newRequest = new Request(profileName, text, "follow");
        flag = online.checkRequest(profileName, "follow", text);
        if (flag == false){
            Toast.makeText(ProfileActivity.this, "Request Already Sent!",
                    Toast.LENGTH_LONG).show();
        }
        //requestsList.add(newRequest);
        else {
            OnlineController.SendRequest sendRequest = new OnlineController.SendRequest();
            sendRequest.execute(newRequest);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * Button method to go to FriendActivity view.
     * @param view
     */
    public void viewFriendEventOnClick(View view){
        Intent intent = new Intent(this, FriendActivity.class);
        startActivity(intent);
    }

    /**
     * Starts the Activity with certain conditions.
     * Checks if user is connected to internet, and if user has a profilename.
     */
    @Override
    protected void onStart() {
        super.onStart();

        // Check if the user is connected to the internet. Otherwise send back to MainActivity.
        if (online.isConnected() == false) {
            Toast.makeText(ProfileActivity.this, "Not connected to internet!",
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        // Update all habits and habit events under this username
        HabitListController.getInstance().storeAll();
        HabitHistoryController.getInstance().storeAll();

        //load the profilename if it exists
        profileName = ProfileNameController.getInstance().getProfileName();
        //offline.GetUserProfile;

        //If User does not have a ProfileName yet then have them create one.
        if (profileName == "" & flag == false){
            Intent intent = new Intent(this,UserName_Activity.class);
            flag = true;
            startActivityForResult(intent, 1);
            onActivityResult(1, 1, intent);
            flag = false;
        }
        // Set the name to your profilename.
        yourName.setText(profileName);

        // Get the Requests From ElasticSearch if there are any.
        OnlineController.GetRequest getRequest = new OnlineController.GetRequest();
        getRequest.execute(profileName);
        try{
            requestsList = getRequest.get();

        } catch (Exception e) {
            Log.i("Error", "Failed to get the requests from the async object");
        }

        //Update List with Requests
        RequestAdapter adapter = new RequestAdapter(requestsList, this);
        requestsFromUser.setAdapter(adapter);

    }

    /**
     * Method to retrieve results from another activity. Used for getting profilename from
     * UserName_Activity.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                profileName = data.getStringExtra("uniqueName");

                // Delete this commented code, it is now handled inside UserName_Activity
//                saveInFile();

                // Update all habits and habit events under this username
                HabitListController.getInstance().storeAll();
                HabitHistoryController.getInstance().storeAll();
            }
            else if(resultCode == Activity.RESULT_CANCELED) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }




    //////////////

    // Fredric, I commented these out, they are now handled using the ProfileNameController
    // please delete them if everything is running okay for you!
    //////////////
//
//    // Will remove later and replace with OfflineController's methods
//    private void loadFromFile() {
//        try {
//            FileInputStream fis = openFileInput(USER_FILENAME);
//            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
//
//            Gson gson = new Gson();
//
//            Type listType = new TypeToken<String>(){}.getType();
//            profileName = gson.fromJson(in, listType);
//
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            profileName = "";
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            throw new RuntimeException();
//        }
//    }

//    //Will remove later and replace with OfflineController's methods
//    private void saveInFile() {
//        try {
//            FileOutputStream fos = openFileOutput(USER_FILENAME,
//                    Context.MODE_PRIVATE);
//            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
//            Gson gson = new Gson();
//            gson.toJson(profileName, out);
//            out.flush();
//            fos.close();
//            //something fails it will catch.
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            throw new RuntimeException();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            throw new RuntimeException();
//        }
//    }



}