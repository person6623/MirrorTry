package com.mirror.mirrortry.glassdetails.atlas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.mirror.mirrortry.R;
import com.mirror.mirrortry.glassdetails.GlassDetailsBean;
import com.mirror.mirrortry.net.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/24.
 */
public class WearTheAtlasAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> atlasUrl;

    public WearTheAtlasAdapter(Context context) {
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.atlas_item, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            ImageLoader loader = VolleySingleton.getInstance().getImageLoader();
            loader.get(atlasUrl.get(position), loader.getImageListener(viewHolder.ivAtlasItem,
                    R.mipmap.null_state, R.mipmap.null_state));
        return convertView;
    }

    class ViewHolder {
        ImageView ivAtlasItem;

        public ViewHolder(View itemView) {
            ivAtlasItem = (ImageView) itemView.findViewById(R.id.iv_atlas_item);
        }
    }
}
