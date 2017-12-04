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
import android.widget.Toast;

import com.wsfmn.controller.HabitListController;
import com.wsfmn.controller.ProfileNameController;
import com.wsfmn.model.Habit;

import static android.view.Window.FEATURE_ACTION_BAR;


/**
 * Show the User their list of Habits and a button to add a new Habit.
 */
public class ViewHabitListActivity extends AppCompatActivity {

    private Context context;
    private ListView habitListView;
    private ArrayAdapter<Habit> adapter;
    private Boolean flag_forceAddHabit = true;

    /**
     * Instantiate the Habit ListView and listener for clicks on the list.
     *
     * @param savedInstanceState
     */
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
            Toast.makeText(ViewHabitListActivity.this, "Add A Habit",Toast.LENGTH_LONG).show();
            flag_forceAddHabit = false;
            Intent intent = new Intent(this, AddNewHabitActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        ProfileNameController.getInstance().updateScore(); // update user's score
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
     * Goto AddNewHabitActivity.
     */
    public void addNewHabit(View view) {
        Intent intent = new Intent(this, AddNewHabitActivity.class);
        startActivity(intent);
    }

}