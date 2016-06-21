package com.mirror.mirrortry.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseFragment;
import com.mirror.mirrortry.net.NetListener;
import com.mirror.mirrortry.net.NetTool;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.zhy.autolayout.AutoRelativeLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dllo on 16/6/21.
 */
public class AllKindFragment extends BaseFragment implements View.OnClickListener {

    private AutoRelativeLayout relativeLayout;
    private RecyclerView recyclerView;
    private ArrayList<MainBean.DataBean.ListBean> datas;
    private MainRecyclerViewAdapter adapter;


    public static Fragment createFragment(String url) {
        Fragment fragment = new AllKindFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public int setLayout() {
        return R.layout.fragment_all_kind;
    }

    @Override
    public void initView(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.main_recyclerLayout);
        relativeLayout = (AutoRelativeLayout) view.findViewById(R.id.rl_title);
    }

    @Override
    public void initData() {
        relativeLayout.setOnClickListener(this);
        adapter = new MainRecyclerViewAdapter(context);
        datas = new ArrayList<>();

        HashMap<String, String> map = new HashMap<>();
        map.put("last_time", "");
        map.put("device_type", "2");
        map.put("page", "");
        map.put("token", "");
        map.put("version", "1.0.1");

        NetTool netTool = new NetTool();
        netTool.getNet(new NetListener() {
            @Override
            public void onSuccessed(String result) {
                Gson gson = new Gson();
                MainBean bean = gson.fromJson(result, MainBean.class);
                adapter.setDatas(bean.getData().getList());
            }

            @Override
            public void onFailed(VolleyError error) {

            }
        }, map, getArguments().getString("url"));

        recyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);

    }

    @Override
    public void onClick(View v) {

        Toast.makeText(context, "点击了", Toast.LENGTH_SHORT).show();
    }
}
