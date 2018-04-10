package com.example.doctorfive.ui.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.doctorfive.adapter.FirstStartFragPageAdapter;
import com.example.doctorfive.base.BaseActivity;
import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.ui.fragment.FirstStartOneFragment;
import com.example.doctorfive.ui.fragment.FirstStartThreeFragment;
import com.example.doctorfive.ui.fragment.FirstStartTwoFragment;
import com.example.doctorfive.weight.DepthPageTransformer;

import java.util.ArrayList;
import java.util.List;

public class FirstStartShowActivity extends BaseActivity {

    private ViewPager viewPager;
    private List<Fragment> list;
    private FirstStartFragPageAdapter firstStartFragPageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_start_show);
        initView();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.fragmentviewpager);
        FirstStartOneFragment firstStartOneFragment = new FirstStartOneFragment();
        FirstStartTwoFragment FirstStarttwoFragment =  new FirstStartTwoFragment();
        FirstStartThreeFragment FirstStartthreeFragment = new FirstStartThreeFragment();

        list = new ArrayList();
        list.add(firstStartOneFragment);
        list.add(FirstStarttwoFragment);
        list.add(FirstStartthreeFragment);
        viewPager.setOffscreenPageLimit(2);

        viewPager.setPageTransformer(true, new DepthPageTransformer());
        /*
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //页面状态改变时调用
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //页面滑动过程中调用
            @Override
            public void onPageSelected(int position) {
            }

            //页面滑动后调用
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        */
        viewPager.setAdapter(new FirstStartFragPageAdapter(getSupportFragmentManager(), list));
    }
}
