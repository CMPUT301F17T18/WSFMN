package com.wsfmn.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wsfmn.model.Habit;
import com.wsfmn.controller.HabitListController;

public class ViewHabitListActivity extends AppCompatActivity {

    private Context context;
    private ListView habitListView;
    private ArrayAdapter<Habit> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit_list);

        context = this;
        habitListView = (ListView) findViewById(R.id.habit_list_view);

        habitListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, HabitListDetailActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter = new ArrayAdapter<Habit>(this,
                android.R.layout.simple_list_item_1,
                HabitListController.getInstance().getHabitList());
        habitListView.setAdapter(adapter);
    }

    /** Called when the user taps the Add New Habit button */
    public void addNewHabit(View view){
        Intent intent = new Intent(this, AddNewHabitActivity.class);
        startActivity(intent);
    }

}
