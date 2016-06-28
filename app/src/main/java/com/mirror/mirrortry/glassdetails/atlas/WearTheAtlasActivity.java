package com.mirror.mirrortry.glassdetails.atlas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.toolbox.ImageLoader;
import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseActivity;
import com.mirror.mirrortry.glassdetails.GlassDetailsBean;
import com.mirror.mirrortry.glassdetails.atlas.pic.PicActivity;
import com.mirror.mirrortry.login.LoginActivity;
import com.mirror.mirrortry.net.NetHelper;
import com.mirror.mirrortry.net.VolleySingleton;
import com.mirror.mirrortry.orderdetails.OrderDetailsActivity;

import java.net.URI;
import java.util.ArrayList;

/**
 * Created by dllo on 16/6/24.
 */
public class WearTheAtlasActivity extends BaseActivity implements View.OnClickListener {
    private ArrayList<GlassDetailsBean.DataBean.ListBean.DataInfoBean.WearVideoBean> wearVideoBean;
    private WearTheAtlasAdapter adapter;
    private ListView listView;
    private ImageView ivAtlasHeadItem;
    private View headView;
    private ArrayList<String> atlasUrl;
    private VideoView videoView;
    private ImageView atlasPlay, atlasClose, atlasBack;
    private TextView atlasBuy;
    private Uri uri;
    private boolean flag;

    @Override
    public int setLayout() {
        return R.layout.activity_wear_the_atlas;
    }

    @Override
    public void initView() {
        listView = findView(R.id.lv_atlas);
        atlasBack = findView(R.id.iv_wear_the_atlas_back);
        atlasBuy = findView(R.id.tv_wear_the_atlas_buy);
        headView = LayoutInflater.from(this).inflate(R.layout.atlas_head_item, null);
        ivAtlasHeadItem = (ImageView) headView.findViewById(R.id.iv_atlas_head_item);
        videoView = (VideoView) headView.findViewById(R.id.vv_atlas);
        atlasPlay = (ImageView) headView.findViewById(R.id.iv_atlas_play);
        atlasClose = (ImageView) headView.findViewById(R.id.iv_atlas_close);

        atlasBack.setOnClickListener(this);
        atlasBuy.setOnClickListener(this);

        //去分割线
        listView.setDividerHeight(0);
        //去滚动条
        listView.setVerticalScrollBarEnabled(false);
        //去头部分割线
        listView.setHeaderDividersEnabled(false);

    }

    @Override
    public void initData() {
        adapter = new WearTheAtlasAdapter(this);
        wearVideoBean = new ArrayList<>();
        atlasUrl = new ArrayList<>();
        wearVideoBean = getIntent().getExtras().getParcelableArrayList("wear_video");

        for (GlassDetailsBean.DataBean.ListBean.DataInfoBean.WearVideoBean videoBean : wearVideoBean) {
            String atlasType = videoBean.getType();
            if (atlasType.equals("9")) {
                ImageLoader imageLoader = VolleySingleton.getInstance().getImageLoader();
                imageLoader.get(videoBean.getData(), imageLoader.getImageListener(ivAtlasHeadItem,
                        R.mipmap.null_state, R.mipmap.null_state));
            } else if (atlasType.equals("8")) {
                //获取视频网址
                uri = Uri.parse(videoBean.getData());
            } else {
                atlasUrl.add(videoBean.getData());
            }
        }

        //调用媒体播放器的播放,暂停等功能的点击事件
        videoView.setMediaController(new MediaController(this));
        videoView.setVisibility(View.GONE);
        atlasClose.setVisibility(View.GONE);

        //获取uri
        videoView.setVideoURI(uri);
        //获取焦点
        videoView.requestFocus();

        //播放键的点击事件
        atlasPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.setVisibility(View.VISIBLE);
                videoView.start();
                atlasClose.setVisibility(View.VISIBLE);
                atlasPlay.setVisibility(View.GONE);
                ivAtlasHeadItem.setVisibility(View.GONE);
            }
        });

        //为Media Player的播放完成事件绑定事件监听器
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.start();
            }
        });

        //关闭键的点击事件
        atlasClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.stopPlayback();
                videoView.setVisibility(View.GONE);
                atlasClose.setVisibility(View.GONE);
                atlasPlay.setVisibility(View.VISIBLE);
                ivAtlasHeadItem.setVisibility(View.VISIBLE);
            }
        });

        Log.d("+++++", "atlasUrl:" + atlasUrl);

        listView.addHeaderView(headView);
        adapter.setAtlasUrl(atlasUrl);
        listView.setAdapter(adapter);

//        adapter.setAtlasOnClickListener(new WearTheAtlasAdapter.AtlasOnClickListener() {
//            @Override
//            public void onClick(int position, String url) {
//                Intent intent = new Intent(WearTheAtlasActivity.this,PicActivity.class);
//                intent.putExtra("url",url);
//                Log.d("WearTheAtlasActivity", url);
//                startActivity(intent);
//            }
//        });

        SharedPreferences sp = getSharedPreferences("isLogin", MODE_PRIVATE);
        flag = sp.getBoolean("login", false);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_wear_the_atlas_back:
                finish();
                break;
            case R.id.tv_wear_the_atlas_buy:
                if (NetHelper.isHaveInternet(this) == true) {
                    if (flag == false) {
                        Intent loginIntent = new Intent(WearTheAtlasActivity.this, LoginActivity.class);
                        loginIntent.putExtra("sign",1);
                        startActivity(loginIntent);
                        finish();
                    } else {

                        Intent buy = new Intent(this, OrderDetailsActivity.class);
                        startActivity(buy);

                    }
                }else {
                    Toast.makeText(this, "訂單失敗,請檢查網絡", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
