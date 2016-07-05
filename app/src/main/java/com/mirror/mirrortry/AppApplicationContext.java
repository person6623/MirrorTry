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
    public static DiskLruCache mDiskLruCache,resultDiskCache,allResultLruCache;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
//        ShareSDK.initSDK(this);
        //获取diskLruCache实例
        //存图片
        try {
            File cacheDir = getDiskCacheDir(this, "bitmap");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //存result
        try {
            File cacheDir = getDiskCacheDir(this, "result");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            resultDiskCache = DiskLruCache.open(cacheDir, getAppVersion(), 1, 100 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();

            //存allResult
        }
        try {
            File cacheDir = getDiskCacheDir(this, "allResult");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            allResultLruCache = DiskLruCache.open(cacheDir, getAppVersion(), 1, 100 * 1024 * 1024);
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
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }
}
