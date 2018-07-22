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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.doctorfive.adapter.MyRecyclerAdapter;
import com.example.doctorfive.base.MyApplication;
import com.example.doctorfive.db.DBHelper;
import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.entity.Optionitem;
import com.example.doctorfive.entity.User;
import com.example.doctorfive.ui.activity.CreateDormitoryActivity;
import com.example.doctorfive.ui.activity.DormitoryActivity;
import com.example.doctorfive.ui.activity.FileActivity;
import com.example.doctorfive.ui.activity.FixHomeActivity;
import com.example.doctorfive.ui.activity.MoneyActivity;
import com.example.doctorfive.ui.activity.PersonalInformation;
import com.example.doctorfive.ui.activity.PowerActivity;
import com.example.doctorfive.ui.activity.SettingActivity;
import com.example.doctorfive.util.CircleCrop;


import java.util.ArrayList;
import java.util.List;


/**
 * tab我的界面
 */
public class MyFragment extends Fragment implements View.OnClickListener,MyRecyclerAdapter.MyItemClickListener {
    //声明控件
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
        // 加载布局和初始化控件
        View view = inflater.inflate(R.layout.fragment_my_interface, container, false);
        initView(view);
        return view;
    }
    @Override
    public void onResume() {//在页面重新回到可操作的界面执行的方法
        super.onResume();
        //phoneNum = getArguments().getString("phoneNum");
        //myUser.setPhoneNum(phoneNum);
        //Log.e("onResume",phoneNum+" !");
        myDBHelper = new DBHelper(getContext());
        myUser = myDBHelper.export(myUser);
        Log.e("onResume",myUser.printUser());
        //myUser = (User) getArguments().getSerializable("myUser");
        Log.e("onResume",myUser.getPhoneNum()+" !!"+myUser.getUserIcon()+"..00");
        username.setText(myUser.getUsername());
        userText.setText(myUser.getAutograph());
        loadingHeaderIcon();
        //得到用户id     刷新头像、签名、用户名
    }

    /**
     * 根据url加载头像
     */
    private void loadingHeaderIcon(){
        String map_url = myUser.getUserIcon();
        Log.e("MyFragment", map_url+"");
        Glide.with(this).load(map_url)
                //.diskCacheStrategy(DiskCacheStrategy.NONE)
                //.skipMemoryCache(true)
                //.transform(new CircleCrop(getContext()))
                .dontAnimate()
                .into(userIcon);
    }

    //初始化控件
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
        myRecyclerAdapter.setOnItemClickListener(this);
        myRecyclerView.setAdapter(myRecyclerAdapter);
        userShow.setOnClickListener(this);
        setting.setOnClickListener(this);
        myUser = new User();
        myUser = (User) getArguments().getSerializable("myUser");
    }

    private void initData(View view){

    }

    private void initOption() {//初始化recyclerview的项
        optionitemList = new ArrayList<>();
        Optionitem optionitem = new Optionitem("我的宿舍",R.drawable.home);
        optionitemList.add(optionitem);
        Optionitem optionitem1 = new Optionitem("电费",R.drawable.electric);
        optionitemList.add(optionitem1);
//        Optionitem optionitem2 = new Optionitem("寝费",R.drawable.money);
//        optionitemList.add(optionitem2);
//        Optionitem optionitem3 = new Optionitem("我的文件",R.drawable.file);
//        optionitemList.add(optionitem3);
        Optionitem optionitem4 = new Optionitem("在线报修",R.drawable.fix_home);
        optionitemList.add(optionitem4);
    }

    @Override
    public void onClick(View view) {//点击事件
        int id = view.getId();
        switch (id){
            case R.id.user_show:
                Intent intent = new Intent(getContext(), PersonalInformation.class);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("myUser",myUser);
                intent.putExtras(bundle1);
                startActivity(intent);
                break;
            case R.id.fragment_my_setting:
                Intent intent1 = new Intent(getContext(), SettingActivity.class);
                intent1.putExtra("myUser", myUser);
                startActivity(intent1);
                break;

        }
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0:
                Log.e("myUser.getDormitoryID()", myUser.getDormitoryID()+"000");
                if(myUser.getDormitoryID()!=null){
                    Intent intent = new Intent(getContext(), DormitoryActivity.class);
                    intent.putExtra("myUser", myUser);
                    getContext().startActivity(intent);
                }else {
                    Intent intent = new Intent(getContext(), CreateDormitoryActivity.class);
                    intent.putExtra("myUser", myUser);
                    getContext().startActivity(intent);
                }
                break;
            case 1:
                Intent intent1 = new Intent(getContext(), PowerActivity.class);
                intent1.putExtra("myUser", myUser);
                getContext().startActivity(intent1);
                break;
//            case 2:
//                Intent intent2 = new Intent(getContext(), MoneyActivity.class);
//                getContext().startActivity(intent2);
//                break;
//            case 3:
//                Intent intent3 = new Intent(getContext(), FileActivity.class);
//                getContext().startActivity(intent3);
//                break;
            case 2:
                Intent intent4 = new Intent(getContext(), FixHomeActivity.class);
                getContext().startActivity(intent4);
                break;
        }
    }
}
