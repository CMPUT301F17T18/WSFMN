/*
 * Copyright © 2017 Team 18 (WSFMN), CMPUT301, University of Alberta – All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact nmayne@ualberta.ca.
 *
 *  Team 18 is: Musaed Alsobaie, Siddhant Khanna, Wei Li, Nicholas Mayne, Fredric Mendi.
 */

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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.wsfmn.controller.HabitHistoryController;
import com.wsfmn.controller.HabitListController;
import com.wsfmn.controller.ImageController;
import com.wsfmn.model.Date;
import com.wsfmn.model.Geolocation;
import com.wsfmn.model.Habit;
import com.wsfmn.model.HabitEvent;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * A class to handle adding and changing HabitEvents in the HabitHistory.
 */
abstract class HabitHistorySuper extends AppCompatActivity {
    static final int TAKE_PHOTO = 1;
    static final int GET_HABIT = 2;
    static final int CHANGE_LOCATION = 3;
    protected DatePickerDialog.OnDateSetListener mDateSetListener;

    HabitHistoryController HHC = HabitHistoryController.getInstance();
    HabitListController HLC = HabitListController.getInstance();
    ImageController IC = ImageController.getInstance();

    TextView title;
    TextView date;
    TextView address;
    EditText comment;
    Button addPicture;
    Button viewPicture;
    Geolocation geolocation;
    String photoPath;
    String prevPhotoPath;
    int habitIdx;
    Habit addedHabit;
    HabitEvent habitEvent;
    Boolean FIRST_PHOTO;

    /**
     * Get data back from activities
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Handle new photo with compression
        if (requestCode == TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            if (FIRST_PHOTO) {
                FIRST_PHOTO = false;
            } else {
                IC.deleteImage(prevPhotoPath); // delete the previous image
            }
            Toast.makeText(HabitHistorySuper.this, "Compressing picture...", Toast.LENGTH_LONG).show();

            photoPath = IC.compressImage(photoPath);
            viewPicture.setEnabled(true);
        }
        // Set habit idx if returned from the HabitList
        if (requestCode == GET_HABIT && resultCode == Activity.RESULT_OK) {
            habitIdx = data.getExtras().getInt("position");
            addedHabit = HLC.getHabit(habitIdx);
            title.setText(addedHabit.getTitle());
        }
        // Add Location
        if (requestCode == CHANGE_LOCATION && resultCode == Activity.RESULT_OK) {
            String new_address = data.getStringExtra("address");
            geolocation = new Geolocation(
                    new_address,
                    new LatLng(
                            data.getDoubleExtra("latitude",0),
                            data.getDoubleExtra("longitude",0)));
            address.setText(new_address);
        }
    }

    /**
     * Select a Habit for the Habit Event
     * @param view
     */
    public void changeHabit(View view){
        Intent intent = new Intent(this, SelectHabitActivity.class);
        startActivityForResult(intent, GET_HABIT);
    }

    /**
     * Begin AddLocationActivity to get a new Location
     * @param view
     */
    public void changeLocation(View view) {
        Intent intent = new Intent(this, AddLocationActivity.class);
        if (geolocation != null) {
            intent.putExtra("address", geolocation.getAddress());
            intent.putExtra("latitude", geolocation.getLatLng().latitude);
            intent.putExtra("longitude", geolocation.getLatLng().longitude);
        }
        startActivityForResult(intent, CHANGE_LOCATION);
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
     * Take a picture and save it.
     * Reuse Code for taking image: https://developer.android.com/training/camera/photobasics.html
     */
    public void dispatchTakePictureIntent(View view) {
        prevPhotoPath = photoPath;
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
                startActivityForResult(takePictureIntent, TAKE_PHOTO);
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
     * Launch the date picker widget.
     * @param view
     */
    @TargetApi(24)
    public void getDate(View view) {
        android.icu.util.Calendar calendar = android.icu.util.Calendar.getInstance();
        int year = calendar.get(android.icu.util.Calendar.YEAR);
        int month = calendar.get(android.icu.util.Calendar.MONTH);
        int day = calendar.get(android.icu.util.Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(HabitHistorySuper.this,
                android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener, year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    /**
     *
     * @return
     */
    public com.wsfmn.model.Date getDateUIHE(){
        String date_1 = date.getText().toString();
        String[] list = date_1.split(" / ");
        int year = Integer.parseInt(list[0]);
        int month = Integer.parseInt(list[1]);
        int day = Integer.parseInt(list[2]);
        //return new com.wsfmn.model.Date(year, month, day);
        Date date_2 = new Date(0, year, month, day);
        date_2.getH();
        date_2.getM();
        date_2.getS();
        return date_2;
    }
}