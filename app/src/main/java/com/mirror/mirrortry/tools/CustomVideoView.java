package com.mirror.mirrortry.tools;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by dllo on 16/7/1.
 */
public class CustomVideoView extends VideoView {
    private PlayPauseListener mListener;

    public CustomVideoView(Context context) {
        super(context);
    }

    public CustomVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setPlayPauseListener(PlayPauseListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void pause() {
        super.pause();
        if (mListener != null) {
            mListener.onPause();
        }
    }

    @Override
    public void start() {
        super.start();
        if (mListener != null) {
            mListener.onPlay();
        }
    }

    public interface PlayPauseListener {
        void onPlay();

        void onPause();
    }
}
