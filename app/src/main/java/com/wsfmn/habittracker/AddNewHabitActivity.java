package com.wsfmn.habittracker;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
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
import static com.wsfmn.habittracker.R.id.saturdayCheckBox;
import static com.wsfmn.habittracker.R.id.sundayCheckBox;
import static com.wsfmn.habittracker.R.id.thursdayCheckBox;
import static com.wsfmn.habittracker.R.id.tuesdayCheckBox;
import static com.wsfmn.habittracker.R.id.wednesdayCheckBox;


public class AddNewHabitActivity extends AppCompatActivity {


    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private EditText habitTitle;
    private EditText habitReason;
    private EditText dateText;
    private Button setDateButton;
    private Button confirmButton;

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
        setContentView(R.layout.activity_add_new_habit);


        habitTitle = (EditText) findViewById(R.id.habitTitleEditText);
        habitReason = (EditText) findViewById(R.id.habitReasonEditText);
        setDateButton = (Button) findViewById(R.id.setDateButton);
        confirmButton = (Button) findViewById(R.id.confirmButton2);
        dateText = (EditText) findViewById(R.id.dateText);

        monday = (CheckBox) findViewById(R.id.mondayCheckBox);
        tuesday = (CheckBox) findViewById(tuesdayCheckBox);
        wednesday = (CheckBox) findViewById(wednesdayCheckBox);
        thursday = (CheckBox) findViewById(thursdayCheckBox);
        friday = (CheckBox) findViewById(fridayCheckBox);
        saturday = (CheckBox) findViewById(saturdayCheckBox);
        sunday = (CheckBox) findViewById(sundayCheckBox);

        dateText.setText(new Date().toString());

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
                dateText.setText(year + " / " + month + " / " + dayOfMonth);
            }
        };

    }


    /**
     * this creates a new Habit object with values it receives from
     * the user, and adds is to the list of habits.
     */
    public void confirm(View view) {
        Intent intent = new Intent(this, HabitListViewActivity.class);

        try {

            WeekDays w = new WeekDays();

            setUnset(w, monday, WeekDays.MONDAY);
            setUnset(w, tuesday, WeekDays.TUESDAY);
            setUnset(w, wednesday, WeekDays.WEDNESDAY);
            setUnset(w, thursday, WeekDays.THURSDAY);
            setUnset(w, friday, WeekDays.FRIDAY);
            setUnset(w, saturday, WeekDays.SATURDAY);
            setUnset(w, sunday, WeekDays.SUNDAY);

            Habit habit = new Habit(habitTitle.getText().toString(),
                    habitReason.getText().toString(),
                    getDateUI(), w);
            HabitListController c = HabitListController.getInstance();

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

    public void setUnset(WeekDays weekDays, CheckBox checkBox, int day){
        if(checkBox.isChecked())
            weekDays.setDay(day);
        else
            weekDays.unsetDay(day);
    }

    public Date getDateUI(){
        String date = dateText.getText().toString();
        String[] list = date.split(" / ");
        int year = Integer.parseInt(list[0]);
        int month = Integer.parseInt(list[1]);
        int day = Integer.parseInt(list[2]);
        return new Date(year, month, day);
    }


}
