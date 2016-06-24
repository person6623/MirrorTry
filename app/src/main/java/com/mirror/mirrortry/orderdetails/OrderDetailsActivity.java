package com.mirror.mirrortry.orderdetails;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mirror.mirrortry.R;
import com.mirror.mirrortry.alladdress.MyAllAddressActivity;
import com.mirror.mirrortry.base.BaseActivity;


/**
 * Created by dllo on 16/6/24.
 */
public class OrderDetailsActivity extends BaseActivity implements View.OnClickListener {
    private TextView name,number,address,please;
    @Override
    public int setLayout() {
        return R.layout.activity_order_details;
    }

    @Override
    public void initView() {
        findView(R.id.rl_write_address).setOnClickListener(this);
        findView(R.id.iv_order_close).setOnClickListener(this);
        name = findView(R.id.tv_recipients);
        number = findView(R.id.tv_phone_number);
        address = findView(R.id.tv_address);
        please = findView(R.id.tv_please_inout);
        name.setVisibility(View.GONE);
        number.setVisibility(View.GONE);
        address.setVisibility(View.GONE);


    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_order_close:

                finish();
                break;

            case R.id.rl_write_address:
                Intent intent = new Intent(this, MyAllAddressActivity.class);
                startActivity(intent);

                break;
        }

    }
}
