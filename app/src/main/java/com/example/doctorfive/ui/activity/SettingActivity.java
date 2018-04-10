package com.example.doctorfive.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.doctorfive.base.ActivityManager;
import com.example.doctorfive.base.BaseActivity;
import com.example.doctorfive.base.MyApplication;
import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.entity.User;
import com.kyleduo.switchbutton.SwitchButton;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private SharedPreferences loginPref;  //保存本地密码SharedPreferences读取对象
    private SharedPreferences.Editor loginEditor;  //保存本地密码SharedPreferences的存储操作实例
    private SharedPreferences coursePref;           //课程表设置SharedPreferences读取对象
    private SharedPreferences.Editor courseEditor; //课程表设置SharedPreferences的存储操作实例
    private SwitchButton courseSet;
    private View returntext;
    private View logon;  //退出登录
    private User myUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();

    }


    private void initView() {
        Intent intent = getIntent();
        myUser = (User) intent.getSerializableExtra("myUser");
        coursePref = getSharedPreferences("courseSet", MODE_PRIVATE);
        courseEditor = coursePref.edit();

        courseSet = (SwitchButton) findViewById(R.id.course_set);
        if (coursePref.getBoolean("clear", false))
            courseSet.setChecked(true);
        courseSet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    courseEditor.putBoolean("clear", true);
                    courseEditor.putString("stuNum",myUser.getStuNum());
                    courseEditor.apply();
                }else {
                    courseEditor.putBoolean("clear", false);
                    courseEditor.putString("stuNum",myUser.getStuNum());
                    courseEditor.apply();
                }
            }
        });
        returntext = findViewById(R.id.returntext);
        returntext.setOnClickListener(this);
        logon = findViewById(R.id.logon);
        logon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.returntext:
                finish();
                break;
            case R.id.logon:
                loginPref = getSharedPreferences("userPwd",MODE_PRIVATE);
                loginEditor = loginPref.edit();
                loginEditor.putBoolean("clear",false);
                loginEditor.putString("phoneNum","");
                loginEditor.putString("password","");
                loginEditor.apply();

                Intent in = new Intent(SettingActivity.this,LoginActivity.class);
                startActivity(in);
                ActivityManager.finishAll();
                Toast.makeText(MyApplication.getContext(),"登出成功！",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
