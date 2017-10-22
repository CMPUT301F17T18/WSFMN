package com.wsfmn.habittracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

        OnlineController.GetHabitsOnline getHabitsOnline
                = new OnlineController.GetHabitsOnline();
        getHabitsOnline.execute("Habit");

        try {
            habitList = new HabitList(getHabitsOnline.get());
        } catch (Exception e) {
            Log.i("Error", "Failed to get the habits from the async object");
        }

        textView = (TextView) findViewById(R.id.textView);
        textView.setText(habitList.getHabit(0).getTitle() + "\n" + habitList.getHabit(0).getDate().toString());

    }

}
