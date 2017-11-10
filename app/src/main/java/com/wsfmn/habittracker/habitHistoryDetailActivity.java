package com.wsfmn.habittracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wsfmn.habit.HabitCommentTooLongException;
import com.wsfmn.habit.HabitTitleTooLongException;
import com.wsfmn.habitcontroller.HabitHistoryController;
import com.wsfmn.habitcontroller.HabitListController;

public class habitHistoryDetailActivity extends AppCompatActivity {

    EditText nameEvent;
    Button addHabit;
    TextView habitName;
    Button addPicture;
    EditText comment;
    Button viewImage;
    Button confirm;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_history_detail);

        nameEvent = (EditText)findViewById(R.id.nameEvent2);
        addHabit = (Button)findViewById(R.id.addHabit2);
        habitName = (TextView) findViewById(R.id.habitName2);
        addPicture = (Button)findViewById(R.id.Picture2);
        comment = (EditText)findViewById(R.id.Comment2);
        viewImage = (Button)findViewById(R.id.ViewImg2);
        confirm = (Button)findViewById(R.id.confirmButton2);

        Intent intent = getIntent();
        Bundle b = getIntent().getExtras();
        position = b.getInt("position");

        HabitHistoryController control = HabitHistoryController.getInstance();

        nameEvent.setText(control.get(position).getId());
        habitName.setText(control.get(position).getHabitFromEvent().getTitle());
        comment.setText(control.get(position).getComment());


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(habitHistoryDetailActivity.this, HabitHistoryActivity.class);
                try {
                    HabitHistoryController control2 = HabitHistoryController.getInstance();
                    control2.get(position).setTitle(nameEvent.getText().toString());
                    control2.get(position).setComment(comment.getText().toString());
                    control2.get(position).setHabit(control2.get(position).getHabitFromEvent());
                    startActivity(intent);
                }
                catch (HabitTitleTooLongException e) {
                    Toast.makeText(habitHistoryDetailActivity.this, e.getMessage(),
                            Toast.LENGTH_LONG).show();
                } catch (HabitCommentTooLongException e) {
                    Toast.makeText(habitHistoryDetailActivity.this, e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }
        });

    }



}
