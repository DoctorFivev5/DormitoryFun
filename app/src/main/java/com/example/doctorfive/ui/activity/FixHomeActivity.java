package com.example.doctorfive.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.doctorfive.base.MyApplication;
import com.example.doctorfive.dormitoryfun.R;

public class FixHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_home);
        View returntext = findViewById(R.id.returntext);
        returntext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button  button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MyApplication.getContext(),"提交成功！",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}