package com.wsfmn.habittracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private HabitList habitList = new HabitList();
    private EditText habitTitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the Add New Habit button */
    public void addNewHabit(View view){
        setResult(RESULT_OK);
        habitTitleText = (EditText) findViewById(R.id.habit_title_text);
        String text = habitTitleText.getText().toString();
        Habit newHabit = new Habit(text, new Date());
        habitList.addHabit(newHabit);

        OnlineController.AddHabitOnline addHabitOnline
                = new OnlineController.AddHabitOnline();
        addHabitOnline.execute(newHabit);
    }


    /** Called when the user taps the Habit List button */
    public void viewHabitList(View view) {
        Intent intent = new Intent(this, HabitListViewActivity.class);
        startActivity(intent);
    }
}
