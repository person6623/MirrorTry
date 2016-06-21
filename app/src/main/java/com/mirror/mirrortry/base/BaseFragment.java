package com.mirror.mirrortry.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by dllo on 16/6/20.
 */
public abstract class BaseFragment extends Fragment {
    protected Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    //设置布局
    public abstract int setLayout();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(setLayout(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //调用初始化组件的方法
        initView(view);
    }

    public abstract void initView(View view);

    //封装绑定id的方法
    protected <T extends View> T findView(int id, View view) {
        return (T) view.findViewById(id);
    }

    //封装Toast的方法
    protected void showToast(String mag) {
        Toast.makeText(context, mag, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化子类的数据
        initData();
    }

    public abstract void initData();
}
