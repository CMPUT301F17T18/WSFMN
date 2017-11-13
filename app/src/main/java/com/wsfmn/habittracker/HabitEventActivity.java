
package com.wsfmn.habittracker;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import com.wsfmn.habit.HabitCommentTooLongException;
import com.wsfmn.habit.HabitEvent;
import com.wsfmn.habitcontroller.HabitHistoryController;
import com.wsfmn.habitcontroller.HabitListController;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

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
    //int i;
    int i;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_event);

        Comment = (EditText)findViewById(R.id.Comment);
        addPic = (Button)findViewById(R.id.Picture);
        nameHabitEvent = (EditText)findViewById(R.id.nameEvent);

        //Location = (Button)findViewById(R.id.Location);
        viewImage = (Button)findViewById(R.id.ViewImg);
        addHabitEvent = (Button)findViewById(R.id.AddHabitEvent);
        addHabit = (Button)findViewById(R.id.addHabit);

        date = (TextView)findViewById(R.id.eventDate);

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
        datevalue = df.format(Calendar.getInstance().getTime());
        date.setText(datevalue);


        //Checking If have camera
        if(!checkCamera()){
            addPic.setEnabled(false);
        }

        addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });


        Button Location = (Button) findViewById(R.id.B_changeLocation);
        Location.setOnClickListener(new View.OnClickListener(){
            @Override
            //https://developer.android.com/training/basics/intents/result.html
            public void onClick(View v){
                Intent  intent = new Intent(HabitEventActivity.this,AddLocationActivity.class);
                startActivityForResult(intent,3);
            }
        });
    }

    private boolean checkCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
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



    String mCurrentPhotoPath;

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
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    //For selecting the habit
    public void selectHabit(View view){
        Intent intent = new Intent(this, selectHabitActivity.class);
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
        if(requestCode==2){
            if(resultCode == Activity.RESULT_OK) {
                //intent3 = data.getIntent();
                Bundle b = data.getExtras();
                i = b.getInt("position");
                changeName(i);
            }
        }
    }
    public void changeName(int i){
        TextView nameHabit = (TextView)findViewById(R.id.habitName);
        HabitListController control = HabitListController.getInstance();
        nameHabit.setText(control.getHabit(i).getTitle().toString());
    }


    //Adding the values we got into habitEvent
    public void confirmHabitEvent(View view){
        Intent intent = new Intent(this, HabitHistoryActivity.class);
        try {
            HabitListController control = HabitListController.getInstance();
            String test = nameHabitEvent.getText().toString();
            HabitEvent hEvent = new HabitEvent(control.getHabit(i),
                    nameHabitEvent.getText().toString(), Comment.getText().toString(), mCurrentPhotoPath, date.getText().toString());
            //Adding Habit Event to the list
            HabitHistoryController control2 = HabitHistoryController.getInstance();
            control2.addAndStore(hEvent);
            control2.storeAll();
            startActivity(intent);
        } catch (HabitCommentTooLongException e) {
            e.printStackTrace();
        }
    }

    public void viewPic(View view){
        Intent intent = new Intent(this, imageActivity.class);
        intent.putExtra("mCurrentPhotoPath", mCurrentPhotoPath);
        startActivity(intent);
    }

//    public void location(){
//        Intent intent = new Intent(HabitEventActivity.this, MapsActivity.class);
//        startActivity(intent);
//    }

}