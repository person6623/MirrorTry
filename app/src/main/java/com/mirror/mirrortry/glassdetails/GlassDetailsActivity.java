package com.mirror.mirrortry.glassdetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseActivity;
import com.mirror.mirrortry.glassdetails.atlas.WearTheAtlasActivity;
import com.mirror.mirrortry.net.NetListener;
import com.mirror.mirrortry.net.NetTool;
import com.mirror.mirrortry.net.URIValues;
import com.mirror.mirrortry.net.VolleySingleton;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dllo on 16/6/22.
 */
public class GlassDetailsActivity extends BaseActivity implements View.OnClickListener {
    //底层listview
    private ListView underlyingListView;
    //上层listview
    private ListView upperListView;
    //adapter
    //底层
    private UnderlyingAdapter underlyingAdapter;
    //上层
    private UpperAdapter upperAdapter;

    private NetTool netTool;
    //背景用
    private ImageView backgroundView;

    private GlassDetailsBean glassDetailsBean;

    private ImageView back,buy;

    private TextView wear;


    @Override
    public int setLayout() {
        return R.layout.activity_glassdetails;
    }

    @Override
    public void initView() {
        //初始化组件
        underlyingListView = findView(R.id.lv_underlying_glass_details);
        //去分割线
        underlyingListView.setDividerHeight(0);
        //去滚动条
        underlyingListView.setVerticalScrollBarEnabled(false);

        upperListView = findView(R.id.lv_upper_glass_details);
        //去分割线
        upperListView.setDividerHeight(0);
        //去滚动条
        upperListView.setVerticalScrollBarEnabled(false);
        backgroundView = findView(R.id.iv_background_glass_details);

        //功能键
        back = findView(R.id.iv_back_glass_details);
        buy = findView(R.id.iv_buy_glass_details);
        wear = findView(R.id.tv_wear_glass_details);

        wear.setOnClickListener(this);


        underlyingAdapter = new UnderlyingAdapter(this);
        upperAdapter = new UpperAdapter(this);

        upperListView.setAdapter(upperAdapter);

        //底层获取焦点
        upperListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return underlyingListView.dispatchTouchEvent(event);
            }
        });


        //设置上层listview随底层listview滑动而滑动
        underlyingListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {




                View itemUnderlying = underlyingListView.getChildAt(0);
                if (itemUnderlying == null) {
                    return;
                }
                //测算实时滑动距离
                //assuming all list items have same height
                int scrolly = -itemUnderlying.getTop() + underlyingListView.getPaddingTop() +
                        underlyingListView.getFirstVisiblePosition() * itemUnderlying.getHeight();
                upperListView.scrollTo(0, (int) (scrolly * 1.1));


            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

    }

    @Override
    public void initData() {
        //获得传入id 对应数据
        final int id = getIntent().getIntExtra("position",-1);

        //获取网络数据
        netTool = new NetTool();

        HashMap<String,String> map = new HashMap<>();
        map.put("device_type","1");
        map.put("version","1.0.1");
        netTool.getNet(new NetListener() {
            @Override
            public void onSuccessed(String result) {

                Gson gson = new Gson();
                Type type = new TypeToken<GlassDetailsBean>(){}.getType();

                glassDetailsBean = gson.fromJson(result,type);

                //获取背景图
                ImageLoader loader = VolleySingleton.getInstance().getImageLoader();
                loader.get(glassDetailsBean.getData().getList().get(id).getData_info().getGoods_img(),
                        ImageLoader.getImageListener(backgroundView, R.mipmap.null_state, R.mipmap.null_state));

                //向adapter中添加数据
                underlyingAdapter.setDataInfoBean(glassDetailsBean.getData().getList().get(id).getData_info());

                underlyingListView.setAdapter(underlyingAdapter);

            }

            @Override
            public void onFailed(VolleyError error) {

            }
        },map, URIValues.GLASS_DETAILS);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        Bundle bundle;
        switch (v.getId()) {
            case R.id.tv_wear_glass_details:
                ArrayList<GlassDetailsBean.DataBean.ListBean.DataInfoBean.WearVideoBean> wearVideoBean =
                        new ArrayList<>();
                Log.d("-=-=-=", "-=-=-" + underlyingAdapter.getDataInfoBean().getGoods_name());
                wearVideoBean.addAll(underlyingAdapter.getDataInfoBean().getWear_video());
                intent = new Intent(this, WearTheAtlasActivity.class);
                bundle = new Bundle();
                bundle.putParcelableArrayList("wear_video",wearVideoBean);
                intent.putExtras(bundle);
                startActivity(intent);
        }
    }
}
