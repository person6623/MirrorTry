package com.mirror.mirrortry.main.specialtoshare.content;

import android.content.Intent;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseActivity;
import com.mirror.mirrortry.main.specialtoshare.SpecialToShareBean;
import com.mirror.mirrortry.net.VolleySingleton;
import com.mirror.mirrortry.verticalviewpager.VerticalViewPager;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/22.
 */
public class SpecialToShareActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private SpecialToShareBean.DataBean.ListBean listBean;
    private ArrayList<SpecialToShareBean.DataBean.ListBean.StoryDataBean.TextArrayBean> textArrayBean;
    private ArrayList<String> imgArray;
    private ArrayList<Fragment> fragments;
    private VerticalViewPager viewPager;
    private ImageView ivShowPageClose, ivSpecialShare, getIvSpecialBackground;
    //viewpagerAdapter
    private SpecialToShareAdapter adapter;
    private int currentPosition;


    @Override
    public int setLayout() {
        return R.layout.activity_special_to_share;
    }

    @Override
    public void initView() {
        ivShowPageClose = findView(R.id.iv_show_page_close);
        ivSpecialShare = findView(R.id.iv_special_share);
        getIvSpecialBackground = findView(R.id.iv_special_background);

        viewPager = findView(R.id.vp_special_share);
        adapter = new SpecialToShareAdapter(getSupportFragmentManager());

        ivSpecialShare.setOnClickListener(this);
        ivShowPageClose.setOnClickListener(this);
        viewPager.setOnPageChangeListener(this);
    }

    @Override
    public void initData() {

        imgArray = new ArrayList<>();
        fragments = new ArrayList<>();


        listBean = getIntent().getExtras().getParcelable("listBean");
        textArrayBean = getIntent().getExtras().getParcelableArrayList("textArrayBean");

        imgArray = getIntent().getStringArrayListExtra("imgArray");


        for (int i = 0; i < textArrayBean.size(); i++) {
            Bundle singleBundle = new Bundle();
            SpecialToShareContentFragment specialToShareContentFragment = new SpecialToShareContentFragment();
            SpecialToShareBean.DataBean.ListBean.StoryDataBean.TextArrayBean singleTextArrayBean = textArrayBean.get(i);
            singleBundle.putParcelable("singleTextArrayBean", singleTextArrayBean);
            specialToShareContentFragment.setArguments(singleBundle);
            fragments.add(specialToShareContentFragment);
        }

        //viewpager加入fragment
        adapter.setFragments(fragments);
        viewPager.setAdapter(adapter);
        initPhoto(0);
    }

    //换页监听
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        initPhoto(position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {


    }

    public void initPhoto(int position) {
        currentPosition = position;
        ImageLoader loader = VolleySingleton.getInstance().getImageLoader();
        loader.get(imgArray.get(currentPosition),
                ImageLoader.getImageListener(getIvSpecialBackground, R.mipmap.null_state, R.mipmap.null_state));


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_show_page_close:
                finish();
                break;
            case R.id.iv_special_share:
                Toast.makeText(this, "你想分享吗", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
