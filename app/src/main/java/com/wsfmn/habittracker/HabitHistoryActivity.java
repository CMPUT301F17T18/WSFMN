package com.wsfmn.habittracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.wsfmn.habit.HabitEvent;
import com.wsfmn.habitcontroller.HabitHistoryController;

import java.util.List;

/**
 * Displays all the Habit Events the user has
 * @version 1.0
 * @see AppCompatActivity
 */
public class HabitHistoryActivity extends AppCompatActivity {

    private ArrayAdapter<HabitEvent> adapter;
    private ListView habitHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habits_history);

        habitHistory = (ListView)findViewById(R.id.habitEventHist);

        //When the user clicks on a HabitEvent item on the HabitEventList
        habitHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HabitHistoryActivity.this, HabitHistoryDetailActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        Button B_Map = (Button) findViewById(R.id.B_map);
        B_Map.setOnClickListener(new View.OnClickListener(){
            @Override
            //https://developer.android.com/training/basics/intents/result.html
            public void onClick(View v){
                Intent  intent = new Intent(HabitHistoryActivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        List value = HabitHistoryController.getInstance().getHabitEventList();
        adapter = new ArrayAdapter<HabitEvent>(this,
                android.R.layout.simple_list_item_1,
                HabitHistoryController.getInstance().getHabitEventList());
        habitHistory.setAdapter(adapter);
    }

    /**
     * Called when the user taps the Add New Habit Event button
     * @param view
     */
    public void addHE(View view){
        Intent intent = new Intent(this, HabitEventActivity.class);
        startActivity(intent);
    }
}
