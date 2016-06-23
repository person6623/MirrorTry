package com.mirror.mirrortry.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseActivity;
import com.mirror.mirrortry.register.RegisterActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dllo on 16/6/21.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, TextWatcher {
    private TextView loginCreateAccount, loginButton, loginBtn;
    private ImageView loginClose, loginWeiboBtn, loginWeixinBtn;
    private EditText loginPhoneNumber, loginPasswordCode;
    private String num;

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

        Intent intent = getIntent();
        String number = intent.getStringExtra("number");
        loginPhoneNumber.setText(number);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_create_account:
                Intent createAccountIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(createAccountIntent);
                finish();
                break;
            case R.id.login_close:
                finish();
                break;
            case R.id.login_button:
                Toast.makeText(this, "dianji", Toast.LENGTH_SHORT).show();
                if (isMobileNo(num) == false){
                    Toast.makeText(this, "请输入正确的电话号码", Toast.LENGTH_SHORT).show();

                }else {


                }
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
        //调用判断手机号的格式
        num = loginPhoneNumber.getText().toString();
        isMobileNo(num);

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

    //对手机号格式的设定
    public static boolean isMobileNo(String mobiles) {
        Pattern pattern = Pattern.compile("^[1][358][0-9]{9}$");
        //        "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，
        //        "{9}"代表后面是可以是0～9的数字，有9位。
        Matcher matcher = pattern.matcher(mobiles);
        return matcher.matches();

    }
}
