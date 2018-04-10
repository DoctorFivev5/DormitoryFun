package com.example.doctorfive.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doctorfive.base.BaseActivity;
import com.example.doctorfive.db.DBHelper;
import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.entity.Schedule;
import com.example.doctorfive.entity.ScheduleItem;

/**
 * 日程详情页面
 */

public class ScheduleDetailActivity extends BaseActivity {
    private int myScheduleItemId;
    private Schedule mySchedule;
    private DBHelper myDBHelper;
    private ImageView imageView;
    private TextView title;
    private TextView time;
    private TextView remarks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_detail);
        initView();
        initData();
    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.schedule_detail_image);
        title = (TextView) findViewById(R.id.schedule_detail_title);
        time = (TextView) findViewById(R.id.schedule_detail_time);
        remarks = (TextView) findViewById(R.id.schedule_detail_remarks);
    }

    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        myScheduleItemId = bundle.getInt("scheduleId");
        myDBHelper = new DBHelper(this);
        mySchedule = myDBHelper.export(myScheduleItemId);
        title.setText(mySchedule.getTitle());
        time.setText(mySchedule.getStartTime());
        remarks.setText(mySchedule.getRemarks());
    }
}
