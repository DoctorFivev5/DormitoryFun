package com.example.doctorfive.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;


import com.example.doctorfive.adapter.KCBGridAdapter;
import com.example.doctorfive.dormitoryfun.R;

import java.util.ArrayList;
import java.util.List;

public class KCBActivity extends AppCompatActivity {

    private Spinner spinner;

    private GridView detailCource;

    private String[][] contents;

    private KCBGridAdapter secondAdapter;


    private List<String> dataList;

    private ArrayAdapter<String> spinnerAdapter;

    private String[] kcb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kcblayout);
        Intent intent = getIntent();
        kcb = intent.getStringArrayExtra("kcb");
        spinner = (Spinner)findViewById(R.id.switchWeek);
        detailCource = (GridView)findViewById(R.id.courceDetail);
        //第二种方式创建Adapter 可操作性强
        fillStringArray();
        secondAdapter = new KCBGridAdapter(this);
        secondAdapter.setContent(contents, 7, 7);
        detailCource.setAdapter(secondAdapter);

        //圈出周几  待实现
        //weekTitle.setCurrentDay(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));

        //////////////创建Spinner数据
        fillDataList();
        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dataList);//把周次填进布局中
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//设置下拉布局
        spinner.setAdapter(spinnerAdapter);//加载下拉布局
    }

    /**
     * 准备数据
     */
    private List<String> init() {
        List<String> list = new ArrayList<String>();
        for (String k : kcb){
            list.add(k);
        }
        return list;
    }

    public void fillStringArray() {
        contents = new String[7][7];
        for (int i = 0; i < 7; i++){
            for (int j = 0; j < 7; j++){
                contents[i][j] = kcb[i*7+j];
            }
        }
    }

    public void fillDataList() {
        dataList = new ArrayList<>();
        for(int i = 1; i < 21; i++) {
            dataList.add("第" + i + "周");
        }
    }
}
