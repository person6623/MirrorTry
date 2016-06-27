package com.mirror.mirrortry.alladdress;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.mirror.mirrortry.R;
import com.mirror.mirrortry.addaddress.AddAddressActivity;
import com.mirror.mirrortry.base.BaseActivity;
import com.mirror.mirrortry.net.NetListener;
import com.mirror.mirrortry.net.NetTool;
import com.mirror.mirrortry.net.URIValues;
import com.mirror.mirrortry.orderdetails.OrderDetailsActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dllo on 16/6/24.
 */
public class MyAllAddressActivity extends BaseActivity implements View.OnClickListener, AllAddressListViewAdapter.ClickItem {
    private ListView listView;
    private ImageView close;
    private AllAddressListViewAdapter adapter;
    private AllAddressBean bean;
    private SharedPreferences sp;
    private String token;

    @Override
    public int setLayout() {
        return R.layout.activity_my_all_address;
    }

    @Override
    public void initView() {

        findView(R.id.iv_all_address_close).setOnClickListener(this);
        findView(R.id.tv_add_address).setOnClickListener(this);
        listView = findView(R.id.address_listView);
    }

    @Override
    public void initData() {
        SharedPreferences getSp = getSharedPreferences("isLogin", MODE_PRIVATE);
        token = getSp.getString("token", " ");
        adapter = new AllAddressListViewAdapter(this);
        initNet();


//        listView.setOnItemClickListener(this);

        //listView滑动时关闭菜单
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                adapter.closeMenu();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                adapter.closeMenu();

            }
        });

        adapter.setClickItem(this);
    }

    private void initNet() {

        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("device_type", "2");
        NetTool netTool = new NetTool();
        netTool.getNet(new NetListener() {
            @Override
            public void onSuccessed(String result) {
                Gson gson = new Gson();
                bean = gson.fromJson(result, AllAddressBean.class);
                adapter.setBeen(bean.getData().getList());
            }

            @Override
            public void onFailed(VolleyError error) {

            }
        }, map, URIValues.ADDRESS_LIST);

                listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_all_address_close:

                Intent order = new Intent(this, OrderDetailsActivity.class);
                startActivity(order);
                finish();
                break;
            case R.id.tv_add_address:
                Intent intent = new Intent(this, AddAddressActivity.class);
                startActivity(intent);
                finish();
                break;

        }

    }

    //listView的点击事件(接口)
    @Override
    public void onLineClick(int position, String addr) {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("addr_id",addr);
        NetTool netTool = new NetTool();
        netTool.getNet(new NetListener() {
            @Override
            public void onSuccessed(String result) {

            }

            @Override
            public void onFailed(VolleyError error) {

            }
        }, map, URIValues.DEFAULT_ADDRESS);
        Toast.makeText(this, "設置默認地址成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, OrderDetailsActivity.class);
        startActivity(intent);
        finish();
    }

    //编辑的监听事件(接口)
    @Override
    public void onEditClick(int position) {
        Intent intent = new Intent(this, AddAddressActivity.class);
        intent.putExtra("mark", 11); //標記用  添加地址的activity接到不同的標記  更換不同的標題
        intent.putExtra("name", bean.getData().getList().get(position).getUsername());
        intent.putExtra("number", bean.getData().getList().get(position).getCellphone());
        intent.putExtra("address", bean.getData().getList().get(position).getAddr_info());
        intent.putExtra("addrId", bean.getData().getList().get(position).getAddr_id());
        startActivity(intent);
        finish();
    }

    //刪除的接口
    @Override
    public void onDeleteClick(int position, String addr) {
        bean.getData().getList().remove(position);
        adapter.notifyDataSetChanged();
        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("addr_id", addr);
        NetTool netTool = new NetTool();
        netTool.getNet(new NetListener() {
            @Override
            public void onSuccessed(String result) {

            }

            @Override
            public void onFailed(VolleyError error) {

            }
        }, map, URIValues.DELETE_ADDRESS);
    }

}
