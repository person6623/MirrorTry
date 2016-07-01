package com.mirror.mirrortry.libcore.io;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.mirror.mirrortry.AppApplicationContext;
import com.mirror.mirrortry.net.ThreadSingleton;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by dllo on 16/7/1.
 */
public class Disk {

    //将图片下载下来存为字节
    public void saveToDir(final String url) {
        ThreadSingleton.getInstance().getExecutorService().execute(new Runnable() {
            @Override
            public void run() {

                try {
                    //将url用MD5编码 作为索引的key值  key即为存储的文件的名字
                    String key = hashKeyForDisk(url);
                    //写入
                    DiskLruCache.Editor editor = AppApplicationContext.mDiskLruCache.edit(key);
                    if (editor != null) {
                        //创建输出流
                        OutputStream outputStream = editor.newOutputStream(0);
                        //
                        if (downloadUrlToStream(url, outputStream)) {
                            editor.commit();
                        } else {
                            editor.abort();
                        }
                    }
                    AppApplicationContext.mDiskLruCache.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    //获取文件夹中的图片
    public Bitmap getPicFromDir(String url) {
        Bitmap bitmap = null;
        String key = hashKeyForDisk(url);
        try {
            DiskLruCache.Snapshot snapshot = AppApplicationContext.mDiskLruCache.get(key);
            if (snapshot != null) {
                InputStream is = snapshot.getInputStream(0);
                bitmap = BitmapFactory.decodeStream(is);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    //MD5编码
    private String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    //将图片解析下来
    private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


}
