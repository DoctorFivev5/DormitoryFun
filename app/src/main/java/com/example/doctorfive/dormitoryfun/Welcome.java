package com.example.doctorfive.dormitoryfun;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telecom.Connection;

import org.litepal.tablemanager.Connector;

/**
 * Created by DoctorFive on 2017/10/12.
 */

public class Welcome extends Activity {
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
            Intent in=new Intent(Welcome.this,Go.class);
            startActivity(in);
            finish();
            editor.putBoolean("isfer", false);
            editor.commit();
        }else{
            //第二次进入跳转
            Intent in=new Intent(Welcome.this,Login.class);
            startActivity(in);
            finish();
            }

    }
}
