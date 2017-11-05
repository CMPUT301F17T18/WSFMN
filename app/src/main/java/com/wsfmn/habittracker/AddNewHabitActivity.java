package com.wsfmn.habittracker;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


import com.wsfmn.habit.Date;
import com.wsfmn.habit.DateNotValidException;
import com.wsfmn.habit.Habit;
import com.wsfmn.habit.HabitReasonTooLongException;
import com.wsfmn.habit.HabitTitleTooLongException;
import com.wsfmn.habit.WeekDays;
import com.wsfmn.habitcontroller.HabitListController;

import static com.wsfmn.habittracker.R.id.fridayCheckBox;
import static com.wsfmn.habittracker.R.id.mondayCheckBox;
import static com.wsfmn.habittracker.R.id.saturdayCheckBox;
import static com.wsfmn.habittracker.R.id.sundayCheckBox;
import static com.wsfmn.habittracker.R.id.thursdayCheckBox;
import static com.wsfmn.habittracker.R.id.tuesdayCheckBox;
import static com.wsfmn.habittracker.R.id.wednesdayCheckBox;


public class AddNewHabitActivity extends AppCompatActivity {

    private final static int DAYS_OF_THE_WEEK = 1;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Context context;

    private EditText habitTitle;
    private EditText habitReason;
    private EditText dateText;
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
        date = new Date();

        habitTitle = (EditText) findViewById(R.id.habitTitleEditText);
        habitReason = (EditText) findViewById(R.id.habitReasonEditText);
        setDateButton = (Button) findViewById(R.id.setDateButton);
        confirmButton = (Button) findViewById(R.id.confirmButton);
        dateText = (EditText) findViewById(R.id.dateText);

        dateText.setText(date.toString());

        setDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            @TargetApi(24)
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddNewHabitActivity.this,
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
                date.setYear(year);
                date.setMonth(month);
                date.setDay(dayOfMonth);
                dateText.setText(date.toString());
            }
        };

    }


    /**
     * this creates a new Habit object with values it receives from
     * the user, and sends the object back to MainActivity after
     * converting it to a string using Gson.
     */
    public void confirm(View view) {
        Intent intent = new Intent(this, HabitListViewActivity.class);

        try {
            Habit habit = new Habit(habitTitle.getText().toString(),
                    habitReason.getText().toString(),
                    date, weekDays);
            HabitListController c = new HabitListController();
            c.addHabit(habit);
            c.store();
            startActivity(intent);
        }
        catch(HabitTitleTooLongException e){
            Toast.makeText(AddNewHabitActivity.this, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
        catch(HabitReasonTooLongException e){
            Toast.makeText(AddNewHabitActivity.this, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
        catch(DateNotValidException e){
            Toast.makeText(AddNewHabitActivity.this, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }



    public void checkBox(View view){
        int day = 0;
        CheckBox checkBox = (CheckBox) view;
        HabitListController c = new HabitListController();

        if(checkBox.getId() == mondayCheckBox){
            day = WeekDays.MONDAY;
        }
        else if(checkBox.getId() == tuesdayCheckBox){
            day = WeekDays.TUESDAY;
        }
        else if(checkBox.getId() == wednesdayCheckBox){
            day = WeekDays.WEDNESDAY;
        }
        else if(checkBox.getId() == thursdayCheckBox){
            day = WeekDays.THURSDAY;
        }
        else if(checkBox.getId() == fridayCheckBox){
            day = WeekDays.FRDIAY;
        }
        else if(checkBox.getId() == saturdayCheckBox){
            day = WeekDays.SATURDAY;
        }
        else if(checkBox.getId() == sundayCheckBox){
            day = WeekDays.SUNDAY;
        }

        if(checkBox.isChecked()){
            weekDays.setDay(day);
        }
        else{
            weekDays.unsetDay(day);
        }
    }


}
