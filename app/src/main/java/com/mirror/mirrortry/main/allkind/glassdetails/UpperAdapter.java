package com.mirror.mirrortry.main.allkind.glassdetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mirror.mirrortry.AppApplicationContext;
import com.mirror.mirrortry.R;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

/**
 * Created by dllo on 16/6/23.
 * 上层listview
 */
public class UpperAdapter extends BaseAdapter {
    private Context context;
    private GlassDetailsBean.DataBean.ListBean.DataInfoBean dataInfoBean;

    public UpperAdapter(Context context) {
        this.context = context;
    }

    public void setDataInfoBean(GlassDetailsBean.DataBean.ListBean.DataInfoBean dataInfoBean) {
        this.dataInfoBean = dataInfoBean;
        notifyDataSetChanged();
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
        UpperViewHolder upperViewHolder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_upper_content, parent, false);
            upperViewHolder = new UpperViewHolder(convertView);
            convertView.setTag(upperViewHolder);
        } else {
            upperViewHolder = (UpperViewHolder) convertView.getTag();
        }

        WindowManager wm = (WindowManager) AppApplicationContext.context
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        upperViewHolder.img.setLayoutParams(new AutoRelativeLayout.LayoutParams(width, height));

        if (position == 0) {
            upperViewHolder.english.setVisibility(View.VISIBLE);
            upperViewHolder.country.setVisibility(View.VISIBLE);
            upperViewHolder.location.setVisibility(View.VISIBLE);
            upperViewHolder.country.setText(dataInfoBean.getGoods_data().get(position).getCountry());
            upperViewHolder.location.setText(dataInfoBean.getGoods_data().get(position).getLocation());
            upperViewHolder.english.setText(dataInfoBean.getGoods_data().get(position).getEnglish());
            upperViewHolder.titleName.setVisibility(View.GONE);
        } else {
            upperViewHolder.titleName.setVisibility(View.VISIBLE);
            upperViewHolder.titleName.setText(dataInfoBean.getGoods_data().get(position).getName());
            upperViewHolder.english.setVisibility(View.GONE);
            upperViewHolder.country.setVisibility(View.GONE);
            upperViewHolder.location.setVisibility(View.GONE);
        }
        upperViewHolder.introContent.setText(dataInfoBean.getGoods_data().get(position).getIntroContent());

        return convertView;
    }

    class UpperViewHolder extends RecyclerView.ViewHolder {
        private TextView country, location, english, titleName, introContent;
        private ImageView img;

        public UpperViewHolder(View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.iv_null_upper);

            country = (TextView) itemView.findViewById(R.id.tv_country_upper);
            location = (TextView) itemView.findViewById(R.id.tv_location_upper);
            english = (TextView) itemView.findViewById(R.id.tv_english_upper);
            titleName = (TextView) itemView.findViewById(R.id.tv_title_name_upper);
            introContent = (TextView) itemView.findViewById(R.id.tv_introContent_upper);
        }
    }
}
