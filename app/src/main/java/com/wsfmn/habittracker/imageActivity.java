package com.wsfmn.habittracker;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class imageActivity extends AppCompatActivity {

    ImageView imge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        imge = (ImageView)findViewById(R.id.imageView2);

        Intent intent = getIntent();

        Bitmap imgBitmap = (Bitmap)intent.getParcelableExtra("imBitmap");
        imge.setImageBitmap(imgBitmap);
    }
}
