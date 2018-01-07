package com.example.doctorfive.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.doctorfive.db.DBHelper;
import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.entity.User;

public class EditImformationActivity extends AppCompatActivity {

    private TextView returnText;
    private EditText autograph;
    private EditText username;
    private EditText sex;
    private EditText school;
    private EditText email;
    private User myUser;
    private DBHelper myDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_imformation);
        initView();
        initData();

        returnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myUser.setUsername(username.getText().toString());
                myUser.setSchool(school.getText().toString());
                if (sex.getText().toString().equals("男"))
                    myUser.setSex(true);
                else if (sex.getText().toString().equals("女")){
                    myUser.setSex(false);
                }
                myUser.setEmail(email.getText().toString());
                myUser.setAutograph(autograph.getText().toString());
                myDBHelper.update(myUser);
                Intent intent2 = new Intent(EditImformationActivity.this, PersonalInformation.class);
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("myUser",myUser);
                intent2.putExtras(bundle2);
                setResult(0x11,intent2);
                finish();
            }
        });
    }

    private void initData() {
        autograph.setText(myUser.getAutograph());
        username.setText(myUser.getUsername());
        sex.setText(myUser.isSex()?"男":"女");
        school.setText(myUser.getSchool());
        email.setText(myUser.getEmail());
    }

    private void initView() {
        myDBHelper = new DBHelper(this);
        Intent intent1 = getIntent();
        Bundle bundle = intent1.getExtras();
        myUser = new User();
        myUser = (User) bundle.getSerializable("myUser");
        returnText = (TextView) findViewById(R.id.left_text);
        autograph = (EditText) findViewById(R.id.autographtext);
        username = (EditText) findViewById(R.id.usernametext);
        sex = (EditText) findViewById(R.id.sextext);
        school = (EditText) findViewById(R.id.schooltext);
        email = (EditText) findViewById(R.id.emailtext);
    }



}
