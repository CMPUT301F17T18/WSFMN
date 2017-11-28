
package com.wsfmn.view;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import com.google.android.gms.maps.model.LatLng;
import com.wsfmn.model.Habit;
import com.wsfmn.model.HabitCommentTooLongException;
import com.wsfmn.model.HabitEvent;
import com.wsfmn.model.HabitEventCommentTooLongException;
import com.wsfmn.model.HabitEventNameException;
import com.wsfmn.controller.HabitHistoryController;
import com.wsfmn.controller.HabitListController;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;


/**
 * Represents when the user Adds a Habit Event for a habit
 * @version 1.0
 * @see Activity
 */
public class HabitEventActivity extends AppCompatActivity {

    Button addPic;

    EditText Comment;
    Button viewImage;
    Button addHabitEvent;
    Button addHabit;
    Intent intent3;
    Intent intent2;
    EditText nameHabitEvent;
    Bitmap imageBitmap;
    TextView date;
    ImageView img;
    Uri photoURI;
    String datevalue;
    //DIFFEREN -----
    TextView T_showAddress;

    //DIFFEREN -----
    LatLng new_coordinate;
    Integer i = null;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    //DIFFEREN -----
    static final int ADD_NEW_LOCATION_CODE = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_event);

        Comment = (EditText)findViewById(R.id.Comment);
        addPic = (Button)findViewById(R.id.Picture);
        nameHabitEvent = (EditText)findViewById(R.id.nameEvent);
        viewImage = (Button)findViewById(R.id.ViewImg);
        addHabitEvent = (Button)findViewById(R.id.AddHabitEvent);
        addHabit = (Button)findViewById(R.id.addHabit);
        //DIFFEREN -----
        T_showAddress = (TextView) findViewById(R.id.T_showAdress);

        date = (TextView)findViewById(R.id.eventDate);

        //Creating date for the Habit Event created
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
        datevalue = df.format(Calendar.getInstance().getTime());
        date.setText(datevalue);


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


        //DIFFERENT -----------
        Button Location = (Button) findViewById(R.id.B_changeLocation);
        Location.setOnClickListener(new View.OnClickListener(){
            @Override
            //https://developer.android.com/training/basics/intents/result.html
            public void onClick(View v){
                Intent  intent = new Intent(HabitEventActivity.this,AddLocationActivity.class);
                startActivityForResult(intent,ADD_NEW_LOCATION_CODE);
            }
        });
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

    //DIFFERENT -------
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    /**
     * Creates the file where the image will be stored
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp;
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
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
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            imageBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
////            img = (ImageView)findViewById(R.id.imageView3);
////            img.setImageBitmap(imageBitmap);
//
//        }
        // Getting the position of the habit from the list
        if(requestCode==2){
            if(resultCode == Activity.RESULT_OK) {
                //intent3 = data.getIntent();
                Bundle b = data.getExtras();
                i = b.getInt("position");
                changeName(i);
            }
        }
        //Add new location
        if(requestCode == ADD_NEW_LOCATION_CODE){
            if(resultCode == Activity.RESULT_OK)

            {
                //Bundle b = data.getExtras();
                Toast.makeText(getApplicationContext(), "Address showed", Toast.LENGTH_LONG).show();

                Double latitude = data.getDoubleExtra("new_latitude",0);
                Double longtitude = data. getDoubleExtra("new_longtitude",0);
                String address = data.getStringExtra("new_address");

                LatLng latLng = new LatLng(latitude,longtitude);

                T_showAddress.setText(address);
            }
        }
    }

    /**
     *  Displaying the name of the habit the user selected for the habit event
     */
    public void changeName(int i){
        TextView nameHabit = (TextView)findViewById(R.id.habitName);
        HabitListController control = HabitListController.getInstance();
        nameHabit.setText(control.getHabit(i).getTitle().toString());
    }

    /**
     * Adding the values/parameters we got into habitEvent hence creating a new habit event
     * @param view
     */
    public void confirmHabitEvent(View view) {
        Intent intent = new Intent(this, HabitHistoryActivity.class);
        try {
            HabitListController control = HabitListController.getInstance();
            String test = nameHabitEvent.getText().toString();
            HabitEvent hEvent = new HabitEvent(control.getHabit(i),
                    nameHabitEvent.getText().toString(), Comment.getText().toString(), CurrentPhotoPath, date.getText().toString());
            Habit habit = control.getHabit(i);
            //Adding Habit Event to the list
            HabitHistoryController control2 = HabitHistoryController.getInstance();

            hEvent.getComment();
            hEvent.getHabitEventTitle();

            control2.addAndStore(hEvent);
            control2.storeAll();
            startActivity(intent);

        }catch(HabitCommentTooLongException e){
            e.printStackTrace();
        }catch(HabitEventCommentTooLongException e){
            Toast.makeText(HabitEventActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }catch(HabitEventNameException e){
            Toast.makeText(HabitEventActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }catch(NullPointerException e){
            Toast.makeText(HabitEventActivity.this, "Habit Event needs to contain Habit", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * viewing image that the user took for the habit event
     * @param view
     */
    public void viewPic(View view){
        Intent intent = new Intent(this, ImageActivity.class);
        intent.putExtra("CurrentPhotoPath", CurrentPhotoPath);
        startActivity(intent);
    }

//    public void location(){
//        Intent intent = new Intent(HabitEventActivity.this, MapsActivity.class);
//        startActivity(intent);
//    }

}