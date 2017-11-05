package com.wsfmn.habittracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wsfmn.habit.Date;
import com.wsfmn.habit.Habit;
import com.wsfmn.habit.HabitList;
import com.wsfmn.habitcontroller.HabitListController;
import com.wsfmn.habitcontroller.OfflineController;

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
        adapter = new ArrayAdapter<Habit>(this,
                android.R.layout.simple_list_item_1,
                HabitListController.getInstance().getHabitsForToday());
        habitListView.setAdapter(adapter);
    }

}
