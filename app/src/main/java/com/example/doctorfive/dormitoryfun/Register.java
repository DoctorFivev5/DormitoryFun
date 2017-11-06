package com.example.doctorfive.dormitoryfun;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by DoctorFive on 2017/10/12.
 */

public class Register extends AppCompatActivity {

    private User user = new User();
    private String phoneNumS;
    private String stuNumS;
    private String password_1S;
    private String password_2S;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        final EditText phoneNum = (EditText) findViewById(R.id.phoneNum);
        final EditText stuNum = (EditText) findViewById(R.id.stuNum);
        final EditText password_1 = (EditText) findViewById(R.id.password_1);
        final EditText password_2 = (EditText) findViewById(R.id.password_2);

        final Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        phoneNum.setText(bundle.getString("username"));

        Button register = (Button) findViewById(R.id.register);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumS = phoneNum.getText().toString();
                stuNumS = stuNum.getText().toString();
                password_1S = password_1.getText().toString();
                password_2S = password_2.getText().toString();
                if (Checkout.isMobileNO(phoneNumS)) {
                    if (Checkout.isPasswordO(password_1S))
                        if (password_1S.equals(password_2S)) {
                            user.setPhoneNum(phoneNumS);
                            user.setStuNum(stuNumS);
                            user.setPassword(password_1S);
                            user.save();
                            Toast.makeText(Register.this, "注册成功！", Toast.LENGTH_SHORT).show();
                            /*
                            *进度条
                            ProgressDialog progressDialog = new ProgressDialog(MyApplication.getContext());
                            progressDialog.setMessage("正在跳转至登陆界面...");
                            */
                            Intent intent1 = new Intent(MyApplication.getContext(),Login.class);
                            Bundle bundle1 = new Bundle();
                            bundle.putCharSequence("username1",phoneNumS);
                            intent.putExtras(bundle1);
                            startActivity(intent1);
                            finish();
                        } else {
                            Toast.makeText(Register.this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });




    }


}
