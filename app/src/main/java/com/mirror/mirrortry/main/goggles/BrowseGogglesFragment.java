package com.mirror.mirrortry.main.goggles;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseFragment;
import com.mirror.mirrortry.glassdetails.GlassDetailsActivity;
import com.mirror.mirrortry.list.ListActivity;
import com.mirror.mirrortry.main.MainBean;
import com.mirror.mirrortry.main.MainContinueBean;
import com.mirror.mirrortry.main.MainRecyclerViewAdapter;
import com.mirror.mirrortry.net.NetListener;
import com.mirror.mirrortry.net.NetTool;
import com.mirror.mirrortry.net.URIValues;
import com.mirror.mirrortry.tools.GlassDetailsInterface;
import com.mirror.mirrortry.tools.TextInterception;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dllo on 16/6/22.
 */
public class BrowseGogglesFragment extends BaseFragment implements View.OnClickListener, GlassDetailsInterface {
    private AutoRelativeLayout relativeLayout;
    private RecyclerView recyclerView;
    private ArrayList<MainBean.DataBean.ListBean> datas;
    private MainRecyclerViewAdapter adapter;
    private TextView title;
    private ProgressBar progressBar;
    //区分平光镜
    private int selectStyle;

    @Override
    public int setLayout() {
        return R.layout.fragment_all_kind;
    }

    @Override
    public void initView(View view) {
        recyclerView = findView(R.id.main_recyclerLayout, view);
        relativeLayout = findView(R.id.rl_title, view);
        progressBar = findView(R.id.pb_all_kind, view);
        title = findView(R.id.tv_title, view);
        title.setText("瀏覽平光眼鏡");
    }

    @Override
    public void initData() {
        relativeLayout.setOnClickListener(this);
        adapter = new MainRecyclerViewAdapter(context);
        adapter.setGlassDetailsInterface(this);
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

                for (int i = 0; i < bean.getData().getList().size(); i++) {
                    if (!bean.getData().getList().get(i).getType().equals("2")) {
                        selectStyle = TextInterception.TextInterception(bean.getData().getList().get(i).getData_info().getModel());

                        if (selectStyle != 5) {
                            datas.add(bean.getData().getList().get(i));
                        }
                    }
                }

                adapter.setDatas(datas);
                progressBar.setVisibility(View.GONE);
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
        intent.putExtra("position", 1);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onGlassClick(int position, List<MainBean.DataBean.ListBean> listBeen, List<MainContinueBean.DataBean.ListBean> continueListBean) {
        Intent intent = new Intent(context, GlassDetailsActivity.class);
        intent.putExtra("jump",1);
        //标志性id
        String id = listBeen.get(position).getData_info().getGoods_id();
        intent.putExtra("jumpId",id);
        context.startActivity(intent);
    }
}
