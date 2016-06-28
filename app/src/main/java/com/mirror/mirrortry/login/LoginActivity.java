package com.mirror.mirrortry.login;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
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
import com.mirror.mirrortry.register.RegisterActivity;
import com.mob.tools.utils.UIHandler;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;

/**
 * Created by dllo on 16/6/21.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, TextWatcher, PlatformActionListener, Handler.Callback {
    private TextView loginCreateAccount, loginButton, loginBtn;
    private ImageView loginClose, loginWeiboBtn, loginWeixinBtn;
    private EditText loginPhoneNumber, loginPasswordCode;
    private String num, passWord;
    private LoginBean bean;

    //----------------------新浪微博授权获取用户信息相关------------------------
    private static final int MSG_TOAST = 1;
    private static final int MSG_ACTION_CCALLBACK = 2;
    private static final int MSG_CANCEL_NOTIFY = 3;
    private Platform mPf;
    TextView mThirdLoginResult;


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
                                editor.putBoolean("login", true);
                                editor.putString("token", bean.getToken());
                                editor.putString("uid", bean.getUid());
                                editor.commit();

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    intent.putExtra("login",111);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onFailed(VolleyError error) {
                            Log.d("LoginActivity", "error:" + error);
                        }
                    }, map, URIValues.LOGIN);
                }
            case R.id.login_weibo_btn:
                thirdSinaLogin();
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
//-----------------------------------------------------新浪微博授权相关-----------------------

    /**
     * 新浪微博授权、获取用户信息页面
     */
    private void thirdSinaLogin() {
        //初始化新浪平台
        Platform pf = ShareSDK.getPlatform(LoginActivity.this, SinaWeibo.NAME);
        pf.SSOSetting(true);
        //设置监听
        pf.setPlatformActionListener(LoginActivity.this);
        //获取登陆用户的信息，如果没有授权，会先授权，然后获取用户信息
        pf.authorize();
    }

    /**
     * 新浪微博授权成功回调页面
     */
    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        /** res是返回的数据，例如showUser(null),返回用户信息，对其解析就行
         *   http://sharesdk.cn/androidDoc/cn/sharesdk/framework/PlatformActionListener.html
         *   1、不懂如何解析hashMap的，可以上网搜索一下
         *   2、可以参考官网例子中的GetInforPage这个类解析用户信息
         *   3、相关的key-value,可以看看对应的开放平台的api
         *     如新浪的：http://open.weibo.com/wiki/2/users/show
         *     腾讯微博：http://wiki.open.t.qq.com/index.php/API%E6%96%87%E6%A1%A3/%E5%B8%90%E6%88%B7%E6%8E%A5%E5%8F%A3/%E8%8E%B7%E5%8F%96%E5%BD%93%E5%89%8D%E7%99%BB%E5%BD%95%E7%94%A8%E6%88%B7%E7%9A%84%E4%B8%AA%E4%BA%BA%E8%B5%84%E6%96%99
         *
         */
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 1;
        msg.arg2 = i;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    /**
     * 授权失败
     */
    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        throwable.printStackTrace();
        throwable.getMessage();
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 2;
        msg.arg2 = i;
        msg.obj = throwable;
        UIHandler.sendMessage(msg, this);

    }

    /**
     * 取消授权
     */
    @Override
    public void onCancel(Platform platform, int i) {
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 3;
        msg.arg2 = i;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_TOAST: {
                String text = String.valueOf(msg.obj);
                Toast.makeText(LoginActivity.this, text, Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_ACTION_CCALLBACK:
                switch (msg.arg1) {
                    case 1: {
                        // 成功, successful notification
                        //授权成功后,获取用户信息，要自己解析，看看oncomplete里面的注释
                        //ShareSDK只保存以下这几个通用值
                        Platform pf = ShareSDK.getPlatform(LoginActivity.this, SinaWeibo.NAME);
                        Log.e("sharesdk use_id", pf.getDb().getUserId()); //获取用户id
                        Log.e("sharesdk use_name", pf.getDb().getUserName());//获取用户名称
                        Log.e("sharesdk use_icon", pf.getDb().getUserIcon());//获取用户头像
//                        mThirdLoginResult.setText("授权成功" + "\n" + "用户id:" + pf.getDb().getUserId() + "\n" + "获取用户名称" +
//                                pf.getDb().getUserName() + "\n" + "获取用户头像" + pf.getDb().getUserIcon());
                        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                        //mPf.author()这个方法每一次都会调用授权，出现授权界面
                        //如果要删除授权信息，重新授权
                        //mPf.getDb().removeAccount();
                        //调用后，用户就得重新授权，否则下一次就不用授权
                    }
                    break;
                    case 2: {
//                        mThirdLoginResult.setText("登录失败");
                        Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                    case 3: {
                        //取消,cancel notification
//                        mThirdLoginResult.setText("取消授权");
                        Toast.makeText(this, "取消授权", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                break;
            case MSG_CANCEL_NOTIFY: {
                NotificationManager nm = (NotificationManager) msg.obj;
                if (nm != null) {
                    nm.cancel(msg.arg1);
                }
            }
            break;
        }

        return false;
    }
}
