package com.example.doctorfive.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.doctorfive.adapter.OptionAdapter;
import com.example.doctorfive.base.MyApplication;
import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.entity.Optionitem;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyInterface extends Fragment {


    private List<Optionitem> optiontList = new ArrayList<>();
    public MyInterface() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_my_interface, container, false);
        initOptions();
        OptionAdapter optionAdapter = new OptionAdapter(this.getActivity(),R.layout.option_item,optiontList);
        ListView listView = (ListView) view.findViewById(R.id.optionlist);
        listView.setAdapter(optionAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Optionitem optionitem = optiontList.get(position);
                Toast.makeText(getActivity(),optionitem.getName(),Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void initOptions(){
        Optionitem optionitem = new Optionitem("寝室详情",R.drawable.my_selected);
        optiontList.add(optionitem);
        optionitem = new Optionitem("寝费详情",R.drawable.my_selected);
        optiontList.add(optionitem);
        optionitem = new Optionitem("文件管理",R.drawable.my_selected);
        optiontList.add(optionitem);
        optionitem = new Optionitem("寝室用电详情",R.drawable.my_selected);
        optiontList.add(optionitem);
        optionitem = new Optionitem("网上报修",R.drawable.my_selected);
        optiontList.add(optionitem);
        optionitem = new Optionitem("个人设置",R.drawable.my_selected);
        optiontList.add(optionitem);
        optionitem = new Optionitem("校园资讯",R.drawable.my_selected);
        optiontList.add(optionitem);
        optionitem = new Optionitem("校园资讯",R.drawable.my_selected);
        optiontList.add(optionitem);
        optionitem = new Optionitem("校园资讯",R.drawable.my_selected);
        optiontList.add(optionitem);
        optionitem = new Optionitem("校园资讯",R.drawable.my_selected);
        optiontList.add(optionitem);
        optionitem = new Optionitem("校园资讯",R.drawable.my_selected);
        optiontList.add(optionitem);
        optionitem = new Optionitem("校园资讯",R.drawable.my_selected);
        optiontList.add(optionitem);
        optionitem = new Optionitem("校园资讯",R.drawable.my_selected);
        optiontList.add(optionitem);
        optionitem = new Optionitem("校园资讯",R.drawable.my_selected);
        optiontList.add(optionitem);
        optionitem = new Optionitem("校园资讯",R.drawable.my_selected);
        optiontList.add(optionitem);


    }

}
