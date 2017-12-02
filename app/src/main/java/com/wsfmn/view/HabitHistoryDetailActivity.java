package com.wsfmn.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.wsfmn.model.Date;

import com.google.android.gms.maps.model.LatLng;
import com.wsfmn.exceptions.HabitEventCommentTooLongException;
import com.wsfmn.exceptions.HabitEventNameException;
import com.wsfmn.controller.HabitHistoryController;
import com.wsfmn.controller.HabitListController;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import static com.wsfmn.view.AddNewHabitEventActivity.REQUEST_TAKE_PHOTO;

/**
 * Called when the user wants to edit an existing Habit Event
 * @version 1.0
 * @see AppCompatActivity
 */
public class HabitHistoryDetailActivity extends AppCompatActivity {

    Button addHabit;
    TextView habitName;
    Button addPicture;
    EditText comment;
    Button viewImage;
    Button confirm;
    TextView date;
    TextView T_address;
    String id2;
    int position;
    Button B_changeLocation;
    static final int CHANGE_LOCATION_CODE = 3;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_history_detail);

        //Declaring variables for the UI
        addHabit = (Button)findViewById(R.id.addHabit2);
        habitName = (TextView) findViewById(R.id.habitName2);
        addPicture = (Button)findViewById(R.id.Picture2);
        comment = (EditText)findViewById(R.id.Comment2);
        viewImage = (Button)findViewById(R.id.ViewImg2);
        confirm = (Button)findViewById(R.id.confirmButton2);
        date = (TextView)findViewById(R.id.dateDetail);
        B_changeLocation = (Button)findViewById(R.id.B_changeLocation);
        T_address = (TextView)findViewById(R.id.T_C_showAddress);

        Intent intent = getIntent();
        Bundle b = getIntent().getExtras();
        try {
            //Getting the position of Habit Event the user selected
            id2 = b.getString("id");
        }catch (NullPointerException e){
            //TODO Can we fix this instead fo catching a NullPointerException?
        }

        HabitHistoryController control = HabitHistoryController.getInstance();
        try {
            habitName.setText(control.get(id2).getHabitFromEvent().getTitle());
            comment.setText(control.get(id2).getComment());
            if (control.get(id2).getGeolocation() != null) {
                T_address.setText(control.get(id2).getGeolocation().getAddress());
            }
            date.setText(new com.wsfmn.model.Date().toString());
        } catch(IndexOutOfBoundsException e){
            //TODO Can we fix this instead fo catching an IndexOutOfBoundsException?
        }

        Button setNewDate = (Button)findViewById(R.id.changeDate);
        setNewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            @TargetApi(24)
            public void onClick(View v) {
                android.icu.util.Calendar calendar = android.icu.util.Calendar.getInstance();
                int year = calendar.get(android.icu.util.Calendar.YEAR);
                int month = calendar.get(android.icu.util.Calendar.MONTH);
                int day = calendar.get(android.icu.util.Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(HabitHistoryDetailActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                date.setText(year + " / " + month + " / " + dayOfMonth);
                date.getText();
            }
        };
    }

    /**
     * Confirm the changes fot the Habit Event
     * @param view
     */
    public void confirmHE(View view){
        Intent intent = new Intent(this, ViewHabitHistoryActivity.class);
        try {
            //Set the habitEvent parameters that the user gets
            HabitHistoryController control2 = HabitHistoryController.getInstance();
            control2.get(id2).setTitle(habitName.getText().toString());
            control2.get(id2).setComment(comment.getText().toString());
            control2.get(id2).setHabit(control2.get(id2).getHabitFromEvent());
            control2.get(id2).setDate(getDateUIHED());
//            control2.get(id2).setActualCurrentPhotoPath(CurrentPhotoPath);
            control2.storeAndUpdate(control2.get(id2));
            startActivity(intent);
        } catch (HabitEventCommentTooLongException e) {
            Toast.makeText(HabitHistoryDetailActivity.this, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } catch (HabitEventNameException e) {
            Toast.makeText(HabitHistoryDetailActivity.this, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Delete the Habit Event from the Habit Event History
     * @param view
     */
    public void deleteHE(View view){
        Intent intent = new Intent(HabitHistoryDetailActivity.this, ViewHabitHistoryActivity.class);
        HabitHistoryController control3 = HabitHistoryController.getInstance();
        control3.removeAndStore(control3.get(id2));
        startActivity(intent);
    }

    /**
     * Change the Habit for the Habit Event
     * @param view
     */
    public void changeHabit(View view){
        Intent intent = new Intent(this, SelectHabitActivity.class);
        startActivityForResult(intent, 2);
    }

    //Has the path that is to be calculated in detail
    String CurrentPhotoPath;
    //Has the path already present
    String path;

    /**
     * View the image of the HabitEvent
     * @param view
     */
    public void viewImage2(View view){
        Intent intent = new Intent(HabitHistoryDetailActivity.this, AddImageActivity.class);
        HabitHistoryController control4 = HabitHistoryController.getInstance();
        path = control4.get(id2).getActualCurrentPhotoPath();
        //If no picture taken before then when it is null value we create new image
        if(path == null) {
            path = CurrentPhotoPath;
        }
        intent.putExtra("CurrentPhotoPath",path);
        startActivity(intent);
    }

    /**
     * Changes the picture of the habit Event
     * @param view
     * @throws IOException
     */
    public void changePicture2(View view) throws IOException {
        try {
            HabitHistoryController control4 = HabitHistoryController.getInstance();
            dispatchTakePictureIntent(control4.get(id2).getActualCurrentPhotoPath());
        }catch (NullPointerException e){
            /*
            Reuse Code: https://developer.android.com/training/camera/photobasics.html
             */
            dispatchTakePictureIntent(createImageFile());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    /**
     * Image file created
     */
    private String createImageFile() throws IOException {
        // Create an image file name
        String timeStamp;
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir);    /* directory */

        // Save a file: path for use with ACTION_VIEW intents
        CurrentPhotoPath = image.getAbsolutePath();
        return CurrentPhotoPath;
    }

    /**
     * take picture and save it in a file
     * @param CurrentPhotoPath
     */
    private void dispatchTakePictureIntent(String CurrentPhotoPath) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            photoFile = new File(CurrentPhotoPath);
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

    /**
     * Getting the balue from the activities
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==2){
            if(resultCode == Activity.RESULT_OK) {
                Bundle b = data.getExtras();
                position = b.getInt("position");
                TextView nameHabit = (TextView)findViewById(R.id.habitName2);
                HabitListController control = HabitListController.getInstance();
                nameHabit.setText(control.getHabit(position).getTitle().toString());
                HabitHistoryController control2 = HabitHistoryController.getInstance();
                control2.get(id2).setHabit(control.getHabit(position));
            }
        }

        if(requestCode == CHANGE_LOCATION_CODE && resultCode == Activity.RESULT_OK) {
            Bundle b = data.getExtras();
            Double latitude = b.getDouble("change_latitude");
            Double longtitude = b. getDouble("change_longtitude");
            String address = b.getString("change_address");

            T_address.setText(address);
            LatLng latLng = new LatLng(latitude,longtitude);
        }
    }

    public com.wsfmn.model.Date getDateUIHED(){
        String dateD = date.getText().toString();
        String[] list = dateD.split(" / ");
        int year = Integer.parseInt(list[0]);
        int month = Integer.parseInt(list[1]);
        int day = Integer.parseInt(list[2]);
        //return new com.wsfmn.model.Date(year, month, day);
        Date date3 = new Date(0, year, month, day);
        date3.getH();
        date3.getM();
        date3.getS();
        return date3;
    }
}
