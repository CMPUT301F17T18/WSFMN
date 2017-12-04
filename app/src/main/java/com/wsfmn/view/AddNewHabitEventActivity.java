package com.wsfmn.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wsfmn.controller.ImageController;
import com.wsfmn.controller.ProfileNameController;
import com.wsfmn.model.Date;

import com.google.android.gms.maps.model.LatLng;
import com.wsfmn.model.Geolocation;
import com.wsfmn.model.Habit;
import com.wsfmn.exceptions.HabitCommentTooLongException;
import com.wsfmn.model.HabitEvent;
import com.wsfmn.exceptions.HabitEventCommentTooLongException;
import com.wsfmn.controller.HabitHistoryController;
import com.wsfmn.controller.HabitListController;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents when the user Adds a Habit Event for a habit
 * @version 1.0
 * @see Activity
 */
public class AddNewHabitEventActivity extends AppCompatActivity {

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int GOT_HABIT_FROM_LIST = 2;
    static final int ADD_NEW_LOCATION_CODE = 3;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    HabitHistoryController HHC = HabitHistoryController.getInstance();
    HabitListController HLC = HabitListController.getInstance();
    ImageController IC = ImageController.getInstance();


    EditText comment;
    TextView address;
    TextView title;
    TextView date;
    Button addPicture;
    Button viewPicture;
    String photoPath;
    Geolocation geolocation;
    Habit addedHabit;
    int habitIdx;
    /**
     * Setup the activity for adding a new HabitEvent to HabitHistory.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_habit_event);

        // Variables for the UI
        title = (TextView)findViewById(R.id.habitName);
        comment = (EditText) findViewById(R.id.Comment);
        date = (TextView) findViewById(R.id.eventDate);
        address = (TextView) findViewById(R.id.T_showAdress);
        addPicture = (Button) findViewById(R.id.Picture);
        viewPicture = (Button) findViewById(R.id.ViewImg);


        // Check if the Habit has been set by a call from the HabitForTodayActivity
        Bundle b = getIntent().getExtras();
        if(b.getString("caller").equals("TODAY")) {
            habitIdx = b.getInt("positionToday");

            addedHabit = HLC.getHabitsForToday().get(habitIdx);
            title.setText(addedHabit.getTitle().toString());
        }

        // Set date for the HabitEvent created
        String dateAndTime = new Date(0).toString();
        date.setText(dateAndTime);

        // Check if device has a camera
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            addPicture.setEnabled(false);
        }

        // React when the date has been set
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
     * Launch the date picker widget.
     * @param view
     */
    @TargetApi(24)
    public void getDate(View view) {
        android.icu.util.Calendar calendar = android.icu.util.Calendar.getInstance();
        int year = calendar.get(android.icu.util.Calendar.YEAR);
        int month = calendar.get(android.icu.util.Calendar.MONTH);
        int day = calendar.get(android.icu.util.Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(AddNewHabitEventActivity.this,
                android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener, year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    /**
     * Get the Location from AddLocationActivity.
     * @param view
     */
    public void getLocation(View view) {
        Intent intent = new Intent(AddNewHabitEventActivity.this, AddLocationActivity.class);
        if (geolocation != null) {
            intent.putExtra("address", geolocation.getAddress());
            intent.putExtra("latitude", geolocation.getLatLng().latitude);
            intent.putExtra("longitude", geolocation.getLatLng().longitude);
        }
        startActivityForResult(intent, ADD_NEW_LOCATION_CODE);
    }

    /**
     * Take a picture and save it.
     * Reuse Code for taking image: https://developer.android.com/training/camera/photobasics.html
     */
    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                //Get the File name
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("Error" , "File creation error");
            }
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
     *
     * @return
     * @throws IOException
     */
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp;
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        photoPath = image.getAbsolutePath();
        return image;
    }

    /**
     * Select the Habit
     * @param view
     */
    public void selectHabit(View view){
        Intent intent = new Intent(this, SelectHabitActivity.class);
        startActivityForResult(intent, 2);
    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Compress photo
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            photoPath = IC.compressImage(photoPath);
        }
        // Set habit idx if returned from the HabitList
        if (requestCode == GOT_HABIT_FROM_LIST && resultCode == Activity.RESULT_OK) {
            habitIdx = data.getExtras().getInt("position");
            addedHabit = HLC.getHabit(habitIdx);
            title.setText(addedHabit.getTitle());
        }
        // Add Location
        if(requestCode == ADD_NEW_LOCATION_CODE && resultCode == Activity.RESULT_OK) {
            String address = data.getStringExtra("address");
            geolocation = new Geolocation(
                                        address,
                                        new LatLng(
                                                data.getDoubleExtra("latitude",0),
                                                data.getDoubleExtra("longitude",0)));
            this.address.setText(address);
        }
    }

    /**
     * Create and save a new valid HabitEVent with the current on-screen parameters.
     * @param view
     */
    public void confirmHabitEvent(View view) {
        Intent intent = new Intent(this, ViewHabitHistoryActivity.class);

        // Encode image as a string for online storage
        String photoStringEncoding = IC.convertImageToString(photoPath);
        try {
            //CREATE THE NEW HABIT EVENT
            HabitEvent NEW_HABIT_EVENT = new HabitEvent(
                                                        addedHabit,
                                                        addedHabit.getTitle(),
                                                        comment.getText().toString(),
                                                        photoStringEncoding,
                                                        photoPath,
                                                        getDateUIHE(),
                                                        geolocation);

            //Add the Habit Event to HabitHistory
            HHC.addAndStore(NEW_HABIT_EVENT);
            HHC.storeAll();

            // Update User's Score
            ProfileNameController.getInstance().updateScore();
            startActivity(intent);

        }catch(HabitCommentTooLongException e){
            e.printStackTrace();
        }catch(HabitEventCommentTooLongException e){
            Toast.makeText(AddNewHabitEventActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }catch(NullPointerException e){
            Toast.makeText(AddNewHabitEventActivity.this, "Habit Event needs to contain Habit", Toast.LENGTH_LONG).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * viewing image that the user took for the habit event
     * @param view
     */
    public void viewPicture(View view){
        Intent intent = new Intent(this, AddImageActivity.class);
        intent.putExtra("CurrentPhotoPath", photoPath);
        startActivity(intent);
    }

    /**
     *
     * @return
     */
    public com.wsfmn.model.Date getDateUIHE(){
        String date = this.date.getText().toString();
        String[] list = date.split(" / ");
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