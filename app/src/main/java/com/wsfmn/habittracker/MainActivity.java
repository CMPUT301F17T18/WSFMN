package com.wsfmn.habittracker;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    HabitList habitList = null;
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
        String habitTitle = habitTitleText.getText().toString();

        Habit newHabit = null;

        try {
            newHabit = new Habit(habitTitle, new Date());
        }
        catch(HabitTitleTooLongException e){
            // TODO: handle exception
        }

        habitList.addHabit(newHabit);

        OfflineController.StoreHabitList storeHabitListOffline =
                new OfflineController.StoreHabitList();
        storeHabitListOffline.execute(habitList);

        updateHabitList();

    }

    /** Update the local HabitList*/
    public void updateHabitList() {

        try {
            OfflineController.GetHabitList getHabitListOffline =
                    new OfflineController.GetHabitList();
            getHabitListOffline.execute();
            habitList = getHabitListOffline.get();
        }
        catch (InterruptedException e){
            Log.i("Error", e.getMessage());

        }
        catch (ExecutionException e) {
            Log.i("Error", e.getMessage());

        }

        // TODO nmayne: lazily printing out habitList
        // we need to do this with a ListView and an adapter
        HABIT_LIST_VIEW = (TextView) findViewById(R.id.habit_list_view);
        String allTheHabits = "";
        for (int i = 0; i < habitList.getSize(); i++) {
            allTheHabits = allTheHabits + "\n"
                    + habitList.getHabit(i).getTitle() + " "
                    + habitList.getHabit(i).getDate().toString();
        }
        HABIT_LIST_VIEW.setText(allTheHabits);
    }
}