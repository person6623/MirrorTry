package com.mirror.mirrortry.alladdress;

import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;

import com.mirror.mirrortry.R;
import com.mirror.mirrortry.addaddress.AddAddressActivity;
import com.mirror.mirrortry.base.BaseActivity;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/24.
 */
public class MyAllAddressActivity extends BaseActivity implements View.OnClickListener {
    private ListView listView;
    private ImageView close;
    private ArrayList<AllAddressBean>been;
    private AllAddressListViewAdapter adapter;
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
        adapter = new AllAddressListViewAdapter(this);
        been = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            been.add(new AllAddressBean("a+ " + i,"b+ " + i,"c+ " + i));
        }
        adapter.setBeen(been);
        listView.setAdapter(adapter);

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
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_all_address_close:
                finish();
                break;
            case R.id.tv_add_address:
                Intent intent = new Intent(this, AddAddressActivity.class);
                startActivity(intent);
                break;

        }


    }
}
