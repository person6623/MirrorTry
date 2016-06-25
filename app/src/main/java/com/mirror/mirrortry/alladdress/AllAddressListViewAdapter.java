package com.mirror.mirrortry.alladdress;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mirror.mirrortry.R;
import com.mirror.mirrortry.addaddress.AddAddressActivity;
import com.mirror.mirrortry.base.BaseActivity;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/24.
 */
public class AllAddressListViewAdapter extends BaseAdapter implements SlidingMenuView.SlidingListener {
    private ArrayList<AllAddressBean> been;
    private Context context;
    private SlidingMenuView slidingMenuView;

    public AllAddressListViewAdapter(Context context) {
        this.context = context;
    }

    public void setBeen(ArrayList<AllAddressBean> been) {
        this.been = been;
        notifyDataSetChanged();
    }

    public void closeMenu() {
        if (slidingMenuView != null) {
            slidingMenuView.closeMenu();
        }
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_address_listview, parent, false);
            holder = new MyViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }
        holder.name.setText(been.get(position).getName());
        holder.address.setText(been.get(position).getAddress());
        holder.number.setText(been.get(position).getNumber());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddAddressActivity.class);
                intent.putExtra("mark",11); //標記用  添加地址的activity接到不同的標記  更換不同的標題
                intent.putExtra("name",been.get(position).getName());
                intent.putExtra("number",been.get(position).getNumber());
                intent.putExtra("address",been.get(position).getAddress());
                context.startActivity(intent);
            }
        });

        //首先获取到textView里的params  里边放的是宽高参数
        ViewGroup.LayoutParams layoutParams = holder.item.getLayoutParams();
        //给他的宽度重新赋值成屏幕宽度
        layoutParams.width = Utils.getScreenWidth(context) - 84; //減掉listView距離兩邊和文字距左側的距離

        //然后在设置给textView
        holder.item.setLayoutParams(layoutParams);

        return convertView;
    }

    @Override
    public void onMenuIsOpen(SlidingMenuView slidingMenuView) {

        this.slidingMenuView = slidingMenuView;

    }

    @Override
    public void onMove(SlidingMenuView slidingMenuView) {

        if (this.slidingMenuView != slidingMenuView) {
            if (this.slidingMenuView != null) {
                this.slidingMenuView.closeMenu();
            }
        }

    }

    class MyViewHolder {
        TextView name, number, address, delete;
        ImageView edit;
        AutoRelativeLayout item;

        public MyViewHolder(View view) {
            name = (TextView) view.findViewById(R.id.tv_item_recipients);
            address = (TextView) view.findViewById(R.id.tv_item_address);
            number = (TextView) view.findViewById(R.id.tv_item_number);
            edit = (ImageView) view.findViewById(R.id.iv_item_edit);
            delete = (TextView) view.findViewById(R.id.tv_item_delete);
            item = (AutoRelativeLayout) view.findViewById(R.id.rl_address);
            ((SlidingMenuView) view).setSlidingListener(AllAddressListViewAdapter.this);
        }
    }
}
