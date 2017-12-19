package com.example.doctorfive.base;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

//import org.litepal.LitePalApplication;

import java.io.ByteArrayOutputStream;

/**
 * Created by DoctorFive on 2017/10/16.
 * 取得当前类的context
 */

public class MyApplication extends Application {
    private  static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        //LitePalApplication.initialize(context);
    }

    public static Context getContext() {
        return context;
    }
    public byte[]img(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

}
