package com.example.doctorfive.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.doctorfive.base.BaseActivity;
import com.example.doctorfive.db.DBHelper;
import com.example.doctorfive.entity.Student;

//import org.litepal.tablemanager.Connector;

/**
 * Created by DoctorFive on 2017/10/12.
 * 判断是否是app是否第一次运行
 */

public class CheckFirstOpen extends BaseActivity {

    private SharedPreferences isFristOpenPref;
    private SharedPreferences.Editor firstOpenEditor;
    private SharedPreferences courseSetPref;
    private SharedPreferences.Editor courseSetEditor;
    private DBHelper myDBHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        firstCome();

    }

    private void initData() {
        myDBHelper = new DBHelper(this);
        isFristOpenPref = getSharedPreferences("is", MODE_PRIVATE);
        firstOpenEditor = isFristOpenPref.edit();
        courseSetPref = getSharedPreferences("courseSet", MODE_PRIVATE);
        courseSetEditor = courseSetPref.edit();
    }

    private void firstCome() {
        boolean isfer = isFristOpenPref.getBoolean("isfer", true);
        Intent in;
        if(isfer){
            //初始化每日课程表显示设置

            courseSetEditor.putBoolean("clear", false);
            courseSetEditor.putString("stuNum","0");
            courseSetEditor.apply();

            //第一次进入跳转
            firstOpenEditor.putBoolean("isfer", false);
            firstOpenEditor.apply();
            in=new Intent(CheckFirstOpen.this,FirstStartShowActivity.class);
            startActivity(in);
            finish();

        }else {
            //第二次进入跳转

            if (courseSetPref.getBoolean("clear", false)) {
                //String phoneNum = courseSetPref.getString("phoneNum","");
                String stuNum = courseSetPref.getString("stuNum", "");
                Student student = new Student();
                student.setStuNum(stuNum);
                student = myDBHelper.export(student);
                if (student != null) {
                    in = new Intent(CheckFirstOpen.this, DayCourseActivity.class);
                    startActivity(in);
                    finish();
                }else {
                    in = new Intent(CheckFirstOpen.this, LoginActivity.class);
                    startActivity(in);
                    finish();
                }

            }else {
                in = new Intent(CheckFirstOpen.this, LoginActivity.class);
                startActivity(in);
                finish();
            }

        }

    }
}
