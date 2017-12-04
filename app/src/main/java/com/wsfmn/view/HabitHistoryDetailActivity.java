package com.wsfmn.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
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
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.wsfmn.model.Date;

import com.google.android.gms.maps.model.LatLng;
import com.wsfmn.exceptions.HabitEventCommentTooLongException;
import com.wsfmn.exceptions.HabitEventNameException;
import com.wsfmn.controller.HabitHistoryController;
import com.wsfmn.controller.HabitListController;
import com.wsfmn.model.Geolocation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;


/**
 * Called when the user wants to edit an existing Habit Event
 * @version 1.0
 * @see AppCompatActivity
 */
public class HabitHistoryDetailActivity extends AppCompatActivity {

    Button addHabit;
    TextView habitName;
    Button addPicture;
    EditText comment;
    Button viewImage;
    Button confirm;
    TextView date;
    TextView T_address;
    String ID;
    Geolocation geolocation;

    //Has the path that is to be calculated in detail
    String CurrentPhotoPath;
    //Has the path already present
    String path;

    int position;
    Button B_changeLocation;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int GOT_HABIT_FROM_LIST = 2;
    static final int CHANGE_LOCATION_CODE = 3;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_history_detail);

        //Declaring variables for the UI
        addHabit = (Button)findViewById(R.id.addHabit2);
        habitName = (TextView) findViewById(R.id.habitName2);
        addPicture = (Button)findViewById(R.id.Picture2);
        comment = (EditText)findViewById(R.id.Comment2);
        viewImage = (Button)findViewById(R.id.ViewImg2);
        confirm = (Button)findViewById(R.id.confirmButton2);
        date = (TextView)findViewById(R.id.dateDetail);
        B_changeLocation = (Button)findViewById(R.id.B_changeLocation);
        T_address = (TextView)findViewById(R.id.T_C_showAddress);

        Bundle b = getIntent().getExtras();
        try {
            //Get the ID of Habit Event the user selected
            ID = b.getString("id");
        }catch (NullPointerException e){
            //TODO Can we fix this instead fo catching a NullPointerException?
        }

        HabitHistoryController c = HabitHistoryController.getInstance();
        try {
            ImageView image = (ImageView)findViewById(R.id.imageView3);
            image.setImageBitmap(c.get(ID).getImageBitmap());

            habitName.setText(c.get(ID).getHabitFromEvent().getTitle());
            comment.setText(c.get(ID).getComment());
            if (c.get(ID).getGeolocation() != null) {
                T_address.setText(c.get(ID).getGeolocation().getAddress());
            }
            date.setText(new com.wsfmn.model.Date().toString());
        } catch(IndexOutOfBoundsException e){
            //TODO Can we fix this instead fo catching an IndexOutOfBoundsException?
        }

        Button setNewDate = (Button)findViewById(R.id.changeDate);
        setNewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            @TargetApi(24)
            public void onClick(View v) {
                android.icu.util.Calendar calendar = android.icu.util.Calendar.getInstance();
                int year = calendar.get(android.icu.util.Calendar.YEAR);
                int month = calendar.get(android.icu.util.Calendar.MONTH);
                int day = calendar.get(android.icu.util.Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(HabitHistoryDetailActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
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
     * Confirm the changes fot the Habit Event
     * @param view
     */
    public void confirmHE(View view){
        Intent intent = new Intent(this, ViewHabitHistoryActivity.class);
        try {
            //Set the habitEvent parameters that the user gets
            HabitHistoryController c = HabitHistoryController.getInstance();
            c.get(ID).setTitle(habitName.getText().toString());
            c.get(ID).setComment(comment.getText().toString());
            c.get(ID).setHabit(c.get(ID).getHabitFromEvent());
            c.get(ID).setDate(getDateUIHED());
            c.get(ID).setCurrentPhotoPath(path);
            c.get(ID).setActualCurrentPhotoPath(path);
            c.get(ID).setGeolocation(geolocation);
            c.storeAndUpdate(c.get(ID));
            startActivity(intent);
        } catch (HabitEventCommentTooLongException e) {
            Toast.makeText(HabitHistoryDetailActivity.this, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } catch (HabitEventNameException e) {
            Toast.makeText(HabitHistoryDetailActivity.this, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Delete the Habit Event from the Habit Event History
     * @param view
     */
    public void deleteHE(View view){
        HabitHistoryController c = HabitHistoryController.getInstance();
        c.removeAndStore(c.get(ID));

        Intent intent = new Intent(HabitHistoryDetailActivity.this, ViewHabitHistoryActivity.class);
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
     * View the image of the HabitEvent
     * @param view
     */
    public void viewImage2(View view){
        Intent intent = new Intent(HabitHistoryDetailActivity.this, AddImageActivity.class);
        path = HabitHistoryController.getInstance().get(ID).getActualCurrentPhotoPath();
        //If no picture taken before then when it is null value we create new image
        if(path == null) {
            path = CurrentPhotoPath;
        }
        intent.putExtra("CurrentPhotoPath",path);
        startActivity(intent);
    }

    /**
     * Changes the picture of the habit Event
     * @param view
     * @throws IOException
     */
    public void changePicture2(View view) throws IOException {
        try {
            dispatchTakePictureIntent(
                    HabitHistoryController.getInstance().get(ID).getActualCurrentPhotoPath());
        }catch (NullPointerException e){
            /*
            Reuse Code: https://developer.android.com/training/camera/photobasics.html
             */
            dispatchTakePictureIntent(createImageFile());
        }
    }

    /**
     * Image file created
     * @return
     * @throws IOException
     */
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    private String createImageFile() throws IOException {
        // Create an image file name
        String timeStamp;
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir);    /* directory */

        // Save a file: path for use with ACTION_VIEW intents
        CurrentPhotoPath = image.getAbsolutePath();
        return CurrentPhotoPath;
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
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            path = HabitHistoryController.getInstance().get(ID).getActualCurrentPhotoPath();
            //If no picture taken before then when it is null value we create new image
            if(path == null) {
                path = CurrentPhotoPath;
            }
            CurrentPhotoPath = compressImage(path);

        }
        if (requestCode == GOT_HABIT_FROM_LIST && resultCode == Activity.RESULT_OK) {
            position = data.getExtras().getInt("position");
            changeHabit(position);
        }

        if (requestCode == CHANGE_LOCATION_CODE && resultCode == Activity.RESULT_OK) {
            String address = data.getStringExtra("address");
            geolocation = new Geolocation(
                    address,
                    new LatLng(
                            data.getDoubleExtra("latitude",0),
                            data.getDoubleExtra("longitude",0))
            );
            T_address.setText(address);
        }
    }

    /**
     * Change the Habit the user selects for this habit event.
     *
     * @param i index of Habit in the HabitHistory
     */
    public void changeHabit(int i) {
        HabitListController l = HabitListController.getInstance();
        HabitHistoryController.getInstance().get(ID).setHabit(l.getHabit(position));
        habitName.setText(l.getHabit(i).getTitle().toString());
    }

    /**
     * Begin AddLocationActivity to get a new Location
     * @param view
     */
    public void changeLocation(View view) {
        Intent intent = new Intent(this, AddLocationActivity.class);
        if (HabitHistoryController.getInstance().get(position).getGeolocation() != null) {
            intent.putExtra("address", HabitHistoryController.getInstance().get(position).getGeolocation().getAddress());
            intent.putExtra("latitude", HabitHistoryController.getInstance().get(position).getGeolocation().getLatLng().latitude);
            intent.putExtra("longitude", HabitHistoryController.getInstance().get(position).getGeolocation().getLatLng().longitude);
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