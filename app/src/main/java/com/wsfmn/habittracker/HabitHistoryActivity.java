package com.wsfmn.habittracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.wsfmn.habit.Habit;
import com.wsfmn.habit.HabitEvent;
import com.wsfmn.habit.HabitHistory;
import com.wsfmn.habit.HabitList;
import com.wsfmn.habitcontroller.HabitHistoryController;
import com.wsfmn.habitcontroller.HabitListController;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Displays all the Habit Events the user has
 * @version 1.0
 * @see AppCompatActivity
 */
public class HabitHistoryActivity extends AppCompatActivity {

    private ArrayAdapter<HabitEvent> adapter;
    private ListView habitHistory;

    private EditText search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habits_history);


        search = findViewById(R.id.search);

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
        adapter = new ArrayAdapter<HabitEvent>(this,
                android.R.layout.simple_list_item_1,
                HabitHistoryController.getFilteredInstance().getHabitEventList());
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

    //  called when user wants to search by title
    public void filterByTitle(View view) {
        HabitHistoryController c = HabitHistoryController.getInstance();
        c.getFilteredInstance().filterByTitle(search.getText().toString());
        adapter.notifyDataSetChanged();
    }

    //  called when user wants to search by comment
    public void filterByComment(View view){
        HabitHistoryController c = HabitHistoryController.getInstance();
        c.getFilteredInstance().filterByComment(search.getText().toString());
        adapter.notifyDataSetChanged();
    }


}
