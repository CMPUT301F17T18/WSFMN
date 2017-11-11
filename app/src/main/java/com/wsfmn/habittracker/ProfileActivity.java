package com.wsfmn.habittracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wsfmn.habit.Request;
import com.wsfmn.habit.RequestAdapter;
import com.wsfmn.habitcontroller.ProfileOnlineController;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ProfileActivity extends Activity {

    private static final String USER_FILENAME = "username.sav";
    private String profileName = "";
    private boolean flag = false;

    private TextView userName;
    private TextView yourName;
    private TextView deleteName;
    private ListView requestsFromUser;
    private ArrayList<Request> requestsList = new ArrayList<Request>();
    RequestAdapter adapter = new RequestAdapter(requestsList, this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userName = (EditText) findViewById(R.id.userName);
        requestsFromUser = (ListView) findViewById(R.id.requestStuff);
        yourName = (TextView) findViewById(R.id.showName);

       /* deleteOK.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                String text = deleteName.getText().toString();
                requestsList.clear();
                adapter.notifyDataSetChanged();

             ProfileOnlineController.DeleteRequest deleteRequest = new ProfileOnlineController.DeleteRequest();
                deleteRequest.execute(text);
                try{
                    requestsList = deleteRequest.get();
                    adapter.notifyDataSetChanged();

                } catch (Exception e) {
                    Log.i("Error", "Failed to get the tweets from the async object");
                }
                adapter.notifyDataSetChanged();

            }
        });*/

    }

    public void shareOnClick(View view){
        String text = userName.getText().toString().toLowerCase().replaceAll("\\s+","");
        Request newRequest = new Request(profileName, text, "share");
        requestsList.add(newRequest);
        ProfileOnlineController.SendRequest sendRequest = new ProfileOnlineController.SendRequest();
        sendRequest.execute(newRequest);
        adapter.notifyDataSetChanged();
    }

    public void followOnClick(View view){
        String text = userName.getText().toString().toLowerCase().replaceAll("\\s+","");
        Request newRequest = new Request(profileName, text, "follow");
        requestsList.add(newRequest);
        ProfileOnlineController.SendRequest sendRequest = new ProfileOnlineController.SendRequest();
        sendRequest.execute(newRequest);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();

        //If User does not have a ProfileName yet then have them create one.
        if (profileName == "" & flag == false){
            Intent intent = new Intent(this,UserName_Activity.class);
            flag = true;
            startActivityForResult(intent, 1);
            onActivityResult(1, 1, intent);
            flag = false;
        }
        yourName.setText(profileName);

        ProfileOnlineController.GetRequest getRequest = new ProfileOnlineController.GetRequest();
        getRequest.execute(profileName);
        try{
            requestsList = getRequest.get();

        } catch (Exception e) {
            Log.i("Error", "Failed to get the requests from the async object");
        }
        RequestAdapter adapter = new RequestAdapter(requestsList, this);
        requestsFromUser.setAdapter(adapter);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                profileName = data.getStringExtra("uniqueName");
                System.out.println(profileName);
                //TODO save the name locally
                saveInFile();
            }
        }
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(USER_FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            Type listType = new TypeToken<String>(){}.getType();
            profileName = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            profileName = "";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

    //from lonely twitter, changed for custom object
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(USER_FILENAME,
                    Context.MODE_PRIVATE);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            // convert list into a string. and saving it on output.
            gson.toJson(profileName, out);
            out.flush();
            fos.close();
            //something fails it will catch.
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }



}