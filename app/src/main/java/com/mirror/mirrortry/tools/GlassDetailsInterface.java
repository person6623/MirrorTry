package com.mirror.mirrortry.tools;

import com.mirror.mirrortry.main.MainBean;
import com.mirror.mirrortry.main.MainContinueBean;

import java.util.List;

/**
 * Created by dllo on 16/6/23.
 */
public interface GlassDetailsInterface {
    void onGlassClick(int position, List<MainBean.DataBean.ListBean> listBeen, List<MainContinueBean.DataBean.ListBean> continueListBean);
}
