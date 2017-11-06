package com.example.doctorfive.dormitoryfun;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by DoctorFive on 2017/10/17.
 */

public class Test extends AppCompatActivity {
    ImageView imageView;
    private String studentNum;
    private DrawerLayout mDrawerLayout;
    private Note[] notes = {new Note("这是第一个呦，呜啦啦啦啦啦"),new Note("这是第二个呦，呜啦啦啦啦啦"),new Note("这是第三个呦，呜啦啦啦啦啦"),new Note("这是第四个呦，呜啦啦啦啦啦"),new Note("这是第五个呦，呜啦啦啦啦啦"),new Note("这是第六个呦，呜啦啦啦啦啦"),new Note("这是第七个呦，呜啦啦啦啦啦")};
    private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_launcher);
        }
        /*
        final Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        String photoNum = bundle.getString("username");
        List<User> user1 = DataSupport.where("phoneNum==?",photoNum).find(User.class);
        for (User user : user1){
            studentNum=user.getStuNum();
        }
        imageView = (ImageView) findViewById(R.id.icon_image);
        */
        /*
        List<Note> noteLists = getNote();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NoteAdapter(noteLists);
        recyclerView.setAdapter(adapter);
        navigationView.setCheckedItem(R.id.nav_call);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });//选取任意一项就关闭侧边栏

        //loadImage(imageView);
        */
    }

    private List<Note> getNote() {
        List<Note> noteList = new ArrayList<>();
        for(int i=0;i<20;i++){
            noteList.add(notes[i%6]);
        }
        return noteList;
    }

    /*
    public void loadImage(ImageView i) {
        Toast.makeText(Test.this,studentNum,Toast.LENGTH_SHORT).show();
        String url = "http://jwc.jxnu.edu.cn/StudentPhoto/"+studentNum+".jpg?a=20171017095306";
        Glide.with(this).load(url).into(i);
    }
    */


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toorbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
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
