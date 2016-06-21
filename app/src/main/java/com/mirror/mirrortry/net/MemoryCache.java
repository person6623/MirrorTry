package com.mirror.mirrortry.net;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by dllo on 16/6/21.
 */
public class MemoryCache implements ImageLoader.ImageCache {
    private LruCache<String, Bitmap> lruCache;

    public MemoryCache() {
        //需要告诉LurCache的最大占用内存数
        int macSize = (int) (Runtime.getRuntime().maxMemory() / 8 / 1024);
        lruCache = new LruCache<String, Bitmap>(macSize) {
            //返回每一个Value它所占用的内存数
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };

    }

    @Override
    public Bitmap getBitmap(String url) {
        return lruCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        lruCache.put(url, bitmap);
    }
}
