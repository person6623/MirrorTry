package com.mirror.mirrortry.glassdetails.atlas;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.mirror.mirrortry.R;
import com.mirror.mirrortry.net.NetTool;

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
        viewHolder.ivAtlasItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atlasOnClickListener.onClick(position,atlasUrl.get(position));
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
    public interface AtlasOnClickListener{
       void onClick(int position,String url);
    }
}
