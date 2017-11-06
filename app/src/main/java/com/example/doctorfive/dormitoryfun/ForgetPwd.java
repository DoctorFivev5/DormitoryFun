package com.example.doctorfive.dormitoryfun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

/**
 * Created by DoctorFive on 2017/10/16.
 */

public class ForgetPwd extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpwd);

        final EditText phoneNum = (EditText) findViewById(R.id.forget_phoneNum);
        final EditText stuNum = (EditText) findViewById(R.id.forget_stuNum);
        final EditText email = (EditText) findViewById(R.id.forget_email);
        final Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        phoneNum.setText(bundle.getString("username"));
    }
}
