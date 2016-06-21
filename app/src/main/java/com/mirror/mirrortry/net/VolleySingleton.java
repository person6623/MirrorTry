package com.mirror.mirrortry.net;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.mirror.mirrortry.AppApplicationContext;

/**
 * Created by dllo on 16/6/20.
 * 单例模式 volley
 */
public class VolleySingleton {
    //请求队列
    private RequestQueue requestQueue;
    //图片解析
    private ImageLoader imageLoader;

    private static VolleySingleton ourInstance;

    public static VolleySingleton getInstance() {
        if (ourInstance == null) {
            synchronized (VolleySingleton.class) {
                if (ourInstance == null) {
                    ourInstance = new VolleySingleton();
                }
            }
        }
        return ourInstance;
    }

    private VolleySingleton() {
        requestQueue = Volley.newRequestQueue(AppApplicationContext.context);
        imageLoader = new ImageLoader(requestQueue, new MemoryCache());
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
