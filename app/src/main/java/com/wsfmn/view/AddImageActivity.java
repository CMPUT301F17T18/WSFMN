/*
 * Copyright © 2017 Team 18 (WSFMN), CMPUT301, University of Alberta – All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact nmayne@ualberta.ca.
 *
 *  Team 18 is: Musaed Alsobaie, Siddhant Khanna, Wei Li, Nicholas Mayne, Fredric Mendi.
 */

package com.wsfmn.view;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

public class AddImageActivity extends AppCompatActivity {

    ImageView image;
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);

        image = (ImageView)findViewById(R.id.viewImageNew);
        Intent intent = getIntent();
        String photoPath = intent.getStringExtra("CurrentPhotoPath");
        Bitmap imageBitmap;

        if ((photoPath != null) && (!photoPath.startsWith("/storage"))) {
            byte[] decodedString = Base64.decode(photoPath, Base64.DEFAULT);
            imageBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        } else {
            imageBitmap = BitmapFactory.decodeFile(photoPath);
        }

        image.setImageBitmap(imageBitmap);
    }
}
