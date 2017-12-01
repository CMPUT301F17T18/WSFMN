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

public class SelectHabitActivity extends AppCompatActivity {

    private Context context;
    private ListView habitListView;
    private ArrayAdapter<Habit> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_habit);

        context = this;
        habitListView = (ListView) findViewById(R.id.listHabitSelect);

        habitListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent4 = new Intent();
                intent4.putExtra("position", position);
                setResult(RESULT_OK, intent4);
                finish();
//                Intent intent2 = new Intent(CONTEXT, AddNewHabitEventActivity.class);
//                startActivity(intent2);
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
}
