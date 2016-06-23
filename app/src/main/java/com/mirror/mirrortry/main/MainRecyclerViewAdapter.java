package com.mirror.mirrortry.main;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.mirror.mirrortry.R;
import com.mirror.mirrortry.net.MemoryCache;
import com.mirror.mirrortry.net.VolleySingleton;
import com.mirror.mirrortry.tools.GlassDetailsInterface;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;

/**
 * Created by dllo on 16/6/21.
 */
public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private List<MainBean.DataBean.ListBean>datas;
    private GlassDetailsInterface glassDetailsInterface;

    public MainRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<MainBean.DataBean.ListBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void setGlassDetailsInterface(GlassDetailsInterface glassDetailsInterface) {
        this.glassDetailsInterface = glassDetailsInterface;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_all_kind_recyclerview, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (Integer.valueOf(datas.get(position).getType()) == 1){
//            holder.blackLine.setVisibility(View.GONE);
//            holder.imgShare.setVisibility(View.GONE);
//            holder.brandShare.setVisibility(View.GONE);
            holder.word.setBackgroundColor(Color.WHITE);
            holder.shareWord.setVisibility(View.GONE);
        holder.name.setText(datas.get(position).getData_info().getGoods_name());
        holder.price.setText("¥" + datas.get(position).getData_info().getGoods_price().toString());
        holder.location.setText(datas.get(position).getData_info().getProduct_area());
        holder.brand.setText(datas.get(position).getData_info().getBrand());
        ImageLoader loader = VolleySingleton.getInstance().getImageLoader();
        loader.get(datas.get(position).getData_info().getGoods_img(),ImageLoader.getImageListener(holder.imageView,
                R.mipmap.null_state,R.mipmap.null_state));
        }else if (Integer.valueOf(datas.get(position).getType()) == 2){
//            holder.name.setVisibility(View.GONE);
//            holder.imageView.setVisibility(View.GONE);
//            holder.brand.setVisibility(View.GONE);
//            holder.price.setVisibility(View.GONE);
//            holder.black.setVisibility(View.GONE);
//            holder.location.setVisibility(View.GONE);
//            holder.shareWord.setBackgroundColor(Color.WHITE);
            holder.word.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.GONE);
            holder.brandShare.setText(datas.get(position).getData_info().getStory_title());
            ImageLoader loader = VolleySingleton.getInstance().getImageLoader();
            loader.get(datas.get(position).getData_info().getStory_img(),ImageLoader.getImageListener(holder.imgShare,
                    R.mipmap.null_state,R.mipmap.null_state));
        }
        holder.allKind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                glassDetailsInterface.onGlassClick(position,datas);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas != null && datas.size() > 0 ? datas.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView brand,price,location,name,brandShare;
        ImageView imageView,black,blackLine,imgShare;
        AutoRelativeLayout word,shareWord,allKind;
        public MyViewHolder(View itemView) {
            super(itemView);
            brand = (TextView) itemView.findViewById(R.id.tv_brand);
            price = (TextView) itemView.findViewById(R.id.tv_price);
            location = (TextView) itemView.findViewById(R.id.tv_produce_place);
            name = (TextView) itemView.findViewById(R.id.tv_goods_name);
            imageView = (ImageView) itemView.findViewById(R.id.iv_item);
            brandShare = (TextView) itemView.findViewById(R.id.tv_brand_share);
            black = (ImageView) itemView.findViewById(R.id.iv_black);
            blackLine = (ImageView) itemView.findViewById(R.id.iv_black_line);
            imgShare = (ImageView) itemView.findViewById(R.id.iv_subject_share);
            word = (AutoRelativeLayout) itemView.findViewById(R.id.rl_word);
            shareWord = (AutoRelativeLayout) itemView.findViewById(R.id.rl_share_word);
            //整体布局
            allKind = (AutoRelativeLayout) itemView.findViewById(R.id.cre_all_kind);

        }
    }
}
