package com.mirror.mirrortry.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseFragment;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/21.
 */
public class AllKindFragment extends BaseFragment implements View.OnClickListener {

    private AutoRelativeLayout relativeLayout;
    private RecyclerView recyclerView;
    private ArrayList<String>datas;
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
        for (int i = 0; i < 5; i++) {
            datas.add("中国");
        }

        adapter.setDatas(datas);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);


    }

    @Override
    public void onClick(View v) {

    }
}
