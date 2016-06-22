package com.mirror.mirrortry.main;

import android.content.Context;
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

import java.util.List;

/**
 * Created by dllo on 16/6/21.
 */
public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private List<MainBean.DataBean.ListBean>datas;

    public MainRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<MainBean.DataBean.ListBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_all_kind_recyclerview, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        RequestQueue queue = Volley.newRequestQueue(context);
        holder.name.setText(datas.get(position).getData_info().getGoods_name());
        holder.price.setText("¥" + datas.get(position).getData_info().getGoods_price().toString());
        holder.location.setText(datas.get(position).getData_info().getProduct_area().toString());
        holder.brand.setText(datas.get(position).getData_info().getBrand().toString());
//        ImageLoader loader = new ImageLoader(queue,new MemoryCache());
//        holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        ImageLoader loader = VolleySingleton.getInstance().getImageLoader();
        loader.get(datas.get(position).getData_info().getGoods_img(),ImageLoader.getImageListener(holder.imageView,
                R.mipmap.null_state,R.mipmap.null_state));
    }

    @Override
    public int getItemCount() {
        return datas != null && datas.size() > 0 ? datas.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title,brand,price,location,name;
        private ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            brand = (TextView) itemView.findViewById(R.id.tv_brand);
            price = (TextView) itemView.findViewById(R.id.tv_price);
            location = (TextView) itemView.findViewById(R.id.tv_produce_place);
            name = (TextView) itemView.findViewById(R.id.tv_goods_name);
            imageView = (ImageView) itemView.findViewById(R.id.iv_item);

        }
    }
}
