package com.mirror.mirrortry.list;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.mirror.mirrortry.R;
import com.mirror.mirrortry.login.LoginActivity;
import com.mirror.mirrortry.main.MainActivity;
import com.zhy.autolayout.AutoRelativeLayout;

/**
 * Created by dllo on 16/6/22.
 */
public class ListActivity extends Activity implements View.OnClickListener {

    private int[]ids = {R.id.rl_list,R.id.rl_see_all,R.id.rl_see_goggles,R.id.rl_see_sunGlass,
                        R.id.rl_subject_share,R.id.rl_shopping_car,R.id.rl_back_main,R.id.rl_exit
    ,R.id.list_login};
    private AutoRelativeLayout listItem;
    private TextView seeAll,seeGoggles,seeSunGlass,subjectShare,shoppingCar,backMain,exit;
    private ImageView all,goggles,sunGlass,share,shopping,back,exited;

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
        all = (ImageView) findViewById(R.id.iv_see_all);
        goggles = (ImageView) findViewById(R.id.iv_see_goggles);
        sunGlass = (ImageView) findViewById(R.id.iv_see_sunGlass);
        share = (ImageView) findViewById(R.id.iv_subject_share);
        shopping = (ImageView) findViewById(R.id.iv_shopping_car);
        back = (ImageView) findViewById(R.id.iv_backMain);
        exited = (ImageView) findViewById(R.id.iv_exit);
        listItem = (AutoRelativeLayout) findViewById(R.id.rl_list_item);
        scaleAnim();
        shoppingCar.setAlpha(1);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.rl_list:
                finish();
                break;
            case R.id.rl_see_all:
                Intent all = new Intent(this, MainActivity.class);
                all .putExtra("num",0);
                startActivity(all);
                finish();
                break;
            case R.id.rl_see_goggles:

                Intent goggles = new Intent(this, MainActivity.class);
                goggles.putExtra("num",1);
                startActivity(goggles);
                finish();
                break;
            case R.id.rl_see_sunGlass:
                Intent sunGlass = new Intent(this, MainActivity.class);
                sunGlass.putExtra("num",2);
                startActivity(sunGlass);
                finish();
                break;
            case R.id.rl_subject_share:
                Intent subject = new Intent(this, MainActivity.class);
                subject.putExtra("num",3);
                startActivity(subject);
                finish();
                break;
            case R.id.rl_shopping_car:
                Intent shopping = new Intent(this, MainActivity.class);
                shopping.putExtra("num",4);
                startActivity(shopping);
                finish();
                break;
            case R.id.rl_back_main:
                Intent back = new Intent(this, MainActivity.class);
                back.putExtra("num",5);
                startActivity(back);
                finish();
                break;
            case R.id.rl_exit:
                Intent exit = new Intent(this, MainActivity.class);
                exit.putExtra("num",0);
                startActivity(exit);
                finish();
                break;
            case R.id.list_login:

                Intent logIn = new Intent(this, LoginActivity.class);
                startActivity(logIn);
                finish();

                break;
            case R.id.rl_list_item:

                break;


        }
    }

    public void scaleAnim() {
        //前两个参数是X轴 从多少到多少
        //3,4参数 是Y轴 从多少到多少
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0.9f, 1, 0.9f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        //动画持续时间
        scaleAnimation.setDuration(1000);
        //縮放后保持縮放后的大小
        scaleAnimation.setFillAfter(true);
        //指定组件开始动画
        listItem.startAnimation(scaleAnimation);
    }

}
