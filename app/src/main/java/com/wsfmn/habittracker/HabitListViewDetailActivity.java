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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wsfmn.habit.Date;
import com.wsfmn.habit.DateNotValidException;
import com.wsfmn.habit.Habit;
import com.wsfmn.habit.HabitReasonTooLongException;
import com.wsfmn.habit.HabitTitleTooLongException;
import com.wsfmn.habit.WeekDays;

import java.lang.reflect.Type;

public class HabitListViewDetailActivity extends AppCompatActivity {


    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Context context;

    private EditText habitTitle;
    private EditText habitReason;
    private EditText dateText;

    private Button setDateButton;
    private Button confirmButton;

    private Date date;
    private WeekDays weekDays;

    private int position;

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
        setContentView(R.layout.activity_habit_list_view_detail);

        context = this;


        habitTitle = (EditText) findViewById(R.id.habitTitleEditText);
        habitReason = (EditText) findViewById(R.id.habitReasonEditText);
        setDateButton = (Button) findViewById(R.id.setDateButton);
        confirmButton = (Button) findViewById(R.id.confirmButton);
        dateText = (EditText) findViewById(R.id.dateText);
        monday = (CheckBox) findViewById(R.id.mondayCheckBox);
        tuesday = (CheckBox) findViewById(R.id.tuesdayCheckBox);
        wednesday = (CheckBox) findViewById(R.id.wednesdayCheckBox);
        thursday = (CheckBox) findViewById(R.id.thursdayCheckBox);
        friday = (CheckBox) findViewById(R.id.fridayCheckBox);
        saturday = (CheckBox) findViewById(R.id.saturdayCheckBox);
        sunday = (CheckBox) findViewById(R.id.sundayCheckBox);

        Intent intent = getIntent();

        Bundle b = getIntent().getExtras();
        position = b.getInt("position");

        String object = b.getString("Habit");
        Gson gson = new Gson();
        Type objectType = new TypeToken<Habit>(){}.getType();
        Habit habit = gson.fromJson(object, objectType);

        habitTitle.setText(habit.getTitle());
        habitReason.setText(habit.getReason());
        date = habit.getDate();
        dateText.setText(habit.getDate().toString());
        weekDays = habit.getWeekDays();


        if(habit.getWeekDays().getDay(WeekDays.MONDAY)){
            monday.setChecked(true);
        }

        if(habit.getWeekDays().getDay(WeekDays.TUESDAY)){
            tuesday.setChecked(true);
        }

        if(habit.getWeekDays().getDay(WeekDays.WEDNESDAY)){
            wednesday.setChecked(true);
        }

        if(habit.getWeekDays().getDay(WeekDays.THURSDAY)){
            thursday.setChecked(true);
        }

        if(habit.getWeekDays().getDay(WeekDays.FRDIAY)){
            friday.setChecked(true);
        }

        if(habit.getWeekDays().getDay(WeekDays.SATURDAY)){
            saturday.setChecked(true);
        }

        if(habit.getWeekDays().getDay(WeekDays.SUNDAY)){
            sunday.setChecked(true);
        }

        setDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            @TargetApi(24)
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        HabitListViewDetailActivity.this,
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
        Intent intent = new Intent(this, MainActivity.class);

        try {
            Habit habit = new Habit(habitTitle.getText().toString(),
                    habitReason.getText().toString(),
                    date, weekDays);

            Gson gson = new Gson();
            String object = gson.toJson(habit);
            intent.putExtra("Habit", object);
            intent.putExtra("position", position);
            intent.putExtra("delete", false);

            setResult(RESULT_OK, intent);
            finish();
        } catch (HabitTitleTooLongException e) {
            Toast.makeText(HabitListViewDetailActivity.this, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } catch (HabitReasonTooLongException e) {
            Toast.makeText(HabitListViewDetailActivity.this, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } catch (DateNotValidException e) {
            Toast.makeText(HabitListViewDetailActivity.this, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     *  called when delete button is clicked
     *  informs HabitListView that delete button
     *  was clicked
     */

    public void delete(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("delete", true);  //  used to indicate that delete button was clicked
        setResult(RESULT_OK, intent);
        finish();
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
