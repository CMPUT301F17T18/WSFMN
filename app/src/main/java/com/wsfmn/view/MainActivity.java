package com.wsfmn.view;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wsfmn.controller.OnlineController;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void habitList(View view){
        Intent intent = new Intent(this, ViewHabitListActivity.class);
        startActivity(intent);
    }

    public void habitsForToday(View view){
        Intent intent = new Intent(this, ViewHabitsForTodayActivity.class);
        startActivity(intent);
    }

    public void profileActivity(View view){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void habitHistory(View view){
        Intent intent = new Intent( this, ViewHabitHistoryActivity.class);
        startActivity(intent);
    }

}