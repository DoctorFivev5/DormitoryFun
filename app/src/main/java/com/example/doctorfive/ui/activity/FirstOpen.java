package com.example.doctorfive.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.doctorfive.dormitoryfun.R;

/**
 * Created by DoctorFive on 2017/10/12.
 * 第一次打开界面
 */

public class FirstOpen extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstopen);

        Button login = (Button) findViewById(R.id.go);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(FirstOpen.this,LoginActivity.class);
                Bundle bundle = new Bundle();
                in.putExtras(bundle);
                startActivity(in);
                finish();
            }
        });

        Button register = (Button) findViewById(R.id.go1);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in1 = new Intent(FirstOpen.this,RegisterActivity.class);
                Bundle bundle = new Bundle();
                in1.putExtras(bundle);
                startActivity(in1);
            }
        });
    }
}
