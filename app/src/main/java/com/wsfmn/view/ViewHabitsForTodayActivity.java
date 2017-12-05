/*
 * Copyright © 2017 Team 18 (WSFMN), CMPUT301, University of Alberta – All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact nmayne@ualberta.ca.
 *
 *  Team 18 is: Musaed Alsobaie, Siddhant Khanna, Wei Li, Nicholas Mayne, Fredric Mendi.
 */

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

public class ViewHabitsForTodayActivity extends AppCompatActivity {

    private Context context;
    private ListView habitListView;
    private ArrayAdapter<Habit> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habits_for_today);

        context = this;
        habitListView = (ListView) findViewById(R.id.habit_list_view);

        habitListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(context, AddNewHabitEventActivity.class);
                intent.putExtra("caller", "TODAY");
                intent.putExtra("positionToday", position);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter = new ArrayAdapter<Habit>(this,
                android.R.layout.simple_list_item_1,
                HabitListController.getInstance().getHabitsForToday());
        habitListView.setAdapter(adapter);
    }

}
