package com.wsfmn.view;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wsfmn.controller.ProfileNameController;
import com.wsfmn.model.Date;
import com.wsfmn.exceptions.DateNotValidException;
import com.wsfmn.model.Habit;
import com.wsfmn.exceptions.HabitReasonTooLongException;
import com.wsfmn.exceptions.HabitTitleTooLongException;
import com.wsfmn.model.WeekDays;
import com.wsfmn.controller.HabitListController;

import static com.wsfmn.view.R.id.fridayCheckBox;
import static com.wsfmn.view.R.id.saturdayCheckBox;
import static com.wsfmn.view.R.id.sundayCheckBox;
import static com.wsfmn.view.R.id.thursdayCheckBox;
import static com.wsfmn.view.R.id.tuesdayCheckBox;
import static com.wsfmn.view.R.id.wednesdayCheckBox;


/**
 *  Add a new Habit to the User's.
 */
public class AddNewHabitActivity extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private EditText habitTitle;
    private EditText habitReason;
    private TextView dateText;
    private Button setDateButton;
    private CheckBox monday;
    private CheckBox tuesday;
    private CheckBox wednesday;
    private CheckBox thursday;
    private CheckBox friday;
    private CheckBox saturday;
    private CheckBox sunday;
    private Boolean checkedAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_habit);

        habitTitle = (EditText) findViewById(R.id.habitTitleEditText);
        habitReason = (EditText) findViewById(R.id.habitReasonEditText);
        setDateButton = (Button) findViewById(R.id.setDateButton);
        dateText = (TextView) findViewById(R.id.dateText);

        monday = (CheckBox) findViewById(R.id.mondayCheckBox);
        tuesday = (CheckBox) findViewById(tuesdayCheckBox);
        wednesday = (CheckBox) findViewById(wednesdayCheckBox);
        thursday = (CheckBox) findViewById(thursdayCheckBox);
        friday = (CheckBox) findViewById(fridayCheckBox);
        saturday = (CheckBox) findViewById(saturdayCheckBox);
        sunday = (CheckBox) findViewById(sundayCheckBox);

        checkedAll = false;

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

        // setup listener for the date UI object
        mDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                dateText.setText(year + " / " + month + " / " + dayOfMonth);
            }
        };
    }

    /**
     * Create a new Habit object with values from the user, and add it to the list of habits.
     *
     * @param view
     */
    public void confirm(View view) {
        Intent intent = new Intent(this, ViewHabitListActivity.class);

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
                                    getDateUI(),
                                    w);

            HabitListController.getInstance().addAndStore(habit);   // save habit
            ProfileNameController.getInstance().updateScore();      // update user's score

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

    /**
     * Set the weekDays for doing a Habit
     * @param weekDays
     * @param checkBox
     * @param day
     */
    public void setUnset(WeekDays weekDays, CheckBox checkBox, int day){
        if(checkBox.isChecked())
            weekDays.setDay(day);
        else
            weekDays.unsetDay(day);
    }

    /**
     * Check or unchceck every date box.
     * @param view
     */
    public void everyDay(View view){
        if (!checkedAll) {
            checkedAll = true;
            monday.setChecked(true);
            tuesday.setChecked(true);
            wednesday.setChecked(true);
            thursday.setChecked(true);
            friday.setChecked(true);
            saturday.setChecked(true);
            sunday.setChecked(true);
        } else {
            checkedAll = false;
            monday.setChecked(false);
            tuesday.setChecked(false);
            wednesday.setChecked(false);
            thursday.setChecked(false);
            friday.setChecked(false);
            saturday.setChecked(false);
            sunday.setChecked(false);
        }
    }

    /**
     *
     * @return
     */
    public Date getDateUI(){
        String date = dateText.getText().toString();
        String[] list = date.split(" / ");
        int year = Integer.parseInt(list[0]);
        int month = Integer.parseInt(list[1]);
        int day = Integer.parseInt(list[2]);
        return new Date(year, month, day);
    }

    /**
     * Handle Action Bar up button click as a normal back button click
     *
     * https://stackoverflow.com/questions/11079718/action-bars-onclick-listener-for-the-home-button
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Go back to MainActivity if the HabitList is empty,
     * otherwise go back to ViewHabitListActivity
     */
    @Override
    public void onBackPressed() {
        if (HabitListController.getInstance().isEmpty()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        super.onBackPressed();
    }
}