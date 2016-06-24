package com.mirror.mirrortry.alladdress;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseActivity;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/24.
 */
public class AllAddressListViewAdapter extends BaseAdapter {
    private ArrayList<AllAddressBean>been;
    private Context context;

    public AllAddressListViewAdapter(Context context) {
        this.context = context;
    }

    public void setBeen(ArrayList<AllAddressBean> been) {
        this.been = been;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return been != null && been.size() > 0 ? been.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return been == null ? null : been.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_address_listview,parent,false);
            holder = new MyViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (MyViewHolder) convertView.getTag();
            holder.name.setText(been.get(position).getName());
            holder.address.setText(been.get(position).getAddress());
            holder.number.setText(been.get(position).getNumber());
        }
        return convertView;
    }

    class MyViewHolder {
        TextView name,number,address;
        ImageView edit;
        public MyViewHolder(View view){
            name = (TextView) view.findViewById(R.id.tv_item_recipients);
            address = (TextView) view.findViewById(R.id.tv_item_address);
            number = (TextView) view.findViewById(R.id.tv_item_number);
            edit = (ImageView) view.findViewById(R.id.iv_item_edit);
        }
    }
}
