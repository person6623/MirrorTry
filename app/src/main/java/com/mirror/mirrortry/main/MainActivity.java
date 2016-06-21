package com.mirror.mirrortry.main;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mirror.mirrortry.R;
import com.mirror.mirrortry.db.DBValues;
import com.mirror.mirrortry.net.URIValues;
import com.mirror.mirrortry.verticalviewpager.VerticalViewPager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private VerticalViewPager viewPager;
    private ArrayList<Fragment>fragments;
    private MainViewPagerAdapter adapter;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (VerticalViewPager) findViewById(R.id.main_viewPager);
        adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        Fragment all = AllKindFragment.createFragment(URIValues.ALL_KIND);
        Fragment goggles = AllKindFragment.createFragment("");
        Fragment sunGlass = AllKindFragment.createFragment("");
        fragments = new ArrayList<>();
        fragments.add(all);
        fragments.add(goggles);
        fragments.add(sunGlass);
        adapter.setFragments(fragments);
        viewPager.setAdapter(adapter);

    }


}
