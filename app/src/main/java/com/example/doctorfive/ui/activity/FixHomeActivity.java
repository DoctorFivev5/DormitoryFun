package com.example.doctorfive.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.doctorfive.base.BaseActivity;
import com.example.doctorfive.base.MyApplication;
import com.example.doctorfive.db.DBHelper;
import com.example.doctorfive.dormitoryfun.R;

/**
 * 报修管理界面
 */
public class FixHomeActivity extends BaseActivity {

    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_home);
        dbHelper = new DBHelper(this);
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
                dbHelper.fixHome();
                finish();
            }
        });
    }
}
