package com.wsfmn.habittracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.wsfmn.habit.Date;
import com.wsfmn.habit.Habit;
import com.wsfmn.habit.HabitList;
import com.wsfmn.habit.OfflineController;

import java.util.concurrent.ExecutionException;

public class HabitsForTodayActivity extends AppCompatActivity {

    private ListView habitListView;
    private ArrayAdapter<Habit> adapter;
    private HabitList habitsForTodayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habits_for_today);

        habitListView = (ListView) findViewById(R.id.habit_list_view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateHabitList();
        adapter = new ArrayAdapter<Habit>(this,
                android.R.layout.simple_list_item_1,
                habitsForTodayList.getHabitsWithDate(new Date()));
        habitListView.setAdapter(adapter);
    }

    public void updateHabitList() {

        try {
            OfflineController.GetHabitList getHabitListOffline =
                    new OfflineController.GetHabitList();
            getHabitListOffline.execute();
            habitsForTodayList = getHabitListOffline.get();
        }
        catch (InterruptedException e){
            Log.i("Error", e.getMessage());

        }
        catch (ExecutionException e) {
            Log.i("Error", e.getMessage());

        }

    }
}
