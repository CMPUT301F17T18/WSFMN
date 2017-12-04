/*
 * Copyright © 2017 Team 18 (WSFMN), CMPUT301, University of Alberta – All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact nmayne@ualberta.ca.
 *
 *  Team 18 is: Musaed Alsobaie, Siddhant Khanna, Wei Li, Nicholas Mayne, Fredric Mendi.
 *
 *  Reuse Code for taking image, and scaling: https://developer.android.com/training/camera/photobasics.html
 *  Reuse Code for compressing images: https://stackoverflow.com/questions/28760941/compress-image-file-from-camera-to-certain-size
 *  Reuse Code for converting image to string: https://stackoverflow.com/questions/36189503/take-picture-and-convert-to-base64
 */

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
 * A class to control the compression, encoding/ decoding, and deletion of images.
 */
public class ImageController {
    private static ImageController INSTANCE = null;

    /**
     * Singleton constructor.
     */
    private ImageController() {
    }

    /**
     * Get the instance of the ImageController singleton.
     *
     * @return instance of ImageController
     */
    public static ImageController getInstance(){
        if(INSTANCE == null){
            INSTANCE = new ImageController();
        }
        return INSTANCE;
    }

    /**
     * Compress an image to within a certain MAX_IMAGE_SIZE specification.
     *
     * @param path to image file
     * @return path to compressed image
     */
    public String compressImage(String path){
        path = scaleImage(path);

        Bitmap imageBitmap = BitmapFactory.decodeFile(path);

        int MAX_IMAGE_SIZE = 65532;
        int streamLength = MAX_IMAGE_SIZE;
        int compressQuality = 105;

        ByteArrayOutputStream bmpStream = new ByteArrayOutputStream();

        while (streamLength >= MAX_IMAGE_SIZE && compressQuality > 5) {
            try {
                bmpStream.flush(); //to avoid out of memory error
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
     * Scale an image to maxHeight and maxWidth dimensions.
     *
     * @param path to image file
     * @return the path after scaling has been completed
     */
    @NonNull
    private String scaleImage(String path) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;

        int scaleFactor = (int) Math.min(photoW/maxWidth,photoH/maxHeight);

        File img = new File(path);
        long length = img.length();

        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inJustDecodeBounds = false;

        Bitmap imageBitmap = BitmapFactory.decodeFile(path);

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

    /**
     * Delete the file at a given path.
     * @param path of the file to delete
     * @return true if deleted, otherwise false
     */
    public Boolean deleteImage(String path) {
        if (path != null) {
            File f = new File(path);
            return f.delete();
        }
        return false;
    }

    /**
     * Convert an image to byte[] and then to a String for online storage
     *
     * @param path image path
     * @return the String encoding of the image
     */
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
