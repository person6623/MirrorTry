package com.mirror.mirrortry.login;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseActivity;
import com.mirror.mirrortry.register.RegisterActivity;

/**
 * Created by dllo on 16/6/21.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, TextWatcher {
    private TextView loginCreateAccount, loginButton, loginBtn;
    private ImageView loginClose, loginWeiboBtn, loginWeixinBtn;
    private EditText loginPhoneNumber, loginPasswordCode;

    @Override
    public int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        loginCreateAccount = findView(R.id.login_create_account);
        loginButton = findView(R.id.login_button);
        loginBtn = findView(R.id.login_btn);
        loginClose = findView(R.id.login_close);
        loginWeiboBtn = findView(R.id.login_weibo_btn);
        loginWeixinBtn = findView(R.id.login_weixin_btn);
        loginPhoneNumber = findView(R.id.login_phone_number);
        loginPasswordCode = findView(R.id.login_password_code);

        loginCreateAccount.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        loginClose.setOnClickListener(this);
        loginWeiboBtn.setOnClickListener(this);
        loginWeixinBtn.setOnClickListener(this);

        //监听EditText
        loginPhoneNumber.addTextChangedListener(this);
        loginPasswordCode.addTextChangedListener(this);


    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_create_account:
                Intent createAccountIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(createAccountIntent);
                break;
            case R.id.login_close:
                finish();
                break;
            default:
                break;

        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        //对登录按钮的显隐性进行判断
        if (loginPhoneNumber.getText().length() != 0) {
            if (loginPasswordCode.getText().length() != 0) {
                loginButton.setVisibility(View.VISIBLE);
                loginBtn.setVisibility(View.INVISIBLE);
            } else {
                loginButton.setVisibility(View.GONE);
                loginBtn.setVisibility(View.VISIBLE);
            }

        } else {
            loginButton.setVisibility(View.GONE);
            loginBtn.setVisibility(View.VISIBLE);
        }
    }
}
