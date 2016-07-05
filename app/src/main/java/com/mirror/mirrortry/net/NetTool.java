package com.mirror.mirrortry.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.mirror.mirrortry.AppApplicationContext;
import com.mirror.mirrortry.R;
import com.mirror.mirrortry.libcore.io.Disk;
import com.mirror.mirrortry.tools.NetImageView;
import com.squareup.okhttp.Request;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Created by dllo on 16/6/21.
 */
public class NetTool {
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;


    public NetTool() {
        requestQueue = VolleySingleton.getInstance().getRequestQueue();
        imageLoader = VolleySingleton.getInstance().getImageLoader();
    }

    public void getAllKind(final NetListener netListener) {
        StringRequest stringRequest = new StringRequest(URIValues.ALL_KIND, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                netListener.onSuccessed(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netListener.onFailed(error);
            }
        });
        requestQueue.add(stringRequest);
    }

    //volley的post请求  只添加了请求的body
    public void getNet(final NetListener netListener, final Map<String, String> params, String url) {
        final StringRequest stringRequest = new StringRequest
                (com.android.volley.Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        netListener.onSuccessed(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        netListener.onFailed(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    //封装imageLoader
    public void getImageNet(String url, NetworkImageView imageVew) {
        if (NetHelper.isHaveInternet(AppApplicationContext.context)) {
            imageVew.setImageUrl(url, imageLoader);
            imageVew.setDefaultImageResId(R.mipmap.null_state);
            imageVew.setErrorImageResId(R.mipmap.null_state);
        } else {
            Bitmap bitmap = new Disk().getPicFromDir(url);
            if (bitmap != null) {

                imageVew.setImageBitmap(bitmap);
            } else {
                imageVew.setDefaultImageResId(R.mipmap.ic_launcher);
            }
        }
    }


}
