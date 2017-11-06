package com.example.doctorfive.dormitoryfun;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

import static com.example.doctorfive.dormitoryfun.Checkout.isMobileNO;

public class Login extends Activity {



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button login = (Button) findViewById(R.id.login);
        Button register = (Button) findViewById(R.id.registerTo);
        Button findPassword = (Button) findViewById(R.id.forget_pwd);
        final TextView username = (TextView) findViewById(R.id.username) ;
        final TextView password = (TextView) findViewById(R.id.password) ;

        //Connector.getDatabase();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameS = username.getText().toString();
                String passwordS = password.getText().toString();
                if (isMobileNO(usernameS)){
                    List<User> users1 = DataSupport.where("phoneNum==?",usernameS).find(User.class);
                    if (users1.isEmpty()){
                        Toast.makeText(Login.this,"该手机号没有注册",Toast.LENGTH_SHORT).show();
                    }else {
                        List<User> users2 = DataSupport.where("phoneNum==? and password==?",usernameS, passwordS).find(User.class);
                        if (users2.isEmpty()){
                            Toast.makeText(Login.this,"密码错误",Toast.LENGTH_SHORT).show();
                        }else {
                            //登录成功--跳转！*****************************************************************
                            //Toast.makeText(Login.this,"终于登陆成功了！！！完全ojbk！！！",Toast.LENGTH_SHORT).show();
                            Intent in=new Intent(Login.this,Test.class);
                            Bundle bundle = new Bundle();
                            bundle.putCharSequence("username",usernameS);
                            in.putExtras(bundle);
                            startActivity(in,savedInstanceState);
                        }
                        /*
                        List<User> users3 = DataSupport.where("phoneNum==? ",usernameS).find(User.class);
                        for (User user :users3) {
                            Log.d("Login", "手机号：" + user.getPhoneNum());
                            Log.d("Login", "密码：" + user.getPassword());
                            Log.d("Login","性别："+user.getSex());
                        }
                        */
                    }
                }

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameS = username.getText().toString();
                Intent in=new Intent(Login.this,Register.class);
                Bundle bundle = new Bundle();
                bundle.putCharSequence("username",usernameS);
                in.putExtras(bundle);
                startActivity(in,savedInstanceState);

            }
        });

        findPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameS = username.getText().toString();
                Intent intent = new Intent(MyApplication.getContext(),ForgetPwd.class);
                Bundle bundle = new Bundle();
                bundle.putCharSequence("username",usernameS);
                intent.putExtras(bundle);
                startActivity(intent,savedInstanceState);
            }
        });


    }
}
