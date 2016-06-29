package com.mirror.mirrortry.orderdetails;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.google.gson.Gson;
import com.mirror.mirrortry.R;
import com.mirror.mirrortry.alladdress.AllAddressBean;
import com.mirror.mirrortry.alladdress.MyAllAddressActivity;
import com.mirror.mirrortry.base.BaseActivity;
import com.mirror.mirrortry.main.MainActivity;
import com.mirror.mirrortry.net.NetListener;
import com.mirror.mirrortry.net.NetTool;
import com.mirror.mirrortry.net.URIValues;
import com.mirror.mirrortry.net.VolleySingleton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;


/**
 * Created by dllo on 16/6/24.
 */
public class OrderDetailsActivity extends BaseActivity implements View.OnClickListener {
    private TextView name, number, address, please, write, brand, price;
    private AllAddressBean bean;
    private ImageView goodsPic;

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
        write = findView(R.id.tv_write_or_alter_address);
        brand = findView(R.id.tv_brand_order);
        price = findView(R.id.tv_goods_price);
        goodsPic = findView(R.id.iv_goods_pic);

    }

    @Override
    public void initData() {

        SharedPreferences getSp = getSharedPreferences("goodsMessage", MODE_PRIVATE);
        brand.setText(getSp.getString("goods_name", " "));
        price.setText("¥" + getSp.getString("goods_price", " "));
        ImageLoader imageLoader = VolleySingleton.getInstance().getImageLoader();
        imageLoader.get(getSp.getString("goods_pic", " "), ImageLoader.getImageListener
                (goodsPic, R.mipmap.null_state, R.mipmap.null_state));

        initNet();

//        brand.setText(getIntent().getStringExtra("goods_name"));
//        price.setText("¥" + getIntent().getStringExtra("goods_price"));
//        ImageLoader imageLoader = VolleySingleton.getInstance().getImageLoader();
//        imageLoader.get(getIntent().getStringExtra("goods_pic"),ImageLoader.getImageListener
//                (goodsPic,R.mipmap.null_state, R.mipmap.null_state));


//        SharedPreferences getSp = getSharedPreferences("address",MODE_PRIVATE);
//        String outName = getSp.getString("name"," ");
//        String outNumber = getSp.getString("number"," ");
//        String outAddress = getSp.getString("address"," ");
//        name.setText("收件人: " + outName);
//        number.setText(outNumber);
//        address.setText("地址: " + outAddress);
//        if (!outName.equals(" ") || !outNumber.equals(" ") || !outAddress.equals(" ")){
//            name.setVisibility(View.VISIBLE);
//            number.setVisibility(View.VISIBLE);
//            address.setVisibility(View.VISIBLE);
//            please.setVisibility(View.GONE);
//
//        }else {
//
//            name.setVisibility(View.GONE);
//            number.setVisibility(View.GONE);
//            address.setVisibility(View.GONE);
//            please.setVisibility(View.VISIBLE);
//        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_order_close:
                Intent main = new Intent(this, MainActivity.class);
                startActivity(main);
                finish();
                break;

            case R.id.rl_write_address:
                Intent intent = new Intent(this, MyAllAddressActivity.class);
                startActivity(intent);
                finish();

                break;
        }

    }

    private void initNet() {
        SharedPreferences getSp = getSharedPreferences("isLogin", MODE_PRIVATE);
        String token = getSp.getString("token", " ");
        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("device_type", "2");
        NetTool netTool = new NetTool();
        netTool.getNet(new NetListener() {
            @Override
            public void onSuccessed(String result) {
                Gson gson = new Gson();
                bean = gson.fromJson(result, AllAddressBean.class);
                if (bean.getData().getList().size() == 0) {
                    name.setVisibility(View.GONE);
                    number.setVisibility(View.GONE);
                    address.setVisibility(View.GONE);
                    please.setVisibility(View.VISIBLE);
                } else {
                    for (AllAddressBean.DataBean.ListBean listBean : bean.getData().getList()) {
                        if (listBean.getIf_moren().equals("1")) {
                            name.setVisibility(View.VISIBLE);
                            number.setVisibility(View.VISIBLE);
                            address.setVisibility(View.VISIBLE);
                            please.setVisibility(View.GONE);
                            name.setText("收件人: " + listBean.getUsername());
                            address.setText("地址: " + listBean.getAddr_info());
                            number.setText(listBean.getCellphone());
                            write.setText("更改地址");

                        }
                    }

                }
            }

            @Override
            public void onFailed(VolleyError error) {

            }
        }, map, URIValues.ADDRESS_LIST);

    }

}
