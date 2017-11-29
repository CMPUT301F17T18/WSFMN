package com.wsfmn.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.wsfmn.controller.HabitListController;
import com.wsfmn.model.Habit;
import com.wsfmn.model.Date;
import com.wsfmn.model.WeekDays;



public class FriendHabitActivity extends AppCompatActivity {

    private Habit fHabit;
    EditText fhTitle;
    EditText fhReason;
    EditText fhDate;

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

        fhTitle.setClickable(true);
        fhReason.setClickable(true);
        fhDate.setClickable(true);
        monday.setClickable(true);
        tuesday.setClickable(true);
        wednesday.setClickable(true);
        thursday.setClickable(true);
        friday.setClickable(true);
        saturday.setClickable(true);
        sunday.setClickable(true);


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


}
