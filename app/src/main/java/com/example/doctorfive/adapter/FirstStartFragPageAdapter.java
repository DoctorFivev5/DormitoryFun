package com.example.doctorfive.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by DoctorFive on 2018/4/5.
 */

public class FirstStartFragPageAdapter extends FragmentPagerAdapter {

    List<Fragment> list;

    public FirstStartFragPageAdapter(FragmentManager fm) {
        super(fm);
    }

    //写构造方法，方便赋值调用
    public FirstStartFragPageAdapter(FragmentManager fm,List<Fragment> list) {
        super(fm);
        this.list=list;
    }

    //根据Item的位置返回对应位置的Fragment，绑定item和Fragment
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    //设置Item的数量
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }
}
