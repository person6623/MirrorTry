package com.mirror.mirrortry.main;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mirror.mirrortry.R;
import com.mirror.mirrortry.login.LoginActivity;
import com.mirror.mirrortry.db.DBValues;
import com.mirror.mirrortry.main.specialtoshare.SpecialToShareFragment;
import com.mirror.mirrortry.net.URIValues;
import com.mirror.mirrortry.verticalviewpager.VerticalViewPager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private VerticalViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private MainViewPagerAdapter adapter;
    private TextView login;
    private ImageView mirror;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (VerticalViewPager) findViewById(R.id.main_viewPager);
        login = (TextView) findViewById(R.id.login);
        mirror = (ImageView) findViewById(R.id.mirror);
        login.setOnClickListener(this);
        mirror.setOnClickListener(this);

        adapter = new MainViewPagerAdapter(getSupportFragmentManager());

        fragments = new ArrayList<>();

        fragments.add(new SpecialToShareFragment());
        fragments.add(new AllKindFragment());
        fragments.add(new BrowseGogglesFragment());
        fragments.add(new BrowseSunGlassFragment());

        adapter.setFragments(fragments);
        viewPager.setAdapter(adapter);
        Intent intent = getIntent();
        int a = intent.getIntExtra("num",0);
        viewPager.setCurrentItem(a,true);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.mirror:
                ScaleAnim();
                break;
        }
    }

    //缩放动画
    public void ScaleAnim() {
        //前两个参数是X轴 从多少到多少
        //3,4参数 是Y轴 从多少到多少
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1.1f, 1, 1.1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        //动画持续时间
        scaleAnimation.setDuration(500);
        //播放动画重复次数
        scaleAnimation.setRepeatCount(1);
        //指定组件开始动画
        mirror.startAnimation(scaleAnimation);
    }



}
