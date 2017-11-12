package com.wsfmn.habittracker;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.wsfmn.habit.ProfileName;
import com.wsfmn.habitcontroller.OnlineController;

public class UserName_Activity extends AppCompatActivity {

    private String profileName;
    private EditText yourName;
    private Boolean flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_name_);

        yourName = (EditText) findViewById(R.id.yourName);
    }

    public void confirmClick(View view){
        profileName = yourName.getText().toString().toLowerCase().replaceAll("\\s+","");
        final ProfileName name = new ProfileName(profileName);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Have this name?  "+ profileName);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // bring the profileName back
                OnlineController.CheckUnique check = new OnlineController.CheckUnique();
                check.execute(profileName);
                try{
                   flag  = check.get();

                } catch (Exception e) {
                    Log.i("Error", "Couldn't get flag from async object");
                }
                if (flag == false){
                    Toast.makeText(UserName_Activity.this, "Name is taken! Type another!",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    OnlineController.StoreNameInDataBase storeName = new OnlineController.StoreNameInDataBase();
                    storeName.execute(name);
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
