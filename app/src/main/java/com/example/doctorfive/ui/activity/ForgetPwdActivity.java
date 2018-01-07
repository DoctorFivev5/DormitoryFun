package com.example.doctorfive.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doctorfive.base.MyApplication;
import com.example.doctorfive.db.DBHelper;
import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.entity.User;

import static com.example.doctorfive.util.CheckoutDataUtil.isPhoneNum;

/**
 * Created by DoctorFive on 2017/10/16.
 * 忘记密码，找回密码界面
 */

public class ForgetPwdActivity extends Activity{
    private EditText phoneNum;
    private EditText stuNum;
    //private EditText email;
    private Button findPwd;
    private Intent intent;
    private Bundle bundle;
    private DBHelper myDBHelper;
    private User myUser;
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpwd);
        phoneNum = (EditText) findViewById(R.id.forget_phoneNum);
        stuNum = (EditText) findViewById(R.id.forget_stuNum);
        //email = (EditText) findViewById(R.id.forget_email);
        findPwd = (Button) findViewById(R.id.findPwd);
        myDBHelper = new DBHelper(this);
        myUser = new User();
        intent = getIntent();
        bundle = intent.getExtras();
        phoneNum.setText(bundle.getString("username"));
        findPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  phoneNumS = phoneNum.getText().toString();
                String  stuNumS = stuNum.getText().toString();
                if (isPhoneNum(phoneNumS)){
                    myUser.setPhoneNum(phoneNumS);
                    myUser.setStuNum(stuNumS);
                    String password = myDBHelper.forgetPassword(myUser);
                    if (password==null)
                        Toast.makeText(MyApplication.getContext(),"学号与手机号不匹配",Toast.LENGTH_SHORT).show();
                    else{
                        Toast.makeText(MyApplication.getContext(),"您的密码为："+password,Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }
            }
        });
    }
}
