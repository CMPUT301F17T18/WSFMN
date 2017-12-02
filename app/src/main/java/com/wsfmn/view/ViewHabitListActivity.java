package com.wsfmn.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.WindowDecorActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wsfmn.controller.HabitListController;
import com.wsfmn.model.Habit;

import static android.view.Window.FEATURE_ACTION_BAR;

/**
 * Show the user their list of Habits and a button to add new Habits.
 */
public class ViewHabitListActivity extends AppCompatActivity {

    private Context context;
    private ListView habitListView;
    private ArrayAdapter<Habit> adapter;
    private Boolean flag_forceAddHabit = true;

    static final int FORCED_ADD_NEW_HABIT = 4;


    /**
     * Instantiate the Habit ListView and listener for taps on the list.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit_list);

        context = this;
//        flag_forceAddHabit = true;

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

    /**
     * Attach the habitListView ArrayAdapter.
     * Take user to AddNewHabitActivity when their HabitList is empty.
     */
    @Override
    protected void onStart() {
        super.onStart();

        adapter = new ArrayAdapter<Habit>(this,
                android.R.layout.simple_list_item_1,
                HabitListController.getInstance().getHabitList());
        habitListView.setAdapter(adapter);

        if (HabitListController.getInstance().isEmpty() && flag_forceAddHabit) {
            flag_forceAddHabit = false;
            Intent intent = new Intent(this, AddNewHabitActivity.class);
            startActivityForResult(intent, FORCED_ADD_NEW_HABIT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FORCED_ADD_NEW_HABIT) {
            if (requestCode == FORCED_ADD_NEW_HABIT)
                flag_forceAddHabit = false;
        }
    }

    /**
     * Enforce return to MainActivity when leaving ViewHabitListActivity.
     * <p>
     * This fixes the issue where clicking the back button takes a user
     * back to the addNewHabitActivity that they were possibly just in.
     * <p>
     * https://stackoverflow.com/questions/18337536/android-overriding-onbackpressed
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Called when the user taps the Add New Habit button.
     */
    public void addNewHabit(View view) {
        Intent intent = new Intent(this, AddNewHabitActivity.class);
        startActivity(intent);
    }

}