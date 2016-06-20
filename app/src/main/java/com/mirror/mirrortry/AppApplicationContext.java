package com.mirror.mirrortry;

import android.app.Application;
import android.content.Context;

/**
 * Created by dllo on 16/6/20.
 */
public class AppApplicationContext extends Application{

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
