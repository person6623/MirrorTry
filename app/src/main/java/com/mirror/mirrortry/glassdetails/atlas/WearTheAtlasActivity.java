package com.mirror.mirrortry.glassdetails.atlas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.toolbox.ImageLoader;
import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseActivity;
import com.mirror.mirrortry.glassdetails.GlassDetailsBean;
import com.mirror.mirrortry.net.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/24.
 */
public class WearTheAtlasActivity extends BaseActivity {
    private ArrayList<GlassDetailsBean.DataBean.ListBean.DataInfoBean.WearVideoBean> wearVideoBean;
    private WearTheAtlasAdapter adapter;
    private ListView listView;
    private ImageView ivAtlasHeadItem;
    private View headView;
    private ArrayList<String> atlasUrl;

    @Override
    public int setLayout() {
        return R.layout.activity_wear_the_atlas;
    }

    @Override
    public void initView() {
        listView = findView(R.id.lv_atlas);
        headView = LayoutInflater.from(this).inflate(R.layout.atlas_head_item, null);
        ivAtlasHeadItem = (ImageView) headView.findViewById(R.id.iv_atlas_head_item);

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

            } else {
                atlasUrl.add(videoBean.getData());
            }
        }
        adapter.setAtlasUrl(atlasUrl);
        listView.setAdapter(adapter);
        listView.addHeaderView(headView);

//        String url = null;
//        for (int i = 0; i < wearVideoBean.size(); i++) {
//
//            String type = wearVideoBean.get(i).getType();
//            if (type == "9") {
//                url = wearVideoBean.get(i).getData();
//            }
//        }
//        ImageLoader imageLoader = VolleySingleton.getInstance().getImageLoader();
//        imageLoader.get(url, imageLoader.getImageListener(ivAtlasHeadItem,
//                R.mipmap.null_state, R.mipmap.null_state));
//
    }
}
