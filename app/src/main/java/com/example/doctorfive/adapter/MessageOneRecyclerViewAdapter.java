package com.example.doctorfive.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.entity.UserMessage;

import java.util.List;

/**
 * Created by DoctorFive on 2018/5/20.
 */

public class MessageOneRecyclerViewAdapter extends RecyclerView.Adapter<MessageOneRecyclerViewAdapter.ViewHolder> {
    private List<UserMessage> msgList;

    public MessageOneRecyclerViewAdapter(List<UserMessage> userMessages){
        this.msgList = userMessages;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView message;
        TextView sendTime;
        public ViewHolder(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message_text1);
            sendTime = itemView.findViewById(R.id.message_time);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.roommate_one_message_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserMessage userMessage = msgList.get(position);
        holder.message.setText(userMessage.getMessage_text());
        holder.sendTime.setText(userMessage.getMessage_time());
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }


}
