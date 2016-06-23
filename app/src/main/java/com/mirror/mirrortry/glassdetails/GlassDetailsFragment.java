package com.mirror.mirrortry.glassdetails;

import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseFragment;

/**
 * Created by dllo on 16/6/22.
 */
public class GlassDetailsFragment extends BaseFragment {
    //底层listview
    private ListView underlyingListView;
    //上层listview
    private ListView upperListView;
    //滑动判断
    private boolean scrollFlg;
    //位置记录
    private int lastVisibleItemPosition;
    //adapter
    //底层
    private UnderlyingAdapter underlyingAdapter;
    //上层
    private UpperAdapter upperAdapter;

    @Override
    public int setLayout() {
        return R.layout.double_listview;
    }

    @Override
    public void initView(View view) {
        underlyingListView = (ListView) view.findViewById(R.id.firstLV);
        upperListView = (ListView) view.findViewById(R.id.secendLV);

        underlyingAdapter = new UnderlyingAdapter(getContext());
        upperAdapter = new UpperAdapter(getContext());

        underlyingListView.setAdapter(underlyingAdapter);
        upperListView.setAdapter(upperAdapter);

        //底层获取焦点
        underlyingListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return underlyingListView.dispatchTouchEvent(event);
            }
        });

        //设置上层listview随底层listview滑动而滑动
        underlyingListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    //静止时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE :
                        scrollFlg = false;
                        break;
                    //触摸时
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL :
                        scrollFlg = true;
                        break;
                    //放开时
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING :
                        scrollFlg = true;
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (scrollFlg = true){
                        //向上滑动
                        if (firstVisibleItem > lastVisibleItemPosition){
                            upperListView.setSelectionFromTop(0,-20);
                        }
                        //向下滑动
                        if (firstVisibleItem < lastVisibleItemPosition){
                            upperListView.setSelectionFromTop(0,20);
                        }
                    }

                lastVisibleItemPosition = firstVisibleItem;
                //底层listview滑动时 上层listview滑动
//                upperListView.set
            }
        });
    }

    @Override
    public void initData() {

    }
}
