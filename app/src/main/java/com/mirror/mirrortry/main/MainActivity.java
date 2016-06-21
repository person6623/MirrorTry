package com.mirror.mirrortry.main;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mirror.mirrortry.R;
import com.mirror.mirrortry.verticalviewpager.VerticalViewPager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private VerticalViewPager viewPager;
    private ArrayList<Fragment>fragments;
    private MainViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (VerticalViewPager) findViewById(R.id.main_viewPager);
        adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        Fragment all = AllKindFragment.createFragment();
        Fragment goggles = AllKindFragment.createFragment();
        Fragment sunGlass = AllKindFragment.createFragment();
        fragments = new ArrayList<>();
        fragments.add(all);
        fragments.add(goggles);
        fragments.add(sunGlass);
        adapter.setFragments(fragments);
        viewPager.setAdapter(adapter);

    }
}
