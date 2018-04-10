package com.example.doctorfive.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.doctorfive.adapter.CourseRecyclerAdapter;
import com.example.doctorfive.base.BaseActivity;
import com.example.doctorfive.db.DBHelper;
import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.entity.CourseItem;
import com.example.doctorfive.entity.Student;
import com.example.doctorfive.entity.Timetable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 获取SharedPreferences中的phoneNum
 */
public class DayCourseActivity extends BaseActivity {

    private SharedPreferences courseSetPref;
    private SharedPreferences.Editor courseSetEditor;
    private DBHelper myDBHelper;
    private RecyclerView courseRecyclerView;
    private FloatingActionButton exit;
    private TextView todayMsg;
    private List<CourseItem> courseItemList;
    private LinearLayoutManager layoutManager;
    private CourseRecyclerAdapter courseRecyclerAdapter;
    private Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_course);
        initView();
        initData();
    }

    private void initView() {
        todayMsg = (TextView) findViewById(R.id.today_mag);
        exit = (FloatingActionButton) findViewById(R.id.exit_course);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DayCourseActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        courseItemList = new ArrayList();
        layoutManager = new LinearLayoutManager(this);
        courseRecyclerView = (RecyclerView) findViewById(R.id.day_course_recycleview);
        courseRecyclerView.setLayoutManager(layoutManager);

    }

    private void initData(){
        calendar = Calendar.getInstance();
        int week_of_day = calendar.get(Calendar.DAY_OF_WEEK);
        //String today = todayMsg.getText()+todayMsg.setText(" 星期")+week_of_day;
        //todayMsg.setText(today);
        myDBHelper = new DBHelper(this);
        courseSetPref = getSharedPreferences("courseSet", MODE_PRIVATE);
        String stuNum = courseSetPref.getString("stuNum","");
        Student student = new Student();
        student.setStuNum(stuNum);
        student = myDBHelper.export(student);
        Timetable timetable = null;
        if(student!=null)
            timetable = myDBHelper.export(student.getStuNum(),17182);
        String[] courseStr;
        if (student!=null&&timetable!=null) {
            //Log.e("week_of_day", String.valueOf(week_of_day));
            String[] course_of_day = timetable.getClasses();
            //Log.e("course_of_day", String.valueOf(course_of_day.length));
            for (int i=0;i<course_of_day.length;i++){
                if ((i+2)%7==week_of_day){
                    //Log.e("(i/7)+1", String.valueOf((i/7)+1));
                    courseStr = course_of_day[i].split("-");
                    if (courseStr.length>1)
                        courseItemList.add(new CourseItem((i/7)+1, courseStr[0], courseStr[1].replaceAll("\\(", "").replaceAll("\\)", "")));
                }
            }
        }
        courseRecyclerAdapter = new CourseRecyclerAdapter(courseItemList);
        courseRecyclerView.setAdapter(courseRecyclerAdapter);
    }
}
