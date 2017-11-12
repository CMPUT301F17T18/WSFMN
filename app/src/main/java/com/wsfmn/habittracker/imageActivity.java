package com.wsfmn.habittracker;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;

public class imageActivity extends AppCompatActivity {

    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        image = (ImageView)findViewById(R.id.viewImageNew);
        Intent intent = getIntent();
//        Uri uri = intent.getParcelableExtra("imageUri");
        Bitmap imageBitmap = BitmapFactory.decodeFile(intent.getStringExtra("mCurrentPhotoPath"));
        image.setImageBitmap(imageBitmap);



//        Bitmap bitmap = null;
//        try {
//            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        image.setImageBitmap(bitmap);

    }
}
