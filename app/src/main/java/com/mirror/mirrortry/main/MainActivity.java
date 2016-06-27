package com.mirror.mirrortry.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mirror.mirrortry.R;
import com.mirror.mirrortry.login.LoginActivity;
import com.mirror.mirrortry.main.allkind.AllKindFragment;
import com.mirror.mirrortry.main.goggles.BrowseGogglesFragment;
import com.mirror.mirrortry.main.shoppingcart.ShoppingFragment;
import com.mirror.mirrortry.main.specialtoshare.SpecialToShareFragment;
import com.mirror.mirrortry.main.sunglass.BrowseSunGlassFragment;
import com.mirror.mirrortry.verticalviewpager.VerticalViewPager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private VerticalViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private MainViewPagerAdapter adapter;
    private TextView login;
    private ImageView mirror;
    private int b;
    private ScaleAnimation scaleAnimation;
    private boolean flag;


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

        fragments.add(new AllKindFragment());
        fragments.add(new BrowseGogglesFragment());
        fragments.add(new BrowseSunGlassFragment());
        fragments.add(new SpecialToShareFragment());
        fragments.add(new ShoppingFragment());

        adapter.setFragments(fragments);
        viewPager.setAdapter(adapter);

        Intent intent = getIntent();
        int a = intent.getIntExtra("num", 0);
        viewPager.setCurrentItem(a);

        SharedPreferences getSp = getSharedPreferences("isLogin", MODE_PRIVATE);
        flag = getSp.getBoolean("login", false);
        if (flag == false) {
            login.setText("登錄");
        } else {
            login.setText("購物車");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                if (flag == false) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    scaleAnim();
                    login.startAnimation(scaleAnimation);
                    viewPager.setCurrentItem(2, true);
                }
                break;
            case R.id.mirror:
                scaleAnim();
                //指定组件开始动画
                mirror.startAnimation(scaleAnimation);
                break;
        }
    }

    //缩放动画
    public void scaleAnim() {
        //前两个参数是X轴 从多少到多少
        //3,4参数 是Y轴 从多少到多少
        scaleAnimation = new ScaleAnimation(1, 1.1f, 1, 1.1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        //动画持续时间
        scaleAnimation.setDuration(500);
        //播放动画重复次数
        scaleAnimation.setRepeatCount(1);

    }


}
