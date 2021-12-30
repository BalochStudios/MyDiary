package com.muaz.mydiary.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.muaz.mydiary.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class UtilityFunctions {

    private static final String TAG = "myTag";

    public static DateFormat getDateFormat() {
        String myFormat = "dd, MMMM yyyy";
        return new SimpleDateFormat(myFormat, Locale.US);
    }

    public static List<String> getAllFonts(Context context) {
        List<String> fontsList = new ArrayList<>();
        try {
            fontsList = Arrays.asList(context.getAssets().list("fonts"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fontsList;
    }

    public static void changeColor(ImageView imageView, Context context, int color) {
        imageView.setColorFilter(ContextCompat.getColor(context, color), android.graphics.PorterDuff.Mode.SRC_IN);
    }

    public static void makeToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
        return stream.toByteArray();
    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

}
