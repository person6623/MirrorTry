package com.mirror.mirrortry.glassdetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.mirror.mirrortry.AppApplicationContext;
import com.mirror.mirrortry.R;
import com.mirror.mirrortry.net.VolleySingleton;
import com.zhy.autolayout.AutoLinearLayout;

/**
 * Created by dllo on 16/6/23.
 * 底层listview
 */
public class UnderlyingAdapter extends BaseAdapter {
    private Context context;
    private GlassDetailsBean.DataBean.ListBean.DataInfoBean dataInfoBean;
    private GlassDetailsShare glassDetailsShare;

    public void setGlassDetailsShare(GlassDetailsShare glassDetailsShare) {
        this.glassDetailsShare = glassDetailsShare;
    }

    public UnderlyingAdapter(Context context) {
        this.context = context;
    }

    public void setDataInfoBean(GlassDetailsBean.DataBean.ListBean.DataInfoBean dataInfoBean) {
        this.dataInfoBean = dataInfoBean;
        notifyDataSetChanged();
    }

    public GlassDetailsBean.DataBean.ListBean.DataInfoBean getDataInfoBean() {
        return dataInfoBean;
    }

    //item的三种布局
    //开头布局
    final int HEAD_TYPE = 0;
    //空布局
    final int NULL_TYPE = 1;
    //正文布局
    final int BODY_TYPE = 2;


    @Override
    public int getCount() {
        return dataInfoBean.getDesign_des().size() + 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //区分布局格式
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEAD_TYPE;
        } else if (position == 1) {
            return NULL_TYPE;
        } else {
            return BODY_TYPE;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        HeadMyHoler headMyHoler = null;
        NullMyHoler nullMyHoler = null;
        BodyMyHoler bodyMyHoler = null;

        ImageLoader loader = VolleySingleton.getInstance().getImageLoader();

        int type = getItemViewType(position);

        if (convertView == null) {
            switch (type) {
                case HEAD_TYPE:
                    convertView = LayoutInflater.from(context).inflate(R.layout.fragment_underlying_content_headerview, parent, false);
                    headMyHoler = new HeadMyHoler(convertView);
                    convertView.setTag(headMyHoler);
                    break;
                case NULL_TYPE:
                    convertView = LayoutInflater.from(context).inflate(R.layout.fragment_underlyuing_content_nullview, parent, false);
                    nullMyHoler = new NullMyHoler(convertView);
                    convertView.setTag(nullMyHoler);
                    break;
                case BODY_TYPE:
                    convertView = LayoutInflater.from(context).inflate(R.layout.fragment_underlying_content_body, parent, false);
                    bodyMyHoler = new BodyMyHoler(convertView);
                    convertView.setTag(bodyMyHoler);
                    break;
            }
        } else {
            switch (type) {
                case HEAD_TYPE:
                    headMyHoler = (HeadMyHoler) convertView.getTag();
                    break;
                case NULL_TYPE:
                    nullMyHoler = (NullMyHoler) convertView.getTag();
                    break;
                case BODY_TYPE:
                    bodyMyHoler = (BodyMyHoler) convertView.getTag();
                    break;
            }
        }

        switch (type) {
            case HEAD_TYPE:
                headMyHoler.name.setText(dataInfoBean.getGoods_name());
                headMyHoler.barnd.setText(dataInfoBean.getBrand());
                headMyHoler.infoDes.setText(dataInfoBean.getInfo_des());
                headMyHoler.price.setText(dataInfoBean.getGoods_price());
                headMyHoler.share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = dataInfoBean.getGoods_share();
                        glassDetailsShare.onClick(position);
                    }
                });
                break;
            case NULL_TYPE:
                WindowManager wm = (WindowManager) AppApplicationContext.context
                        .getSystemService(Context.WINDOW_SERVICE);

                int width = wm.getDefaultDisplay().getWidth();
                int height = wm.getDefaultDisplay().getHeight();

                nullMyHoler.imgn.setLayoutParams(new AutoLinearLayout.LayoutParams(width, height));
                break;
            case BODY_TYPE:
                if (position == 2) {
                    bodyMyHoler.barnd.setText(dataInfoBean.getBrand());
                } else {
                    bodyMyHoler.barnd.setVisibility(View.GONE);
                    bodyMyHoler.line.setVisibility(View.GONE);
                }

                loader.get(dataInfoBean.getDesign_des().get(position - 2).getImg(),
                        ImageLoader.getImageListener(bodyMyHoler.img, R.mipmap.null_state, R.mipmap.null_state));
                break;
        }


        return convertView;
    }

    class HeadMyHoler extends RecyclerView.ViewHolder {
        private TextView name, barnd, infoDes, price;
        private ImageView share;

        public HeadMyHoler(View itemView) {
            super(itemView);
            share = (ImageView) itemView.findViewById(R.id.iv_share_glass_details);
            name = (TextView) itemView.findViewById(R.id.tv_name_glass_details);
            barnd = (TextView) itemView.findViewById(R.id.tv_brand_glass_details);
            infoDes = (TextView) itemView.findViewById(R.id.tv_info_des_glass_details);
            price = (TextView) itemView.findViewById(R.id.tv_price_glass_details);
        }
    }

    class NullMyHoler extends RecyclerView.ViewHolder {
        private ImageView imgn;

        public NullMyHoler(View itemView) {
            super(itemView);
            imgn = (ImageView) itemView.findViewById(R.id.iv_imgn_glass_details);
        }
    }

    class BodyMyHoler extends RecyclerView.ViewHolder {
        private TextView barnd;
        private ImageView img, line;


        public BodyMyHoler(View itemView) {
            super(itemView);
            barnd = (TextView) itemView.findViewById(R.id.tv_brand_body_glass_details);
            img = (ImageView) itemView.findViewById(R.id.iv_img_glass_details);
            line = (ImageView) itemView.findViewById(R.id.iv_line_glass_details);
        }
    }

    public interface GlassDetailsShare {
        void onClick(int position);
    }
}
