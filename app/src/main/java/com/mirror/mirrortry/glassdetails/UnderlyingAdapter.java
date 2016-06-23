package com.mirror.mirrortry.glassdetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.mirror.mirrortry.R;

/**
 * Created by dllo on 16/6/23.
 * 底层listview
 */
public class UnderlyingAdapter extends BaseAdapter{
    private Context context;

    public UnderlyingAdapter(Context context) {
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
        MyHoler myHoler = null;
        if (convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_underlying_content,parent,false);
            myHoler = new MyHoler(convertView);
            convertView.setTag(myHoler);
        } else {
            myHoler = (MyHoler) convertView.getTag();
        }

        return convertView;
    }
    class MyHoler extends RecyclerView.ViewHolder{
        LinearLayout one;
        public MyHoler(View itemView) {
            super(itemView);
            one = (LinearLayout) itemView.findViewById(R.id.one);
        }
    }
}
