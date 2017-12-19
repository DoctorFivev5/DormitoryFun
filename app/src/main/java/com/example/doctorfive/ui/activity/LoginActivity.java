package com.example.doctorfive.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctorfive.base.MyApplication;
import com.example.doctorfive.db.DBHelper;
import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.entity.User;

import static com.example.doctorfive.util.CheckoutDataUtil.isLogin;

public class LoginActivity extends Activity implements View.OnClickListener {

    private SharedPreferences pref;  //SharedPreferences的提取操作实例
    private SharedPreferences.Editor editor;  //SharedPreferences的存储操作实例
    private DBHelper myDbHelper;  //数据库操作对象
    private User myUser;   //User对象
    private Button login;  //登陆控件
    private Button register;//注册控件
    private Button findPassword;//忘记密码控件
    private TextView phoneNum;//手机号码输入框控件
    private TextView password;//密码输入框控件

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //验证信息
        setContentView(R.layout.login);
        initViews();
        autoLogin();
        showPassword();
    }


    /*
    初始化view控件
     */
    private void initViews(){
        myDbHelper = new DBHelper(this);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.registerTo);
        findPassword = (Button) findViewById(R.id.forget_pwd);
        phoneNum = (TextView) findViewById(R.id.username) ;
        password = (TextView) findViewById(R.id.password) ;
        pref = getSharedPreferences("userPwd",MODE_PRIVATE);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        findPassword.setOnClickListener(this);
    }

    /*
    检查本地是否保存有效密码
     */
    private void autoLogin(){
        Boolean isClear = pref.getBoolean("clear",false);
        String savePhoneNum = pref.getString("phoneNum","");
        String savePassword = pref.getString("password","");
        myUser = new User();
        myUser.setPhoneNum(savePhoneNum);
        myUser.setPassword(savePassword);
        if (isClear) {
            if (myDbHelper.checkHaveUser(myUser)) {
                login(myUser);
            }
        }
    }

    /*
    把保存的密码输出到文本框中
     */
    private void showPassword(){
        String saveUsername = pref.getString("phoneNum","");
        String savePassword = pref.getString("password","");
        phoneNum.setText(saveUsername);
        password.setText(savePassword);
    }

    /*
    登陆跳转至主界面
     */
    private void login(User user) {
        Intent in=new Intent(MyApplication.getContext(),MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putCharSequence("phoneNum",user.getPhoneNum());
        in.putExtras(bundle);
        startActivity(in);
        finish();
    }

    /*
    按钮的点击事件
     */
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.login:
                String  phoneNumS1 = phoneNum.getText().toString();
                String  passwordS1 = password.getText().toString();
                User user = new User();
                user.setPhoneNum(phoneNumS1);
                user.setPassword(passwordS1);
                if (isLogin(user)){
                    {
                        //执行记住密码功能
                        editor = pref.edit();
                        editor.putBoolean("clear",true);
                        editor.putString("phoneNum",phoneNumS1);
                        editor.putString("password",passwordS1);
                        editor.apply();
                        Toast.makeText(MyApplication.getContext(),"登陆成功！",Toast.LENGTH_SHORT).show();
                        login(user);
                        //传递账号信息进入用户界面
                    }
                }
                break;
            case R.id.registerTo:
                String phoneNumS2 = phoneNum.getText().toString();
                Intent in = new Intent(MyApplication.getContext(),RegisterActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putCharSequence("phoneNum",phoneNumS2);
                in.putExtras(bundle2);
                startActivity(in);
                break;
            case R.id.forget_pwd:
                String phoneNumS3 = phoneNum.getText().toString();
                Intent intent = new Intent(MyApplication.getContext(),ForgetPwdActivity.class);
                Bundle bundle3 = new Bundle();
                bundle3.putCharSequence("phoneNum",phoneNumS3);
                intent.putExtras(bundle3);
                startActivity(intent);
                break;
        }
    }
}

