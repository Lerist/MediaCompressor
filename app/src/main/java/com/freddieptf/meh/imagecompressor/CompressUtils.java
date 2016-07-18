package com.freddieptf.meh.imagecompressor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by freddieptf on 18/07/16.
 */
public class CompressUtils {

    public static void compressPic(File picture, BitmapFactory.Options options, int quality){
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(picture), null, options);
            FileOutputStream outputStream = new FileOutputStream(picture.getParent() + "/compressed_" + picture.getName());
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.close();
            System.gc();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap scaleImageForPreview(String picPath, int size){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = calculateInSampleSize(picPath, size);
        return BitmapFactory.decodeFile(picPath, options);
    }

    public static int calculateInSampleSize(String picPath, int REQUIRED_SIZE){
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(picPath, o);

        // Find the correct scale value. It should be the power of 2.
        int scale = 1;
        while(o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE) {
            scale *= 2;
        }
        return scale;
    }

}
