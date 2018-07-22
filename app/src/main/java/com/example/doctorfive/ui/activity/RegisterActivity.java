package com.example.doctorfive.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doctorfive.base.BaseActivity;
import com.example.doctorfive.db.DBHelper;
import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.entity.Pwd;
import com.example.doctorfive.entity.User;
import com.example.doctorfive.util.CheckoutDataUtil;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

/**
 * 注册页面
 * Created by DoctorFive on 2017/10/12.
 */

public class RegisterActivity extends BaseActivity {

    private static final int REGISTERSECCESS = 111;
    private static final int REGISTERFAIL = 000;
    private EditText phoneNum;  //注册手机号输入框
    private EditText password_1  ;//密码输入框
    private EditText password_2;  //确认密码输入框
    private String phoneNumS;  //手机号输入框的参数
    private String password_1S;  //确认密码输入框参数
    private String password_2S;  //确认密码输入框参数
    private DBHelper myDbHelper;  //数据库操作对象
    private DBHelper.DBListener myDBListener;
    private User myUser;

    private Intent intent;
    private Bundle bundle;
    private Button register;
    private LoadingDialog dialog;
    @SuppressLint("HandlerLeak")
    Handler myHandler;

    {
        myHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case REGISTERSECCESS:
                        dialog.loadSuccess();
                        Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                        break;
                    case REGISTERFAIL:
                        dialog.loadFailed();
                        initDialog();
                        //dialog.close();
                        break;
                }
            }
        };
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        initViews();
    }

    public void initViews(){
        initDialog();


        myDBListener = new DBHelper.DBListener() {
            @Override
            public void doNetRequestChange(Object object) {
                if (object==null){
                    //注册失败
                    Message msg = new Message();
                    msg.what = REGISTERFAIL;
                    myHandler.sendMessage(msg);
                }else {
                    //注册成功
                    Message msg = new Message();
                    msg.what = REGISTERSECCESS;
                    myHandler.sendMessage(msg);
                    finish();
                }
            }
        };
        myDbHelper = new DBHelper(this,myDBListener);
        phoneNum = (EditText) findViewById(R.id.phoneNum);
        password_1 = (EditText) findViewById(R.id.password_1);
        password_2 = (EditText) findViewById(R.id.password_2);
        intent = getIntent();
        bundle = intent.getExtras();
        register = (Button) findViewById(R.id.register);
        myUser = new User();
        phoneNum.setText(bundle.getString("username"));

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumS = phoneNum.getText().toString();
                password_1S = password_1.getText().toString();
                password_2S = password_2.getText().toString();
                if (CheckoutDataUtil.isPhoneNum(phoneNumS)){
                    if (CheckoutDataUtil.isPasswordStyle(password_1S)){
                        if (password_1S.equals(password_2S)) {
                            dialog.show();
                            //发送请求
                            myDbHelper.register(new Pwd(phoneNumS, password_2S));
                        } else {
                            Toast.makeText(RegisterActivity.this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(RegisterActivity.this, "请输入9位以上的密码！", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });




    }
    private void initDialog() {
        dialog= new LoadingDialog(this);
        dialog.setLoadingText("")
                .setSuccessText("注册成功")
                .setFailedText("帐号已存在")
                .closeFailedAnim()
                .setInterceptBack(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog!=null){
            dialog.close();
        }
    }

}
