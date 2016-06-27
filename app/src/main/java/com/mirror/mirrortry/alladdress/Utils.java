package com.mirror.mirrortry.alladdress;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by dllo on 16/6/25.
 */
public class Utils {
    public static int getScreenWidth(Context context){

        //获取屏幕宽度
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        //创建DisplayMetrics
        //屏幕的一些参数通过该类获取
        DisplayMetrics outMetrics = new DisplayMetrics();

        //调用windowManager 的getDefaultDisplay() 方法  对这个outMetrics
        //里的各个参数进行赋值
        windowManager.getDefaultDisplay().getMetrics(outMetrics);

        return outMetrics.widthPixels;

    }
}
