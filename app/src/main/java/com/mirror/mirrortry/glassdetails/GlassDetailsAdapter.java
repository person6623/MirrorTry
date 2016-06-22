package com.mirror.mirrortry.glassdetails;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by dllo on 16/6/22.
 */
public class GlassDetailsAdapter extends FragmentPagerAdapter {
    private Fragment fragment;

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
        notifyDataSetChanged();
    }

    public GlassDetailsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragment;
    }

    @Override
    public int getCount() {
        return 1;
    }
}
