package com.mirror.mirrortry.glassdetails;

import android.util.Log;
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

                View itemUnderlying = underlyingListView.getChildAt(0);
                if (itemUnderlying == null) {
                    return;
                }
                //测算实时滑动距离
                //assuming all list items have same height
                int scrolly = -itemUnderlying.getTop() + underlyingListView.getPaddingTop() +
                        underlyingListView.getFirstVisiblePosition() * itemUnderlying.getHeight();
                upperListView.scrollTo(0, (int) (scrolly * 1.5));

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    public void initData() {

    }
}
