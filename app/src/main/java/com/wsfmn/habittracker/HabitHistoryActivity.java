package com.wsfmn.habittracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wsfmn.habit.HabitEvent;

import java.util.ArrayList;

public class HabitHistoryActivity extends Activity {

    String nameOfHabitEvent;
    EditText habitEventTitle;

    private ArrayList<HabitEvent> habitAdapter;

    Button addHabitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habits_history);


        addHabitButton = (Button)findViewById(R.id.addHabit);
        habitEventTitle = (EditText)findViewById(R.id.nameEvent);

    }

    /** Called when the user taps the Add New Habit Event button */
    public void viewHabitEvent(View view){
        Intent intent = new Intent(this, HabitEventActivity.class);
        startActivity(intent);
    }
}
