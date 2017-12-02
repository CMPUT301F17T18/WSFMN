package com.wsfmn.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.wsfmn.controller.HabitListController;
import com.wsfmn.model.HabitEvent;
import com.wsfmn.controller.HabitHistoryController;

import static java.lang.Thread.sleep;

/**
 * Displays all the Habit Events the user has
 * @version 1.0
 * @see AppCompatActivity
 */
public class ViewHabitHistoryActivity extends AppCompatActivity {

    private ArrayAdapter<HabitEvent> adapter;
    private ListView habitHistory;
    private EditText search;
    int highlightMode = 7;
    public static final int FILTER_BY_TITLE_CODE=5;
    public static final int FILTER_BY_COMMENT_CODE= 6;
    String filterString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit_history);

        search = findViewById(R.id.search);
        habitHistory = (ListView)findViewById(R.id.habitEventHist);
        filterString=search.getText().toString();

        //When the user clicks on a HabitEvent item on the HabitEventList
        habitHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ViewHabitHistoryActivity.this, HabitHistoryDetailActivity.class);
                HabitEvent temp = (HabitEvent) habitHistory.getItemAtPosition(position);
                intent.putExtra("id", temp.getId());
                startActivity(intent);
            }
        });

        Button B_Map = (Button) findViewById(R.id.B_map);
        B_Map.setOnClickListener(new View.OnClickListener(){
            @Override
            //https://developer.android.com/training/basics/intents/result.html
            public void onClick(View v){
                Intent  intent = new Intent(ViewHabitHistoryActivity.this,ViewMapActivity.class);
                intent.putExtra("filterString", filterString);
                intent.putExtra("highlightMode",highlightMode);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter = new ArrayAdapter<HabitEvent>(this,
                android.R.layout.simple_list_item_1,
                HabitHistoryController.getInstance().getFilteredInstance().getHabitEventList());
        habitHistory.setAdapter(adapter);
    }

    /**
     * Called when the user taps the Add New Habit Event button
     * Goes to add a new habit if the user currently has no habits
     * @param view
     */
    public void addHE(View view){
        if (HabitListController.getInstance().isEmpty()) {
            Intent intent = new Intent(this, AddNewHabitActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, AddNewHabitEventActivity.class);
            startActivity(intent);
        }
    }

    //  called when user wants to search by title
    public void filterByTitle(View view) {
        HabitHistoryController.getInstance().getFilteredInstance().filterByTitle(search.getText().toString());
        adapter.notifyDataSetChanged();

        highlightMode = FILTER_BY_TITLE_CODE;
    }

    //  called when user wants to search by comment
    public void filterByComment(View view){
        HabitHistoryController.getInstance().getFilteredInstance().filterByComment(search.getText().toString());
        adapter.notifyDataSetChanged();

        highlightMode = FILTER_BY_COMMENT_CODE;
    }
}