package com.mirror.mirrortry.main.specialtoshare;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseFragment;
import com.mirror.mirrortry.main.specialtoshare.content.SpecialToShareActivity;
import com.mirror.mirrortry.net.NetListener;
import com.mirror.mirrortry.net.NetTool;
import com.mirror.mirrortry.net.URIValues;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dllo on 16/6/22.
 */
public class SpecialToShareFragment extends BaseFragment {
    private List<SpecialToShareBean.DataBean.ListBean> shareBeen;
    private SpecialToShareRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private NetTool netTool;

    @Override
    public int setLayout() {
        return R.layout.fragment_special_to_share;
    }

    @Override
    public void initView(View view) {
        recyclerView = findView(R.id.special_share_rv, view);

    }

    @Override
    public void initData() {
        adapter = new SpecialToShareRecyclerViewAdapter(context);
        shareBeen = new ArrayList<>();
        netTool = new NetTool();

        HashMap<String, String> map = new HashMap();
        map.put("device_type", "1");
        //解析
        netTool.getNet(new NetListener() {
            @Override
            public void onSuccessed(String result) {
                Gson gson = new Gson();
                SpecialToShareBean bean = gson.fromJson(result, SpecialToShareBean.class);
                adapter.setShareBeen(bean.getData().getList());
            }

            @Override
            public void onFailed(VolleyError error) {

            }
        }, map, URIValues.SPECIAL_TO_SHARE);

        recyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);

        //实现接口  包裹化传值
        adapter.setMyRecycleViewOnClickListener(new SpecialToShareRecyclerViewAdapter.MyRecycleViewOnClickListener() {
            @Override
            public void onClick(int position, List<SpecialToShareBean.DataBean.ListBean> beanList) {
                Intent intent = new Intent(context, SpecialToShareActivity.class);
                intent.putParcelableArrayListExtra("beanList", (ArrayList) beanList);
                startActivity(intent);
            }
        });

    }
}