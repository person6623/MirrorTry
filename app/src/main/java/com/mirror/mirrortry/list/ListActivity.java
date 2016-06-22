package com.mirror.mirrortry.list;

import android.content.Intent;
import android.view.View;

import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseActivity;
import com.mirror.mirrortry.login.LoginActivity;
import com.mirror.mirrortry.main.MainActivity;
import com.zhy.autolayout.AutoRelativeLayout;

/**
 * Created by dllo on 16/6/22.
 */
public class ListActivity extends BaseActivity implements View.OnClickListener {

    private int[]ids = {R.id.rl_list,R.id.rl_see_all,R.id.rl_see_goggles,R.id.rl_see_sunGlass,
                        R.id.rl_subject_share,R.id.rl_shopping_car,R.id.rl_back_main,R.id.rl_exit
    ,R.id.list_login};

    @Override
    public int setLayout() {
        return R.layout.activity_list;
    }

    @Override
    public void initView() {
        for (int i = 0; i < ids.length; i++) {
            findViewById(ids[i]).setOnClickListener(this);
        }
    }

    @Override
    public void initData() {

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
                break;
            case R.id.rl_see_goggles:

                Intent goggles = new Intent(this, MainActivity.class);
                goggles.putExtra("num",1);
                startActivity(goggles);

                break;
            case R.id.rl_see_sunGlass:
                Intent sunGlass = new Intent(this, MainActivity.class);
                sunGlass.putExtra("num",2);
                startActivity(sunGlass);
                break;
            case R.id.rl_subject_share:
                Intent subject = new Intent(this, MainActivity.class);
                subject.putExtra("num",3);
                startActivity(subject);
                break;
            case R.id.rl_shopping_car:
                Intent shopping = new Intent(this, MainActivity.class);
                shopping.putExtra("num",4);
                startActivity(shopping);
                break;
            case R.id.rl_back_main:
                Intent back = new Intent(this, MainActivity.class);
                back.putExtra("num",5);
                startActivity(back);
                break;
            case R.id.rl_exit:
                Intent exit = new Intent(this, MainActivity.class);
                exit.putExtra("num",6);
                startActivity(exit);
                break;
            case R.id.list_login:

                Intent logIn = new Intent(this, LoginActivity.class);
                startActivity(logIn);

                break;


        }
    }
}
