package com.example.doctorfive.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.entity.ScheduleItem;
import com.example.doctorfive.ui.activity.ScheduleDetailActivity;

import java.util.List;

/**
 * Created by DoctorFive on 2017/12/27.
 */

public class ScheduleRecyclerAdapter extends RecyclerView.Adapter<ScheduleRecyclerAdapter.ViewHolder> {

    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的
    private static View mHeaderView;
    private static View mFooterView;
    private List<ScheduleItem> mySheduleitemList;



    static class ViewHolder extends RecyclerView.ViewHolder {
        View scheduleView;
        int myScheduleId;
        View myScheduleLayout;      //日程layout控件
        ImageView mySchedulTypeImage;//日程类型图片
        TextView mySchedulTitle;//日程标题
        TextView mySchedulTime;//日程时间
        ImageView myStateImage;//跳转图标
        public ViewHolder(View itemView) {
            super(itemView);
            scheduleView = itemView;
            //如果是headerview或者是footerview,直接返回
            if (itemView == mHeaderView){
                return;
            }
            if (itemView == mFooterView){
                return;
            }
            myScheduleLayout = itemView.findViewById(R.id.schedule_item_layout);
            mySchedulTypeImage = (ImageView) itemView.findViewById(R.id.schedulTypeImage);
            mySchedulTitle = (TextView) itemView.findViewById(R.id.schedulTitle);
            mySchedulTime = (TextView) itemView.findViewById(R.id.schedulTime);
            myStateImage = (ImageView) itemView.findViewById(R.id.schedulStateImage);
        }
    }

    public ScheduleRecyclerAdapter(List<ScheduleItem> scheduleItemList){
        this.mySheduleitemList = scheduleItemList;
    }

    //HeaderView和FooterView的get和set函数
    public View getHeaderView() {
        return mHeaderView;
    }
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }
    public View getFooterView() {
        return mFooterView;
    }
    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount()-1);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER) {
            return new ViewHolder(mHeaderView);
        }
        if(mFooterView != null && viewType == TYPE_FOOTER){
            return new ViewHolder(mFooterView);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.scheduleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                ScheduleItem scheduleItem = mySheduleitemList.get(position);
                Log.e("ScheduleRecyclerAdapter", scheduleItem.getScheduleId()+" ");
                Intent intent = new Intent(parent.getContext(), ScheduleDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("scheduleId", scheduleItem.getScheduleId());
                intent.putExtras(bundle);
                parent.getContext().startActivity(intent);
            }
        });


        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(getItemViewType(position) == TYPE_NORMAL){
            if(holder instanceof ViewHolder) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了 如果没有使用headeritem就改成position
                ScheduleItem scheduleItem = mySheduleitemList.get(position);
                holder.myScheduleId = scheduleItem.getScheduleId();
                holder.mySchedulTypeImage.setImageResource(scheduleItem.getTypeImageId());
                holder.mySchedulTitle.setText(scheduleItem.getSchedulTitle());
                holder.mySchedulTime.setText(scheduleItem.getSchedulTime());
                holder.myStateImage.setImageResource(scheduleItem.getStateImageId());
                return;
            }
            return;
        }else if(getItemViewType(position) == TYPE_HEADER){
            return;
        }else{
            return;
        }
    }

    @Override
    public int getItemCount() {
        if(mHeaderView == null && mFooterView == null){
            return mySheduleitemList.size();
        }else if(mHeaderView == null && mFooterView != null){
            return mySheduleitemList.size() + 1;
        }else if (mHeaderView != null && mFooterView == null){
            return mySheduleitemList.size() + 1;
        }else {
            return mySheduleitemList.size() + 2;
        }
    }

    /** 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view    * */
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null){
            return TYPE_NORMAL;
        }
        if (position == 0){
            //第一个item应该加载Header
            return TYPE_HEADER;
        }

        return TYPE_NORMAL;
    }




}
