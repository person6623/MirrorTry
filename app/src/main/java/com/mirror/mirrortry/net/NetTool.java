package com.mirror.mirrortry.net;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.squareup.okhttp.Request;

import java.util.Map;

/**
 * Created by dllo on 16/6/21.
 */
public class NetTool {
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    public NetTool(){
        requestQueue = VolleySingleton.getInstance().getRequestQueue();
        imageLoader = VolleySingleton.getInstance().getImageLoader();
    }

    public void getAllKind(final NetListener netListener){
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

    public void getNet(final NetListener netListener, final Map<String,String> params, String url){
        final StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                netListener.onSuccessed(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netListener.onFailed(error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
