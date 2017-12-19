package com.example.doctorfive.base;

import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import com.example.doctorfive.dormitoryfun.R;

/**
 * Created by DoctorFive on 2017/10/30.
 * 内容提供器
 */

public class MyActionProvider extends ActionProvider {
    public MyActionProvider(Context context) {
        super(context);
    }

    @Override
    public View onCreateActionView() {
        return null;
    }

    @Override
    public void onPrepareSubMenu(SubMenu subMenu) {
        subMenu.clear();
        subMenu.add(0,0,0,"sub item 1").setIcon(R.mipmap.niubi)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        return true;
                    }
                });
        subMenu.add(0,1,1,"sub item 2").setIcon(R.mipmap.niubi)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }
        });
    }

    @Override
    public boolean hasSubMenu() {
        return true;
    }
}
