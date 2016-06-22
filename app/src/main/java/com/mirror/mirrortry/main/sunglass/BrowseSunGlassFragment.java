package com.mirror.mirrortry.main.sunglass;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseFragment;
import com.mirror.mirrortry.list.ListActivity;
import com.mirror.mirrortry.main.MainBean;
import com.mirror.mirrortry.main.MainRecyclerViewAdapter;
import com.mirror.mirrortry.net.NetListener;
import com.mirror.mirrortry.net.NetTool;
import com.mirror.mirrortry.net.URIValues;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dllo on 16/6/22.
 */
public class BrowseSunGlassFragment extends BaseFragment implements View.OnClickListener {
    private AutoRelativeLayout relativeLayout;
    private RecyclerView recyclerView;
    private ArrayList<MainBean.DataBean.ListBean> datas;
    private MainRecyclerViewAdapter adapter;
    private TextView title;
    @Override
    public int setLayout() {
        return R.layout.fragment_all_kind;
    }

    @Override
    public void initView(View view) {
        recyclerView = findView(R.id.main_recyclerLayout,view);
        relativeLayout =findView(R.id.rl_title,view);
        title = findView(R.id.tv_title,view);
        title.setText("瀏覽太陽眼鏡");
    }

    @Override
    public void initData() {
        relativeLayout.setOnClickListener(this);
        adapter = new MainRecyclerViewAdapter(context);
        datas = new ArrayList<>();

        //添加post请求的body
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
        }, map, URIValues.ALL_KIND);

        recyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, ListActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
