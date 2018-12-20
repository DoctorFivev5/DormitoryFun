package com.example.doctorfive.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctorfive.adapter.KCBGridAdapter;
import com.example.doctorfive.db.DBHelper;
import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.entity.Student;
import com.example.doctorfive.entity.Timetable;
import com.example.doctorfive.weight.UtilsDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * tab课表 显示界面
 */
public class KCBFragment extends Fragment implements View.OnClickListener {
    private DBHelper dbHelper;

    //private Spinner spinner;
    private TextView utilText;
    private TextView team;
    private GridView detailCource;


    private String[][] contents;

    private KCBGridAdapter secondAdapter;


    private List<String> dataList;

    //private ArrayAdapter<String> spinnerAdapter;
    private Student student;
    private Timetable timetable = new Timetable();
    private String[] kcb;
    public KCBFragment() {
        // Required empty public constructor
    }

    public void onStart() {
        super.onStart();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.kcblayout, container, false);
        initView(view);
        dbHelper = new DBHelper(getActivity());
        if (isAdded()) {//判断Fragment已经依附Activity
            student = (Student) getArguments().getSerializable("student");
            student = dbHelper.export(student);

            Log.e("KCBFragment",student.getStuNum()+" 000");
            timetable = dbHelper.export(student.getStuNum(),17182);
            Log.e("KCBFragment",timetable.getClass1()+" 000  "+timetable.getStuNum());
            kcb = timetable.getClasses();
        }else {
            Log.e("KCBFragment","没有依赖");
        }
        initData();
        //spinner = (Spinner) view.findViewById(R.id.switchWeek);
        detailCource = (GridView) view.findViewById(R.id.courceDetail);
        //第二种方式创建Adapter 可操作性强
        fillStringArray();
        secondAdapter = new KCBGridAdapter(getActivity());
        secondAdapter.setContent(contents, 7, 7);
        detailCource.setAdapter(secondAdapter);

        //圈出周几  待实现
        //weekTitle.setCurrentDay(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));

        //////////////创建Spinner数据
        fillDataList();
        //spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, dataList);//把周次填进布局中
        //spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//设置下拉布局
        //spinner.setAdapter(spinnerAdapter);//加载下拉布局
        return view;
    }

    private void initData() {
        String number = student.getStuNum().substring(0,4);
        Log.e("init",number);
        if (number.equals("2015")){
            team.setText("大四第一学期");
        }else if (number.equals("2016")){
            team.setText("大三第一学期");
        }else if (number.equals("2017")){
            team.setText("大二第一学期");
        }else if (number.equals("2018")){
            team.setText("大一第一学期");
        }
    }

    private void initView(View view) {
        utilText = view.findViewById(R.id.fragment_kcb_left);
        team = view.findViewById(R.id.fragment_kcb_title);
        utilText.setOnClickListener(this);
        team.setOnClickListener(this);
    }

    /**
     * 准备数据
     */
    private List<String> init() {
        List<String> list = new ArrayList<String>();
        for (String k : kcb){
            k = k.replaceAll("-","");
            list.add(k);
        }
        return list;
    }

    public void fillStringArray() {
        contents = new String[7][7];
        for (int i = 0; i < 7; i++){
            for (int j = 0; j < 7; j++){
                contents[i][j] = kcb[i*7+j].replaceAll("-","");
            }
        }
    }

    public void fillDataList() {
        dataList = new ArrayList<>();
        for(int i = 1; i < 21; i++) {
            dataList.add("第" + i + "周");
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.fragment_kcb_left:
                Log.e("gongju","mmp");
                UtilsDialog utilsDialog = UtilsDialog.getInstance();
                utilsDialog.show(getActivity().getFragmentManager(),"xixix");
                break;
            case R.id.fragment_kcb_title:
                break;
        }
    }
}
