package com.mirror.mirrortry.main.specialtoshare.content;

import android.content.Intent;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseActivity;
import com.mirror.mirrortry.main.MainContinueBean;
import com.mirror.mirrortry.main.specialtoshare.SpecialToShareBean;
import com.mirror.mirrortry.net.NetTool;
import com.mirror.mirrortry.net.VolleySingleton;
import com.mirror.mirrortry.orderdetails.OrderDetailsActivity;
import com.mirror.mirrortry.verticalviewpager.VerticalViewPager;

import java.util.ArrayList;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by dllo on 16/6/22.
 */
public class SpecialToShareActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    //专题传入
    private SpecialToShareBean.DataBean.ListBean listBean;
    private ArrayList<SpecialToShareBean.DataBean.ListBean.StoryDataBean.TextArrayBean> textArrayBean;
    //全部传入
    private MainContinueBean.DataBean.ListBean.DataInfoBean continueBean;
    private ArrayList<MainContinueBean.DataBean.ListBean.DataInfoBean.StoryDataBean.TextArrayBean> continueArrayBean;

    private ArrayList<String> imgArray;
    private ArrayList<Fragment> fragments;
    private VerticalViewPager viewPager;
    private ImageView ivShowPageClose, ivSpecialShare, getIvSpecialBackground;
    //viewpagerAdapter
    private SpecialToShareAdapter adapter;
    private int currentPosition;
    private ProgressBar progressBar;
    private NetTool netTool;


    @Override
    public int setLayout() {
        return R.layout.activity_special_to_share;
    }

    @Override
    public void initView() {
        ivShowPageClose = findView(R.id.iv_show_page_close);
        ivSpecialShare = findView(R.id.iv_special_share);
        getIvSpecialBackground = findView(R.id.iv_special_background);
        progressBar = findView(R.id.pb_share_background);

        viewPager = findView(R.id.vp_special_share);
        adapter = new SpecialToShareAdapter(getSupportFragmentManager());

        ivSpecialShare.setOnClickListener(this);
        ivShowPageClose.setOnClickListener(this);
        viewPager.setOnPageChangeListener(this);


    }

    @Override
    public void initData() {
        fragments = new ArrayList<>();
        netTool = new NetTool();
        imgArray = new ArrayList<>();

        int key;

        //全部界面传入
        if (getIntent().getIntExtra("selectType", 0) == 1) {
            continueBean = getIntent().getExtras().getParcelable("continueListBean");
            continueArrayBean = getIntent().getExtras().getParcelableArrayList("textArrayBean");
//            Log.d("-=-=-=asdasd", ""+continueArrayBean.size());
//            Log.d("-=-=-=-texy", "continueArrayBean.size():" + continueArrayBean.size());
            imgArray = getIntent().getStringArrayListExtra("imgArray");


//            Log.d("////---", continueBean.getType());
//            Log.d("/*/*/*/*", continueBean.getData_info().getStory_title());
//                Log.d("....****", continueBean.getData_info().getStory_id());
//                Log.d("---****", continueBean.getData_info().getStory_url());

            for (int i = 0; i < continueArrayBean.size(); i++) {
                Bundle singleBundle = new Bundle();
                //传入判断
                key = 0;
                singleBundle.putInt("key", 0);

                SpecialToShareContentFragment specialToShareContentFragment = new SpecialToShareContentFragment();
                MainContinueBean.DataBean.ListBean.DataInfoBean.StoryDataBean.TextArrayBean continueArray = continueArrayBean.get(i);
                singleBundle.putParcelable("continueArray", continueArray);
                specialToShareContentFragment.setArguments(singleBundle);
                fragments.add(specialToShareContentFragment);


            }
        } else {
            //专题界面传入
            listBean = getIntent().getExtras().getParcelable("listBean");
            textArrayBean = getIntent().getExtras().getParcelableArrayList("textArrayBean");
            imgArray = getIntent().getStringArrayListExtra("imgArray");

            for (int i = 0; i < textArrayBean.size(); i++) {
                Bundle singleBundle = new Bundle();

                key = 1;
                singleBundle.putInt("key", 1);

                SpecialToShareContentFragment specialToShareContentFragment = new SpecialToShareContentFragment();
                SpecialToShareBean.DataBean.ListBean.StoryDataBean.TextArrayBean singleTextArrayBean = textArrayBean.get(i);
                singleBundle.putParcelable("singleTextArrayBean", singleTextArrayBean);
                specialToShareContentFragment.setArguments(singleBundle);
                fragments.add(specialToShareContentFragment);
            }
        }


        //viewpager加入fragment
        adapter.setFragments(fragments);
        viewPager.setAdapter(adapter);
        initPhoto(0);
    }

    //换页监听
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        initPhoto(position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {


    }

    public void initPhoto(int position) {
        currentPosition = position;
        progressBar.setVisibility(View.VISIBLE);
        netTool.getImageLoaderNet(imgArray.get(currentPosition), getIvSpecialBackground, progressBar);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_show_page_close:
                finish();
                break;
            case R.id.iv_special_share:
                showShare();
//                Toast.makeText(this, "你想分享吗", Toast.LENGTH_SHORT).show();

                break;
            default:
                break;
        }
    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.app_name));


        if (getIntent().getIntExtra("selectType", 0) == 1) {
            // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
            oks.setTitleUrl(continueBean.getStory_url());

            // text是分享文本，所有平台都需要这个字段
            oks.setText(continueBean.getStory_title() + continueBean.getStory_url() + "(来自@MIRROR陈列馆)");
            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
            oks.setImageUrl(continueBean.getStory_img());//确保SDcard下面存在此张图片

            // url仅在微信（包括好友和朋友圈）中使用
            oks.setUrl(continueBean.getStory_url());

            // comment是我对这条分享的评论，仅在人人网和QQ空间使用
            oks.setComment("愁一愁,瞧一瞧");
            // site是分享此内容的网站名称，仅在QQ空间使用
            oks.setSite(getString(R.string.app_name));

            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            oks.setSiteUrl(continueBean.getStory_url());

// 启动分享GUI
            oks.show(this);

        } else {

            // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
            oks.setTitleUrl(listBean.getStory_url());

            // text是分享文本，所有平台都需要这个字段
            oks.setText(listBean.getStory_title() + listBean.getStory_url() + "(来自@MIRROR陈列馆)");
            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
            oks.setImageUrl(listBean.getStory_img());//确保SDcard下面存在此张图片

            // url仅在微信（包括好友和朋友圈）中使用
            oks.setUrl(listBean.getStory_url());

            // comment是我对这条分享的评论，仅在人人网和QQ空间使用
            oks.setComment("愁一愁,瞧一瞧");
            // site是分享此内容的网站名称，仅在QQ空间使用
            oks.setSite(getString(R.string.app_name));

            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            oks.setSiteUrl(listBean.getStory_url());

// 启动分享GUI
            oks.show(this);
        }
    }
}
