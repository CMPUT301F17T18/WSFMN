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



public class FriendHabitActivity extends AppCompatActivity {

    private Habit fHabit;
    private HabitEvent fEvent;
    EditText fhTitle;
    EditText fhReason;
    EditText fhDate;
    EditText fEventComment;
    EditText fEventDate;
    EditText fEventAddress;
    ImageView friendImage;

    private CheckBox monday;
    private CheckBox tuesday;
    private CheckBox wednesday;
    private CheckBox thursday;
    private CheckBox friday;
    private CheckBox saturday;
    private CheckBox sunday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_habit);

        Intent intent = getIntent();
        fHabit = (Habit) intent.getSerializableExtra("friend");


        fhTitle = (EditText)findViewById(R.id.fhTitle);
        fhReason = (EditText)findViewById(R.id.fhReason);
        fhDate = (EditText) findViewById(R.id.fhDate);
        monday = (CheckBox) findViewById(R.id.monday);
        tuesday = (CheckBox) findViewById(R.id.tuesday);
        wednesday = (CheckBox) findViewById(R.id.wednesday);
        thursday = (CheckBox) findViewById(R.id.thursday);
        friday = (CheckBox) findViewById(R.id.friday);
        saturday = (CheckBox) findViewById(R.id.saturday);
        sunday = (CheckBox) findViewById(R.id.sunday);

        fEventComment = (EditText) findViewById(R.id.fEventComment);
        fEventDate = (EditText) findViewById(R.id.fEventDate);
        fEventAddress = (EditText) findViewById(R.id.fEventAddress);

        friendImage = (ImageView) findViewById(R.id.friendImage);

        fhTitle.setClickable(false);
        fhReason.setClickable(false);
        fhDate.setClickable(false);
        monday.setClickable(false);
        tuesday.setClickable(false);
        wednesday.setClickable(false);
        thursday.setClickable(false);
        friday.setClickable(false);
        saturday.setClickable(false);
        sunday.setClickable(false);
        fEventComment.setClickable(false);
        fEventDate.setClickable(false);
        fEventAddress.setClickable(false);


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

    public void setCheckBox(CheckBox checkBox, int day){
        checkBox.setChecked(fHabit.getWeekDays().getDay(day));
    }

    @Override
    protected void onStart() {
        super.onStart();
        OnlineController.GetRecentEvent fRecentEvent = new OnlineController.GetRecentEvent();
        fRecentEvent.execute(fHabit.getTitle().toLowerCase(),fHabit.getOwner());
        try{
            fEvent = fRecentEvent.get();
            fEventComment.setText(fEvent.getComment());
            fEventDate.setText(fEvent.getDate().toString());
            fEventAddress.setText(fEvent.getGeolocation().getAddress());
            friendImage.setImageBitmap(fEvent.getImageBitmap());

        } catch (Exception e) {
            Log.i("Error", "Failed to get the requests from the async object");
        }

    }
}
