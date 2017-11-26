package com.example.doctorfive.dormitoryfun;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctorfive.dbhelp.DBHelper;

import static com.example.doctorfive.dormitoryfun.Checkout.isMobileNO;

public class Login_1 extends Activity {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //验证信息
            pref = getSharedPreferences("userPwd",MODE_PRIVATE);
            Boolean isClear = pref.getBoolean("clear",false);
            if (isClear){
                String saveUsername = pref.getString("username","");
                String savePassword = pref.getString("password","");
                login(saveUsername,savedInstanceState);
            }

            setContentView(R.layout.login);
            dbHelper = new DBHelper(this);
            Button login = (Button) findViewById(R.id.login);
            Button register = (Button) findViewById(R.id.registerTo);
            Button findPassword = (Button) findViewById(R.id.forget_pwd);
            final TextView username = (TextView) findViewById(R.id.username) ;
            final TextView password = (TextView) findViewById(R.id.password) ;

            //Connector.getDatabase();

            //填入已保存的账号密码
            String saveUsername = pref.getString("username","");
            String savePassword = pref.getString("password","");
            username.setText(saveUsername);
            password.setText(savePassword);


            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String  usernameS = username.getText().toString();
                    String  passwordS = password.getText().toString();
                    if (isMobileNO(usernameS)){

                            //int usernameS = Integer.valueOf(usernameSS.trim());//抛出了numberformatexception异常
                        SQLiteDatabase db = dbHelper.getDb();
                        //Cursor cursor = db.query("Student", "phoneNum", usernameS + "=userName", null,null,null,null);
                        //查询项必须是数组
                        Cursor cursor = db.rawQuery("select phoneNum from Student where phoneNum='usernameS'", null);

                        if (cursor.getCount()==0){
                            Toast.makeText(MyApplication.getContext(),"该手机号没有注册",Toast.LENGTH_SHORT).show();
                        }else {
                            Cursor cursor1 = db.rawQuery("select phoneNum, password from Student where phoneNum=usernameS and password=passwordS", null);
                            if (cursor1.getCount()==0){
                                Toast.makeText(MyApplication.getContext(),"密码错误",Toast.LENGTH_SHORT).show();
                            }else {
                                //登录成功--跳转！*****************************************************************
                                //Toast.makeText(Login_1.this,"终于登陆成功了！！！完全ojbk！！！",Toast.LENGTH_SHORT).show();

                                //执行记住密码功能
                                editor = pref.edit();
                                editor.putBoolean("clear",true);
                                editor.putString("username",usernameS);
                                editor.putString("password",passwordS);
                                editor.apply();
                                Toast.makeText(MyApplication.getContext(),"登陆成功！",Toast.LENGTH_SHORT).show();
                                login(usernameS,savedInstanceState);
                                //传递账号信息进入用户界面

                            }


                        }
                    }

                }
            });

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String usernameS = username.getText().toString();
                    Intent in=new Intent(MyApplication.getContext(),Register.class);
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

    private void login(String usernameS,Bundle savedInstanceState) {
        Intent in=new Intent(MyApplication.getContext(),ManterialTest.class);
        Bundle bundle = new Bundle();
        bundle.putCharSequence("username",usernameS);
        in.putExtras(bundle);
        startActivity(in,savedInstanceState);
        finish();
    }
}

