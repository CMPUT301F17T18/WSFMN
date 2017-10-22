package com.wsfmn.habittracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private HabitList habitList;
    private EditText habitTitleText;
    private TextView HABIT_LIST_VIEW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateHabitList();
    }

    /** Called when the user taps the Add New Habit button */
    public void addNewHabit(View view){
        setResult(RESULT_OK);
        habitTitleText = (EditText) findViewById(R.id.habit_title_text);
        String text = habitTitleText.getText().toString();
        Habit newHabit = new Habit(text, new Date());
        habitList.addHabit(newHabit);

        OnlineController.AddHabit addHabitOnline
                = new OnlineController.AddHabit();
        addHabitOnline.execute(newHabit);
//        Log.d("AddingNewHabitID", newHabit.getId());
        updateHabitList();
    }

    /** Update the local HabitList with a search of online Habits */
    public void updateHabitList() {
        OnlineController.GetHabitList getHabitList
                = new OnlineController.GetHabitList();
        getHabitList.execute("habit");

        try {
            habitList = new HabitList(getHabitList.get());
        } catch (Exception e) {
            Log.i("Error", "Failed to get the habits from the async object");

//            habitList = Offlinecontroller.getHabitList();
        }

        HABIT_LIST_VIEW = (TextView) findViewById(R.id.habit_list_view);

        // nmayne: This is my lazy way of checking that there are habits on the server
        // we need to do this with a ListView and an adapter
        String allTheHabits = "";
        for (int i = 0; i < 5; i++) {
            allTheHabits = allTheHabits
                    + "\n" + habitList.getHabit(i).getTitle()
                    + habitList.getHabit(i).getDate().toString()
                    + habitList.getHabit(i).getId();
        }
        HABIT_LIST_VIEW.setText(allTheHabits);
    }

}
