package com.wsfmn.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.wsfmn.controller.ProfileNameController;
import com.wsfmn.model.ProfileName;
import com.wsfmn.controller.OnlineController;

/**
 * Activity for the user to create their username and check if it is available
 * on Elastic Search.
 *
 * @version 1.0
 * @see Activity
 */
public class UserName_Activity extends AppCompatActivity {

    private String profileName;
    private EditText yourName;
    private Boolean flag;
    private OnlineController online = new OnlineController();


    /**
     * Create the Activity, set and create some variables.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_name_);

        yourName = (EditText) findViewById(R.id.yourUserName);
    }

    /**
     * Button method to allow the user to confirm their username.
     * Will check on Elastic Search if username is already used and if it is unique then
     * store it in Elastic Search.
     * @param view
     */
    public void confirmClick(View view) {
        profileName = yourName.getText().toString().toLowerCase().replaceAll("\\s+", "");
        if (profileName.length() == 0) {
            Toast.makeText(UserName_Activity.this, "Type in a name!",
                    Toast.LENGTH_LONG).show();
        } else {
            final ProfileName name = new ProfileName(profileName);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Have this name?  " + profileName);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // bring the profileName back
                    flag = online.checkName(profileName);


//                     SLATED FOR DELETION
//                    //OnlineController.CheckUnique check = new OnlineController.CheckUnique();
//                    //check.execute(profileName);
//                    /*try{
//                    flag  = check.get();
//                    } catch (Exception e) {
//                        Log.i("Error", "Couldn't get flag from async object");
//                    }*/


                    if (flag == false) {
                        Toast.makeText(UserName_Activity.this, "Name is taken! Type another!",
                                Toast.LENGTH_LONG).show();
                    } else {
                        online.storeName(name); // Store online
                        ProfileNameController.getInstance().storeNewProfileNameOffline(name); // Store offline

                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("uniqueName", profileName);
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                    }

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
    }

    public void backOnClick(View view){
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    /**
     * Starts and checks if user is connected to internet.
     */
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (online.isConnected() == true){}
        else {
            Toast.makeText(UserName_Activity.this, "Not connected to internet!",
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }


    }
}
