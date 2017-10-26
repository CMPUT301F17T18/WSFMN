package com.wsfmn.habittracker;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.nfc.Tag;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;


public class addNewHabitActivity extends AppCompatActivity {

    private final static int DAYS_OF_THE_WEEK = 1;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Context context;

    private EditText habitTitle;
    private EditText habitReason;
    private Button setDateButton;
    private Button confirmButton;
    private Date date;
    private WeekDays weekDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_habit);

        context = this;
        weekDays = new WeekDays();
        date = null;

        habitTitle = (EditText) findViewById(R.id.habitTitleEditText);
        habitReason = (EditText) findViewById(R.id.habitReasonEditText);
        setDateButton = (Button) findViewById(R.id.setDateButton);
        confirmButton = (Button) findViewById(R.id.confirmButton);


        setDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            @TargetApi(24)
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        addNewHabitActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        // sets up listener for the date UI object
        mDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                date = new Date(year, month, dayOfMonth);
                Toast.makeText(addNewHabitActivity.this, date.toString(),
                        Toast.LENGTH_LONG).show();
            }
        };

    }


    /**
     * this creates a new Habit object with values it receives from
     * the user, and sends the object back to MainActivity after
     * converting it to a string using Gson.
     */
    public void confirm(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        if(date == null)
            date = new Date();

        try {
            Habit habit = new Habit(habitTitle.getText().toString(),
                    habitReason.getText().toString(),
                    date, weekDays);
            Gson gson = new Gson();
            String object = gson.toJson(habit);
            intent.putExtra("Habit", object);


            setResult(RESULT_OK, intent);
            finish();
        }
        catch(HabitTitleTooLongException r){
            // TODO: handle exception
        }
        catch(HabitReasonTooLongException e){
            // TODO: handle exception
        }


    }


    // the setDate method uses methods that don't work
    // well with API lower than 24.
    // it might be changed later
    @TargetApi(24)
    public void setDate(){

    }


    public void mondayCheckBox(View view){
        CheckBox checkBox = (CheckBox) view;
        if(checkBox.isChecked()){
            weekDays.setDay(WeekDays.MONDAY);
        }
        else{
            weekDays.unsetDay(WeekDays.MONDAY);
        }
    }

    public void tuesdayCheckBox(View view){
        CheckBox checkBox = (CheckBox) view;
        if(checkBox.isChecked()){
            weekDays.setDay(WeekDays.TUESDAY);
        }
        else{
            weekDays.unsetDay(WeekDays.TUESDAY);
        }
    }

    public void wednesdayCheckBox(View view){
        CheckBox checkBox = (CheckBox) view;
        if(checkBox.isChecked()){
            weekDays.setDay(WeekDays.WEDNESDAY);
        }
        else{
            weekDays.unsetDay(WeekDays.WEDNESDAY);
        }
    }

    public void thursdayCheckBox(View view){
        CheckBox checkBox = (CheckBox) view;
        if(checkBox.isChecked()){
            weekDays.setDay(WeekDays.THURSDAY);
        }
        else{
            weekDays.unsetDay(WeekDays.THURSDAY);
        }
    }

    public void fridayCheckBox(View view){
        CheckBox checkBox = (CheckBox) view;
        if(checkBox.isChecked()){
            weekDays.setDay(WeekDays.FRDIAY);
        }
        else{
            weekDays.unsetDay(WeekDays.FRDIAY);
        }
    }

    public void saturdayCheckBox(View view){
        CheckBox checkBox = (CheckBox) view;
        if(checkBox.isChecked()){
            weekDays.setDay(WeekDays.SATURDAY);
        }
        else{
            weekDays.unsetDay(WeekDays.SATURDAY);
        }
    }

    public void sundayCheckBox(View view){
        CheckBox checkBox = (CheckBox) view;
        if(checkBox.isChecked()){
            weekDays.setDay(WeekDays.SUNDAY);
        }
        else{
            weekDays.unsetDay(WeekDays.SUNDAY);
        }
    }


}
