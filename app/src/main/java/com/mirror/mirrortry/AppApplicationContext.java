package com.mirror.mirrortry;

import android.app.Application;
import android.content.Context;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by dllo on 16/6/20.
 */
public class AppApplicationContext extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        ShareSDK.initSDK(this);
    }
}
