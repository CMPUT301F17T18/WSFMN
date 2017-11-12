package com.wsfmn.habittracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.wsfmn.habit.HabitEvent;
import com.wsfmn.habitcontroller.HabitHistoryController;

import java.util.List;

public class HabitHistoryActivity extends Activity {

    private ArrayAdapter<HabitEvent> adapter;
    private ListView habitHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habits_history);

        habitHistory = (ListView)findViewById(R.id.habitEventHist);

        habitHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HabitHistoryActivity.this, habitHistoryDetailActivity.class);
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

    /** Called when the user taps the Add New Habit Event button */
    public void addHabitEvent(View view){
        Intent intent = new Intent(this, HabitEventActivity.class);
        startActivity(intent);
    }
}
