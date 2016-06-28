package com.mirror.mirrortry.glassdetails.atlas;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.mirror.mirrortry.R;
import com.mirror.mirrortry.glassdetails.atlas.pic.PicActivity;
import com.mirror.mirrortry.net.NetTool;
import com.mirror.mirrortry.smoothimageview.SquareCenterImageView;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/24.
 */
public class WearTheAtlasAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> atlasUrl;
    private NetTool netTool;
    private AtlasOnClickListener atlasOnClickListener;

    public void setAtlasOnClickListener(AtlasOnClickListener atlasOnClickListener) {
        this.atlasOnClickListener = atlasOnClickListener;
    }

    public WearTheAtlasAdapter(Context context) {
        this.context = context;
        netTool = new NetTool();
    }

    public void setAtlasUrl(ArrayList<String> atlasUrl) {
        this.atlasUrl = atlasUrl;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return atlasUrl != null && atlasUrl.size() > 0 ? atlasUrl.size() : 0;

    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.atlas_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        netTool.getImageLoaderNet(atlasUrl.get(position), viewHolder.ivAtlasItem, null);
        //调接口
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.ivAtlasItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SquareCenterImageView imageView = new SquareCenterImageView(context);
//                atlasOnClickListener.onClick(position, atlasUrl.get(position));
                Intent intent = new Intent(context, PicActivity.class);
                String url = atlasUrl.get(position);
                intent.putExtra("url", url);
                intent.putExtra("position", position);
                int[] location = new int[2];
                finalViewHolder.ivAtlasItem.getLocationOnScreen(location);
                intent.putExtra("locationX", location[0]);
                intent.putExtra("locationY", location[1]);
                intent.putExtra("width", finalViewHolder.ivAtlasItem.getWidth());
                intent.putExtra("height", finalViewHolder.ivAtlasItem.getHeight());
                context.startActivity(intent);
                ((WearTheAtlasActivity) context).overridePendingTransition(0, 0);

            }
        });

        return convertView;
    }

    class ViewHolder {
        ImageView ivAtlasItem;

        public ViewHolder(View itemView) {
            ivAtlasItem = (ImageView) itemView.findViewById(R.id.iv_atlas_item);

        }
    }

    public interface AtlasOnClickListener {
        void onClick(int position, String url);
    }
}
