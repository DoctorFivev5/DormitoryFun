package com.example.doctorfive.dormitoryfun;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;

import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;


import android.view.ActionProvider;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.doctorfive.dbhelp.DBHelper;
import com.example.doctorfive.dbhelp.Student;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;




public class ManterialTest extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Note[] notes = {new Note("这是第一个呦，呜啦啦啦啦啦"),new Note("这是第二个呦，呜啦啦啦啦啦"),new Note("这是第三个呦，呜啦啦啦啦啦"),new Note("这是第四个呦，呜啦啦啦啦啦"),new Note("这是第五个呦，呜啦啦啦啦啦"),new Note("这是第六个呦，呜啦啦啦啦啦"),new Note("这是第七个呦，呜啦啦啦啦啦")};
    private List<Note> noteList = new ArrayList<>();
    private NoteAdapter adapter;
    private ShareActionProvider mShareActionProvider;//系统自带ActionProvider--ShareActionProvider
    private MyActionProvider mMyActionProvider;//自定义MyActionProvider;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manteriallayout);
        final Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        final Student student = new Student();
        student.setPhoneNum(bundle.getString("phoneNum"));
        dbHelper = new DBHelper(this);
        db = dbHelper.getDb();
        Cursor cursor = db.rawQuery("select * from Student where phoneNum=?", new String[]{student.getPhoneNum()});
        dbHelper.export(cursor,student);

        Toolbar toolbar =(Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);//获取navigationView内的布局
        RelativeLayout insertLayout = (RelativeLayout) headerView.findViewById(R.id.nav_header_relarelativelayout);
        ImageView bg = (ImageView) headerView.findViewById(R.id.bg);
        ImageView icon =  (ImageView) headerView.findViewById(R.id.icon_image);
        TextView name = (TextView) headerView.findViewById(R.id.name);
        name.setText(student.getName());
        TextView stuNum = (TextView) headerView.findViewById(R.id.signature);
        stuNum.setText(student.getStuNum());
        //NavigationView获取Header View的问题
        String map_url = "http://jwc.jxnu.edu.cn/StudentPhoto/"+student.getStuNum()+".jpg?a=20171124191233";
        //Toast.makeText(MyApplication.getContext(),map_url,Toast.LENGTH_LONG).show();
        Glide.with(this).load(map_url)
                .transform(new CircleCrop(this))
                .into(icon);
        //.apply(bitmapTransform(new CropTransformation(50,50,)))
        //.apply(bitmapTransform(new BlurTransformation(25)))
        //.override(50, 50).placeholder(R.drawable.niubi)
        //icon.setImageURI("http://jwc.jxnu.edu.cn/StudentPhoto/"+bundle.getString("username")+".jpg?a=20171124191233");
        //http://jwc.jxnu.edu.cn/StudentPhoto/201626702119.jpg?a=20171124191233

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.niubi);
        }



        /*
        *Tab导航栏过时了
        * 想加入一个4个fragment页面 进行切换
        *
        tab = actionBar
                .newTab()
                .setText("hehe")
                .setTabListener(
                        new TabListener<AlbumFragment>(this,"album",AlbumFragment.class)
                );
        actionBar.addTab(tab);*/

        insertLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManterialTest.this,PersonalInformation.class);
                Bundle bundle = new Bundle();
                bundle.putCharSequence("phoneNum",student.getPhoneNum());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                SharedPreferences.Editor editor = getSharedPreferences("userPwd",MODE_PRIVATE).edit();
                switch (item.getItemId()){
                    case R.id.personal_information:
                        Intent in1 = new Intent(ManterialTest.this,PersonalInformation.class);
                        startActivity(in1);
                        break;
                    case R.id.cancel:
                        editor.putBoolean("clear",false);
                        editor.apply();
                        Intent in2 = new Intent(ManterialTest.this,Login_1.class);
                        startActivity(in2);
                        finish();
                        Toast.makeText(MyApplication.getContext(),"注销成功！",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.clear_data:
                        editor.putBoolean("clear",false);
                        editor.putString("phoneNum","");
                        editor.putString("password","");
                        editor.apply();
                        Intent in3 = new Intent(ManterialTest.this,Login_1.class);
                        startActivity(in3);
                        finish();
                        Toast.makeText(MyApplication.getContext(),"取消自动登录成功！",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        mDrawerLayout.closeDrawers();
                }

                return true;
            }
        });//选取任意一项就关闭侧边栏

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MyApplication.getContext(),"牛逼！！！",Toast.LENGTH_SHORT).show();
                Toast.makeText(MyApplication.getContext(),"真牛逼！！！",Toast.LENGTH_SHORT).show();
            }
        });
        /*
        显示在主界面的一些东西*/
        initNotes();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NoteAdapter(noteList);
        recyclerView.setAdapter(adapter);

    }

    private void initNotes() {
        noteList.clear();
        for (int i = 0; i < 50; i++){
            Random random = new Random();
            int index = random.nextInt(notes.length);
            noteList.add(notes[index]);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.toorbar,menu);
        MenuItem deleteItem = menu.findItem(R.id.delete);
        MenuItem shareItem = menu.findItem(R.id.setting);
        mMyActionProvider = (MyActionProvider ) MenuItemCompat.getActionProvider(deleteItem);//自定义MyActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);//系统自带ActionProvider--ShareActionProvider
        mShareActionProvider.setShareIntent(getDefaultIntent());
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
            case R.id.delete:
                break;
            case R.id.setting:
                break;
            default:
                break;
        }
        return true;
    }//头部导航
}
