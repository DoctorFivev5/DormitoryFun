package com.example.doctorfive.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.entity.Optionitem;

import java.util.List;

/**
 * Created by DoctorFive on 2017/12/18.
 */

public class OptionAdapter extends ArrayAdapter<Optionitem> {
    private int resourceId;
    public OptionAdapter(Context context, int textViewResourceId, List<Optionitem> objects){
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Optionitem optionitem = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView optionImage = (ImageView) view.findViewById(R.id.option_image);
        TextView optionText = (TextView) view.findViewById(R.id.option_name);
        optionImage.setImageResource(optionitem.getImageId());
        optionText.setText(optionitem.getName());
        return view;
    }
}
