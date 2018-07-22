package com.example.doctorfive.ui.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctorfive.adapter.DormitoryRecyclerAdapter;
import com.example.doctorfive.base.MyApplication;
import com.example.doctorfive.db.DBHelper;
import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.entity.DormitoryItem;
import com.example.doctorfive.entity.User;

import java.util.List;


public class JoinDormitoryActivity extends AppCompatActivity {

    private TextView returnButton;
    private RecyclerView dormitoryRecycler;
    private DormitoryRecyclerAdapter dormitoryRecyclerAdapter;
    private List<DormitoryItem> dormitoryItems;
    private User myUser;
    private DBHelper myDBHelper;
    private DBHelper.DBListener myDBListener;
    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    Toast.makeText(JoinDormitoryActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    dormitoryRecyclerAdapter = new DormitoryRecyclerAdapter(dormitoryItems, JoinDormitoryActivity.this, myUser);

                    //LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    dormitoryRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    dormitoryRecycler.setAdapter(dormitoryRecyclerAdapter);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_dormitory);
        initView();
    }

    private void initView() {
        myUser = (User) getIntent().getSerializableExtra("myUser");
        returnButton = findViewById(R.id.join_dormitory_left_text);
        dormitoryRecycler = findViewById(R.id.dormitory_recyclerview);
        myDBListener = new DBHelper.DBListener() {
            @Override
            public void doNetRequestChange(Object object) {
                if (object == null){
                    Message message = new Message();
                    message.what = 0;
                    myHandler.sendMessage(message);
                }else {
                    dormitoryItems = (List<DormitoryItem>) object;
                    Message message = new Message();
                    message.what = 1;
                    myHandler.sendMessage(message);
                }
            }
        };
        myDBHelper = new DBHelper(this, myDBListener);
        myDBHelper.okhttpQueryDormitory();

    }



}
