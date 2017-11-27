package com.example.doctorfive.dormitoryfun;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doctorfive.dbhelp.DBHelper;

import static com.example.doctorfive.dormitoryfun.Checkout.isMobileNO;

/**
 * Created by DoctorFive on 2017/10/16.
 */

public class ForgetPwd extends Activity{
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpwd);
        dbHelper = new DBHelper(this);
        db = dbHelper.getDb();
        final EditText phoneNum = (EditText) findViewById(R.id.forget_phoneNum);
        final EditText stuNum = (EditText) findViewById(R.id.forget_stuNum);
        EditText email = (EditText) findViewById(R.id.forget_email);
        Button findPwd = (Button) findViewById(R.id.findPwd);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        phoneNum.setText(bundle.getString("username"));

        findPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  phoneNumS = phoneNum.getText().toString();
                String  stuNumS = stuNum.getText().toString();

                if (isMobileNO(phoneNumS)){// Attempt to invoke virtual method 'android.database.Cursor android.database.sqlite.SQLiteDatabase.rawQuery(java.lang.String, java.lang.String[])' on a null object reference
                    Cursor cursor = db.rawQuery("select phoneNum, studentNum, password from Student where phoneNum=? and studentNum=?", new String[]{phoneNumS,stuNumS});
                    if (cursor.getCount()==0){
                        Toast.makeText(MyApplication.getContext(),"学号或手机号输入有误！",Toast.LENGTH_SHORT).show();
                    }else {
                        cursor.moveToFirst();
                        Toast.makeText(MyApplication.getContext(),"你的密码为： "+cursor.getString(cursor.getColumnIndex("password")),Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
}
