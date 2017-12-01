
package com.wsfmn.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * Represents when the user Adds a Habit Event for a habit
 * @version 1.0
 * @see Activity
 */
public class AddNewHabitEventActivity extends AppCompatActivity {

    private java.util.Date actualTimeStamp;

    Button addPic;
    TextView nameHabit;
    EditText Comment;
    Button viewImage;
    Button addHabitEvent;
    Button addHabit;
    Intent intent3;
    Intent intent2;
    TextView date2;
    ImageView img;
    Uri photoURI;
    String datevalue;
    TextView T_showAddress;
    HabitEvent he2;
    Habit habitList;
    Habit habitFromTodaysList;
    Geolocation geolocation;
    Bundle b;
    Habit habitFromList;
    LatLng new_coordinate;
    Integer i;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int ADD_NEW_LOCATION_CODE = 3;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    String CurrentPhotoPath2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_habit_event);

        Bundle b = getIntent().getExtras();
        if(b!=null) {
            i = b.getInt("positionToday");
            changeNameToday(i);
        }

        Comment = (EditText)findViewById(R.id.Comment);
        addPic = (Button)findViewById(R.id.Picture);
        viewImage = (Button)findViewById(R.id.ViewImg);
        addHabitEvent = (Button)findViewById(R.id.AddHabitEvent);
        addHabit = (Button)findViewById(R.id.addHabit);
        T_showAddress = (TextView) findViewById(R.id.T_showAdress);

        date2 = (TextView)findViewById(R.id.eventDate);

        //Creating date for the Habit Event created
        String dateAndTime = new Date(0).toString();
        date2.setText(dateAndTime);


        // If statement handles the case where the activity is called from a listView
        // //(e.g. ViewHabitsForTodayActivity)

        //Checking If device has camera
        if(!checkCamera()){
            addPic.setEnabled(false);
        }

        //To take the picture
        addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Reuse Code for taking image: https://developer.android.com/training/camera/photobasics.html
                 */
                dispatchTakePictureIntent();
            }
        });

        Button Location = (Button) findViewById(R.id.B_changeLocation);
        Location.setOnClickListener(new View.OnClickListener(){
            @Override
            //https://developer.android.com/training/basics/intents/result.html
            public void onClick(View v){
                Intent  intent = new Intent(AddNewHabitEventActivity.this, AddLocationActivity.class);
                startActivityForResult(intent, ADD_NEW_LOCATION_CODE);
            }
        });

        Button setNewDate = (Button)findViewById(R.id.setNewDate);
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
        mDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                date2.setText(year + " / " + month + " / " + dayOfMonth);
                date2.getText();
            }
        };
    }

    /**
     * Checks if have the camera
     * @return returns Boolean if has camera or not
     */
    private boolean checkCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    static final int REQUEST_TAKE_PHOTO = 1;

    /**
     * Taking the image and putting it in the file
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
                Log.e("Error" , "File creation error");
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


    String CurrentPhotoPath;

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    /**
     * Creates the file where the image will be stored
     */
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
    //For selecting the habit
    public void selectHabit(View view){
        Intent intent = new Intent(this, SelectHabitActivity.class);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==2){
            if(resultCode == Activity.RESULT_OK) {
                //intent3 = data.getIntent();
                Bundle b = data.getExtras();
                i = b.getInt("position");
                changeName(i);
            }
        }
        //Add new location
        if(requestCode == ADD_NEW_LOCATION_CODE) {
            if(resultCode == Activity.RESULT_OK) {
//                Bundle b = data.getExtras();
//                Toast.makeText(getApplicationContext(), "Address showed", Toast.LENGTH_LONG).show();
                Double latitude = data.getDoubleExtra("new_latitude",0);
                Double longtitude = data.getDoubleExtra("new_longtitude",0);
                LatLng latLng = new LatLng(latitude,longtitude);
                String address = data.getStringExtra("new_address");

                geolocation = new Geolocation(address, latLng);
                T_showAddress.setText(address);
            }
        }
    }

    /**
     *  Display the habit the user selected for this habit event
     */
    public void changeName(int i){
        nameHabit = (TextView)findViewById(R.id.habitName);
        HabitListController control = HabitListController.getInstance();
        nameHabit.setText(control.getHabit(i).getTitle().toString());
    }

    public void changeNameToday(int i){
        nameHabit = (TextView)findViewById(R.id.habitName);
        HabitListController control = HabitListController.getInstance();
        nameHabit.setText(control.getHabitsForToday().get(i).getTitle().toString());
    }

    /**
     * Adding the values/parameters we got into habitEvent hence creating a new habit event
     * @param view
     */
    public void confirmHabitEvent(View view) {
        Intent intent = new Intent(this, ViewHabitHistoryActivity.class);
        try {
            if(CurrentPhotoPath!=null) {
                Bitmap imageBitmap = BitmapFactory.decodeFile(CurrentPhotoPath);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();

                CurrentPhotoPath2 = Base64.encodeToString(b, Base64.DEFAULT);
                System.out.println(CurrentPhotoPath);
            }
            HabitListController control = HabitListController.getInstance();
            HabitEvent hEvent = new HabitEvent(control.getHabit(i),
                    nameHabit.getText().toString(), Comment.getText().toString(), CurrentPhotoPath, getDateUIHE(), geolocation, CurrentPhotoPath2);

            Habit habit = control.getHabit(i);
            //Adding Habit Event to the list
            HabitHistoryController control2 = HabitHistoryController.getInstance();

            hEvent.getComment();
            hEvent.getHabitEventTitle();

            control2.addAndStore(hEvent);
            control2.storeAll();
            ProfileNameController.getInstance().updateScore();
            startActivity(intent);

        }catch(HabitCommentTooLongException e){
            e.printStackTrace();
        }catch(HabitEventCommentTooLongException e){
            Toast.makeText(AddNewHabitEventActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }catch(HabitEventNameException e){
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
    public void viewPic(View view){
        Intent intent = new Intent(this, AddImageActivity.class);
        intent.putExtra("CurrentPhotoPath", CurrentPhotoPath);
        startActivity(intent);
//        Comment.setText(getDateUIHE().toDateString());
    }

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

}