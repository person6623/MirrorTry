package com.mirror.mirrortry.register;

import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseActivity;
import com.mirror.mirrortry.login.LoginActivity;
import com.mirror.mirrortry.net.NetListener;
import com.mirror.mirrortry.net.NetTool;
import com.mirror.mirrortry.net.URIValues;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dllo on 16/6/21.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener, TextWatcher {
    private TextView tvCreateRegisterAccount;
    private Button btnSendRegisterVerificationCode;
    private EditText etRegisterPhoneNumber, etRegisterVerificationCode, etSetRegisterPassword;
    private ImageView ivRegisterClose;
    //num不能为空 否则空指针
    private String num = "0", code, passWord;
    private int time = 60;
    private boolean flag = true;
    private NetTool netTool;
    private CodeBean bean;
    private RegisterBean registerBean;

    @Override
    public int setLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        tvCreateRegisterAccount = findView(R.id.tv_create_register_account);
        btnSendRegisterVerificationCode = findView(R.id.btn_send_register_verification_code);
        ivRegisterClose = findView(R.id.iv_register_close);
        etRegisterPhoneNumber = findView(R.id.et_register_phone_number);
        etRegisterVerificationCode = findView(R.id.et_register_verification_code);
        etSetRegisterPassword = findView(R.id.et_set_register_password);

        tvCreateRegisterAccount.setOnClickListener(this);
        btnSendRegisterVerificationCode.setOnClickListener(this);
        ivRegisterClose.setOnClickListener(this);

        etRegisterPhoneNumber.addTextChangedListener(this);
        etRegisterVerificationCode.addTextChangedListener(this);
        etSetRegisterPassword.addTextChangedListener(this);


    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_register_close:
                finish();
                break;
            case R.id.tv_create_register_account:

                if (isMobileNo(num) == true && etSetRegisterPassword.getText().length() != 0
                        && etRegisterPhoneNumber.getText().length() != 0
                        && etRegisterVerificationCode.getText().length() != 0) {

                    register();

                } else {
                    Toast.makeText(this, "請完善信息", Toast.LENGTH_SHORT).show();

                }

                break;
            case R.id.btn_send_register_verification_code:

                if (isMobileNo(num) == false) {
                    Toast.makeText(this, "请输入正确的电话号码", Toast.LENGTH_SHORT).show();
                } else {
                    getCode();
                    btnState();

                }

                break;
            default:
                break;
        }
    }

    //对手机号格式的设定
    public static boolean isMobileNo(String mobiles) {
        Pattern pattern = Pattern.compile("^[1][3578][0-9]{9}$");
        //        "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，
        //        "{9}"代表后面是可以是0～9的数字，有9位。
        Matcher matcher = pattern.matcher(mobiles);
        return matcher.matches();
    }

    //驗證碼發送后按鈕的狀態和倒計時
    public void btnState() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag) {
                    time--;
                    try {
                        Thread.sleep(1000);
                        btnSendRegisterVerificationCode.post(new Runnable() {
                            @Override
                            public void run() {
                                btnSendRegisterVerificationCode.setText(time + "s后重新获取");
                                btnSendRegisterVerificationCode.setClickable(false);
                                btnSendRegisterVerificationCode.setBackgroundColor(Color.GRAY);
                            }
                        });

                        if (time <= 1) {
                            flag = false;
                            btnSendRegisterVerificationCode.post(new Runnable() {
                                @Override
                                public void run() {
                                    btnSendRegisterVerificationCode.setText("獲取驗證碼");
                                    btnSendRegisterVerificationCode.setClickable(true);
                                    btnSendRegisterVerificationCode.setBackground(getDrawable(R.mipmap.verification_code_button));
                                }
                            });

                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                flag = true;
                time = 60;
            }
        }).start();


    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        num = etRegisterPhoneNumber.getText().toString();
        isMobileNo(num);
        code = etRegisterVerificationCode.getText().toString();
        passWord = etSetRegisterPassword.getText().toString();

    }

    //獲取驗證碼的post
    private void getCode() {
        HashMap<String, String> map = new HashMap<>();
        map.put("phone_number", num);
        netTool = new NetTool();
        netTool.getNet(new NetListener() {
            @Override
            public void onSuccessed(String result) {
//                Gson gson = new Gson();
//                bean = gson.fromJson(result, CodeBean.class);

            }

            @Override
            public void onFailed(VolleyError error) {

            }
        }, map, URIValues.GET_CODE);
    }

    //註冊的post
    private void register() {
        HashMap<String, String> map = new HashMap<>();
        map.put("phone number", num);
        map.put("number", code);
        map.put("password", passWord);
        netTool = new NetTool();
        netTool.getNet(new NetListener() {
            @Override
            public void onSuccessed(String result) {
                Gson gson = new Gson();
                registerBean = gson.fromJson(result, RegisterBean.class);
                if (registerBean.getMsg().equals("验证码错误")) {
                    Toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                } else if (registerBean.getMsg().equals("此手机号已被注册")) {
                    Toast.makeText(RegisterActivity.this, "此手机号已被注册", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.putExtra("number", num);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailed(VolleyError error) {

            }
        }, map, URIValues.REGISTER);
    }
}