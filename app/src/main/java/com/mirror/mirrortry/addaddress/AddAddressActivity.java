package com.mirror.mirrortry.addaddress;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.mirror.mirrortry.R;
import com.mirror.mirrortry.alladdress.MyAllAddressActivity;
import com.mirror.mirrortry.base.BaseActivity;
import com.mirror.mirrortry.net.NetListener;
import com.mirror.mirrortry.net.NetTool;
import com.mirror.mirrortry.net.URIValues;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dllo on 16/6/25.
 */
public class AddAddressActivity extends BaseActivity implements View.OnClickListener {
    private TextView submit, title, recipients, phoneNumber, receiveAddress;
    private EditText name, number, address;
    private NetTool netTool;
    private int a;
    private String addressId;
    private String token;

    @Override
    public int setLayout() {
        return R.layout.activity_add_address;
    }

    @Override
    public void initView() {
        findView(R.id.iv_add_address_close).setOnClickListener(this);
        title = findView(R.id.tv_add_or_revise_address);
        recipients = findView(R.id.tv_add_or_revise_name);
        phoneNumber = findView(R.id.tv_add_or_revise_number);
        receiveAddress = findView(R.id.tv_add_or_revise_goods_address);
        submit = findView(R.id.tv_submit_address);
        submit.setOnClickListener(this);
        name = findView(R.id.et_add_recipients_name);
        number = findView(R.id.et_add_phone_number);
        address = findView(R.id.et_add_take_address);

    }

    @Override
    public void initData() {
        SharedPreferences getSp = getSharedPreferences("isLogin", MODE_PRIVATE);
        token = getSp.getString("token", " ");
        Intent intent = getIntent();
        a = intent.getIntExtra("mark", 0);
        String reName = intent.getStringExtra("name");
        String reNumber = intent.getStringExtra("number");
        String reAddress = intent.getStringExtra("address");
        addressId = intent.getStringExtra("addrId");
        name.setText(reName);
        number.setText(reNumber);
        address.setText(reAddress);
        if (a == 11) {
            title.setText("編輯地址");
            recipients.setText("編輯收件人姓名");
            phoneNumber.setText("編輯聯繫人電話號碼");
            receiveAddress.setText("編輯收貨地址");
            submit.setText("提交修改");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add_address_close:
                finish();
                break;
            case R.id.tv_submit_address:
                //走修改的post

                if (name.getText().length() == 0 || number.getText().length() == 0
                        || address.getText().length() == 0) {

                    Toast.makeText(this, "請填寫信息", Toast.LENGTH_SHORT).show();

                } else if (isMobileNo(number.getText().toString()) == false) {
                    Toast.makeText(this, "電話號碼不正確", Toast.LENGTH_SHORT).show();

                } else if (a == 11) {
                   //修改的post
                    postAlterNet();
                    Intent intent = new Intent(this, MyAllAddressActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    //添加的post
                    postNet();
                    Intent intent = new Intent(this, MyAllAddressActivity.class);
                    startActivity(intent);
                    finish();
                }

                break;

        }
    }

    //修改地址的post
    private void postAlterNet() {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);  //從登陸那拿到token
        map.put("addr_id",addressId);
        map.put("username", name.getText().toString());
        map.put("cellphone", number.getText().toString());
        map.put("addr_info", address.getText().toString());
        netTool = new NetTool();
        netTool.getNet(new NetListener() {
            @Override
            public void onSuccessed(String result) {

            }

            @Override
            public void onFailed(VolleyError error) {

            }
        }, map, URIValues.EDIT_ADDRESS);
    }

    //添加地址的post
    private void postNet() {

        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);  //從登陸那拿到token
        map.put("username", name.getText().toString());
        map.put("cellphone", number.getText().toString());
        map.put("addr_info", address.getText().toString());
        netTool = new NetTool();
        netTool.getNet(new NetListener() {
            @Override
            public void onSuccessed(String result) {

            }

            @Override
            public void onFailed(VolleyError error) {

            }
        }, map, URIValues.ADD_ADDRESS);

    }

    //对手机号格式的设定
    public static boolean isMobileNo(String mobiles) {
        Pattern pattern = Pattern.compile("^[1][3578][0-9]{9}$");
        //        "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，
        //        "{9}"代表后面是可以是0～9的数字，有9位。
        Matcher matcher = pattern.matcher(mobiles);
        return matcher.matches();
    }


}
