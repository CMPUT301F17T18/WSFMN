package com.wsfmn.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.wsfmn.controller.OnlineController;

/**
 * Activity to allow user to see events of followed users.
 *
 * @version 1.0
 * @see Activity
 */
public class FriendActivity extends Activity {

    private OnlineController online = new OnlineController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

    }


    protected void onStart() {
        super.onStart();
        if (online.isConnected() == true){}
        else {
            Toast.makeText(FriendActivity.this, "Not connected to internet!",
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
