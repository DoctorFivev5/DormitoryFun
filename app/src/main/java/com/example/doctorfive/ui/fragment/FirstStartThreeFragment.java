package com.example.doctorfive.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.ui.activity.FirstOpen;

/**
 *
 */
public class FirstStartThreeFragment extends Fragment {

    //private ImageView imageView;
    private Button button;
    public FirstStartThreeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first_start_three, container, false);
        //imageView = view.findViewById(R.id.first_start_three_image);
        button = view.findViewById(R.id.start_app);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Intent intent = new Intent(getContext(), FirstOpen.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }

}
