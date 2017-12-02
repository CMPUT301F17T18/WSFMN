package com.wsfmn.view;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

public class AddImageActivity extends AppCompatActivity {

    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);

        image = (ImageView)findViewById(R.id.viewImageNew);
        Intent intent = getIntent();
        String photoPath = intent.getStringExtra("CurrentPhotoPath");

        int targetW = 256;
        int targetH = 256;

        int scaleFactor = Math.max(targetH, targetW);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, bmOptions);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        Bitmap imageBitmap;

        // TODO: This is brittle, it relies on the device using the directory. Fix by appending a tag to the beginning of the encoded string
        if ((photoPath != null) && (!photoPath.startsWith("/storage"))) {
            byte[] decodedString = Base64.decode(photoPath, Base64.DEFAULT);
            imageBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        } else {
            imageBitmap = BitmapFactory.decodeFile(photoPath);
        }

        image.setImageBitmap(imageBitmap);

    }

}
