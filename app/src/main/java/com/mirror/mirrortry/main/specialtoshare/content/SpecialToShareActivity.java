package com.mirror.mirrortry.main.specialtoshare.content;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseActivity;
import com.mirror.mirrortry.main.specialtoshare.SpecialToShareBean;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/22.
 */
public class SpecialToShareActivity extends BaseActivity {

    private Intent intent;
    private ArrayList<SpecialToShareBean.DataBean.ListBean> beanList;
    private ArrayList<Fragment> fragments;


    @Override
    public int setLayout() {
        return R.layout.activity_special_to_share;
    }

    @Override
    public void initView() {
        intent = getIntent();
        beanList = intent.getParcelableArrayListExtra("beanList");


    }

    @Override
    public void initData() {

    }
}
