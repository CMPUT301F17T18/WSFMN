package com.wsfmn.habittracker;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    HabitList habits = null;
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

        Habit newHabit = null;

        try {
            newHabit = new Habit(text, new Date());
        }
        catch(HabitTitleTooLongException e){
            // TODO: handle exception
        }

        habits.addHabit(newHabit);

        OnlineController.AddHabit addHabitOnline = new OnlineController.AddHabit();
        addHabitOnline.execute(newHabit);

        updateHabitList();
    }

    /** Update the local HabitList with a search of online Habits */
    public void updateHabitList() {
        EditText editText = (EditText) findViewById(R.id.habit_title_text);
        String searchString = editText.getText().toString().replaceAll(" ", "").toLowerCase();

        // TODO nmayne: add OfflineController, for speed this should run first, followed by online
//        habitList = Offlinecontroller.getHabitList();

        //Get habits from server
        OnlineController.GetHabitList getHabitList = new OnlineController.GetHabitList();
        try {
            getHabitList.execute(searchString);
            habits = getHabitList.get();
            // Delay 1 second for transaction to finish (usual time is around 200 ms)
            long currentTime = Calendar.getInstance().getTimeInMillis();
            while((Calendar.getInstance().getTimeInMillis() - currentTime) < 1000 ){}

        } catch (Exception e) {
            Log.i("Error", "Failed to get the habits from the async object");
        }


        // TODO nmayne: lazily checking that there are habits on the server, we need to do this with a ListView and an adapter
        HABIT_LIST_VIEW = (TextView) findViewById(R.id.habit_list_view);
        String allTheHabits = "";
        for (int i = 0; i < habits.getSize(); i++) {
            allTheHabits = allTheHabits + "\n"
                    + habits.getHabit(i).getTitle() + " "
                    + habits.getHabit(i).getDate().toString();
        }
        HABIT_LIST_VIEW.setText(allTheHabits);
    }
}