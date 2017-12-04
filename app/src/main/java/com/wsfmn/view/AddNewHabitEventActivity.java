package com.wsfmn.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wsfmn.controller.ImageController;
import com.wsfmn.controller.ProfileNameController;
import com.wsfmn.model.Date;

import com.wsfmn.model.Habit;
import com.wsfmn.exceptions.HabitCommentTooLongException;
import com.wsfmn.model.HabitEvent;
import com.wsfmn.exceptions.HabitEventCommentTooLongException;
import com.wsfmn.controller.HabitHistoryController;
import com.wsfmn.controller.HabitListController;

import java.text.ParseException;

/**
 * Called when the user wants to adds a HabitEvent for a Habit.
 * @version 1.0
 * @see AppCompatActivity
 */
public class AddNewHabitEventActivity extends HabitHistorySuper {

    /**
     * Setup the activity for adding a new HabitEvent to HabitHistory.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_habit_event);

        // Variables for the UI
        title = (TextView)findViewById(R.id.habitName);
        comment = (EditText) findViewById(R.id.Comment);
        date = (TextView) findViewById(R.id.eventDate);
        address = (TextView) findViewById(R.id.T_showAdress);
        addPicture = (Button) findViewById(R.id.Picture);
        viewPicture = (Button) findViewById(R.id.ViewImg);

        // Check if the Habit has been set by a call from the HabitForTodayActivity
        Bundle b = getIntent().getExtras();
        if(b!=null) {
            if (b.getString("caller").equals("TODAY")) {
                habitIdx = b.getInt("positionToday");

                addedHabit = HLC.getHabitsForToday().get(habitIdx);
                title.setText(addedHabit.getTitle().toString());
            }
        }
        // Set date for the HabitEvent created
        String dateAndTime = new Date(0).toString();
        date.setText(dateAndTime);

        // Check if device has a camera
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            addPicture.setEnabled(false);
        }

        FIRST_PHOTO = true; // Adding new HabitEvent starts with no photo
        viewPicture.setEnabled(false);

        // React when the date has been set
        mDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                date.setText(year + " / " + month + " / " + dayOfMonth);
                date.getText();
            }
        };
    }

    /**
     * Create and save a new valid HabitEvent with the current on-screen parameters.
     * @param view
     */
    public void addHE(View view) {
        Intent intent = new Intent(this, ViewHabitHistoryActivity.class);

        // Encode image as a string for online storage
        String photoStringEncoding = IC.convertImageToString(photoPath);
        try {
            //CREATE THE NEW HABIT EVENT
            HabitEvent NEW_HABIT_EVENT = new HabitEvent(
                    addedHabit,
                    addedHabit.getTitle(),
                    comment.getText().toString(),
                    photoStringEncoding,
                    photoPath,
                    getDateUIHE(),
                    geolocation);

            //Add the Habit Event to HabitHistory
            HHC.addAndStore(NEW_HABIT_EVENT);
            HHC.storeAll();

            // Update User's Score
            ProfileNameController.getInstance().updateScore();
            startActivity(intent);

        }catch(HabitCommentTooLongException e){
            e.printStackTrace();
        }catch(HabitEventCommentTooLongException e){
            Toast.makeText(AddNewHabitEventActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }catch(NullPointerException e){
            Toast.makeText(AddNewHabitEventActivity.this, "Habit Event needs to contain Habit", Toast.LENGTH_LONG).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}