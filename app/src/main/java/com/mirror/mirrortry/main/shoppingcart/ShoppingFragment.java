package com.mirror.mirrortry.main.shoppingcart;

import android.content.Intent;
import android.view.View;

import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseFragment;
import com.mirror.mirrortry.list.ListActivity;

/**
 * Created by dllo on 16/6/24.
 */
public class ShoppingFragment extends BaseFragment implements View.OnClickListener {
    @Override
    public int setLayout() {
        return R.layout.fragment_shopping_cart;
    }

    @Override
    public void initView(View view) {
        findView(R.id.rl_shopping_title,view).setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, ListActivity.class);
        intent.putExtra("position",4);
        startActivity(intent);
        getActivity().finish();
    }
}
