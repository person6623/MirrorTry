package com.mirror.mirrortry.tools;

import android.util.Log;

/**
 * Created by dllo on 16/6/28.
 */
public class TextInterception {
    public static int TextInterception(String text) {
        if (text.split("-").length > 1) {
            if (text.split("-")[1].equals("S")) {
                return 5;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }
}
