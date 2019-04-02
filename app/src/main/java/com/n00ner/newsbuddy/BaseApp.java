package com.n00ner.newsbuddy;

import android.app.Application;
import android.content.Context;

public class BaseApp extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        BaseApp.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return BaseApp.context;
    }
}
