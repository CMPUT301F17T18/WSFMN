package com.wsfmn.controller;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 */
public class ImageController {
    private static ImageController INSTANCE = null;

    /**
     *
     */
    private ImageController() {
    }

    /**
     *
     * @return
     */
    public static ImageController getInstance(){
        if(INSTANCE == null){
            INSTANCE = new ImageController();
        }
        return INSTANCE;
    }

    /**
     *
     * @param path to image file
     * @return path to compressed image
     */
    public String compressImage(String path){
        path = scaleImage(path);

        Bitmap imageBitmap = BitmapFactory.decodeFile(path);

        int MAX_IMAGE_SIZE = 65532;
        int streamLength = MAX_IMAGE_SIZE;
        int compressQuality = 100;

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

        File f = new File(path);
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
     * @param path to image file
     * @return
     */
    @NonNull
    private String scaleImage(String path) {
        Bitmap imageBitmap = null;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
//        Bitmap imageBitmap = BitmapFactory.decodeFile(path, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;

        int scaleFactor = (int) Math.min(photoW/maxWidth,photoH/maxHeight);

        File img = new File(path);
        long length = img.length();

        bmOptions.inSampleSize = scaleFactor;

        bmOptions.inJustDecodeBounds = false;

//        Bitmap imageBitmap = BitmapFactory.decodeFile(path);

        if (length > 65532) {
            imageBitmap = BitmapFactory.decodeFile(path, bmOptions);
        }

        File f = new File(path);
        FileOutputStream fo;

        if (f.exists()) {
            f.delete();
        }
        try {
            fo = new FileOutputStream(f);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fo);
            fo.flush();
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return f.getAbsolutePath();
    }


    // Convert picture to byte[] for online storage
    public String convertImageToString(String path) {
        String photoStringEncoding = "";
        if(path!=null) {
            Bitmap imageBitmap = BitmapFactory.decodeFile(path);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();

            photoStringEncoding = Base64.encodeToString(b, Base64.DEFAULT);
        }
        return photoStringEncoding;
    }


}
