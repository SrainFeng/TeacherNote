package com.example.srain.teachernote;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

/**
 * Project: TeacherNote
 * Date: 2018/6/25
 *
 * @author srain
 */
public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LitePal.initialize(this);
    }

    public static Context getContext() {
        return context;
    }
}
