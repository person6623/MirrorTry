package com.mirror.mirrortry.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by dllo on 16/6/24.
 */
public class NetHelper {


    //判斷手機是否聯網
    public static boolean isHaveInternet(Context context){

        try {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            return (info != null && info.isConnected());
        }catch (Exception e){
            return false;
        }
    }
}
