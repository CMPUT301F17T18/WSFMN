package com.wsfmn.habittracker;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class HabitEventActivity extends AppCompatActivity {

    Button addPic;
    Button Location;
    EditText Comment;
    ImageView image;
    Button viewImage;
    Button addHabitEvent;
    Button addHabit;

    Intent intent;
    Intent intent2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_event);

        Comment = (EditText)findViewById(R.id.Comment);
        addPic = (Button)findViewById(R.id.Picture);
        image = (ImageView)findViewById(R.id.imageView);
        Location = (Button)findViewById(R.id.Location);
        viewImage = (Button)findViewById(R.id.ViewImg);
        addHabitEvent = (Button)findViewById(R.id.AddHabitEvent);
        addHabit = (Button)findViewById(R.id.addHabit);

        //Checking If have camera
        if(!checkCamera()){
            addPic.setEnabled(false);
        }

    }

    private boolean checkCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }


    public void launchCamera(View view){
        //Launches the Camera
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Getting the result back from the camera of the user
        startActivityForResult(intent, 1);
    }

    /** Called when the user taps the View Picture button */
    public void viewPic(View view){
        Intent intent = new Intent(this, imageActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                //Get Image
                Bundle extra = data.getExtras();
                //Converting the image to bitmap
                Bitmap pic = (Bitmap) extra.get("data");
                //Displaying the image
                image.setImageBitmap(pic);
            }
        }
    }

}
