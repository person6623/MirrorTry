package com.mirror.mirrortry.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.mirror.mirrortry.R;
import com.mirror.mirrortry.libcore.io.Disk;
import com.mirror.mirrortry.net.CustumCache;
import com.mirror.mirrortry.net.MemoryCache;
import com.mirror.mirrortry.net.NetHelper;
import com.mirror.mirrortry.net.NetTool;
import com.mirror.mirrortry.net.ThreadSingleton;
import com.mirror.mirrortry.net.VolleySingleton;
import com.mirror.mirrortry.tools.GlassDetailsInterface;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;

/**
 * Created by dllo on 16/6/21.
 */
public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private List<MainBean.DataBean.ListBean> datas;
    private GlassDetailsInterface glassDetailsInterface;
    private NetTool netTool;
    private List<MainContinueBean.DataBean.ListBean> listBeens;
    private Handler handler;

    public MainRecyclerViewAdapter(Context context) {
        this.context = context;
        netTool = new NetTool();
    }

    public void setDatas(List<MainBean.DataBean.ListBean> datas) {
        this.datas = datas;
        Log.d("MainRecyclerViewAdapter", datas.get(1).getData_info().getGoods_img());
        notifyDataSetChanged();
    }

    public void setListBeens(List<MainContinueBean.DataBean.ListBean> listBeens) {
        this.listBeens = listBeens;
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
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (Integer.valueOf(datas.get(position).getType()) == 1) {
            holder.name.setVisibility(View.VISIBLE);
            holder.imageView.setVisibility(View.VISIBLE);
            holder.brand.setVisibility(View.VISIBLE);
            holder.price.setVisibility(View.VISIBLE);
            holder.black.setVisibility(View.VISIBLE);
            holder.location.setVisibility(View.GONE);
            holder.blackLine.setVisibility(View.GONE);
            holder.imgShare.setVisibility(View.GONE);
            holder.brandShare.setVisibility(View.GONE);
            holder.word.setBackgroundColor(Color.WHITE);
            holder.shareWord.setBackgroundColor(Color.TRANSPARENT);
            holder.word.setVisibility(View.VISIBLE);
            holder.name.setText(datas.get(position).getData_info().getGoods_name());
            holder.price.setText("¥" + datas.get(position).getData_info().getGoods_price().toString());
            holder.location.setText(datas.get(position).getData_info().getProduct_area());
            holder.brand.setText(datas.get(position).getData_info().getBrand());

            if (NetHelper.isHaveInternet(context)) {

                netTool.getImageNet(datas.get(position).getData_info().getGoods_img(), holder.imageView);

            } else {

                Log.d("MainRecyclerViewAdapter", "1123123" + datas.get(position).getData_info().getGoods_img());
                if (CustumCache.getBitmap(datas.get(position).getData_info().getGoods_img()) == null) {
                    Bitmap bitmap = Disk.getPicFromDir(datas.get(position).getData_info().getGoods_img());
                    holder.img.setImageBitmap(bitmap);
                    CustumCache.putBitmap(datas.get(position).getData_info().getGoods_img(),bitmap);

                } else {
                    holder.img.setImageBitmap(CustumCache.getBitmap(datas.get(position).getData_info().getGoods_img()));
                }

            }

        } else if (Integer.valueOf(datas.get(position).getType()) == 2) {
            holder.name.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.GONE);
            holder.brand.setVisibility(View.GONE);
            holder.price.setVisibility(View.GONE);
            holder.black.setVisibility(View.GONE);
            holder.location.setVisibility(View.GONE);
            holder.blackLine.setVisibility(View.VISIBLE);
            holder.imgShare.setVisibility(View.VISIBLE);
            holder.brandShare.setVisibility(View.VISIBLE);
            holder.shareWord.setBackgroundColor(Color.WHITE);
            holder.word.setBackgroundColor(Color.TRANSPARENT);
            holder.brandShare.setText(datas.get(position).getData_info().getStory_title());
            if (NetHelper.isHaveInternet(context)) {
                netTool.getImageNet(datas.get(position).getData_info().getStory_img(), holder.imgShare);

            } else {

                if (CustumCache.getBitmap(datas.get(position).getData_info().getStory_img()) == null) {
                    ThreadSingleton.getInstance().getExecutorService().execute(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap bitmap = new Disk().getPicFromDir(datas.get(position).getData_info().getStory_img());
                            Message message = new Message();
                            message.what = 0;
                            message.obj = bitmap;
                            handler.sendMessage(message);
                        }
                    });
                    handler = new Handler(new Handler.Callback() {
                        @Override
                        public boolean handleMessage(Message msg) {
                            if (msg.what == 0) {

                                holder.imageViewShare.setImageBitmap((Bitmap) msg.obj);
                                CustumCache.putBitmap(datas.get(position).getData_info().getStory_img(), (Bitmap) msg.obj);

                            }
                            return false;
                        }
                    });
                } else {
                    holder.imageViewShare.setImageBitmap(CustumCache.getBitmap(datas.get(position).getData_info().getStory_img()));
                }

            }

        }
        holder.allKind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                glassDetailsInterface.onGlassClick(position, datas, listBeens);
            }
        });
    }

    @Override
    public int getItemCount() {


        return datas != null && datas.size() > 0 ? datas.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView brand, price, location, name, brandShare;
        NetworkImageView imageView, imgShare;
        ImageView black, blackLine;
        AutoRelativeLayout word, shareWord, allKind;
        ProgressBar pbAllKind, pbSubjectShare;
        ImageView img, imageViewShare;

        public MyViewHolder(View itemView) {
            super(itemView);
            brand = (TextView) itemView.findViewById(R.id.tv_brand);
            price = (TextView) itemView.findViewById(R.id.tv_price);
            location = (TextView) itemView.findViewById(R.id.tv_produce_place);
            name = (TextView) itemView.findViewById(R.id.tv_goods_name);
            imageView = (NetworkImageView) itemView.findViewById(R.id.iv_item);
            brandShare = (TextView) itemView.findViewById(R.id.tv_brand_share);
            black = (ImageView) itemView.findViewById(R.id.iv_black);
            blackLine = (ImageView) itemView.findViewById(R.id.iv_black_line);
            imgShare = (NetworkImageView) itemView.findViewById(R.id.iv_subject_share);
            word = (AutoRelativeLayout) itemView.findViewById(R.id.rl_word);
            shareWord = (AutoRelativeLayout) itemView.findViewById(R.id.rl_share_word);
            pbAllKind = (ProgressBar) itemView.findViewById(R.id.pb_item_all_kind);
            pbSubjectShare = (ProgressBar) itemView.findViewById(R.id.pb_subject_share);
            //整体布局
            allKind = (AutoRelativeLayout) itemView.findViewById(R.id.cre_all_kind);
            img = (ImageView) itemView.findViewById(R.id.iv_item_img);
            imageViewShare = (ImageView) itemView.findViewById(R.id.iv_subject_img);

        }
    }
}
