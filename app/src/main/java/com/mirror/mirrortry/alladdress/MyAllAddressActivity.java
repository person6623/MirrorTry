package com.mirror.mirrortry.alladdress;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.mirror.mirrortry.R;
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
        listView = findView(R.id.address_listView);

    }

    @Override
    public void initData() {
        adapter = new AllAddressListViewAdapter(this);
        been = new ArrayList<>();
        been.add(new AllAddressBean("a","b","c"));
        adapter.setBeen(been);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
