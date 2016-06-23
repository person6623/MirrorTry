package com.mirror.mirrortry.main.specialtoshare.content;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseFragment;
import com.mirror.mirrortry.main.specialtoshare.SpecialToShareBean;

/**
 * Created by dllo on 16/6/22.
 */
public class SpecialToShareContentFragment extends BaseFragment {
    private RelativeLayout rlStyleTwo, rlStyleThree, rlStyleOne, rlStyleFour, rlStyleFive;
    private TextView tvSmallTitleStyleTwo, tvSubStyleTwo, tvSubStyleThree, tvTitleStyleThree,
            tvSmallTitleStyleThree, tvVerticalTitleStyleOne, tvSubStyleOne, tvColorTitleStyleOne,
            tvTitleStyleOne, tvSmallTitleStyleOne, tvTitleStyleFive, tvSmallTitleStyleFive;

    @Override
    public int setLayout() {
        return R.layout.fragment_special_to_share_content;
    }

    @Override
    public void initView(View view) {
        //styleOne
        rlStyleOne = findView(R.id.rl_share_content_style_one, view);
        tvColorTitleStyleOne = findView(R.id.tv_share_content_color_title_style_one, view);
        tvSubStyleOne = findView(R.id.tv_share_content_sub_title_style_one, view);
        tvTitleStyleOne = findView(R.id.tv_share_content_title_style_one, view);
        tvSmallTitleStyleOne = findView(R.id.tv_share_content_small_title_style_one, view);
        tvVerticalTitleStyleOne = findView(R.id.tv_share_content_vertical_title_style_one, view);
        //styleTwo
        rlStyleTwo = findView(R.id.rl_share_content_style_two, view);
        tvSubStyleTwo = findView(R.id.tv_share_content_sub_title_style_two, view);
        tvSmallTitleStyleTwo = findView(R.id.tv_share_content_small_title_style_two, view);
        //styleThree
        rlStyleThree = findView(R.id.rl_share_content_style_three, view);
        tvSubStyleThree = findView(R.id.tv_share_content_sub_title_style_three, view);
        tvTitleStyleThree = findView(R.id.tv_share_content_title_style_three, view);
        tvSmallTitleStyleThree = findView(R.id.tv_share_content_small_title_style_three, view);
        //styleFour
        rlStyleFour = findView(R.id.rl_share_content_type_four, view);
        //styleFive
        rlStyleFive = findView(R.id.rl_share_content_style_five, view);
        tvTitleStyleFive = findView(R.id.tv_share_content_title_style_five, view);
        tvSmallTitleStyleFive = findView(R.id.tv_share_content_small_title_style_five, view);
    }

    @Override
    public void initData() {

    }

    //fragment里被调用的组件初始化方法
    public void initComponent(SpecialToShareBean.DataBean.ListBean.StoryDataBean.TextArrayBean textArrayBean) {
        switch (textArrayBean.getCategory()) {
            case "styleOne":

                break;
            case "styleTwo":
                break;
            case "styleThree":
                break;
            case "styleFour":
                break;
            case "styleFive":
                break;
        }
    }
}
