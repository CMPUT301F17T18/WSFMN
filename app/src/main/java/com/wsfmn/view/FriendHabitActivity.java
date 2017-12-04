package com.wsfmn.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wsfmn.controller.HabitListController;
import com.wsfmn.controller.OnlineController;
import com.wsfmn.model.Habit;
import com.wsfmn.model.Date;
import com.wsfmn.model.HabitEvent;
import com.wsfmn.model.WeekDays;
import com.wsfmn.model.Geolocation;

/**
 * Activity to view all details of another user's habit and event.
 *
 * @version 1.0
 * @see AppCompatActivity
 */
public class FriendHabitActivity extends AppCompatActivity {

    private Habit fHabit;     // collect the habit from intent
    private HabitEvent fEvent; // collect the event from Elastic Search
    TextView fhTitle;           // habit title
    TextView fhReason;          // habit reason
    TextView fhDate;            // habit date

    // shows the most recent event details
    TextView fEventComment;     // Event comment
    TextView fEventDate;        // Event Date
    TextView fEventAddress;     // Event address
    ImageView friendImage;       // image of the Event


    // checkboxes when they plan to do the event.
    private CheckBox monday;
    private CheckBox tuesday;
    private CheckBox wednesday;
    private CheckBox thursday;
    private CheckBox friday;
    private CheckBox saturday;
    private CheckBox sunday;

    /**
     * Creates variables and activities
     *
     * @param savedInstanceState saves the state of app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_habit);

        Intent intent = getIntent(); // get intent from previous activity
        fHabit = (Habit) intent.getSerializableExtra("friend");


        fhTitle = (TextView) findViewById(R.id.fhTitle);
        fhReason = (TextView) findViewById(R.id.fhReason);
        fhDate = (TextView) findViewById(R.id.fhDate);
        monday = (CheckBox) findViewById(R.id.monday);
        tuesday = (CheckBox) findViewById(R.id.tuesday);
        wednesday = (CheckBox) findViewById(R.id.wednesday);
        thursday = (CheckBox) findViewById(R.id.thursday);
        friday = (CheckBox) findViewById(R.id.friday);
        saturday = (CheckBox) findViewById(R.id.saturday);
        sunday = (CheckBox) findViewById(R.id.sunday);

        fEventComment = (TextView) findViewById(R.id.fEventComment);
        fEventDate = (TextView) findViewById(R.id.fEventDate);
        fEventAddress = (TextView) findViewById(R.id.fEventAddress);

        friendImage = (ImageView) findViewById(R.id.friendImage);



        //make sure days are not editable
        monday.setClickable(false);
        tuesday.setClickable(false);
        wednesday.setClickable(false);
        thursday.setClickable(false);
        friday.setClickable(false);
        saturday.setClickable(false);
        sunday.setClickable(false);

        // set the days that they have picked in their habit.
        setCheckBox(monday, WeekDays.MONDAY);
        setCheckBox(tuesday, WeekDays.TUESDAY);
        setCheckBox(wednesday, WeekDays.WEDNESDAY);
        setCheckBox(thursday, WeekDays.THURSDAY);
        setCheckBox(friday, WeekDays.FRIDAY);
        setCheckBox(saturday, WeekDays.SATURDAY);
        setCheckBox(sunday, WeekDays.SUNDAY);

        fhTitle.setText(fHabit.getTitle());
        fhReason.setText(fHabit.getReason());
        fhDate.setText(fHabit.getDate().toString());
    }

    // method to check the day boxes
    public void setCheckBox(CheckBox checkBox, int day){
        checkBox.setChecked(fHabit.getWeekDays().getDay(day));
    }

    /**
     * Starts the Activity with certain conditions.
     *  Gets the Most recent habit event and sets the text views to corresponding values.
     */
    @Override
    protected void onStart() {
        super.onStart();
        OnlineController.GetRecentEvent fRecentEvent = new OnlineController.GetRecentEvent();
        fRecentEvent.execute(fHabit.getSearchTitle(),fHabit.getOwner());
        try{
            fEvent = fRecentEvent.get();
            fEventComment.setText(fEvent.getComment());
            friendImage.setImageBitmap(fEvent.getImageBitmap());
            fEventDate.setText(fEvent.getDate().toString());
            fEventAddress.setText(fEvent.getGeolocation().getAddress());


        } catch (Exception e) {
            Log.i("Error", "Failed to get the requests from the async object");
        }

    }
}
