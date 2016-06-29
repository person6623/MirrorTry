package com.mirror.mirrortry.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseActivity;
import com.mirror.mirrortry.main.MainActivity;
import com.mirror.mirrortry.net.NetListener;
import com.mirror.mirrortry.net.NetTool;
import com.mirror.mirrortry.net.URIValues;
import com.mirror.mirrortry.orderdetails.OrderDetailsActivity;
import com.mirror.mirrortry.register.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dllo on 16/6/21.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, TextWatcher {
    private TextView loginCreateAccount, loginButton, loginBtn;
    private ImageView loginClose, loginWeiboBtn, loginWeixinBtn;
    private EditText loginPhoneNumber, loginPasswordCode;
    private String num, passWord;
    private LoginBean bean;

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
//                Toast.makeText(this, "dianji", Toast.LENGTH_SHORT).show();
                if (isMobileNo(num) == false) {
                    Toast.makeText(this, "请输入正确的电话号码", Toast.LENGTH_SHORT).show();

                    //还少密码正确与否的判断

                } else {

                    HashMap<String, String> map = new HashMap<>();
                    map.put("phone_number", num);
                    map.put("password", passWord);
                    NetTool netTool = new NetTool();
                    netTool.getNet(new NetListener() {
                        @Override
                        public void onSuccessed(String result) {
//                            Gson gson = new Gson();
//                            bean = gson.fromJson(result, LoginBean.class);
                            bean = new LoginBean();
                            try {
                                JSONObject object = new JSONObject(result);
                                if (object.has("msg")) {
                                    bean.setMsg(object.getString("msg"));
                                }
                                if (object.has("data")) {
                                    JSONObject obj = object.getJSONObject("data");
                                    if (obj.has("token")) {
                                        bean.setToken(obj.getString("token"));
                                    }
                                    if (obj.has("uid")) {
                                        bean.setUid(obj.getString("uid"));
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Log.d("LoginActivity", result);
                            if (bean.getMsg().equals("密码错误")) {
                                Toast.makeText(LoginActivity.this, "密碼錯誤", Toast.LENGTH_SHORT).show();
                            } else if (bean.getMsg().equals("此手机号未注册")) {

                                Toast.makeText(LoginActivity.this, "此手機號未註冊", Toast.LENGTH_SHORT).show();

                            } else {

                                SharedPreferences sp = getSharedPreferences("isLogin", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
//                                editor.clear();
//                                editor.commit();
                                editor.putBoolean("login", true);
                                editor.putString("token", bean.getToken());
                                editor.putString("uid", bean.getUid());
                                editor.commit();

//                                Intent broad = new Intent("com.mirror.mirrortry.login.BROAD");
//                                broad.putExtra("login", true);
//                                sendBroadcast(broad);
                                if (getIntent().getIntExtra("sign", -1) == 1) {

                                    Intent intent = new Intent(LoginActivity.this, OrderDetailsActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onFailed(VolleyError error) {
                            Log.d("LoginActivity", "error:" + error);
                        }
                    }, map, URIValues.LOGIN);


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
        passWord = loginPasswordCode.getText().toString();
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
