package com.example.doctorfive.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.doctorfive.dormitoryfun.R;

import static com.example.doctorfive.util.CheckoutDataUtil.isPhoneNum;

/**
 * Created by DoctorFive on 2017/10/16.
 * 忘记密码，找回密码界面
 */

public class ForgetPwdActivity extends Activity{
    private EditText phoneNum;
    private EditText stuNum;
    private EditText email;
    private Button findPwd;
    private Intent intent;
    private Bundle bundle;
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpwd);
        phoneNum = (EditText) findViewById(R.id.forget_phoneNum);
        stuNum = (EditText) findViewById(R.id.forget_stuNum);
        email = (EditText) findViewById(R.id.forget_email);
        findPwd = (Button) findViewById(R.id.findPwd);
        intent = getIntent();
        bundle = intent.getExtras();
        phoneNum.setText(bundle.getString("username"));
        findPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  phoneNumS = phoneNum.getText().toString();
                //String  stuNumS = stuNum.getText().toString();

                if (isPhoneNum(phoneNumS)){
                    finish();
                }
            }
        });
    }
}
