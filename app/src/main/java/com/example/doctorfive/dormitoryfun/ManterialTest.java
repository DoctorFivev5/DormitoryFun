package com.example.doctorfive.dormitoryfun;


import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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
import android.widget.Toast;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manteriallayout);
        Toolbar toolbar =(Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.niubi);
        }

        navigationView.setCheckedItem(R.id.nav_call);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
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
