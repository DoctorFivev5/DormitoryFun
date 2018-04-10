package com.example.doctorfive.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.doctorfive.dormitoryfun.R;

/**
 *
 */
public class FirstStartTwoFragment extends Fragment {

    //private ImageView imageView;

    public FirstStartTwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first_start_two, container, false);
        //imageView = view.findViewById(R.id.first_start_two_image);
        return view;
    }

}
