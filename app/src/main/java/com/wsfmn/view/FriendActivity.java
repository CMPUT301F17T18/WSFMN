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
 * Activity to allow user to see events of followed users.
 *
 * @version 1.0
 * @see Activity
 */
public class FriendActivity extends Activity {

    private static final String USER_FILENAME = "username.sav";
    private String profileName = "";


    private Habit selected;
    private FriendAdapter adapter;
    ArrayList<String> namesFriends = new ArrayList<String>();
    String[] namesFriendsList;

    ArrayList<Habit> names = new ArrayList<Habit>();

    private ListView fEventList;

    private OnlineController online = new OnlineController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        fEventList = (ListView)findViewById(R.id.fEvent);

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

    protected void onStart() {
        super.onStart();
        if (online.isConnected() == true) {
        } else {
            Toast.makeText(FriendActivity.this, "Not connected to internet!",
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        loadFromFile();


        OnlineController.GetFriendNames getFriendEvents = new OnlineController.GetFriendNames();
        getFriendEvents.execute();
        try {
            namesFriends = getFriendEvents.get();
            //Collections.sort(namesFriends); Could be used later in scoreboard possibly.
            namesFriendsList = namesFriends.toArray(new String[namesFriends.size()]);


        } catch (Exception e) {
            Log.i("Error", "Failed to get the requests from the async object");
        }

        OnlineController.GetHabitNames getHabitNames = new OnlineController.GetHabitNames();
        getHabitNames.execute(namesFriendsList);

        try {
            names = getHabitNames.get();
            Collections.sort(names);

        } catch (Exception e) {
            Log.i("Error", "Failed to get the requests from the async object");
        }

        adapter = new FriendAdapter(this, R.layout.listfriend_item, names);
        fEventList.setAdapter(adapter);

    }


    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(USER_FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            Type listType = new TypeToken<String>() {
            }.getType();
            profileName = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            profileName = "";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}
