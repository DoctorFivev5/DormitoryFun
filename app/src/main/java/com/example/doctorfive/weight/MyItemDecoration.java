package com.example.doctorfive.weight;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.doctorfive.dormitoryfun.R;

/**
 * Created by DoctorFive on 2018/1/5.
 */

public class MyItemDecoration extends RecyclerView.ItemDecoration {
    private int dividerHeight;


    public MyItemDecoration(Context context) {
        dividerHeight = context.getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = dividerHeight;//类似加了一个bottom padding
    }

}
