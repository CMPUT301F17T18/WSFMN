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
    private TextView searchName;
    private ListView requestsFromUser;
    private ArrayList<Request> requestsList = new ArrayList<Request>();
    private ArrayAdapter<Request> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userName = (EditText) findViewById(R.id.userName);
        searchName = (EditText) findViewById(R.id.searchName);
        Button userOK = (Button) findViewById(R.id.userOK);
        Button searchOK = (Button) findViewById(R.id.searchOK);
        Button getRequest = (Button) findViewById(R.id.getRequest);
        requestsFromUser = (ListView) findViewById(R.id.requestStuff);
        yourName = (TextView) findViewById(R.id.showName);

        System.out.println(profileName);


        userOK.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                String text = userName.getText().toString();
                Request newRequest = new Request(profileName, text);
                requestsList.add(newRequest);
                adapter.notifyDataSetChanged();
                //saveInFile(); // TODO replace this with elastic search
                ProfileOnlineController.SendRequest sendRequest = new ProfileOnlineController.SendRequest();
                sendRequest.execute(newRequest);
            }
        });

        searchOK.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                String text = searchName.getText().toString();
                requestsList.clear();
                adapter.notifyDataSetChanged();

             ProfileOnlineController.DeleteRequest deleteRequest = new ProfileOnlineController.DeleteRequest();
                deleteRequest.execute("name3");
                try{
                    requestsList = deleteRequest.get();
                    adapter.notifyDataSetChanged();

                } catch (Exception e) {
                    Log.i("Error", "Failed to get the tweets from the async object");
                }
                adapter.notifyDataSetChanged();

            }
        });

        getRequest.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                setResult(RESULT_OK);
                String text = searchName.getText().toString();


                /*ProfileOnlineController.GetRequest getRequest = new ProfileOnlineController.GetRequest();
                getRequest.execute("name3");
                System.out.println("execute");
                try{
                    requestsList = getRequest.get();
                    adapter.notifyDataSetChanged();
                    System.out.println("Got");

                } catch (Exception e) {
                    Log.i("Error", "Failed to get the tweets from the async object");
                }
                adapter.notifyDataSetChanged();
                System.out.println("update");*/

            }

        });
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();

        //If User does not have a ProfileNam yet then have them create one.
        if (profileName == "" & flag == false){
            Intent intent = new Intent(this,UserName_Activity.class);
            flag = true;
            startActivityForResult(intent, 1);
            onActivityResult(1, 1, intent);
            flag = false;
        }
        yourName.setText(profileName);

       /* ProfileOnlineController.GetRequest getRequest = new ProfileOnlineController.GetRequest();
        //String text = "Name3";
        getRequest.execute(profileName);
        try{
            requestsList = getRequest.get();

        } catch (Exception e) {
            Log.i("Error", "Failed to get the tweets from the async object");
        }*/
        adapter = new ArrayAdapter<Request>(this,
                android.R.layout.simple_list_item_1, requestsList);
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