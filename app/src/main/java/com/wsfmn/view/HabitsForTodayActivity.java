package com.wsfmn.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wsfmn.model.Habit;
import com.wsfmn.controller.HabitListController;

public class HabitsForTodayActivity extends AppCompatActivity {

    private ListView habitListView;
    private ArrayAdapter<Habit> adapter;


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
