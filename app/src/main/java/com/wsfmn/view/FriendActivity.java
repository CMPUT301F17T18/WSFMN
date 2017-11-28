package com.wsfmn.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wsfmn.model.Habit;
import com.wsfmn.model.RequestAdapter;
import com.wsfmn.controller.OnlineController;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * Activity to allow user to see events of followed users.
 *
 * @version 1.0
 * @see Activity
 */
public class FriendActivity extends Activity {

    private static final String USER_FILENAME = "username.sav";
    private String profileName = "";


    private ArrayAdapter<Habit> adapter;
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


       /* ArrayList<String> test = new ArrayList<String>();
        test.add("Hello");
        test.add("Trying");

        String [] testlist = test.toArray(new String[test.size()]);
*/

        OnlineController.GetFriendNames getFriendEvents = new OnlineController.GetFriendNames();
        getFriendEvents.execute();
        try {
            namesFriends = getFriendEvents.get();
            namesFriendsList = namesFriends.toArray(new String[namesFriends.size()]);


        } catch (Exception e) {
            Log.i("Error", "Failed to get the requests from the async object");
        }

        OnlineController.GetHabitNames getHabitNames = new OnlineController.GetHabitNames();
        getHabitNames.execute(namesFriendsList);

        try {
            names = getHabitNames.get();

        } catch (Exception e) {
            Log.i("Error", "Failed to get the requests from the async object");
        }

        adapter = new ArrayAdapter<Habit>(this, android.R.layout.simple_list_item_1, names);
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
