package com.wsfmn.habittracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.wsfmn.habit.HabitList;

public class HabitListViewActivity extends AppCompatActivity {

    private HabitList habitList;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_list_view);
    }


    @Override
    protected void onStart() {
        super.onStart();


    }

}
