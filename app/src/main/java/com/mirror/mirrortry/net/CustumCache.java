package com.mirror.mirrortry.net;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

/**
 * Created by dllo on 16/7/2.
 */
public class CustumCache {
    //硬缓存空间
    private static final int cacheSize = 8 * 1024 * 1024;

    private static final LruCache<String, Bitmap> bitmapLruCache = new LruCache<String, Bitmap>(cacheSize) {

        //bitmap的size
        @Override
        public int sizeOf(String key, Bitmap value) {
            return value.getRowBytes() * value.getHeight();
        }

        @Override
        protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
//            super.entryRemoved(evicted, key, oldValue, newValue);
            bitmapSoftCache.put(key, new SoftReference<Bitmap>(oldValue));
        }
    };

    //软引用
    //初始容量
    private static final int SOFT_CACHE_CAPACITY = 40;

    private final static LinkedHashMap<String, SoftReference<Bitmap>> bitmapSoftCache =
            new LinkedHashMap<String, SoftReference<Bitmap>>(SOFT_CACHE_CAPACITY, 0.75f, true) {
                @Override
                public SoftReference<Bitmap> put(String key, SoftReference<Bitmap> value) {
                    return super.put(key, value);
                }

                //判断软存是否占满
                @Override
                protected boolean removeEldestEntry(Entry<String, SoftReference<Bitmap>> eldest) {
                    if (size() > SOFT_CACHE_CAPACITY) {
                        Log.d(">>>>>>>>>>>", "软存已满");
                        return true;
                    }
                    return false;
                }
            };


    //缓存bitmap
    public static boolean putBitmap(String key, Bitmap bitmap) {
        if (bitmap != null) {
            synchronized (bitmapLruCache) {
                bitmapLruCache.put(key, bitmap);
            }
            return true;
        }
        return false;
    }

    //从缓存中获取bitmap
    public static Bitmap getBitmap(String key) {
        synchronized (bitmapLruCache) {
            if (bitmapLruCache.get(key) != null) {
                return bitmapLruCache.get(key);
            }

        }
        //当缓存读取不到对应的bitmap则去软存中查找
        synchronized (bitmapSoftCache) {
            if (bitmapSoftCache.get(key) != null) {
                return bitmapSoftCache.get(key).get();
            } else {
                bitmapSoftCache.remove(key);
            }
        }
        return null;
    }
}
