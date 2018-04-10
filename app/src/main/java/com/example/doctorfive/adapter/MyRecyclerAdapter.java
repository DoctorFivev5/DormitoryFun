package com.example.doctorfive.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.entity.Optionitem;
import com.example.doctorfive.ui.activity.DormitoryActivity;
import com.example.doctorfive.ui.activity.FileActivity;
import com.example.doctorfive.ui.activity.FixHomeActivity;
import com.example.doctorfive.ui.activity.MoneyActivity;
import com.example.doctorfive.ui.activity.PowerActivity;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>  {

    private List<Optionitem> optionitemList;
    private MyItemClickListener mItemClickListener;
    public MyRecyclerAdapter(List<Optionitem> optionitemList) {
        this.optionitemList = optionitemList;
    }

    /**
     * Created by DoctorFive on 2018/1/5.
     */

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private View optionView;
        private ImageView imageView;
        private TextView textView;
        private MyItemClickListener mListener;

        public ViewHolder(View itemView, MyItemClickListener listener) {
            super(itemView);
            optionView = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.option_image);
            textView = (TextView) itemView.findViewById(R.id.option_name);
            mListener = listener;
            optionView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getAdapterPosition());
            }
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.option_item, parent, false);
        return new ViewHolder(view, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Optionitem optionitem = optionitemList.get(position);
        holder.imageView.setImageResource(optionitem.getImageId());
        holder.textView.setText(optionitem.getName());
    }

    @Override
    public int getItemCount() {
        return optionitemList.size();
    }

    public interface MyItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(MyItemClickListener listener){
        this.mItemClickListener = listener;
    }

}

/*
public void onClick(View view) {
                int position = holder.getAdapterPosition();
                switch (position) {
                    case 0:
                        Intent intent = new Intent(parent.getContext(), DormitoryActivity.class);
                        parent.getContext().startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(parent.getContext(), PowerActivity.class);
                        parent.getContext().startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(parent.getContext(), MoneyActivity.class);
                        parent.getContext().startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(parent.getContext(), FileActivity.class);
                        parent.getContext().startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4 = new Intent(parent.getContext(), FixHomeActivity.class);
                        parent.getContext().startActivity(intent4);
                        break;
                }
            }
 */
