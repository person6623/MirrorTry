package com.mirror.mirrortry.main.specialtoshare;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.mirror.mirrortry.R;
import com.mirror.mirrortry.net.NetTool;
import com.mirror.mirrortry.net.VolleySingleton;

import java.util.List;

/**
 * Created by dllo on 16/6/22.
 */
public class SpecialToShareRecyclerViewAdapter extends RecyclerView.Adapter<SpecialToShareRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private List<SpecialToShareBean.DataBean.ListBean> shareBeen;
    private MyRecycleViewOnClickListener myRecycleViewOnClickListener;
    private NetTool netTool;

    public void setMyRecycleViewOnClickListener(MyRecycleViewOnClickListener myRecycleViewOnClickListener) {
        this.myRecycleViewOnClickListener = myRecycleViewOnClickListener;
    }

    public SpecialToShareRecyclerViewAdapter(Context context) {
        this.context = context;
        netTool = new NetTool();
    }

    public void setShareBeen(List<SpecialToShareBean.DataBean.ListBean> shareBeen) {
        this.shareBeen = shareBeen;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_special_share_recyclerview, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.specialShareTv.setText(shareBeen.get(position).getStory_title());
        //用imagelode设置图片
        holder.progressBar.setVisibility(View.VISIBLE);
        netTool.getImageLoaderNet(shareBeen.get(position).getStory_img(), holder.specialSharePicIv, holder.progressBar);
        //调用接口
        holder.itemShareRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getLayoutPosition();
                myRecycleViewOnClickListener.onClick(position, shareBeen);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shareBeen != null && shareBeen.size() > 0 ? shareBeen.size() : 0;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView specialSharePicIv;
        TextView specialShareTv;
        RelativeLayout itemShareRl;
        ProgressBar progressBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            specialSharePicIv = (ImageView) itemView.findViewById(R.id.iv_special_share_pic);
            specialShareTv = (TextView) itemView.findViewById(R.id.tv_special_share);
            itemShareRl = (RelativeLayout) itemView.findViewById(R.id.item_share_rl);
            progressBar = (ProgressBar) itemView.findViewById(R.id.pb_special_share_pic);
        }
    }

    //接口
    public interface MyRecycleViewOnClickListener {
        void onClick(int position, List<SpecialToShareBean.DataBean.ListBean> beanList);
    }
}
