package com.mirror.mirrortry.glassdetails;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mirror.mirrortry.AppApplicationContext;
import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseActivity;
import com.mirror.mirrortry.glassdetails.atlas.WearTheAtlasActivity;
import com.mirror.mirrortry.login.LoginActivity;
import com.mirror.mirrortry.net.NetHelper;
import com.mirror.mirrortry.net.NetListener;
import com.mirror.mirrortry.net.NetTool;
import com.mirror.mirrortry.net.URIValues;
import com.mirror.mirrortry.net.VolleySingleton;

import com.mirror.mirrortry.orderdetails.OrderDetailsActivity;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by dllo on 16/6/22.
 */
public class GlassDetailsActivity extends BaseActivity implements View.OnClickListener {
    //底层listview
    private ListView underlyingListView;
    //上层listview
    private ListView upperListView;
    //adapter
    //底层
    private UnderlyingAdapter underlyingAdapter;
    //上层
    private UpperAdapter upperAdapter;

    private NetTool netTool;
    //背景用
    private ImageView backgroundView;

    private GlassDetailsBean glassDetailsBean;

    private ImageView back, headViewImage;

    private TextView  buy;

    private boolean flag;

    private String url;

    private String titleUrl;
    private TextView wear;
    //功能栏布局
    private AutoRelativeLayout functionAutoRelativeLayout;

    //弹出动画首次运行
    private boolean isPopUp = true;

    @Override
    public int setLayout() {
        return R.layout.activity_glassdetails;
    }

    @Override
    public void initView() {
        //初始化组件
        underlyingListView = findView(R.id.lv_underlying_glass_details);
        //去分割线
        underlyingListView.setDividerHeight(0);
        //去滚动条
        underlyingListView.setVerticalScrollBarEnabled(false);

        upperListView = findView(R.id.lv_upper_glass_details);
        //去分割线
        upperListView.setDividerHeight(0);
        //去滚动条
        upperListView.setVerticalScrollBarEnabled(false);
        //添加一个透明头布局 补足位置
        View headView = LayoutInflater.from(this).inflate(R.layout.fragment_underlyuing_content_nullview, null);
        headViewImage = (ImageView) headView.findViewById(R.id.iv_imgn_glass_details);
        WindowManager wm = (WindowManager) AppApplicationContext.context
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        headViewImage.setLayoutParams(new AutoLinearLayout.LayoutParams(width, height));

        upperListView.addHeaderView(headView);
        upperListView.addFooterView(headView);

        backgroundView = findView(R.id.iv_background_glass_details);

        functionAutoRelativeLayout = findView(R.id.arl_function_glass_details);

        //功能键
        back = findView(R.id.iv_back_glass_details);
        buy = findView(R.id.tv_buy_glass_details);
        wear = findView(R.id.tv_wear_glass_details);

        wear.setOnClickListener(this);
        buy.setOnClickListener(this);
        back.setOnClickListener(this);

        underlyingAdapter = new UnderlyingAdapter(this);
        upperAdapter = new UpperAdapter(this);

        underlyingAdapter.setGlassDetailsShare(new UnderlyingAdapter.GlassDetailsShare() {
            @Override
            public void onClick(int position) {
                url = glassDetailsBean.getData().getList().get(position).getData_info().getGoods_share();
                titleUrl = glassDetailsBean.getData().getList().get(position).getData_info().getBrand();
                showShare();

            }
        });


        //底层获取焦点
        upperListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return underlyingListView.dispatchTouchEvent(event);
            }
        });


        //设置上层listview随底层listview滑动而滑动
        underlyingListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {


//                View itemUnderlying = underlyingListView.getChildAt(0);
//                if (itemUnderlying == null) {
//                    return;
//                }
//                //测算实时滑动距离
//                //assuming all list items have same height
//                int scrolly = -itemUnderlying.getTop() + underlyingListView.getPaddingTop() +
//                        underlyingListView.getFirstVisiblePosition() * itemUnderlying.getHeight();
//                upperListView.scrollTo(0, (int) (scrolly * 1.1));
////                int scrolly = underlyingListView.getChildAt(0).getTop();
////                upperListView.scrollTo(0, -scrolly);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (underlyingListView.getChildAt(1) == null) {
                    return;
                }
