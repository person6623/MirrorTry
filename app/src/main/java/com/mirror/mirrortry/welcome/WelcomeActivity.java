package com.mirror.mirrortry.welcome;

import android.content.Intent;
import android.os.CountDownTimer;


import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseActivity;
import com.mirror.mirrortry.login.LoginActivity;
import com.mirror.mirrortry.main.MainActivity;
import com.mirror.mirrortry.register.RegisterActivity;

/**
 * Created by dllo on 16/6/21.
 */
public class WelcomeActivity extends BaseActivity {
    private CountDownTimer timer;

    @Override
    public int setLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initData() {
        timer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }


    @Override
    public void initView() {

    }
}