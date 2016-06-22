package com.mirror.mirrortry.register;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dllo on 16/6/21.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvCreateRegisterAccount, tvSendRegisterVerificationCode;
    private EditText etRegisterPhoneNumber, etRegisterVerificationCode, etSetRegisterPassword;
    private ImageView ivRegisterClose;
    private String num;

    @Override
    public int setLayout() {
        return R.layout.activity_register;
    }


    @Override
    public void initView() {
        tvCreateRegisterAccount = findView(R.id.tv_create_register_account);
        tvSendRegisterVerificationCode = findView(R.id.tv_send_register_verification_code);
        ivRegisterClose = findView(R.id.iv_register_close);
        etRegisterPhoneNumber = findView(R.id.et_register_phone_number);
        etRegisterVerificationCode = findView(R.id.et_register_verification_code);
        etSetRegisterPassword = findView(R.id.et_set_register_password);

        tvCreateRegisterAccount.setOnClickListener(this);
        tvSendRegisterVerificationCode.setOnClickListener(this);
        ivRegisterClose.setOnClickListener(this);

        //调用判断手机号的格式
        num = etRegisterPhoneNumber.getText().toString();
        isMobileNo(num);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

    //对手机号格式的设定
    public static boolean isMobileNo(String mobiles) {
        Pattern pattern = Pattern.compile("^[1][358][0-9]{9}$");
        //        "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，
        //        "{9}"代表后面是可以是0～9的数字，有9位。
        Matcher matcher = pattern.matcher(mobiles);
        return matcher.matches();
    }
}