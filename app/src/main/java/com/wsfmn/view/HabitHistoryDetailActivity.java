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
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wsfmn.controller.ImageController;
import com.wsfmn.model.Date;

import com.google.android.gms.maps.model.LatLng;
import com.wsfmn.exceptions.HabitEventCommentTooLongException;
import com.wsfmn.exceptions.HabitEventNameException;
import com.wsfmn.controller.HabitHistoryController;
import com.wsfmn.controller.HabitListController;
import com.wsfmn.model.Geolocation;
import com.wsfmn.model.Habit;
import com.wsfmn.model.HabitEvent;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Called when the user wants to edit an existing Habit Event
 * @version 1.0
 * @see AppCompatActivity
 */
public class HabitHistoryDetailActivity extends AppCompatActivity {

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int GOT_HABIT_FROM_LIST = 2;
    static final int CHANGE_LOCATION_CODE = 3;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    HabitHistoryController HHC = HabitHistoryController.getInstance();
    HabitListController HLC = HabitListController.getInstance();
    ImageController IC = ImageController.getInstance();

    EditText comment;
    TextView address;
    TextView title;
    TextView date;
    String photoPath;
    Geolocation geolocation;
    Habit addedHabit;
    HabitEvent habitEvent;
    int habitIdx;

    String ID;  // The ID of the HabitEvent that was clicked.

