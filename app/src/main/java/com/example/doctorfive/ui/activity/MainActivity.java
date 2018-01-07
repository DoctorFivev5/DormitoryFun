package com.example.doctorfive.ui.activity;



import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;

import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;


import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.doctorfive.base.MyApplication;
import com.example.doctorfive.db.DBHelper;
import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.entity.Student;
import com.example.doctorfive.entity.User;
import com.example.doctorfive.ui.fragment.ChatInterface;
import com.example.doctorfive.ui.fragment.KCBFragment;
import com.example.doctorfive.ui.fragment.MyInterface;
import com.example.doctorfive.ui.fragment.ScheduleInterface;
import com.example.doctorfive.ui.fragment.TimetableInterface;
import com.example.doctorfive.util.CircleCropUtil;


public class MainActivity extends AppCompatActivity implements View.OnClickListener ,TimetableInterface.FragmentInteraction{
    private DBHelper mDBHelper;  //数据库操作对象
    private DrawerLayout mDrawerLayout;//该界面的总布局
    //private ShareActionProvider mShareActionProvider;//系统自带ActionProvider--ShareActionProvider
    //private MyActionProvider mMyActionProvider;//自定义MyActionProvider;
    private User user;//User实体
    private Student student;
    private Intent intent;
    private Bundle bundle;

    //private Toolbar toolbar;        //顶部导航栏
    private ChatInterface chatFragment;         //聊天界面Fragment
    private ScheduleInterface scheduleFragment;    //日程界面Fragment
    private TimetableInterface timetableFragment;   //课表登录界面Fragment
    private KCBFragment kcbFragment;        //课表界面Fragment
    private MyInterface myFragment;           //我的界面Fragment
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
    //private NavigationView navigationView;  //侧滑栏
    //private View headerView;//navigationView内的头部控件
    //private RelativeLayout insertLayout;//获取navigationView内的头部布局
    //private ImageView headerBG;
    //private ImageView headerIcon;
    //private TextView headerName;
    //private TextView headerMessage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);//无标题
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_layout);

        initViews();//初始化布局
        initValue();//实体赋值
        setTabSelection(0);//选择fragment初始界面
        //loadingHeaderIcon();//加载头像
        /*
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
        }
        */
        //头像点击事件
        /*
        insertLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,PersonalInformation.class);
                Bundle bundle = new Bundle();
                bundle.putCharSequence("phoneNum", user.getPhoneNum());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        */

        /*
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                SharedPreferences.Editor editor = getSharedPreferences("userPwd",MODE_PRIVATE).edit();
                switch (item.getItemId()){
                    case R.id.personal_information://个人信息页面
                        Toast.makeText(MyApplication.getContext(),"有点问题",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.cancel://关闭主界面转到登录界面
                        editor.putBoolean("clear",false);
                        editor.apply();
                        Intent in2 = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(in2);
                        finish();
                        Toast.makeText(MyApplication.getContext(),"注销成功！",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.clear_data://关闭主界面且删除记住的帐号密码转到主界面
                        editor.putBoolean("clear",false);
                        editor.putString("phoneNum","");
                        editor.putString("password","");
                        editor.apply();
                        Intent in3 = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(in3);
                        finish();
                        Toast.makeText(MyApplication.getContext(),"取消自动登录成功！",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        mDrawerLayout.closeDrawers();//关闭侧滑栏
                }

                return true;
            }
        });//选取任意一项就关闭侧边栏
        */
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

        //toolbar =(Toolbar) findViewById(R.id.toolbar1);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout1);
        //navigationView = (NavigationView) findViewById(R.id.nav_view);
        //initNavigationView();
        chatLayout.setOnClickListener(this);
        scheduleLayout.setOnClickListener(this);
        timetableLayout.setOnClickListener(this);
        myLayout.setOnClickListener(this);
    }

    private void initValue(){
        mDBHelper = new DBHelper(this);
        //toolbar.setTitle("");
        //setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();
        intent = getIntent();
        bundle = intent.getExtras();
        user = new User();
        user.setPhoneNum(bundle.getString("phoneNum"));
        user = mDBHelper.export(user);
        student = new Student();
        student.setStuNum(user.getStuNum());
        student = mDBHelper.export(student);
    }
    /*
    private void initNavigationView(){
        headerView = navigationView.getHeaderView(0);//获取navigationView内的布局
        insertLayout = (RelativeLayout) headerView.findViewById(R.id.nav_header_relarelativelayout);
        headerBG = (ImageView) headerView.findViewById(R.id.bg);
        headerIcon =  (ImageView) headerView.findViewById(R.id.icon_image);
        headerName = (TextView) headerView.findViewById(R.id.name);
        headerMessage = (TextView) headerView.findViewById(R.id.signature);
        //headerMessage.setText(user.getStuNum());
    }
    */
    /*
    private void loadingHeaderIcon(){
        String map_url = "http://jwc.jxnu.edu.cn/StudentPhoto/"+ user.getStuNum()+".jpg?a=20171124191233";
        Glide.with(this).load(map_url)
                .transform(new CircleCropUtil(this))
                .placeholder(R.drawable.niubi)
                .into(headerIcon);
    }
    */

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chat_layout:
                // 当点击了消息tab时，选中第1个tab
                setTabSelection(0);
                break;
            case R.id.schedule_layout:
                // 当点击了联系人tab时，选中第2个tab
                setTabSelection(1);
                break;
            case R.id.timetable_layout:
                // 当点击了动态tab时，选中第3个tab
                setTabSelection(2);
                break;
            case R.id.my_layout:
                // 当点击了设置tab时，选中第4个tab
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
                    chatFragment = new ChatInterface();
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
                Log.e("不会不会","0 "+user.getId());
                if (scheduleFragment == null) {
                    // 如果scheduleFragment为空，则创建一个并添加到界面上
                    scheduleFragment = new ScheduleInterface();
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
                    bundle2.putString("phoneNum",user.getPhoneNum());

                    if (timetableFragment == null) {
                        // 如果TimetableFragment为空，则创建一个并添加到界面上
                        timetableFragment = new TimetableInterface();
                        timetableFragment.setArguments(bundle2);
                        transaction.add(R.id.content, timetableFragment);
                        Toast.makeText(MyApplication.getContext(),"选课阶段可能出现查询课表功能无响应，可多尝试几次",Toast.LENGTH_SHORT).show();
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
                bundle2.putString("phoneNum",user.getPhoneNum());
                if (myFragment == null) {
                    // 如果SettingFragment为空，则创建一个并添加到界面上
                    myFragment = new MyInterface();
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
     *
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
        //MenuItem deleteItem = menu.findItem(R.id.delete);
        //MenuItem shareItem = menu.findItem(R.id.setting);
        //mMyActionProvider = (MyActionProvider ) MenuItemCompat.getActionProvider(deleteItem);//自定义MyActionProvider
        //mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);//系统自带ActionProvider--ShareActionProvider
        //mShareActionProvider.setShareIntent(getDefaultIntent());
        return super.onCreateOptionsMenu(menu);
    }

    private Intent getDefaultIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.backup:
                break;
            /*
            case R.id.delete:
                break;
            case R.id.setting:
                break;
                */
            default:
                break;
        }
        return true;
    }//头部导航

    @Override
    public void process(Student student) {
        this.student = student;
        setTabSelection(2);
    }
}
