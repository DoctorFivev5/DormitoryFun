package com.example.doctorfive.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

//import org.litepal.tablemanager.Connector;

/**
 * Created by DoctorFive on 2017/10/12.
 * 判断是否是app是否第一次运行
 */

public class CheckFirstOpen extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firstCome();
    }

    private void firstCome() {
        SharedPreferences shared=getSharedPreferences("is", MODE_PRIVATE);
        boolean isfer=shared.getBoolean("isfer", true);
        SharedPreferences.Editor editor=shared.edit();
        if(isfer){
            //第一次进入跳转
            editor.putBoolean("isfer", false);
            editor.commit();
            Intent in=new Intent(CheckFirstOpen.this,FirstOpen.class);
            startActivity(in);
            finish();

        }else{
            //第二次进入跳转
            Intent in=new Intent(CheckFirstOpen.this,LoginActivity.class);
            startActivity(in);
            finish();
            }

    }
}
