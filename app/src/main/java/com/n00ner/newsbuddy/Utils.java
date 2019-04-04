package com.n00ner.newsbuddy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.VectorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

public class Utils {
    public static void setCornerImage(ImageView image, Context context, int radius){
        if(image.getDrawable() instanceof VectorDrawable)
            return;
        Bitmap imageBitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory
                .create(context.getResources(), imageBitmap);

        imageDrawable.setCircular(true);
        imageDrawable.setCornerRadius((float) radius);
        image.setImageDrawable(imageDrawable);
    }

    public static boolean hasNetwork(Context context){
        boolean isConnected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if(activeNetwork != null && activeNetwork.isConnected()){
            isConnected = true;
        }
        return isConnected;
    }
}
