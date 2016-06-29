package com.mirror.mirrortry.list;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mirror.mirrortry.R;
import com.mirror.mirrortry.login.LoginActivity;
import com.mirror.mirrortry.main.MainActivity;
import com.zhy.autolayout.AutoRelativeLayout;

/**
 * Created by dllo on 16/6/22.
 */
public class ListActivity extends Activity implements View.OnClickListener {

    private int[] ids = {R.id.rl_list, R.id.rl_see_all, R.id.rl_see_goggles, R.id.rl_see_sunGlass,
            R.id.rl_subject_share, R.id.rl_shopping_car, R.id.rl_back_main, R.id.rl_exit};
    private AutoRelativeLayout listItem;
    private TextView seeAll, seeGoggles, seeSunGlass, subjectShare, shoppingCar, backMain, exit, login;
    private ImageView all, goggles, sunGlass, share, shopping, back, exited,mirror;
    private int position;
    private ScaleAnimation scaleAnimation,textScaleAnimation;
    private boolean flag;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        for (int i = 0; i < ids.length; i++) {
            findViewById(ids[i]).setOnClickListener(this);
        }
        findViewById(R.id.rl_list).setOnClickListener(this);
        seeAll = (TextView) findViewById(R.id.tv_see_all);
        seeGoggles = (TextView) findViewById(R.id.tv_see_goggles);
        seeSunGlass = (TextView) findViewById(R.id.tv_see_sunGlass);
        subjectShare = (TextView) findViewById(R.id.tv_subject_share);
        shoppingCar = (TextView) findViewById(R.id.tv_shopping_car);
        backMain = (TextView) findViewById(R.id.tv_backMain);
        exit = (TextView) findViewById(R.id.tv_exit);
        login = (TextView) findViewById(R.id.list_login);
        login.setOnClickListener(this);
        mirror = (ImageView) findViewById(R.id.list_mirror);
        mirror.setOnClickListener(this);

        all = (ImageView) findViewById(R.id.iv_see_all);
        goggles = (ImageView) findViewById(R.id.iv_see_goggles);
        sunGlass = (ImageView) findViewById(R.id.iv_see_sunGlass);
        share = (ImageView) findViewById(R.id.iv_subject_share);
        shopping = (ImageView) findViewById(R.id.iv_shopping_car);
        back = (ImageView) findViewById(R.id.iv_backMain);
        exited = (ImageView) findViewById(R.id.iv_exit);
        listItem = (AutoRelativeLayout) findViewById(R.id.rl_list_item);

        scaleAnim();
        //指定组件开始动画
        listItem.startAnimation(scaleAnimation);

        setShow();

        sp = getSharedPreferences("isLogin", MODE_PRIVATE);
        flag = sp.getBoolean("login", false);
        if (flag == false) {
            login.setText("登錄");
        } else {
            login.setText("購物車");
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rl_list:
                finish();
                break;
            case R.id.rl_see_all:
                Intent all = new Intent(this, MainActivity.class);
                all.putExtra("num",0);
                startActivity(all);
                finish();
                break;
            case R.id.rl_see_goggles:

                Intent goggles = new Intent(this, MainActivity.class);
                goggles.putExtra("num", 1);
                startActivity(goggles);
                finish();
                break;
            case R.id.rl_see_sunGlass:
                Intent sunGlass = new Intent(this, MainActivity.class);
                sunGlass.putExtra("num", 2);
                startActivity(sunGlass);
                finish();
                break;
            case R.id.rl_subject_share:
                Intent subject = new Intent(this, MainActivity.class);
                subject.putExtra("num", 3);
                startActivity(subject);
                finish();
                break;
            case R.id.rl_shopping_car:
                Intent shopping = new Intent(this, MainActivity.class);
                shopping.putExtra("num", 4);
                startActivity(shopping);
                finish();
                break;
            case R.id.rl_back_main:
                Intent back = new Intent(this, MainActivity.class);
                back.putExtra("num", 0);
                startActivity(back);
                finish();
                break;
            case R.id.rl_exit:

                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();
                editor.putBoolean("login",false);
                editor.commit();
//                Intent broad = new Intent("com.mirror.mirrortry.login.BROAD");
//                broad.putExtra("login", true);
//                sendBroadcast(broad);

                Intent exit = new Intent(this, MainActivity.class);
                exit.putExtra("num",0);
                startActivity(exit);
                finish();
                break;
            case R.id.list_login:

                if (flag == false) {
                    Intent logIn = new Intent(this, LoginActivity.class);
                    startActivity(logIn);
                    finish();
                } else {
                    scaleTextAnim();
                    login.startAnimation(textScaleAnimation);

                }

                break;

            case R.id.list_mirror:
                scaleTextAnim();
                mirror.startAnimation(textScaleAnimation);
                break;


        }
    }

    public void scaleAnim() {
        //前两个参数是X轴 从多少到多少
        //3,4参数 是Y轴 从多少到多少
        scaleAnimation = new ScaleAnimation(1, 0.9f, 1, 0.9f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        //动画持续时间
        scaleAnimation.setDuration(1000);
        //縮放后保持縮放后的大小
        scaleAnimation.setFillAfter(true);

    }

    public void setShow() {
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        switch (position) {
            case 0:
                seeAll.setAlpha(1);
                all.setVisibility(View.VISIBLE);
                break;
            case 1:
                seeGoggles.setAlpha(1);
                goggles.setVisibility(View.VISIBLE);
                break;
            case 2:
                seeSunGlass.setAlpha(1);
                sunGlass.setVisibility(View.VISIBLE);
                break;
            case 3:
                subjectShare.setAlpha(1);
                share.setVisibility(View.VISIBLE);
                break;
            case 4:
                shoppingCar.setAlpha(1);
                shopping.setVisibility(View.VISIBLE);
        }
    }

    //缩放动画
    public void scaleTextAnim() {
        //前两个参数是X轴 从多少到多少
        //3,4参数 是Y轴 从多少到多少
        textScaleAnimation = new ScaleAnimation(1, 1.1f, 1, 1.1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        //动画持续时间
        textScaleAnimation.setDuration(500);
        //播放动画重复次数
        textScaleAnimation.setRepeatCount(1);


    }

}
