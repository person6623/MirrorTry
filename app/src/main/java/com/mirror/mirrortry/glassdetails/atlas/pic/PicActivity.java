package com.mirror.mirrortry.glassdetails.atlas.pic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.mirror.mirrortry.R;
import com.mirror.mirrortry.glassdetails.atlas.WearTheAtlasActivity;
import com.mirror.mirrortry.net.NetTool;

import java.net.URI;
import java.util.ArrayList;

/**
 * Created by dllo on 16/6/25.
 */
public class PicActivity extends Activity {
    private ImageView imageView;
    private String url;
    private NetTool netTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pic);

        imageView = (ImageView) findViewById(R.id.iv_pic);

        Intent inIntent = getIntent();
        url = inIntent.getStringExtra("url");
        Log.d("PicActivity", url);

        netTool = new NetTool();
        netTool.getImageLoaderNet(url, imageView, null);

        ScaleAnim();

//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent outIntent = new Intent(PicActivity.this, WearTheAtlasActivity.class);
//                startActivity(outIntent);
//            }
//        });


    }

    public void ScaleAnim() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.9f, 1, 0.9f, 1,
                Animation.RELATIVE_TO_PARENT, 0.5f,
                Animation.RELATIVE_TO_PARENT, 0.5f);
        scaleAnimation.setDuration(800);
        imageView.startAnimation(scaleAnimation);
    }


}
