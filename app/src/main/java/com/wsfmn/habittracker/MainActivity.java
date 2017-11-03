package com.wsfmn.habittracker;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wsfmn.habit.Habit;
import com.wsfmn.habit.HabitList;
import com.wsfmn.habit.OfflineController;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

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

}