    /**
     * Setup the activity for changing an existing HabitEvent in HabitHistory.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_history_detail);

        // Variables for the UI
        title = (TextView) findViewById(R.id.hd_title);
        comment = (EditText)findViewById(R.id.hd_editComment);
        date = (TextView)findViewById(R.id.hd_date);
        address = (TextView)findViewById(R.id.hd_address);

        // Get the ID of the HabitEvent to view
        Bundle b = getIntent().getExtras();
        ID = b.getString("id");
        habitEvent = HHC.get(ID);

        // Instantiate view vars
        addedHabit = habitEvent.getHabit();
        title.setText(habitEvent.getTitle());
        comment.setText(habitEvent.getComment());
        date.setText(habitEvent.getDate().toString()); // TODO: Correct Date String?
        photoPath = habitEvent.getPhotoPath();
        if (habitEvent.getGeolocation() != null) {
            address.setText(habitEvent.getGeolocation().getAddress());
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

        DatePickerDialog dialog = new DatePickerDialog(HabitHistoryDetailActivity.this,
                android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener, year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    /**
     * Save the changes for this Habit Event
     * @param view
     */
    public void confirmHE(View view){
        Intent intent = new Intent(this, ViewHabitHistoryActivity.class);

        // Encode image as a string for online storage
        String photoStringEncoding = IC.convertImageToString(photoPath);

        try {
            //Set the habitEvent parameters that the user gets
            habitEvent.setTitle(title.getText().toString());
            habitEvent.setComment(comment.getText().toString());
            habitEvent.setHabit(addedHabit);
            habitEvent.setDate(getDateUIHED());
            habitEvent.setPhotoPath(photoPath);
            habitEvent.setPhotoStringEncoding(photoStringEncoding);
            habitEvent.setGeolocation(geolocation);

            HHC.storeAndUpdate(habitEvent);
            startActivity(intent);

        } catch (HabitEventCommentTooLongException e) {
            Toast.makeText(this, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } catch (HabitEventNameException e) {
            Toast.makeText(this, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Delete the Habit Event from the Habit Event History
     * @param view
     */
    public void deleteHE(View view){
        HHC.removeAndStore(habitEvent);
        Intent intent = new Intent(this, ViewHabitHistoryActivity.class);
        startActivity(intent);
    }

    /**
     * Select a Habit for the Habit Event
     * @param view
     */
    public void changeHabit(View view){
        Intent intent = new Intent(this, SelectHabitActivity.class);
        startActivityForResult(intent, GOT_HABIT_FROM_LIST);
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
        // Compress photo
        if(requestCode==REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK){
            photoPath = IC.compressImage(photoPath);
        }
        // Set habit idx if returned from the HabitList
        if (requestCode == GOT_HABIT_FROM_LIST && resultCode == Activity.RESULT_OK) {
            habitIdx = data.getExtras().getInt("position");
            addedHabit = HLC.getHabit(habitIdx);
            title.setText(addedHabit.getTitle());
        }
        // Add Location
        if (requestCode == CHANGE_LOCATION_CODE && resultCode == Activity.RESULT_OK) {
            String new_address = data.getStringExtra("address");
            geolocation = new Geolocation(
                    new_address,
                    new LatLng(
                            data.getDoubleExtra("latitude",0),
                            data.getDoubleExtra("longitude",0))
            );
            address.setText(new_address);
        }
    }

    /**
     * Begin AddLocationActivity to get a new Location
     * @param view
     */
    public void changeLocation(View view) {
        Intent intent = new Intent(this, AddLocationActivity.class);
        if (habitEvent.getGeolocation() != null) {
            intent.putExtra("address", habitEvent.getGeolocation().getAddress());
            intent.putExtra("latitude", habitEvent.getGeolocation().getLatLng().latitude);
            intent.putExtra("longitude", habitEvent.getGeolocation().getLatLng().longitude);
        }
        startActivityForResult(intent, CHANGE_LOCATION_CODE);
    }

    /**
     *
     * @return
     */
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

//    /**
//     *
//     * @param CurrentPhotoPath
//     * @return
//     */
//    public String compressImage(String CurrentPhotoPath){
//        Bitmap imageBitmapCheck = BitmapFactory.decodeFile(CurrentPhotoPath);
//        int i = imageBitmapCheck.getByteCount();
//
//        CurrentPhotoPath = scaleImage(CurrentPhotoPath);
//
//        Bitmap imageBitmap = BitmapFactory.decodeFile(CurrentPhotoPath);
//        int i4 = imageBitmap.getByteCount();
//        File f = new File(CurrentPhotoPath);
//
//        int MAX_IMAGE_SIZE = 65532;
//        int streamLength = MAX_IMAGE_SIZE;
//        int compressQuality = 105;
//
//        ByteArrayOutputStream bmpStream = new ByteArrayOutputStream();
//
//        while (streamLength >= MAX_IMAGE_SIZE && compressQuality > 5) {
//            try {
//                bmpStream.flush();//to avoid out of memory error
//                bmpStream.reset();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            compressQuality -= 5;
//            imageBitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream);
//            byte[] bmpPicByteArray = bmpStream.toByteArray();
//            streamLength = bmpPicByteArray.length;
//        }
//
//        byte[] bmpPicByteArray = bmpStream.toByteArray();
//        streamLength = bmpPicByteArray.length;
//
//        FileOutputStream fo;
//
//        try {
//            fo = new FileOutputStream(f);
//            fo.write(bmpStream.toByteArray());
//            fo.flush();
//            fo.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return f.getAbsolutePath();
//    }
//
//    /**
//     *
//     * @param CurrentPhotoPath
//     * @return
//     */
//    public String scaleImage(String CurrentPhotoPath) {
//
//        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        bmOptions.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(CurrentPhotoPath, bmOptions);
//
//        int photoW = bmOptions.outWidth;
//        int photoH = bmOptions.outHeight;
//
//        float maxHeight = 816.0f;
//        float maxWidth = 612.0f;
//
//        int scaleFactor = (int) Math.min(photoW/maxWidth,photoH/maxHeight);
//
//        File img = new File(CurrentPhotoPath);
//        long length = img.length();
//
//        bmOptions.inSampleSize = scaleFactor;
//
//        bmOptions.inJustDecodeBounds = false;
//
//        Bitmap imageBitmap = BitmapFactory.decodeFile(CurrentPhotoPath);
//
//        if (length > 65532) {
//            imageBitmap = BitmapFactory.decodeFile(CurrentPhotoPath, bmOptions);
//        }
//
//
//        int i = imageBitmap.getByteCount();
//
//
//        File file = new File(CurrentPhotoPath);
//        if (file.exists()) {
//            file.delete();
//        }
//        try {
//            FileOutputStream out = new FileOutputStream(file);
//            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
//            out.flush();
//            out.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return file.getAbsolutePath();
//    }
}