package com.example.doctorfive.dormitoryfun;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

/**
 * Created by DoctorFive on 2017/10/21.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private Context mContext;
    private List<Note> mNoteList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView note_text;
        TextView data_text;
        public ViewHolder(View view){
            super(view);
            cardView = (CardView) view;
            note_text = (TextView) view.findViewById(R.id.note_text);
            data_text = (TextView) view.findViewById(R.id.data_text);
        }
    }

    public NoteAdapter(List<Note> noteList){
        mNoteList = noteList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.note_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Note note = mNoteList.get(position);
        holder.note_text.setText(note.getText());
        holder.data_text.setText(note.getCalendar());
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }
}
