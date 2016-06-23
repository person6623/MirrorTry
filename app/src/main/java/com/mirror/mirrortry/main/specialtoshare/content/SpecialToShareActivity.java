package com.mirror.mirrortry.main.specialtoshare.content;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseActivity;
import com.mirror.mirrortry.main.specialtoshare.SpecialToShareBean;
import com.mirror.mirrortry.net.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/22.
 */
public class SpecialToShareActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private Intent intent;
    private SpecialToShareBean.DataBean.ListBean listBean;
    private ArrayList<Fragment> fragments;
    private ViewPager viewPager;
    //viewpagerAdapter
    private SpecialToShareAdapter adapter;

    private int currentPosition;


    @Override
    public int setLayout() {
        return R.layout.activity_special_to_share;
    }

    @Override
    public void initView() {
        viewPager = (ViewPager) findViewById(R.id.vp_special_share);
        adapter = new SpecialToShareAdapter(getSupportFragmentManager());

        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void initData() {
        intent = getIntent();
        listBean = intent.getExtras().getParcelable("beanList");

        //fragment初始化
        for (SpecialToShareBean.DataBean.ListBean.StoryDataBean.TextArrayBean textArrayBean : listBean.getStory_data().getText_array()) {
            SpecialToShareContentFragment specialToShareContentFragment = new SpecialToShareContentFragment();
            specialToShareContentFragment.initComponent(textArrayBean);
            fragments.add(specialToShareContentFragment);
        }
        //viewpager加入fragment
        adapter.setFragments(fragments);
        viewPager.setAdapter(adapter);
    }

    //换页监听
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position + 1;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

        ImageView imageView = new ImageView(this);

        ImageLoader loader = VolleySingleton.getInstance().getImageLoader();
        loader.get(listBean.getStory_data().getImg_array().get(currentPosition),
                ImageLoader.getImageListener(imageView,R.mipmap.null_state,R.mipmap.null_state));

        //为viewpager添加背景
        viewPager.setBackground(imageView.getDrawable());
    }
}
