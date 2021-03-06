package com.mirror.mirrortry.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by dllo on 16/6/20.
 */
public abstract class BaseActivity extends AppCompatActivity {
    //绑定布局
    public abstract int setLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());

        //调用初始化组件的方法
        initView();
        //初始化子类的布局
        initData();
    }
        
    //封装绑定id的方法
    protected <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    public abstract void initData();

    public abstract void initView();

}
