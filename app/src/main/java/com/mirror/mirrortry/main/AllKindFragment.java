package com.mirror.mirrortry.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseFragment;
import com.mirror.mirrortry.net.NetListener;
import com.mirror.mirrortry.net.NetTool;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/21.
 */
public class AllKindFragment extends BaseFragment implements View.OnClickListener {

    private AutoRelativeLayout relativeLayout;
    private RecyclerView recyclerView;
    private ArrayList<MainBean.DataBean.ListBean>datas;
    private MainRecyclerViewAdapter adapter;

    public static Fragment createFragment(){
        Fragment fragment = new AllKindFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("equityurl" ,url);
//        fragment.setArguments(bundle);
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
        NetTool netTool = new NetTool();
        netTool.getAllKind(new NetListener() {
            @Override
            public void onSuccessed(String result) {
                Gson gson = new Gson();
                MainBean bean = gson.fromJson(result,MainBean.class);
                adapter.setDatas(bean.getData().getList());
            }

            @Override
            public void onFailed(VolleyError error) {

            }
        });

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
