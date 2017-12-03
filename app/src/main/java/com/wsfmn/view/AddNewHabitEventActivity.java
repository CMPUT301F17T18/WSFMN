
package com.wsfmn.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wsfmn.controller.OnlineController;
import com.wsfmn.controller.ProfileNameController;
import com.wsfmn.model.Date;

import com.google.android.gms.maps.model.LatLng;
import com.wsfmn.model.Geolocation;
import com.wsfmn.model.Habit;
import com.wsfmn.exceptions.HabitCommentTooLongException;
import com.wsfmn.model.HabitEvent;
import com.wsfmn.exceptions.HabitEventCommentTooLongException;
import com.wsfmn.exceptions.HabitEventNameException;
import com.wsfmn.controller.HabitHistoryController;
import com.wsfmn.controller.HabitListController;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.zip.Inflater;


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

    EditText Comment;
    TextView T_showAddress;
    TextView nameHabit;
    TextView date2;
    Button addPic;
    Button viewImage;
    Button addHabitEvent;
    Button addHabit;
    String CurrentPhotoPath;
    Uri photoURI;
    Geolocation geolocation;
    Integer habitIdx;

    /**
     * Setup the activity for adding a new HabitEvent to HabitHistory.
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_habit_event);

        // If not null then this activity was called from the HabitsForTodayActivity
        // So we preset the Habit to the one that was clicked
        Bundle b = getIntent().getExtras();
        if(b != null) {
            habitIdx = b.getInt("positionToday");
            changeNameToday(habitIdx);
        }

        Comment = (EditText) findViewById(R.id.Comment);
        date2 = (TextView) findViewById(R.id.eventDate);
        T_showAddress = (TextView) findViewById(R.id.T_showAdress);
        addPic = (Button) findViewById(R.id.Picture);
        viewImage = (Button) findViewById(R.id.ViewImg);
        addHabitEvent = (Button) findViewById(R.id.AddHabitEvent);
        addHabit = (Button) findViewById(R.id.addHabit);

        //Creating date for the Habit Event created
        String dateAndTime = new Date(0).toString();
        date2.setText(dateAndTime);

        //Check if device has a camera
        if (!checkCamera()) {
            addPic.setEnabled(false);
        }

        //To take the picture
        addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // Reuse Code for taking image: https://developer.android.com/training/camera/photobasics.html
            dispatchTakePictureIntent();
//            CurrentPhotoPath = scaleImage(CurrentPhotoPath);

            }
        });

        Button Location = (Button) findViewById(R.id.B_changeLocation);
        Location.setOnClickListener(new View.OnClickListener() {
            @Override
            //https://developer.android.com/training/basics/intents/result.html
            public void onClick(View v) {
                Intent intent = new Intent(AddNewHabitEventActivity.this, AddLocationActivity.class);
                if (geolocation != null) {
                    intent.putExtra("address", geolocation.getAddress());
                    intent.putExtra("latitude", geolocation.getLatLng().latitude);
                    intent.putExtra("longitude", geolocation.getLatLng().longitude);
                }
                startActivityForResult(intent, ADD_NEW_LOCATION_CODE);
            }
        });

        Button setNewDate = (Button) findViewById(R.id.setNewDate);
        setNewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            @TargetApi(24)
            public void onClick(View v) {
                android.icu.util.Calendar calendar = android.icu.util.Calendar.getInstance();
                int year = calendar.get(android.icu.util.Calendar.YEAR);
                int month = calendar.get(android.icu.util.Calendar.MONTH);
                int day = calendar.get(android.icu.util.Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddNewHabitEventActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                date2.setText(year + " / " + month + " / " + dayOfMonth);
                date2.getText();
            }
        };
    }

    /**
     * Checks if the user has a camera
     * @return if has camera or not
     */
    private boolean checkCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    /**
     * Take a picture and save it.
     */
    private void dispatchTakePictureIntent() {
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
                Log.e("Error", "File creation error");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
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

        // Save a file: path for use with ACTION_VIEW intents
        CurrentPhotoPath = image.getAbsolutePath();
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
        // Set the image path
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            CurrentPhotoPath = compressImage(CurrentPhotoPath);
//            CurrentPhotoPath = scaleImage(CurrentPhotoPath);
        }
        // Set habit idx if returned from the HabitList
        if (requestCode == GOT_HABIT_FROM_LIST && resultCode == Activity.RESULT_OK) {
            habitIdx = data.getExtras().getInt("position");
            changeName(habitIdx);
        }
        // Add Location
        if(requestCode == ADD_NEW_LOCATION_CODE && resultCode == Activity.RESULT_OK) {
            String address = data.getStringExtra("address");
            geolocation = new Geolocation(
                                        address,
                                        new LatLng(
                                                data.getDoubleExtra("latitude",0),
                                                data.getDoubleExtra("longitude",0)));
            T_showAddress.setText(address);
        }
    }

    /**
     * Display the Habit the user selected for this habit event.
     *
     * @param i index of Habit in the HabitHistory
     */
    public void changeName(int i) {
        nameHabit = (TextView) findViewById(R.id.habitName);
        HabitListController control = HabitListController.getInstance();
        nameHabit.setText(control.getHabit(i).getTitle().toString());
    }

    /**
     * Display the Habit the user selected for this habit event (from Habits For Today).
     * @param i index of Habit in HabitsForToday
     */
    public void changeNameToday(int i){
        nameHabit = (TextView)findViewById(R.id.habitName);
        HabitListController control = HabitListController.getInstance();
        nameHabit.setText(control.getHabitsForToday().get(i).getTitle().toString());
    }

    /**
     * Adding the values/parameters we got into habitEvent hence creating a new habit event
     *
     * @param view
     */
    public void confirmHabitEvent(View view) {
        Intent intent = new Intent(this, ViewHabitHistoryActivity.class);
        String actualCurrentPhotoPath = CurrentPhotoPath;
        try {
            if (CurrentPhotoPath != null) {
                Bitmap imageBitmap = BitmapFactory.decodeFile(CurrentPhotoPath);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();

                CurrentPhotoPath = Base64.encodeToString(b, Base64.DEFAULT);
                System.out.println(CurrentPhotoPath);
            }
            HabitListController control = HabitListController.getInstance();
            HabitEvent hEvent = new HabitEvent(control.getHabit(habitIdx),
                    nameHabit.getText().toString(), Comment.getText().toString(), CurrentPhotoPath, actualCurrentPhotoPath, getDateUIHE(), geolocation);

            Habit habit = control.getHabit(habitIdx);
            //Adding Habit Event to the list
            HabitHistoryController control2 = HabitHistoryController.getInstance();

            hEvent.getComment();
            hEvent.getHabitEventTitle();

            control2.addAndStore(hEvent);
            control2.storeAll();
            ProfileNameController.getInstance().updateScore();
            startActivity(intent);

        } catch (HabitCommentTooLongException e) {
            e.printStackTrace();
        } catch (HabitEventCommentTooLongException e) {
            Toast.makeText(AddNewHabitEventActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (HabitEventNameException e) {
            Toast.makeText(AddNewHabitEventActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (NullPointerException e) {
            Toast.makeText(AddNewHabitEventActivity.this, "Habit Event needs to contain Habit", Toast.LENGTH_LONG).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * viewing image that the user took for the habit event
     *
     * @param view
     */
    public void viewPic(View view) {
        Intent intent = new Intent(this, AddImageActivity.class);
        intent.putExtra("CurrentPhotoPath", CurrentPhotoPath);
        startActivity(intent);
    }

    /**
     *
     * @return
     */
    public com.wsfmn.model.Date getDateUIHE(){
        String date = date2.getText().toString();
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

    /**
     *
     * @param CurrentPhotoPath
     * @return
     */
    public String compressImage(String CurrentPhotoPath){
        Bitmap imageBitmapCheck = BitmapFactory.decodeFile(CurrentPhotoPath);
        int i = imageBitmapCheck.getByteCount();

        CurrentPhotoPath = scaleImage(CurrentPhotoPath);

        Bitmap imageBitmap = BitmapFactory.decodeFile(CurrentPhotoPath);
        int i4 = imageBitmap.getByteCount();
        File f = new File(CurrentPhotoPath);

        int MAX_IMAGE_SIZE = 65532;
        int streamLength = MAX_IMAGE_SIZE;
        int compressQuality = 105;

        ByteArrayOutputStream bmpStream = new ByteArrayOutputStream();

        while (streamLength >= MAX_IMAGE_SIZE && compressQuality > 5) {
            try {
                bmpStream.flush();//to avoid out of memory error
                bmpStream.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
            compressQuality -= 5;
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream);
            byte[] bmpPicByteArray = bmpStream.toByteArray();
            streamLength = bmpPicByteArray.length;
        }

        byte[] bmpPicByteArray = bmpStream.toByteArray();
        streamLength = bmpPicByteArray.length;

        FileOutputStream fo;

        try {
            fo = new FileOutputStream(f);
            fo.write(bmpStream.toByteArray());
            fo.flush();
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return f.getAbsolutePath();
    }

    /**
     *
     * @param CurrentPhotoPath
     * @return
     */
    public String scaleImage(String CurrentPhotoPath) {

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(CurrentPhotoPath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;

        int scaleFactor = (int) Math.min(photoW/maxWidth,photoH/maxHeight);

        File img = new File(CurrentPhotoPath);
        long length = img.length();

        bmOptions.inSampleSize = scaleFactor;

        bmOptions.inJustDecodeBounds = false;

        Bitmap imageBitmap = BitmapFactory.decodeFile(CurrentPhotoPath);

        if (length > 65532) {
            imageBitmap = BitmapFactory.decodeFile(CurrentPhotoPath, bmOptions);
        }


        int i = imageBitmap.getByteCount();


        File file = new File(CurrentPhotoPath);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file.getAbsolutePath();
    }
}