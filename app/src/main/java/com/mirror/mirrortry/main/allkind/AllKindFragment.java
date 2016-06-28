package com.mirror.mirrortry.main.allkind;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseFragment;
import com.mirror.mirrortry.glassdetails.GlassDetailsActivity;
import com.mirror.mirrortry.list.ListActivity;
import com.mirror.mirrortry.main.MainBean;
import com.mirror.mirrortry.main.MainContinueBean;
import com.mirror.mirrortry.main.MainRecyclerViewAdapter;
import com.mirror.mirrortry.main.specialtoshare.SpecialToShareBean;
import com.mirror.mirrortry.main.specialtoshare.content.SpecialToShareActivity;
import com.mirror.mirrortry.net.NetListener;
import com.mirror.mirrortry.net.NetTool;
import com.mirror.mirrortry.net.URIValues;
import com.mirror.mirrortry.tools.GlassDetailsInterface;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.zhy.autolayout.AutoRelativeLayout;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dllo on 16/6/21.
 */
public class AllKindFragment extends BaseFragment implements View.OnClickListener, GlassDetailsInterface {

    private AutoRelativeLayout relativeLayout;
    private RecyclerView recyclerView;
    private ArrayList<MainBean.DataBean.ListBean> datas;
    private MainRecyclerViewAdapter adapter;
    private TextView title;
    private ProgressBar progressBar;

    @Override
    public int setLayout() {
        return R.layout.fragment_all_kind;
    }

    @Override
    public void initView(View view) {

        recyclerView = findView(R.id.main_recyclerLayout, view);
        relativeLayout = findView(R.id.rl_title, view);
        title = findView(R.id.tv_title, view);
        progressBar = findView(R.id.pb_all_kind, view);

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

//                Type type = new TypeToken<ArrayList<MainContinueBean>>(){}.getType();

                MainContinueBean mainContinueBeans = gson.fromJson(result,MainContinueBean.class);

                Log.d("-=-=-=bean", "bean.getData().getList().size():" + bean.getData().getList().size());
                Log.d("-=-=-=conBean", "mainContinueBeans.getData().getList().size():" + mainContinueBeans.getData().getList().size());

                adapter.setDatas(bean.getData().getList());

                adapter.setListBeens(mainContinueBeans.getData().getList());

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
        intent.putExtra("position", 0);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onGlassClick(int position, List<MainBean.DataBean.ListBean> listBeen, List<MainContinueBean.DataBean.ListBean> continueListBean) {
        //全部传入判断
        int selectType = 1;

        if (listBeen.get(position).getType().equals("1")) {
            Intent intent = new Intent(context, GlassDetailsActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        } else {
            Intent intent = new Intent(context, SpecialToShareActivity.class);
            Bundle bundle = new Bundle();

            MainContinueBean.DataBean.ListBean continueList = continueListBean.get(position);

            ArrayList<MainContinueBean.DataBean.ListBean.DataInfoBean.StoryDataBean.TextArrayBean> textArrayBean =
                    (ArrayList<MainContinueBean.DataBean.ListBean.DataInfoBean.StoryDataBean.TextArrayBean>) continueListBean.get(position).getData_info().getStory_data().getText_array();

            Log.d("-=-=0-0-=", "textArrayBean.size():" + textArrayBean.size());

            ArrayList<String> imgArray = (ArrayList<String>) continueListBean.get(position).getData_info().getStory_data().getImg_array();

            bundle.putParcelable("continueListBean", continueList);
            bundle.putParcelableArrayList("textArrayBean", textArrayBean);
            intent.putStringArrayListExtra("imgArray", imgArray);

            intent.putExtra("selectType",selectType);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
