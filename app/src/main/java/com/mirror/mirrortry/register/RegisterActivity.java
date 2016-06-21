package com.mirror.mirrortry.register;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseActivity;

/**
 * Created by dllo on 16/6/21.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvCreateRegisterAccount, tvSendRegisterVerificationCode;
    private ImageView ivRegisterClose;

    @Override
    public int setLayout() {
        return R.layout.activity_register;
    }


    @Override
    public void initView() {
        tvCreateRegisterAccount = findView(R.id.tv_create_register_account);
        tvSendRegisterVerificationCode = findView(R.id.tv_send_register_verification_code);
        ivRegisterClose = findView(R.id.iv_register_close);

        tvCreateRegisterAccount.setOnClickListener(this);
        tvSendRegisterVerificationCode.setOnClickListener(this);
        ivRegisterClose.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_register_close:
                finish();
                break;
            case R.id.tv_create_register_account:

                break;
            case R.id.tv_send_register_verification_code:

                break;
            default:
                break;
        }
    }
}
