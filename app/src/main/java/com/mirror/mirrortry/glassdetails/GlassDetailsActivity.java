package com.mirror.mirrortry.glassdetails;

import android.support.v4.view.ViewPager;

import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseActivity;

/**
 * Created by dllo on 16/6/22.
 */
public class GlassDetailsActivity extends BaseActivity {
    private ViewPager viewPager;
    private GlassDetailsAdapter glassDetailsAdapter;

    @Override
    public int setLayout() {
        return R.layout.general_viewpager;
    }

    @Override
    public void initView() {
        viewPager = (ViewPager) findViewById(R.id.generalVP);
        glassDetailsAdapter = new GlassDetailsAdapter(getSupportFragmentManager());

        glassDetailsAdapter.setFragment(new GlassDetailsFragment());

        viewPager.setAdapter(glassDetailsAdapter);

    }

    @Override
    public void initData() {

    }
}
