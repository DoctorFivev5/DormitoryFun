package com.example.doctorfive.dormitoryfun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by DoctorFive on 2017/10/12.
 */

public class Go extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //验证用户信息
        //载入数据库
        setContentView(R.layout.go);

        Button login = (Button) findViewById(R.id.go);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Go.this,Login_1.class);
                Bundle bundle = new Bundle();
                in.putExtras(bundle);
                startActivity(in);
                finish();
            }//不能跳转！！！！！！！！！！！！！！！！！！！！！！！！！！！
        });

        Button register = (Button) findViewById(R.id.go1);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in1 = new Intent(Go.this,Register.class);
                Bundle bundle = new Bundle();
                in1.putExtras(bundle);
                startActivity(in1);
                finish();//不能跳转！！！！！！！！！！！！！！！！！！！！！！！！！！！
            }
        });
    }
}
