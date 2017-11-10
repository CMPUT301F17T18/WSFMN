package com.wsfmn.habittracker;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
        Intent intent = new Intent(this, HabitListViewActivity.class);
        startActivity(intent);
    }

    public void habitsForToday(View view){
        Intent intent = new Intent(this, HabitsForTodayActivity.class);
        startActivity(intent);
    }

    public void profileActivity(View view){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void habitHistory(View view){
        Intent intent = new Intent( this, HabitHistoryActivity.class);
        startActivity(intent);
    }

}