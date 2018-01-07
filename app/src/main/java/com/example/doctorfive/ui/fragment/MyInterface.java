package com.example.doctorfive.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.doctorfive.adapter.MyRecyclerAdapter;
import com.example.doctorfive.db.DBHelper;
import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.entity.Optionitem;
import com.example.doctorfive.entity.User;
import com.example.doctorfive.ui.activity.PersonalInformation;
import com.example.doctorfive.ui.activity.SettingActivity;
import com.example.doctorfive.util.CircleCropUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyInterface extends Fragment implements View.OnClickListener {

    private String phoneNum;        //用户手机号
    private User myUser;        //用户对象
    private DBHelper myDBHelper;//数据库操作对象
    private TextView setting;   //设置按钮
    private View userShow;      //用户信息展示控件
    private ImageView userIcon; //用户头像
    private TextView username;  //用户名称
    private TextView userText;  //用户签名
    private RecyclerView myRecyclerView;    //滚动清单
    private MyRecyclerAdapter myRecyclerAdapter;
    private List<Optionitem> optionitemList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_my_interface, container, false);
        initView(view);
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        phoneNum = getArguments().getString("phoneNum");
        myUser = new User();
        myUser.setPhoneNum(phoneNum);
        Log.e("onResume",phoneNum+" !");
        myDBHelper = new DBHelper(getContext());
        myUser = myDBHelper.export(myUser);
        Log.e("onStart",myUser.getPhoneNum()+" !!");
        username.setText(myUser.getUsername());
        userText.setText(myUser.getAutograph());
        loadingHeaderIcon();
    }

    private void loadingHeaderIcon(){
        String map_url = myUser.getIcon();
        Glide.with(this).load(map_url)
                .transform(new CircleCropUtil(getContext()))
                .placeholder(R.mipmap.ic_launcher)
                .into(userIcon);
    }

    private void initView(View view) {
        setting = (TextView) view.findViewById(R.id.fragment_my_setting);
        userShow = view.findViewById(R.id.user_show);
        userIcon = view.findViewById(R.id.user_icon);
        username = view.findViewById(R.id.username);
        userText = view.findViewById(R.id.autograph);
        initOption();
        initData(view);
        myRecyclerView = view.findViewById(R.id.fragment_my_recycler);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myRecyclerAdapter = new MyRecyclerAdapter(optionitemList);
        myRecyclerView.setAdapter(myRecyclerAdapter);
        userShow.setOnClickListener(this);
        setting.setOnClickListener(this);
    }

    private void initData(View view){

    }

    private void initOption() {
        optionitemList = new ArrayList<>();
        Optionitem optionitem = new Optionitem("我的宿舍",R.drawable.home);
        optionitemList.add(optionitem);
        Optionitem optionitem1 = new Optionitem("电费",R.drawable.electric);
        optionitemList.add(optionitem1);
        Optionitem optionitem2 = new Optionitem("寝费",R.drawable.money);
        optionitemList.add(optionitem2);
        Optionitem optionitem3 = new Optionitem("我的文件",R.drawable.file);
        optionitemList.add(optionitem3);
        Optionitem optionitem4 = new Optionitem("在线报修",R.drawable.fix_home);
        optionitemList.add(optionitem4);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.user_show:
                Intent intent = new Intent(getContext(), PersonalInformation.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("phoneNum",myUser.getPhoneNum());
                intent.putExtras(bundle1);
                startActivity(intent);
                break;
            case R.id.fragment_my_setting:
                Intent intent1 = new Intent(getContext(), SettingActivity.class);
                startActivity(intent1);
                break;

        }
    }
}
