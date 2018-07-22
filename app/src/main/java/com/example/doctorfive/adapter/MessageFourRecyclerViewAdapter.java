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

public class MessageFourRecyclerViewAdapter extends RecyclerView.Adapter<MessageFourRecyclerViewAdapter.ViewHolder> {
    private List<UserMessage> msgList;

    public MessageFourRecyclerViewAdapter(List<UserMessage> userMessages){
        this.msgList = userMessages;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.message_text4);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.roommate_four_message_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserMessage userMessage = msgList.get(position);
        holder.textView.setText(userMessage.getMessage_text());
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }


}
