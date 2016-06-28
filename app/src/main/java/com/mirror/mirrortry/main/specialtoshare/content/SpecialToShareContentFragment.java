package com.mirror.mirrortry.main.specialtoshare.content;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseFragment;
import com.mirror.mirrortry.main.MainContinueBean;
import com.mirror.mirrortry.main.specialtoshare.SpecialToShareBean;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/22.
 */
public class SpecialToShareContentFragment extends BaseFragment {
    private RelativeLayout rlStyleTwo, rlStyleThree, rlStyleOne, rlStyleFour, rlStyleFive;
    private TextView tvSmallTitleStyleTwo, tvSubStyleTwo, tvTitleStyleTwo, tvSubStyleThree, tvTitleStyleThree,
            tvSmallTitleStyleThree, tvVerticalTitleStyleOne, tvSubStyleOne, tvColorTitleStyleOne,
            tvTitleStyleOne, tvSmallTitleStyleOne, tvTitleStyleFive, tvSmallTitleStyleFive;
    private SpecialToShareBean.DataBean.ListBean.StoryDataBean.TextArrayBean textArrayBean;

    private MainContinueBean.DataBean.ListBean.DataInfoBean.StoryDataBean.TextArrayBean continueTextArrayBean;

    @Override
    public int setLayout() {
        return R.layout.fragment_special_to_share_content;
    }

    @Override
    public void initView(View view) {
        //styleOne
        rlStyleOne = findView(R.id.rl_share_content_style_one, view);
        tvSubStyleOne = findView(R.id.tv_share_content_sub_title_style_one, view);
        tvTitleStyleOne = findView(R.id.tv_share_content_title_style_one, view);
        tvSmallTitleStyleOne = findView(R.id.tv_share_content_small_title_style_one, view);
        tvColorTitleStyleOne = findView(R.id.tv_share_content_color_title_style_one, view);
        tvVerticalTitleStyleOne = findView(R.id.tv_share_content_vertical_title_style_one, view);
        //styleTwo
        rlStyleTwo = findView(R.id.rl_share_content_style_two, view);
        tvSubStyleTwo = findView(R.id.tv_share_content_sub_title_style_two, view);
        tvTitleStyleTwo = findView(R.id.tv_share_content_title_style_two, view);
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
        Bundle bundle = getArguments();
        if (bundle.getInt("key") == 0) {
            continueTextArrayBean = bundle.getParcelable("continueArray");
            String Category = continueTextArrayBean.getCategory();
            switch (Category) {
                case "styleOne":
                    if (continueTextArrayBean.getColorTitle() == null) {
                        rlStyleFour.setVisibility(View.VISIBLE);
                    } else {
                        rlStyleOne.setVisibility(View.VISIBLE);
                        tvColorTitleStyleOne.setText(continueTextArrayBean.getColorTitle());
                        tvSubStyleOne.setText(continueTextArrayBean.getSubTitle());
                        tvSmallTitleStyleOne.setText(continueTextArrayBean.getSmallTitle());
                        tvTitleStyleOne.setText(continueTextArrayBean.getTitle());
                        tvVerticalTitleStyleOne.setText(continueTextArrayBean.getVerticalTitle());
                    }
                    break;
                case "styleTwo":
                    Log.d("SpecialToShareContentFr", continueTextArrayBean.getSmallTitle());

                    rlStyleTwo.setVisibility(View.VISIBLE);
                    tvSmallTitleStyleTwo.setText(continueTextArrayBean.getSmallTitle());
                    tvTitleStyleTwo.setText(continueTextArrayBean.getTitle());
                    tvSubStyleTwo.setText(continueTextArrayBean.getSubTitle());
                    break;
                case "styleThree":
                    rlStyleThree.setVisibility(View.VISIBLE);
                    tvTitleStyleThree.setText(continueTextArrayBean.getTitle());
                    tvSmallTitleStyleThree.setText(continueTextArrayBean.getSmallTitle());
                    tvSubStyleThree.setText(continueTextArrayBean.getSubTitle());
                    break;
                case "styleFour":
                    break;
                case "styleFive":
                    rlStyleFive.setVisibility(View.VISIBLE);
                    tvSmallTitleStyleFive.setText(continueTextArrayBean.getSmallTitle());
                    tvTitleStyleFive.setText(continueTextArrayBean.getTitle());
                    break;
            }
        } else {
            textArrayBean = bundle.getParcelable("singleTextArrayBean");
            String Category = textArrayBean.getCategory();
            switch (Category) {
                case "styleOne":
                    if (textArrayBean.getColorTitle() == null) {
                        rlStyleFour.setVisibility(View.VISIBLE);
                    } else {
                        rlStyleOne.setVisibility(View.VISIBLE);
                        tvColorTitleStyleOne.setText(textArrayBean.getColorTitle());
                        tvSubStyleOne.setText(textArrayBean.getSubTitle());
                        tvSmallTitleStyleOne.setText(textArrayBean.getSmallTitle());
                        tvTitleStyleOne.setText(textArrayBean.getTitle());
                        tvVerticalTitleStyleOne.setText(textArrayBean.getVerticalTitle());
                    }
                    break;
                case "styleTwo":
                    Log.d("SpecialToShareContentFr", textArrayBean.getSmallTitle());

                    rlStyleTwo.setVisibility(View.VISIBLE);
                    tvSmallTitleStyleTwo.setText(textArrayBean.getSmallTitle());
                    tvTitleStyleTwo.setText(textArrayBean.getTitle());
                    tvSubStyleTwo.setText(textArrayBean.getSubTitle());
                    break;
                case "styleThree":
                    rlStyleThree.setVisibility(View.VISIBLE);
                    tvTitleStyleThree.setText(textArrayBean.getTitle());
                    tvSmallTitleStyleThree.setText(textArrayBean.getSmallTitle());
                    tvSubStyleThree.setText(textArrayBean.getSubTitle());
                    break;
                case "styleFour":
                    break;
                case "styleFive":
                    rlStyleFive.setVisibility(View.VISIBLE);
                    tvSmallTitleStyleFive.setText(textArrayBean.getSmallTitle());
                    tvTitleStyleFive.setText(textArrayBean.getTitle());
                    break;
            }
        }

    }

}
