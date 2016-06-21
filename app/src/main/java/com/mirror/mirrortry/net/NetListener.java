package com.mirror.mirrortry.net;

import com.android.volley.VolleyError;

/**
 * Created by dllo on 16/6/21.
 */
public interface NetListener {

    void onSuccessed(String result);
    void onFailed(VolleyError error);
}
