package com.mirror.mirrortry.main.shoppingcart;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseFragment;
import com.mirror.mirrortry.list.ListActivity;
import com.zhy.autolayout.AutoRelativeLayout;

/**
 * Created by dllo on 16/6/24.
 */
public class ShoppingFragment extends BaseFragment implements View.OnClickListener {
    private TextView title;

    @Override
    public int setLayout() {
        return R.layout.fragment_all_kind;
    }

    @Override
    public void initView(View view) {
        title = findView(R.id.tv_title, view);
        title.setText("我的購物車");
//        findView(R.id.rl_shopping_title,view).setOnClickListener(this);
        findView(R.id.rl_title, view).setOnClickListener(this);
        findView(R.id.pb_all_kind, view).setVisibility(View.GONE);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, ListActivity.class);
        intent.putExtra("position", 4);
        startActivity(intent);

    }
}
