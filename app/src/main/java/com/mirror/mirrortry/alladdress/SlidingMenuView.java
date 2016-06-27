package com.mirror.mirrortry.alladdress;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.mirror.mirrortry.R;

/**
 * Created by dllo on 16/6/25.
 */
public class SlidingMenuView extends HorizontalScrollView {
    private int mScrollWidth;
    private boolean isOpen = false; //记录菜单的打开或者关闭
    private SlidingListener slidingListener;

    public void setSlidingListener(SlidingListener slidingListener) {
        this.slidingListener = slidingListener;
    }

    public SlidingMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //让菜单的每一个横向滚动的跟随线消失
        setHorizontalScrollBarEnabled(false);
        isOpen = false;  //默认菜单是关闭状态

    }

    //该方法是用于放置子视图的位置调用的
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        //拿到带有删除字样的textView的宽度
        TextView textView = (TextView) findViewById(R.id.tv_item_delete);
        mScrollWidth = textView.getWidth();
        changeScroll();

    }

    public void closeMenu(){
        //把滚动条滚动到0,0 位置  就相当于把他关上了
        smoothScrollTo(0,0);
        isOpen = false;

    }

    //当用户点击或者滑动等操作
    // 作用在这个View上的时候 就会回到这个方法
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case MotionEvent.ACTION_MOVE:
                //当用户在我的view上滑动的时候
                slidingListener.onMove(this);
                break;

            case MotionEvent.ACTION_UP:
                //当用户抬起手指时

                changeScroll();

                return  true;


        }


        return super.onTouchEvent(ev);
    }

    //判断菜单打开或者关闭
    private void changeScroll(){

        //如果用户滑动超过menuTV 一半  打开菜单
        //如果距离不足就关上菜单
        if (getScrollX() > mScrollWidth / 2){
            isOpen = true;
            smoothScrollTo(mScrollWidth,0);

            //把自己传给adapter   让他记录下  当前打开的这个菜单
            slidingListener.onMenuIsOpen(this);

        }else {

            closeMenu();

        }

    }

    public interface SlidingListener{
        void onMenuIsOpen(SlidingMenuView slidingMenuView);

        //当滑动的时候  把自己也传过去  方便adapter比较  是否是同一个slidingMenu
        void onMove(SlidingMenuView slidingMenuView);
    }


}
