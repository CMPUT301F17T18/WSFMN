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

import com.wsfmn.controller.ProfileNameController;

import com.wsfmn.exceptions.HabitEventCommentTooLongException;
import com.wsfmn.exceptions.HabitEventNameException;
import com.wsfmn.controller.HabitHistoryController;

import com.wsfmn.model.HabitEvent;

/**
 * Called when the user wants to edit an existing Habit Event.
 * @version 1.0
 * @see AppCompatActivity
 */
public class HabitHistoryDetailActivity extends HabitHistorySuper {

    /**
     * Setup the activity for changing an existing HabitEvent in HabitHistory.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_history_detail);

        // Variables for the UI
        title = (TextView) findViewById(R.id.hd_title);
        comment = (EditText) findViewById(R.id.hd_editComment);
        date = (TextView) findViewById(R.id.hd_date);
        address = (TextView) findViewById(R.id.hd_address);
        addPicture = (Button) findViewById(R.id.hd_changePicture);
        viewPicture = (Button) findViewById(R.id.hd_viewPicture);

        // Get the ID of the HabitEvent to view
        Bundle b = getIntent().getExtras();
        habitEvent = HHC.get(b.getString("id"));
        // Instantiate view vars
        addedHabit = habitEvent.getHabit();
        title.setText(habitEvent.getTitle());
        comment.setText(habitEvent.getComment());
        date.setText(habitEvent.getDate().toString()); // TODO: Correct Date String?
        geolocation = habitEvent.getGeolocation();
        photoPath = habitEvent.getPhotoPath();
        if (habitEvent.getGeolocation() != null) {
            address.setText(habitEvent.getGeolocation().getAddress());
        }


        // Check if device has a camera
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            addPicture.setEnabled(false);
        }

        // Check if there is a photo
        if (photoPath == null) {
            FIRST_PHOTO = true;
            viewPicture.setEnabled(false);
        } else {
            FIRST_PHOTO = false;
        }

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
     * Delete the Habit Event from the Habit Event History
     * @param view
     */
    public void deleteHE(View view){
        HHC.removeAndStore(habitEvent);
        Intent intent = new Intent(this, ViewHabitHistoryActivity.class);
        startActivity(intent);
    }

    /**
     * Save the changes for this Habit Event
     * @param view
     */
    public void confirmHE(View view){
        Intent intent = new Intent(this, ViewHabitHistoryActivity.class);

        // Encode image as a string for online storage
        String photoStringEncoding = IC.convertImageToString(photoPath);

        try {
            //Set the habitEvent parameters that the user gets
            habitEvent.setTitle(title.getText().toString());
            habitEvent.setComment(comment.getText().toString());
            habitEvent.setHabit(addedHabit);
            habitEvent.setDate(getDateUIHE());
            habitEvent.setPhotoPath(photoPath);
            habitEvent.setPhotoStringEncoding(photoStringEncoding);
            habitEvent.setGeolocation(geolocation);

            HHC.storeAndUpdate(habitEvent);
            // Update User's Score
            ProfileNameController.getInstance().updateScore();
            startActivity(intent);

        } catch (HabitEventCommentTooLongException e) {
            Toast.makeText(this, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } catch (HabitEventNameException e) {
            Toast.makeText(this, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
}