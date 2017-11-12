package com.wsfmn.habittracker;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wsfmn.habit.HabitCommentTooLongException;
import com.wsfmn.habit.HabitEventCommentTooLongException;
import com.wsfmn.habit.HabitEventNameException;
import com.wsfmn.habit.HabitTitleTooLongException;
import com.wsfmn.habitcontroller.HabitHistoryController;
import com.wsfmn.habitcontroller.HabitListController;

import java.io.File;
import java.io.IOException;

import static com.wsfmn.habittracker.HabitEventActivity.REQUEST_TAKE_PHOTO;

public class habitHistoryDetailActivity extends AppCompatActivity {

    EditText nameEvent;
    Button addHabit;
    TextView habitName;
    Button addPicture;
    EditText comment;
    Button viewImage;
    Button confirm;
    TextView date;
    int position2;
    int i;

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
        date = (TextView)findViewById(R.id.dateDetail);

        Intent intent = getIntent();
        Bundle b = getIntent().getExtras();
        position2 = b.getInt("position");

        HabitHistoryController control = HabitHistoryController.getInstance();

        try {
            nameEvent.setText(control.get(position2).getHabitEventTitle());
        } catch (HabitEventNameException e) {
            Toast.makeText(habitHistoryDetailActivity.this, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
        habitName.setText(control.get(position2).getHabitFromEvent().getTitle());
        try {
            comment.setText(control.get(position2).getComment());
        } catch (HabitEventCommentTooLongException e) {
            e.printStackTrace();
        }
        date.setText(control.get(position2).getDate());

    }

    public void confirmHE(View view){
        Intent intent = new Intent(this, HabitHistoryActivity.class);
        try {
            HabitHistoryController control2 = HabitHistoryController.getInstance();
            control2.get(position2).setTitle(nameEvent.getText().toString());
            control2.get(position2).setComment(comment.getText().toString());
            control2.get(position2).setHabit(control2.get(position2).getHabitFromEvent());
            control2.store();
            control2.storeAndUpdate(control2.get(position2));
            startActivity(intent);
        } catch (HabitEventCommentTooLongException e) {
            Toast.makeText(habitHistoryDetailActivity.this, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } catch (HabitEventNameException e) {
            Toast.makeText(habitHistoryDetailActivity.this, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void deleteHE(View view){
        Intent intent = new Intent(habitHistoryDetailActivity.this, HabitHistoryActivity.class);
        HabitHistoryController control3 = HabitHistoryController.getInstance();
        control3.remove(position2);
        control3.store();
        startActivity(intent);
    }

    public void changeHabit(View view){
        Intent intent = new Intent(this, selectHabitActivity.class);
        startActivityForResult(intent, 2);
    }

    public void viewImage2(View view){
        Intent intent = new Intent(habitHistoryDetailActivity.this, imageActivity.class);
        HabitHistoryController control4 = HabitHistoryController.getInstance();
        intent.putExtra("mCurrentPhotoPath", control4.get(position2).getmCurrentPhotoPath());
        startActivity(intent);
    }

    public void changePicture2(View view){
        HabitHistoryController control4 = HabitHistoryController.getInstance();
        dispatchTakePictureIntent(control4.get(position2).getmCurrentPhotoPath());
    }


    private void dispatchTakePictureIntent(String mCurrentPhotoPath) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            photoFile = new File(mCurrentPhotoPath);
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==2){
            if(resultCode == Activity.RESULT_OK) {
                //intent3 = data.getIntent();
                Bundle b = data.getExtras();
                i = b.getInt("position");
                TextView nameHabit = (TextView)findViewById(R.id.habitName2);
                HabitListController control = HabitListController.getInstance();
                nameHabit.setText(control.getHabit(i).getTitle().toString());
                HabitHistoryController control2 = HabitHistoryController.getInstance();
                control2.get(position2).setHabit(control.getHabit(i));
            }
        }
    }
}
