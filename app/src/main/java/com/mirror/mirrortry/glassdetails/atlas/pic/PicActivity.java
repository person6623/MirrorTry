package com.mirror.mirrortry.glassdetails.atlas.pic;

//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.view.animation.Animation;
//import android.view.animation.ScaleAnimation;
//import android.widget.ImageView;
//
//import com.mirror.mirrortry.R;
//import com.mirror.mirrortry.glassdetails.atlas.WearTheAtlasActivity;
//import com.mirror.mirrortry.net.NetTool;
//
//import java.net.URI;
//import java.util.ArrayList;
//
///**
// * Created by dllo on 16/6/25.
// */
//public class PicActivity extends Activity {
//    private ImageView imageView;
//    private String url;
//    private NetTool netTool;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_pic);
//
//        imageView = (ImageView) findViewById(R.id.iv_pic);
//
//        Intent inIntent = getIntent();
//        url = inIntent.getStringExtra("url");
//        Log.d("PicActivity", url);
//
//        netTool = new NetTool();
//        netTool.getImageLoaderNet(url, imageView, null);
//
//        ScaleAnim();
//
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ScaleFinishAnim();
//                finish();
//
//            }
//        });
//
//
//    }
//
//    public void ScaleAnim() {
//        ScaleAnimation scaleAnimation = new ScaleAnimation(0.9f, 1, 0.9f, 1,
//                Animation.RELATIVE_TO_PARENT, 0.5f,
//                Animation.RELATIVE_TO_PARENT, 0.5f);
//        scaleAnimation.setDuration(800);
//        imageView.startAnimation(scaleAnimation);
//    }
//
//    public void ScaleFinishAnim() {
//        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0.9f, 1, 0.9f,
//                Animation.RELATIVE_TO_PARENT, 0.5f,
//                Animation.RELATIVE_TO_PARENT, 0.5f);
//        scaleAnimation.setDuration(800);
//        imageView.startAnimation(scaleAnimation);
//    }
//
//}


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView.ScaleType;

import com.mirror.mirrortry.net.NetTool;
import com.mirror.mirrortry.smoothimageview.SmoothImageView;

public class PicActivity extends Activity {
    private int mLocationX;
    private int mLocationY;
    private int mWidth;
    private int mHeight;
    SmoothImageView imageView = null;
    private NetTool netTool;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra("url");
        mLocationX = getIntent().getIntExtra("locationX", 0);
        mLocationY = getIntent().getIntExtra("locationY", 0);
        mWidth = getIntent().getIntExtra("width", 0);
        mHeight = getIntent().getIntExtra("height", 0);
        netTool = new NetTool();
        imageView = new SmoothImageView(this);
        imageView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
        imageView.transformIn();
        imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        imageView.setScaleType(ScaleType.FIT_CENTER);
        setContentView(imageView);

        netTool.getImageLoaderNet(url, imageView, null);

//        ImageLoader.getInstance().displayImage(mDatas.get(mPosition), imageView);
//		imageView.setImageResource(R.drawable.temp);
        // ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 1.0f, 0.5f,
        // 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
        // 0.5f);
        // scaleAnimation.setDuration(300);
        // scaleAnimation.setInterpolator(new AccelerateInterpolator());
        // imageView.startAnimation(scaleAnimation);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setOnTransformListener(new SmoothImageView.TransformListener() {

                    @Override
                    public void onTransformComplete(int mode) {
                        if (mode == 2) {
                            finish();
                        }
                    }
                });
                imageView.transformOut();
            }
        });

    }


    @Override
    public void onBackPressed() {
        imageView.setOnTransformListener(new SmoothImageView.TransformListener() {
            @Override
            public void onTransformComplete(int mode) {
                if (mode == 2) {
                    finish();
                }
            }
        });
        imageView.transformOut();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(0, 0);
        }
    }

}
