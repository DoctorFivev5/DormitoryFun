package com.example.doctorfive.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctorfive.base.MyApplication;
import com.example.doctorfive.dormitoryfun.R;

public class SettingActivity extends AppCompatActivity {

    private SharedPreferences pref;  //SharedPreferences的提取操作实例
    private SharedPreferences.Editor editor;  //SharedPreferences的存储操作实例
    private View returntext;
    private View logon;  //退出登录
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        returntext = findViewById(R.id.returntext);
        returntext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        logon = findViewById(R.id.logon);
        logon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor = pref.edit();
                editor.putBoolean("clear",false);
                editor.putString("phoneNum","");
                editor.putString("password","");
                editor.apply();
                Intent in = new Intent(SettingActivity.this,LoginActivity.class);
                startActivity(in);
                finish();
                Toast.makeText(MyApplication.getContext(),"登出成功！",Toast.LENGTH_SHORT).show();
            }
        });
        pref = getSharedPreferences("userPwd",MODE_PRIVATE);
    }
}