//                if (underlyingListView.getChildAt(2) == null){
//                    return;
//                }

                //头部渐隐
                View b = underlyingListView.getChildAt(0);
                if (b == null) {
                    return;
                }
                int scrollyHeader = -b.getTop();
                if (firstVisibleItem == 0) {
                    underlyingListView.getChildAt(0).setAlpha(1.1f - (float) scrollyHeader / 1700);
                }


                //listview 联动
                if (underlyingListView.getChildAt(1) == null){
                    return;
                }

                View itemUnderlying = underlyingListView.getChildAt(1);

                int scrolly = -itemUnderlying.getTop() + underlyingListView.getPaddingTop() +
                        underlyingListView.getFirstVisiblePosition() * itemUnderlying.getHeight();

                upperListView.setSelectionFromTop(0, -(int) (scrolly * 1.1));



                //弹出功能栏
                int height = functionAutoRelativeLayout.getScrollY();

                if (firstVisibleItem == 1 && isPopUp == false){
                    functionAutoRelativeLayout.setVisibility(View.GONE);
                    TranslateAnimation translateAnimation = new TranslateAnimation(0,-1500,height,height);
                    translateAnimation.setDuration(500);
                    functionAutoRelativeLayout.setAnimation(translateAnimation);
                    isPopUp = true;
                }else if (firstVisibleItem > 1 && isPopUp == true){
                    functionAutoRelativeLayout.setVisibility(View.VISIBLE);
                    TranslateAnimation translateAnimation = new TranslateAnimation(-1500,0,height,height);
                    translateAnimation.setDuration(500);
                    functionAutoRelativeLayout.setAnimation(translateAnimation);
                    isPopUp = false;
                }

            }
        });

    }

    @Override
    public void initData() {
        //获得传入id 对应数据
        final int id = getIntent().getIntExtra("position", -1);

        //获取网络数据
        netTool = new NetTool();

        HashMap<String, String> map = new HashMap<>();
        map.put("device_type", "1");
        map.put("version", "1.0.1");
        netTool.getNet(new NetListener() {
            @Override
            public void onSuccessed(String result) {

                Gson gson = new Gson();
                Type type = new TypeToken<GlassDetailsBean>() {
                }.getType();

                glassDetailsBean = gson.fromJson(result, type);

                //获取背景图
                ImageLoader loader = VolleySingleton.getInstance().getImageLoader();
                loader.get(glassDetailsBean.getData().getList().get(id).getData_info().getGoods_img(),
                        ImageLoader.getImageListener(backgroundView, R.mipmap.null_state, R.mipmap.null_state));

                //向adapter中添加数据
                underlyingAdapter.setDataInfoBean(glassDetailsBean.getData().getList().get(id).getData_info());

                underlyingListView.setAdapter(underlyingAdapter);

                Log.d("-=-=-=-=-=-=", "**" + glassDetailsBean.getData().getList().get(id).getData_info().getGoods_data().size());

                upperAdapter.setDataInfoBean(glassDetailsBean.getData().getList().get(id).getData_info());


                upperListView.setAdapter(upperAdapter);

            }

            @Override
            public void onFailed(VolleyError error) {

            }
        }, map, URIValues.GLASS_DETAILS);
        SharedPreferences sp = getSharedPreferences("isLogin", MODE_PRIVATE);
        flag = sp.getBoolean("login", false);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        Bundle bundle;
        switch (v.getId()) {
            case R.id.tv_wear_glass_details:
                ArrayList<GlassDetailsBean.DataBean.ListBean.DataInfoBean.WearVideoBean> wearVideoBean =
                        new ArrayList<>();
                Log.d("-=-=-=", "-=-=-" + underlyingAdapter.getDataInfoBean().getGoods_name());
                wearVideoBean.addAll(underlyingAdapter.getDataInfoBean().getWear_video());
                intent = new Intent(this, WearTheAtlasActivity.class);
                bundle = new Bundle();
                bundle.putParcelableArrayList("wear_video", wearVideoBean);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.tv_buy_glass_details:
                if (NetHelper.isHaveInternet(this) == true) {
                    if (flag == false) {
                        intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent buy = new Intent(this, OrderDetailsActivity.class);
                        startActivity(buy);
                        finish();
                    }
                }else {
                    Toast.makeText(this, "訂單失敗,請檢查網絡", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.iv_back_glass_details:
                Toast.makeText(this, "返回", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    private void showShare() {

        Log.d("++++++++", url);

        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.app_name));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(titleUrl);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("愁一愁,瞧一瞧");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);

// 启动分享GUI
        oks.show(this);
    }
}
