package com.mirror.mirrortry.tools;

import android.util.Log;

/**
 * Created by dllo on 16/6/28.
 */
public class TextInterception {
    public static int TextInterception(String text) {
        if (text.split("-").length > 1) {
            if (text.split("-")[1].equals("S")) {
                Log.d("-=-=-=asdad", text.split("-")[1]);
                return 5;
            } else {
                Log.d("-=-=-=klklklk", text.split("-")[1]);
                return 0;
            }
        } else {
            return 0;
        }
    }
}
