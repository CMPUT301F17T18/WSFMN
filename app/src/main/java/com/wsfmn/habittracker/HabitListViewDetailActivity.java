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
import com.wsfmn.habitcontroller.HabitListController;
import com.wsfmn.habit.HabitReasonTooLongException;
import com.wsfmn.habit.HabitTitleTooLongException;
import com.wsfmn.habit.WeekDays;

import static com.wsfmn.habittracker.R.id.fridayCheckBox;
import static com.wsfmn.habittracker.R.id.mondayCheckBox;
import static com.wsfmn.habittracker.R.id.saturdayCheckBox;
import static com.wsfmn.habittracker.R.id.sundayCheckBox;
import static com.wsfmn.habittracker.R.id.thursdayCheckBox;
import static com.wsfmn.habittracker.R.id.tuesdayCheckBox;
import static com.wsfmn.habittracker.R.id.wednesdayCheckBox;

public class HabitListViewDetailActivity extends AppCompatActivity {


    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Context context;

    private EditText habitTitle;
    private EditText habitReason;
    private EditText dateText;

    private Button setDateButton;
    private Button confirmButton;

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
        tuesday = (CheckBox) findViewById(tuesdayCheckBox);
        wednesday = (CheckBox) findViewById(wednesdayCheckBox);
        thursday = (CheckBox) findViewById(thursdayCheckBox);
        friday = (CheckBox) findViewById(fridayCheckBox);
        saturday = (CheckBox) findViewById(saturdayCheckBox);
        sunday = (CheckBox) findViewById(sundayCheckBox);

        Intent intent = getIntent();
        Bundle b = getIntent().getExtras();
        position = b.getInt("position");

        HabitListController c = new HabitListController();

        habitTitle.setText(c.getHabit(position).getTitle());
        habitReason.setText(c.getHabit(position).getReason());
        dateText.setText(c.getHabit(position).getDate().toString());


        monday.setChecked(c.getHabit(position).getWeekDays().getDay(WeekDays.MONDAY));
        tuesday.setChecked(c.getHabit(position).getWeekDays().getDay(WeekDays.TUESDAY));
        wednesday.setChecked(c.getHabit(position).getWeekDays().getDay(WeekDays.WEDNESDAY));
        thursday.setChecked(c.getHabit(position).getWeekDays().getDay(WeekDays.THURSDAY));
        friday.setChecked(c.getHabit(position).getWeekDays().getDay(WeekDays.FRDIAY));
        saturday.setChecked(c.getHabit(position).getWeekDays().getDay(WeekDays.SATURDAY));
        sunday.setChecked(c.getHabit(position).getWeekDays().getDay(WeekDays.SUNDAY));


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
                HabitListController c = new HabitListController();
                try {
                    c.getHabit(position).setDate(new Date(year, month, dayOfMonth));
                }
                catch(DateNotValidException e){
                    Toast.makeText(HabitListViewDetailActivity.this, e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                dateText.setText(c.getHabit(position).getDate().toString());
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

            HabitListController c = new HabitListController();
            c.getHabit(position).setTitle(habitTitle.getText().toString());
            c.getHabit(position).setReason(habitReason.getText().toString());
            c.store();

            startActivity(intent);

        } catch (HabitTitleTooLongException e) {
            Toast.makeText(HabitListViewDetailActivity.this, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } catch (HabitReasonTooLongException e) {
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
        Intent intent = new Intent(this, HabitListViewActivity.class);
        HabitListController c = new HabitListController();
        c.deleteHabitAt(position);
        c.store();
        startActivity(intent);
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
            c.getHabit(position).getWeekDays().setDay(day);
        }
        else{
            c.getHabit(position).getWeekDays().unsetDay(day);
        }
    }



}
