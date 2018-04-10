package com.example.doctorfive.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.doctorfive.dormitoryfun.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class FirstStartOneFragment extends Fragment {
    //private ImageView imageView;


    public FirstStartOneFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first_start_one, container, false);
        //imageView = view.findViewById(R.id.first_start_one_image);
        return view;
    }

}
