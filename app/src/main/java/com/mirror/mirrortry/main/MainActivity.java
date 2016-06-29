package com.mirror.mirrortry.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
//    private MySendBroadcastReceiver receiver;
//    private Intent intent = getIntent();

//    private boolean isopen = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        isopen = false;
        Log.d("MainActivity", "onCreate");


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


        SharedPreferences getSp = getSharedPreferences("isLogin", MODE_PRIVATE);
        flag = getSp.getBoolean("login", false);
        if (flag == false) {
            login.setText("登錄");
        } else {
            login.setText("購物車");
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences getSp = getSharedPreferences("isLogin", MODE_PRIVATE);
        boolean flag = getSp.getBoolean("login", false);
        Log.d("=======>>>>>>>>>>>", "flag:" + flag);
        if (flag == false) {
            login.setText("登錄");
        } else {
            login.setText("購物車");
        }
    }

    //如果IntentActivity处于任务栈的顶端，也就是说之前打开过的Activity，现在处于
//    onPause
//    onStop 状态的话
//    其他应用再发送Intent的话，执行顺序为：
//    onNewIntent
//    onRestart
//    onStart
//    onResume
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        int a = intent.getIntExtra("num", 0);
        Log.d("+++++++++>>>>>>>>>>", "a:" + a);
        viewPager.setCurrentItem(a);
//        receiver = new MySendBroadcastReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("com.mirror.mirrortry.login.BROAD");
//        registerReceiver(receiver, filter);
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
                    viewPager.setCurrentItem(4, true);
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

//
//    class MySendBroadcastReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            flag = intent.getBooleanExtra("login", false);
//            login.setText("購物車");
//        }
//    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unregisterReceiver(receiver);
//    }
}
