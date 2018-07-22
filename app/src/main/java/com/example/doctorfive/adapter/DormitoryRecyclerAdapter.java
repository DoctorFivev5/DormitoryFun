package com.example.doctorfive.adapter;

import android.app.Activity;
import android.app.Application;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doctorfive.base.MyApplication;
import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.entity.DormitoryItem;
import com.example.doctorfive.entity.User;
import com.example.doctorfive.ui.activity.JoinDormitoryActivity;
import com.example.doctorfive.weight.JoinDormitoryDialog;
import com.example.doctorfive.weight.UtilsDialog;

import java.util.List;

/**
 * Created by DoctorFive on 2018/6/7.
 */

public class DormitoryRecyclerAdapter extends RecyclerView.Adapter<DormitoryRecyclerAdapter.ViewHolder> {
    private Context mContext;
    private List<DormitoryItem> dormitoryItems;
    private User myUser;

    public DormitoryRecyclerAdapter(List<DormitoryItem> dormitoryItems, Context context, User user) {
        this.dormitoryItems = dormitoryItems;
        mContext = context;
        myUser = user;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dormitoryNum;
        TextView dormitoryName;
        TextView dormitoryText;
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            dormitoryNum = itemView.findViewById(R.id.join_dormitory_num);
            dormitoryName = itemView.findViewById(R.id.join_dormitory_name);
            dormitoryText = itemView.findViewById(R.id.join_dormitory_text);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dormitory_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = holder.dormitoryNum.getText().toString();
                JoinDormitoryDialog joinDormitoryDialog = JoinDormitoryDialog.getInstance();
                Bundle bundle = new Bundle();
                bundle.putString("number",number);
                bundle.putSerializable("myUser", myUser);
                joinDormitoryDialog.setArguments(bundle);
                FragmentActivity activity = (FragmentActivity)(mContext);
                FragmentManager fm = activity.getFragmentManager();
                joinDormitoryDialog.show(fm,"cao");
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DormitoryItem dormitoryItem = dormitoryItems.get(position);
        holder.dormitoryNum.setText(dormitoryItem.getDormitoryNum());
        holder.dormitoryName.setText(dormitoryItem.getDormitoryName());
        holder.dormitoryText.setText(dormitoryItem.getDormitoryText());
    }

    @Override
    public int getItemCount() {
        return dormitoryItems.size();
    }


}
