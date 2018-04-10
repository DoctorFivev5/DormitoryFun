package com.example.doctorfive.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;

import android.support.v7.app.AppCompatActivity;


import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.doctorfive.base.BaseActivity;
import com.example.doctorfive.db.DBHelper;
import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.entity.Student;
import com.example.doctorfive.entity.User;
import com.example.doctorfive.ui.fragment.ChatFragment;
import com.example.doctorfive.ui.fragment.KCBFragment;
import com.example.doctorfive.ui.fragment.MyFragment;
import com.example.doctorfive.ui.fragment.ScheduleFragment;
import com.example.doctorfive.ui.fragment.TimetableFragment;

/**
 * 4个tab fragment依附的主界面
 */

public class MainActivity extends BaseActivity implements View.OnClickListener ,TimetableFragment.FragmentInteraction{
    private DBHelper mDBHelper;  //数据库操作对象
    //private DrawerLayout mDrawerLayout;//该界面的总布局
    //private ShareActionProvider mShareActionProvider;//系统自带ActionProvider--ShareActionProvider
    //private MyActionProvider mMyActionProvider;//自定义MyActionProvider;
    private User user;//User实体
    private Student student;
    private Intent intent;
    private Bundle bundle;

    //private Toolbar toolbar;        //顶部导航栏
    private ChatFragment chatFragment;         //聊天界面Fragment
    private ScheduleFragment scheduleFragment;    //日程界面Fragment
    private TimetableFragment timetableFragment;   //课表登录界面Fragment
    private KCBFragment kcbFragment;        //课表界面Fragment
    private MyFragment myFragment;           //我的界面Fragment
    private View chatLayout;        //下方聊天界面导航栏布局
    private View scheduleLayout;   //下方日程界面导航栏布局
    private View timetableLayout;  //下方课表界面导航栏布局
    private View myLayout;          //下方我的界面导航栏布局
    private ImageView chatImage;      //在Tab布局上显示聊天图标的控件
    private ImageView scheduleImage; //在Tab布局上显示日程图标的控件
    private ImageView timetableImage;//在Tab布局上显示课表图标的控件
    private ImageView myImage;        //在Tab布局上显示我的图标的控件
    private TextView chatText;       //在Tab布局上显示聊天标题的控件
    private TextView scheduleText;  //在Tab布局上显示日程标题的控件
    private TextView timetableText; //在Tab布局上显示课表标题的控件
    private TextView myText;         //在Tab布局上显示我的标题的控件
    private FragmentManager fragmentManager; //用于对Fragment进行管理

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        initViews();//初始化布局
        initValue();//实体赋值
        setTabSelection(0);//选择fragment初始界面

    }

    //初始化view控件
    private void initViews(){
        chatLayout = findViewById(R.id.chat_layout);
        scheduleLayout = findViewById(R.id.schedule_layout);
        timetableLayout = findViewById(R.id.timetable_layout);
        myLayout = findViewById(R.id.my_layout);

        chatImage = (ImageView) findViewById(R.id.chat_image);
        scheduleImage = (ImageView) findViewById(R.id.schedule_image);
        timetableImage = (ImageView) findViewById(R.id.timetable_image);
        myImage = (ImageView) findViewById(R.id.my_image);

        chatText = (TextView) findViewById(R.id.chat_text);
        scheduleText = (TextView) findViewById(R.id.schedule_text);
        timetableText = (TextView) findViewById(R.id.timetable_text);
        myText = (TextView) findViewById(R.id.my_text);


        //mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout1);

        chatLayout.setOnClickListener(this);
        scheduleLayout.setOnClickListener(this);
        timetableLayout.setOnClickListener(this);
        myLayout.setOnClickListener(this);
    }

    private void initValue(){
        mDBHelper = new DBHelper(this);
        fragmentManager = getSupportFragmentManager();
        intent = getIntent();
        bundle = intent.getExtras();
        //user = new User();
        //user.setPhoneNum(bundle.getString("phoneNum"));
        //user = mDBHelper.export(user);
        user = (User) bundle.getSerializable("myUser");
        student = new Student();
        student.setStuNum(user.getStuNum());
        student = mDBHelper.export(student);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chat_layout:
                // 当点击了聊天tab时，选中第1个tab
                setTabSelection(0);
                break;
            case R.id.schedule_layout:
                // 当点击了日程tab时，选中第2个tab
                setTabSelection(1);
                break;
            case R.id.timetable_layout:
                // 当点击了课程表tab时，选中第3个tab
                setTabSelection(2);
                break;
            case R.id.my_layout:
                // 当点击了我的设置tab时，选中第4个tab
                setTabSelection(3);
                break;
            default:
                break;
        }
    }

    private void setTabSelection(int index) {
        // 每次选中之前先清除掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                // 当点击了消息tab时，改变控件的图片和文字颜色
                chatImage.setImageResource(R.drawable.chat_unselected);
                chatText.setTextColor(ResourcesCompat.getColor(getResources(),R.color.skyblue,null));
                if (chatFragment == null) {
                    // 如果chatFragment为空，则创建一个并添加到界面上
                    chatFragment = new ChatFragment();
                    transaction.add(R.id.content, chatFragment);
                } else {
                    // 如果chatFragment不为空，则直接将它显示出来
                    transaction.show(chatFragment);
                }
                break;
            case 1:
                // 当点击了日程tab时，改变控件的图片和文字颜色
                scheduleImage.setImageResource(R.drawable.schedule_unselected);
                scheduleText.setTextColor(ResourcesCompat.getColor(getResources(),R.color.skyblue,null));
                Bundle bundle1 = new Bundle();
                bundle1.putInt("user_id",user.getId());
                //Log.e("不会不会","0 "+user.getId());
                if (scheduleFragment == null) {
                    // 如果scheduleFragment为空，则创建一个并添加到界面上
                    scheduleFragment = new ScheduleFragment();
                    scheduleFragment.setArguments(bundle1);
                    transaction.add(R.id.content, scheduleFragment);
                } else {
                    // 如果scheduleFragment不为空，则直接将它显示出来
                    transaction.show(scheduleFragment);
                }
                break;
            case 2:
                // 当点击了课程表tab时，改变控件的图片和文字颜色
                timetableImage.setImageResource(R.drawable.timetable_unselected);
                timetableText.setTextColor(ResourcesCompat.getColor(getResources(),R.color.skyblue,null));

                if (student==null||mDBHelper.export(student.getStuNum(),17182)==null) {
                    Bundle bundle2 = new Bundle();
                    bundle2.putSerializable("myUser",user);

                    if (timetableFragment == null) {
                        // 如果TimetableFragment为空，则创建一个并添加到界面上
                        timetableFragment = new TimetableFragment();
                        timetableFragment.setArguments(bundle2);
                        transaction.add(R.id.content, timetableFragment);
                        //Toast.makeText(MyApplication.getContext(),"选课阶段可能出现查询课表功能无响应，可多尝试几次",Toast.LENGTH_SHORT).show();
                    } else {
                        // 如果TimetableFragment不为空，则直接将它显示出来
                        transaction.show(timetableFragment);
                        //在这里添加判断
                    }
                }else if (mDBHelper.export(student.getStuNum(),17182)!=null){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("student",student);
                    if (timetableFragment != null) {
                        // 如果TimetableFragment存在，就去除
                        transaction.remove(timetableFragment);
                    }
                    if (kcbFragment == null){
                        kcbFragment = new KCBFragment();
                        kcbFragment.setArguments(bundle);
                        transaction.add(R.id.content, kcbFragment);
                    } else {
                        // 如果TimetableFragment不为空，则直接将它显示出来
                        transaction.show(kcbFragment);
                        //在这里添加判断
                    }
                }
                break;
            case 3:
            default:
                // 当点击了我的tab时，改变控件的图片和文字颜色
                myImage.setImageResource(R.drawable.my_unselected);
                myText.setTextColor(ResourcesCompat.getColor(getResources(),R.color.skyblue,null));
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("myUser",user);
                if (myFragment == null) {
                    // 如果SettingFragment为空，则创建一个并添加到界面上
                    myFragment = new MyFragment();
                    myFragment.setArguments(bundle2);
                    transaction.add(R.id.content, myFragment);
                } else {
                    // 如果SettingFragment不为空，则直接将它显示出来
                    transaction.show(myFragment);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        chatImage.setImageResource(R.drawable.chat_unselected);
        chatText.setTextColor(Color.parseColor("#82858b"));
        scheduleImage.setImageResource(R.drawable.schedule_unselected);
        scheduleText.setTextColor(Color.parseColor("#82858b"));
        timetableImage.setImageResource(R.drawable.timetable_unselected);
        timetableText.setTextColor(Color.parseColor("#82858b"));
        myImage.setImageResource(R.drawable.my_unselected);
        myText.setTextColor(Color.parseColor("#82858b"));
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     * @param transaction
     *            用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (chatFragment != null) {
            transaction.hide(chatFragment);
        }
        if (scheduleFragment != null) {
            transaction.hide(scheduleFragment);
        }
        if (timetableFragment != null) {
            transaction.hide(timetableFragment);
        }
        if (kcbFragment != null){
            transaction.hide(kcbFragment);
        }
        if (myFragment != null) {
            transaction.hide(myFragment);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.toorbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private Intent getDefaultIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        return intent;
    }

    @Override
    public void process(Student student) {
        this.student = student;
        setTabSelection(2);
    }

}
