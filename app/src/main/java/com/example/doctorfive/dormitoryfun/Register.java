package com.example.doctorfive.dormitoryfun;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.doctorfive.dbhelp.DBHelper;
import com.example.doctorfive.dbhelp.Student;

/**
 * Created by DoctorFive on 2017/10/12.
 */

public class Register extends AppCompatActivity {

    private String phoneNumS;
    private String stuNumS;
    private String password_1S;
    private String password_2S;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        dbHelper = new DBHelper(this);
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
                    /*
                    try {
                        phoneNumSS = Integer.parseInt(phoneNumS);
                        stuNumSS = Integer.parseInt(stuNumS);
                        passwordSS = Integer.parseInt(password_1S);
                        Toast.makeText(MyApplication.getContext(),"没问题",Toast.LENGTH_SHORT).show();

                    }catch (Exception e){
                        phoneNumSS = 0;
                        stuNumSS = 0;
                        passwordSS = 0;
                        Toast.makeText(MyApplication.getContext(),"有问题",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    */
                    SQLiteDatabase db = dbHelper.getDb();
                    Cursor cursor = db.rawQuery("select phoneNum from Student where phoneNum='usernameS'", null);
                    if (cursor.getCount()>0){
                        Toast.makeText(Register.this, "该手机号已经被注册！", Toast.LENGTH_SHORT).show();
                    }else
                    if (Checkout.isPasswordO(password_1S))
                        if (password_1S.equals(password_2S)) {
                            Student student = new Student();
                            //student.setPhoneNum(Integer.parseInt(phoneNumSS));
                            //student.setStuNum(Integer.parseInt(stuNumSS));
                            student.setPhoneNum(phoneNumS);
                            student.setStuNum(stuNumS);
                            student.setPassword(password_1S);
                            /*
                            student.setUsername("傻逼");
                            student.setSex(true);
                            student.setEmail("null");
                            student.setSchool("null");
                            student.setState(1);
                            student.setName("null");
                            student.setDormitoryID("0");
                            */
                            dbHelper.insert(student);


                            /**
                             *没有注册成功！！！！！！！！！！！！！！！！！
                             * 具体查看sql文件 over
                             */


                            Toast.makeText(Register.this, "注册成功！"+phoneNumS+"  "+stuNumS+"  "+password_1S, Toast.LENGTH_SHORT).show();
                            /*
                            *进度条
                            ProgressDialog progressDialog = new ProgressDialog(MyApplication.getContext());
                            progressDialog.setMessage("正在跳转至登陆界面...");
                            */
                            Intent intent1 = new Intent(MyApplication.getContext(),Login_1.class);
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
