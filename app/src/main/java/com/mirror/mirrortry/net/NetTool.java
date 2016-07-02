package com.mirror.mirrortry.net;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.mirror.mirrortry.R;
import com.squareup.okhttp.Request;

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
    public void getImageLoaderNet(String url, ImageView imageVew, ProgressBar progressBar) {
        imageLoader.get(url, new ImageListenerWithAlpha
                (R.mipmap.null_state, R.mipmap.null_state, imageVew, progressBar));

    }


    public class ImageListenerWithAlpha implements ImageLoader.ImageListener {
        int defaultImg, errorImg;
        ImageView imageView;
        ProgressBar progressBar;

        public ImageListenerWithAlpha(int defaultImg, int errorImg, ImageView imageView, ProgressBar progressBar) {
            this.defaultImg = defaultImg;
            this.errorImg = errorImg;
            this.imageView = imageView;
            this.progressBar = progressBar;
        }

        @Override
        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
            if (response.getBitmap() != null) {
                imageView.setImageBitmap(response.getBitmap());
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
            }else if (defaultImg != 0) {
                imageView.setImageResource(defaultImg);
            }
        }

        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }
}
