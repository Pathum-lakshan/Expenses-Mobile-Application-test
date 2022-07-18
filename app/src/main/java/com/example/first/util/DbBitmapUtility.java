package com.example.first.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class DbBitmapUtility {
    private static Bitmap Bitmaps;

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public static Bitmap getImage(byte[] image) {

        if (image==null){
            return Bitmaps;
        }else {

        return BitmapFactory.decodeByteArray(image, 0, image.length);

        }
    }
}
