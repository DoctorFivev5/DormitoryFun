package com.example.doctorfive.ui.activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.doctorfive.db.DBHelper;
import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.entity.Schedule;
import com.example.doctorfive.util.CalendarUtil;

import java.util.Calendar;

/*
讲道理4个日程种类
应该设置成4个fragment的
没时间写了
 */


public class ScheduleActivity extends AppCompatActivity implements View.OnClickListener {


    private int user_id;            //用来保存日程的外键
    private String date;               //用来保存
    private Schedule mySchedule;    //用来保存日程的信息
    private Button cancalButton;    //关闭日程界面按钮
    private Button determineButton;//完成日程编辑按钮
    private View banjiLayot;        //班级日程视图
    private View birthdayLayout;    //生日日程视图
    private View dormitoryLayout;   //宿舍日程视图
    private View normalLayout;      //一般日程视图
    private EditText title;         //日程标题
    private Button startTime;       //日程开始时间
    private EditText overtime;      //日程持续时间
    private EditText remarks;       //备注

    private String titleText;       //标题文本
    private String startTimeText;   //开始时间文本
    private String overtimeText;    //持续时间文本
    private String remarksText;     //备注文本

    private DBHelper myDBHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        mySchedule = new Schedule();
        cancalButton = (Button) findViewById(R.id.cancel);
        determineButton = (Button) findViewById(R.id.determine);
        banjiLayot = findViewById(R.id.banji_layout);
        birthdayLayout = findViewById(R.id.birthday_layout);
        dormitoryLayout = findViewById(R.id.dormitory_layout);
        normalLayout = findViewById(R.id.normal_layout);
        title = (EditText) findViewById(R.id.title);
        startTime = (Button) findViewById(R.id.starttime);
        overtime = (EditText) findViewById(R.id.overtime);
        remarks = (EditText) findViewById(R.id.remarks);
        cancalButton.setOnClickListener(this);
        determineButton.setOnClickListener(this);
        banjiLayot.setOnClickListener(this);
        birthdayLayout.setOnClickListener(this);
        dormitoryLayout.setOnClickListener(this);
        normalLayout.setOnClickListener(this);
        startTime.setOnClickListener(this);
    }

    private void initData() {
        myDBHelper = new DBHelper(this);
        mySchedule.setType("班级");
        Calendar calender = Calendar.getInstance();
        String time = calender.get(Calendar.HOUR_OF_DAY)+":"+calender.get(Calendar.MINUTE);

        startTime.setText(time);
        Intent intent1 = getIntent();
        Bundle bundle1 = intent1.getExtras();
        user_id = bundle1.getInt("user_id");
        date = bundle1.getString("date");
        mySchedule.setDay(date);
        mySchedule.setUserID(user_id);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.cancel:
                finish();
                break;
            case R.id.determine:
                titleText = title.getText().toString();
                startTimeText = startTime.getText().toString();
                overtimeText = overtime.getText().toString();
                remarksText = remarks.getText().toString();
                if (titleText.isEmpty())
                    mySchedule.setTitle("(无标题)");
                else
                    mySchedule.setTitle(titleText);
                mySchedule.setRemindTime(overtimeText);
                mySchedule.setRemarks(remarksText);
                mySchedule.setStartTime(startTimeText);
                Log.e("Schedule傻逼",date);
                myDBHelper.insert(mySchedule);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("mySchedule", mySchedule);
                Log.e("sssssssssssss",mySchedule.getStartTime());
                intent.putExtras(bundle);
                setResult(0x01,intent);
                finish();
                break;
            case R.id.banji_layout:
                mySchedule.setType("班级");
                Toast.makeText(ScheduleActivity.this,"班级日程",Toast.LENGTH_SHORT).show();
                break;
            case R.id.birthday_layout:
                mySchedule.setType("生日");
                Toast.makeText(ScheduleActivity.this,"生日",Toast.LENGTH_SHORT).show();
                break;
            case R.id.dormitory_layout:
                mySchedule.setType("宿舍");
                Toast.makeText(ScheduleActivity.this,"宿舍日程",Toast.LENGTH_SHORT).show();
                break;
            case R.id.normal_layout:
                mySchedule.setType("一般");
                Toast.makeText(ScheduleActivity.this,"一般日程",Toast.LENGTH_SHORT).show();
                break;
            case R.id.starttime:
                Calendar calender = Calendar.getInstance();
                new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String times = CalendarUtil.timeFormat(hourOfDay, minute);
                        Toast.makeText(ScheduleActivity.this, "选择的时间" + times, Toast.LENGTH_SHORT).show();
                        startTime.setText(times);
                    }
                }, calender.get(Calendar.HOUR_OF_DAY), calender.get(Calendar.MINUTE), true).show();
                break;
        }
    }
}
