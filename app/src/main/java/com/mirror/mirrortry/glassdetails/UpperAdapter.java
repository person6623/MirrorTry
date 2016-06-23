package com.mirror.mirrortry.glassdetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mirror.mirrortry.R;

/**
 * Created by dllo on 16/6/23.
 * 上层listview
 */
public class UpperAdapter extends BaseAdapter {
    private Context context;

    public UpperAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.fragment_upper_content,parent,false);
        return convertView;
    }
}
