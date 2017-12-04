package com.wsfmn.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wsfmn.controller.App;
import com.wsfmn.model.FriendAdapter;
import com.wsfmn.model.Habit;
import com.wsfmn.model.ProfileName;
import com.wsfmn.model.RequestAdapter;
import com.wsfmn.controller.OnlineController;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * Activity to allow user to see habits and events of followed users.
 *
 * @version 1.0
 * @see Activity
 */
public class FriendActivity extends Activity {

    private Habit selected;            // have a selected habit from the list of friend habits.
    private FriendAdapter adapter;     // adapter for the list to show certain fields.
    ArrayList<String> namesFriends = new ArrayList<String>(); // have a Array list of profilename
    String[] namesFriendsList;                           // List of friend names.

    ArrayList<Habit> names = new ArrayList<Habit>();       // Array list of Habits.

    private ListView fEventList;                        // ListView for Habits.

    private OnlineController online = new OnlineController(); // Onlinecontroller for elastic search.

    /**
     * Starts the Activity with certain conditions.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        fEventList = (ListView)findViewById(R.id.fEvent);       //set the listview


        // When clicking on a habit go view it's habit details and most recent habit event.
        fEventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = (Habit) fEventList.getItemAtPosition(position);

                Intent intent = new Intent(FriendActivity.this, FriendHabitActivity.class);
                intent.putExtra("friend", (Serializable) selected);
                startActivity(intent);
            }
        });
    }

    /**
     * Check if the app is online.
     */
    protected void onStart() {
        super.onStart();
        // Is online? if not go back to mainactivity.
        if (online.isConnected() == true) {
        } else {
            Toast.makeText(FriendActivity.this, "Not connected to internet!",
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        // Online controller to get the name of friends
        OnlineController.GetFriendNames getFriendEvents = new OnlineController.GetFriendNames();
        getFriendEvents.execute();
        try {
            namesFriends = getFriendEvents.get(); // Get profilenames
            namesFriendsList = namesFriends.toArray(new String[namesFriends.size()]); // convert to a list of names.
        } catch (Exception e) {
            Log.i("Error", "Failed to get the requests from the async object");
        }

        // Online controller to get the Habits.
        OnlineController.GetHabitNames getHabitNames = new OnlineController.GetHabitNames();
        getHabitNames.execute(namesFriendsList);
        try {
            names = getHabitNames.get(); // Get the Habits
            Collections.sort(names);     // order the habits by user name then by habit title.

        } catch (Exception e) {
            Log.i("Error", "Failed to get the requests from the async object");
        }

        //update the list with added information.
        adapter = new FriendAdapter(this, R.layout.listfriend_item, names);
        fEventList.setAdapter(adapter);

    }

}
