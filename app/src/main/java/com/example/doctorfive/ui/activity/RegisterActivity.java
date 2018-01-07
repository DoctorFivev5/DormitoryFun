package com.example.doctorfive.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doctorfive.db.DBHelper;
import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.entity.User;
import com.example.doctorfive.util.CheckoutDataUtil;

/**
 * Created by DoctorFive on 2017/10/12.
 */

public class RegisterActivity extends AppCompatActivity {

    private EditText phoneNum;  //注册手机号输入框
    private EditText password_1  ;//密码输入框
    private EditText password_2;  //确认密码输入框
    private String phoneNumS;  //手机号输入框的参数
    private String password_1S;  //确认密码输入框参数
    private String password_2S;  //确认密码输入框参数
    private DBHelper myDbHelper;  //数据库操作对象
    private User myUser;

    private Intent intent;
    private Bundle bundle;
    private Button register;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        initViews();
        phoneNum.setText(bundle.getString("username"));
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumS = phoneNum.getText().toString();
                password_1S = password_1.getText().toString();
                password_2S = password_2.getText().toString();
                if (CheckoutDataUtil.isRegister(phoneNumS)) {
                    if (CheckoutDataUtil.isPasswordO(password_1S))
                        if (password_1S.equals(password_2S)) {
                            myUser.setPhoneNum(phoneNumS);
                            myUser.setPassword(password_1S);
                            myUser.setUsername("无用户名");
                            myUser.setEmail("无");
                            myUser.setSchool("无");
                            myUser.setSex(true);
                            myDbHelper.insert(myUser);
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });




    }

    public void initViews(){
        myDbHelper = new DBHelper(this);
        phoneNum = (EditText) findViewById(R.id.phoneNum);
        password_1 = (EditText) findViewById(R.id.password_1);
        password_2 = (EditText) findViewById(R.id.password_2);
        intent = getIntent();
        bundle = intent.getExtras();
        register = (Button) findViewById(R.id.register);
        myUser = new User();
    }


}
