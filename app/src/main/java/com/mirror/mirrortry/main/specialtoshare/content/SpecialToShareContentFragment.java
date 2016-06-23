package com.mirror.mirrortry.main.specialtoshare.content;

import android.view.View;

import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseFragment;
import com.mirror.mirrortry.main.specialtoshare.SpecialToShareBean;

/**
 * Created by dllo on 16/6/22.
 */
public class SpecialToShareContentFragment extends BaseFragment {


    @Override
    public int setLayout() {
        return R.layout.fragment_special_to_share_content;
    }

    @Override
    public void initView(View view) {


    }

    @Override
    public void initData() {

    }

    //fragment里被调用的组件初始化方法
    public void initComponent(SpecialToShareBean.DataBean.ListBean.StoryDataBean.TextArrayBean textArrayBean){

    }
}
