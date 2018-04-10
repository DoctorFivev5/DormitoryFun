package com.example.doctorfive.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.doctorfive.base.BaseActivity;
import com.example.doctorfive.base.MyApplication;
import com.example.doctorfive.db.DBHelper;
import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.entity.Pwd;
import com.example.doctorfive.entity.User;
import com.example.doctorfive.util.CheckoutDataUtil;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;


public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private SharedPreferences loginPref;  //保存本地密码SharedPreferences读取对象
    private SharedPreferences.Editor loginEditor;  //SharedPreferences的存储操作对象
    private DBHelper myDbHelper;  //数据库操作对象
    private User myUser;   //User对象
    private Button login;  //登陆控件
    private Button register;//注册控件
    private Button findPassword;//忘记密码控件
    private TextView phoneNum;//手机号码输入框控件
    private TextView password;//密码输入框控件
    private LoadingDialog dialog;
    private DBHelper.DBListener myDBListener;
    private static final int LODINGFAILED = 1;
    private static final int LODINGSUCEESE = 2;
    private static final int LODINGCLOSE = 3;
    Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case LODINGFAILED:
                    dialog.loadFailed();
                    initDialog();
                    break;
                case LODINGSUCEESE:
                    dialog.loadSuccess();
                    //dialog.close();
                    break;
                case LODINGCLOSE:
                    dialog.close();
                    break;
            }
        }
    };

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
        initDialog();
        //dialog.show();
        myDBListener = new DBHelper.DBListener() {
        @Override
        public void doNetRequestChange(User user) {
            //dialog.cancel();
            String  phoneNumS1 = phoneNum.getText().toString();
            String  passwordS1 = password.getText().toString();
            myUser = myDbHelper.getMyUser();
            Message msg = new Message();
            if (myUser==null){
                msg.what = LODINGFAILED;
                myHandler.sendMessage(msg);
                return;

            }else {
                msg.what = LODINGSUCEESE;
                myHandler.sendMessage(msg);
                //还是要用handler
                rememberPassword(phoneNumS1, passwordS1);
            }
            //添加跳转
        }
    };
        myDbHelper = new DBHelper(this, myDBListener);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.registerTo);
        findPassword = (Button) findViewById(R.id.forget_pwd);
        phoneNum = (TextView) findViewById(R.id.username) ;
        password = (TextView) findViewById(R.id.password) ;
        loginPref = getSharedPreferences("userPwd",MODE_PRIVATE);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        findPassword.setOnClickListener(this);

    }


    private void initDialog() {
        dialog= new LoadingDialog(this);
        dialog.setLoadingText("登录中...")
                .setSuccessText("登录成功")
                .setFailedText("帐号或密码错误")
                .closeFailedAnim()
                .setInterceptBack(false);
    }

    /*
    检查本地是否保存有效密码
     */
    private void autoLogin(){
        Boolean isClear = loginPref.getBoolean("clear",false);
        String savePhoneNum = loginPref.getString("phoneNum","");
        String savePassword = loginPref.getString("password","");
        Log.e("autoLogin", savePhoneNum);
        Log.e("autoLogin", savePassword);
        if (isClear) {
            dialog.show();
            myDbHelper.login(new Pwd(savePhoneNum, savePassword));
        }
    }

    /*
    把保存的密码输出到文本框中
     */
    private void showPassword(){
        String saveUsername = loginPref.getString("phoneNum","");
        String savePassword = loginPref.getString("password","");
        phoneNum.setText(saveUsername);
        password.setText(savePassword);
    }

    public void rememberPassword(String phoneNum, String password){
        loginEditor = loginPref.edit();
        loginEditor.putBoolean("clear",true);
        loginEditor.putString("phoneNum",phoneNum);
        loginEditor.putString("password",password);
        loginEditor.apply();
        //Toast.makeText(MyApplication.getContext(),"登陆成功！",Toast.LENGTH_SHORT).show();
        login(myUser);
    }

    /*
    登陆跳转至主界面
     */
    private void login(User user) {
        Intent in=new Intent(MyApplication.getContext(),MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("myUser",myUser);
        myDbHelper.insert(myUser);
        Log.e("login",myUser.printUser());
        //bundle.putCharSequence("phoneNum",user.getPhoneNum());
        in.putExtras(bundle);
        startActivity(in);
        Message msg = new Message();
        msg.what = LODINGCLOSE;
        myHandler.sendMessage(msg);
        finish();
    }
    /*
    按钮的点击事件
     */
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            //登录按钮
            case R.id.login:
                String  phoneNumS1 = phoneNum.getText().toString();
                String  passwordS1 = password.getText().toString();
                if (CheckoutDataUtil.isPhoneNum(phoneNumS1)&&CheckoutDataUtil.isPasswordStyle(passwordS1)) {
                    Pwd pwd = new Pwd(phoneNumS1,passwordS1);
                    dialog.show();
                    myDbHelper.login(pwd);
                    break;
                }else {
                    break;
                }
            //注册按钮
            case R.id.registerTo:
                String phoneNumS2 = phoneNum.getText().toString();
                Intent in = new Intent(MyApplication.getContext(),RegisterActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putCharSequence("phoneNum",phoneNumS2);
                in.putExtras(bundle2);
                startActivity(in);
                break;
            //忘记密码按钮
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog!=null){
            dialog.close();
        }
    }
}

