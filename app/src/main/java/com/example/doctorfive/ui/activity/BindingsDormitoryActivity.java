package com.example.doctorfive.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.doctorfive.base.BaseActivity;
import com.example.doctorfive.dormitoryfun.R;

public class BindingsDormitoryActivity extends BaseActivity implements View.OnClickListener {

    private Button binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bindings_dormitory);
        initView();
    }

    private void initView(){
        binding = findViewById(R.id.binding_dormitory_button);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.binding_dormitory_button:

                break;
        }
    }
}
