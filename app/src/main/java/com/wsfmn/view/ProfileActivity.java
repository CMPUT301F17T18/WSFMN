package com.wsfmn.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wsfmn.controller.App;
import com.wsfmn.controller.HabitHistoryController;
import com.wsfmn.controller.HabitListController;
import com.wsfmn.controller.ProfileNameController;
import com.wsfmn.model.LeaderBoardAdapter;
import com.wsfmn.model.ProfileName;
import com.wsfmn.model.Request;
import com.wsfmn.model.RequestAdapter;
import com.wsfmn.model.RequestList;
import com.wsfmn.controller.OnlineController;


import java.util.ArrayList;


/**
 * Represents Profiling Activity, Allowing the user to follow, share,
 * and view other friends' events
 *
 * @version 1.0
 * @see Activity
 */
public class ProfileActivity extends Activity {

//    private static final String USER_FILENAME = "username.sav";  /// deprecated, delete
    private String profileName = "";
    private boolean flag = false;

    private TextView userName;
    private TextView yourName;
    private ListView requestsFromUser;
    private ListView leaderBoard;

    private RequestList requestsList = new RequestList();
    ArrayList<ProfileName> leaderList;
    ArrayList<String> namesFriends = new ArrayList<String>();
    String[] namesFriendsList;
    LeaderBoardAdapter ladapter;
    RequestAdapter adapter = new RequestAdapter(requestsList, this);
    private OnlineController online = new OnlineController();




//    private OfflineController offline = new OfflineController();      //// deprecated, delete

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
        leaderBoard = (ListView) findViewById(R.id.leaderBoard);
        yourName = (TextView) findViewById(R.id.showName);

    }


    /**
     * Button method to Follow events from user. Searching for user with textbox input
     * @param view
     */
    public void followOnClick(View view){
        String text = userName.getText().toString().toLowerCase().replaceAll("\\s+","");
        Request newRequest = new Request(profileName, text, "follow");
        flag = online.checkRequest(profileName, "follow", text);

        if (!flag){
            Toast.makeText(ProfileActivity.this, "Request Already Sent!",
                    Toast.LENGTH_LONG).show();
        }

       else if(online.checkName(text)){
            Toast.makeText(ProfileActivity.this, "User Doesn\'t Exist!",
                    Toast.LENGTH_LONG).show();
        }

        else if(!online.checkFriends(text)){
            Toast.makeText(ProfileActivity.this, "This Person Is On Your FriendList Already!",
                    Toast.LENGTH_LONG).show();
        }

        else if(text.equals(profileName)){
            Toast.makeText(ProfileActivity.this, "Sorry, Can\'t Add Yourself!",
                    Toast.LENGTH_LONG).show();
        }

        //requestsList.add(newRequest);
        else {
            InputMethodManager mgr = (InputMethodManager) getSystemService(ProfileActivity.this.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(userName.getWindowToken(), 0);
            OnlineController.SendRequest sendRequest = new OnlineController.SendRequest();
            sendRequest.execute(newRequest);
            adapter.notifyDataSetChanged();
            Toast.makeText(ProfileActivity.this, "REQUEST SENT",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Button method to go to FriendActivity view.
     * @param view
     */
    public void viewFriendEventOnClick(View view){

        if(leaderList.size() > 1) {
            Intent intent = new Intent(this, FriendActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Follow A Friend!", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Starts the Activity with certain conditions.
     * Checks if user is connected to internet, and if user has a profilename.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
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
            Intent intent = new Intent(this,UserNameActivity.class);
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

        OnlineController.GetFriendNames getFriendEvents = new OnlineController.GetFriendNames();
        getFriendEvents.execute();
        try {
            namesFriends = getFriendEvents.get();
            namesFriendsList = namesFriends.toArray(new String[namesFriends.size()]);


        } catch (Exception e) {
            Log.i("Error", "Failed to get the requests from the async object");
        }

        OnlineController.GetFriendScore getFriendScrore = new OnlineController.GetFriendScore();
        getFriendScrore.execute(namesFriendsList);
        try {
            leaderList = getFriendScrore.get();

        } catch (Exception e) {
            Log.i("Error", "Failed to get the requests from the async object");
        }

        OnlineController.GetFriendScore getYourScore = new OnlineController.GetFriendScore();
        getYourScore.execute(App.USERNAME);
        try {
            leaderList.addAll(getYourScore.get());


        } catch (Exception e) {
            Log.i("Error", "Failed to get the requests from the async object");
        }

        //sorting list
        for(int i = 0; i < leaderList.size(); i++){
            for(int j = i; j < leaderList.size(); j++){
                if(leaderList.get(i).getScore() < leaderList.get(j).getScore()){
                    ProfileName temp = leaderList.get(i);
                    leaderList.set(i, leaderList.get(j));
                    leaderList.set(j, temp);
                }
            }
        }

        ladapter = new LeaderBoardAdapter(this, R.layout.listleader_item, leaderList);
        leaderBoard.setAdapter(ladapter);


        //Update List with Requests
        RequestAdapter adapter = new RequestAdapter(requestsList, this);
        requestsFromUser.setAdapter(adapter);

    }




    /**
     * Method to retrieve results from another activity. Used for getting profilename from
     * UserNameActivity.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                profileName = data.getStringExtra("uniqueName");

                // Delete this commented code, it is now handled inside UserNameActivity
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





}