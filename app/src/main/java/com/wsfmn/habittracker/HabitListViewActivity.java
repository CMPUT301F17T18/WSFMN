package com.wsfmn.habittracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wsfmn.habit.Habit;
import com.wsfmn.habit.HabitList;
import com.wsfmn.habit.OfflineController;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

public class HabitListViewActivity extends AppCompatActivity {

    private final static int CREATE_HABIT_REQUEST = 1;
    private final static int VIEW_HABIT_REQUEST = 2;
    private Context context;

    private ListView habitListView;
    private ArrayAdapter<Habit> adapter;
    private HabitList habitList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_list_view);
        context = this;


        habitListView = (ListView) findViewById(R.id.habit_list_view);

        habitListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, HabitListViewDetailActivity.class);

                Habit habit = habitList.getHabit(position);

                Gson gson = new Gson();
                String object = gson.toJson(habit);
                intent.putExtra("Habit", object);
                intent.putExtra("position", position);

                startActivityForResult(intent, VIEW_HABIT_REQUEST);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateHabitList();
        adapter = new ArrayAdapter<Habit>(this,
                android.R.layout.simple_list_item_1, habitList.getHabitList());
        habitListView.setAdapter(adapter);
    }

    /** Called when the user taps the Add New Habit button */
    public void addNewHabit(View view){
        Intent intent = new Intent(this, addNewHabitActivity.class);
        startActivityForResult(intent, CREATE_HABIT_REQUEST);
    }

    /**
     *
     * this function receives data from addNewHabitActivity.
     * The data is a Habit objects that either replaces a
     * Habit object or adds it to the array
     *
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_HABIT_REQUEST && resultCode == RESULT_OK) {
            Bundle b = data.getExtras();

            String object = b.getString("Habit");
            Gson gson = new Gson();
            Type objectType = new TypeToken<Habit>(){}.getType();
            Habit habit = gson.fromJson(object, objectType);

            habitList.addHabit(habit);

            adapter.notifyDataSetChanged();
            OfflineController.StoreHabitList storeHabitListOffline = new OfflineController.StoreHabitList();
            storeHabitListOffline.execute(habitList);
        }

        if (requestCode == VIEW_HABIT_REQUEST && resultCode == RESULT_OK) {
            Bundle b = data.getExtras();

            int position = b.getInt("position");
            String object = b.getString("Habit");
            Gson gson = new Gson();
            Type objectType = new TypeToken<Habit>(){}.getType();
            Habit habit = gson.fromJson(object, objectType);

            habitList.setHabit(position, habit);

            adapter.notifyDataSetChanged();
            OfflineController.StoreHabitList storeHabitListOffline = new OfflineController.StoreHabitList();
            storeHabitListOffline.execute(habitList);
        }
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

    }

}
