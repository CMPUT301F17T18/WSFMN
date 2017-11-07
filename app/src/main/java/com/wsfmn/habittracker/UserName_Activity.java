package com.wsfmn.habittracker;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.wsfmn.habit.ProfileName;
import com.wsfmn.habitcontroller.ProfileOnlineController;

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
        profileName = yourName.getText().toString().toLowerCase();
        final ProfileName name = new ProfileName(profileName);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You won't be able to change your name. Have this name?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // bring the profileName back
               /* ProfileOnlineController.CheckUnique check = new ProfileOnlineController.CheckUnique();
                check.execute(profileName);
                try{
                   flag  = check.get();
                    System.out.println(flag);

                } catch (Exception e) {
                    Log.i("Error", "Failed to get the tweets from the async object");
                }
                if (flag == false) {
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(UserName_Activity.this);
                    builder2.setTitle("Name is Taken, Type another!");
                    builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder2.show();

                }
                else {
                    ProfileOnlineController.StoreNameInDataBase storeName = new ProfileOnlineController.StoreNameInDataBase();
                    storeName.execute(name);
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("uniqueName", profileName);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }*/
                ProfileOnlineController.StoreNameInDataBase storeName = new ProfileOnlineController.StoreNameInDataBase();
                storeName.execute(name);
                Intent returnIntent = new Intent();
                returnIntent.putExtra("uniqueName", profileName);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
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
