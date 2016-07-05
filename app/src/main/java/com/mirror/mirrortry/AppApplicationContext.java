package com.mirror.mirrortry;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.mirror.mirrortry.libcore.io.DiskLruCache;

import java.io.File;
import java.io.IOException;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by dllo on 16/6/20.
 */
public class AppApplicationContext extends Application {

    public static Context context;
    public static  DiskLruCache mDiskLruCache;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        ShareSDK.initSDK(this);
        //获取diskLruCache实例
        try {
            File cacheDir = getDiskCacheDir(this, "bitmap");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    private int getAppVersion() {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    //获取缓存地址
    //有无sd卡的情况判断
    private File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
//        判断SD卡是否存在，并且是否具有读写权限
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                !Environment.isExternalStorageRemovable()) {
            //获取应用程序自己的缓存目录
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            //获取应用程序自己的缓存目录
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }
}
