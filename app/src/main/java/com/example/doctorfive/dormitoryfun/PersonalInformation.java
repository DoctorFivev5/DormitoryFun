package com.example.doctorfive.dormitoryfun;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.doctorfive.dbhelp.DBHelper;
import com.example.doctorfive.dbhelp.Student;

public class PersonalInformation extends AppCompatActivity implements View.OnClickListener {

    private ImageView bg;
    private ImageView icon;
    private Button changeInformation;
    private View inflate;
    private Button choosePhoto;
    private Button takePhoto;
    private Button cancel;
    private Dialog dialog;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_information);
        final Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        final Student student = new Student();
        student.setPhoneNum(bundle.getString("phoneNum"));
        dbHelper = new DBHelper(this);
        db = dbHelper.getDb();
        Cursor cursor = db.rawQuery("select * from Student where phoneNum=?", new String[]{student.getPhoneNum()});
        dbHelper.export(cursor,student);

        bg = (ImageView) findViewById(R.id.bg);
        icon = (ImageView) findViewById(R.id.icon_image);
        changeInformation = (Button) findViewById(R.id.change_information);
        dbHelper = new DBHelper(this);
        db = dbHelper.getDb();
        String map_url = "http://jwc.jxnu.edu.cn/StudentPhoto/"+student.getStuNum()+".jpg?a=20171124191233";
        //Toast.makeText(MyApplication.getContext(),map_url,Toast.LENGTH_LONG).show();
        Glide.with(this).load(map_url)
                .transform(new CircleCrop(this))
                .into(icon);
        bg.setOnClickListener(this);
        icon.setOnClickListener(this);
        changeInformation.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bg:
                Toast.makeText(this,"更换背景",Toast.LENGTH_SHORT).show();
                break;
            case R.id.icon_image:
                showMyDialog();
                break;
            case R.id.change_information:
                Toast.makeText(this,"修改个人信息",Toast.LENGTH_SHORT).show();
                break;
            case R.id.takePhoto:
                Toast.makeText(this,"点击了拍照",Toast.LENGTH_SHORT).show();
                break;
            case R.id.choosePhoto:
                Toast.makeText(this,"点击了选择照片",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
            default:
                break;

        }
    }

    public void showMyDialog(){
        dialog = new Dialog(this,R.style.myDialog);
        inflate = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null);
        choosePhoto = (Button) inflate.findViewById(R.id.choosePhoto);
        takePhoto = (Button) inflate.findViewById(R.id.takePhoto);
        cancel = (Button) inflate.findViewById(R.id.btn_cancel);
        choosePhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        cancel.setOnClickListener(this);
        dialog.setContentView(inflate);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity( Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;
        dialogWindow.setAttributes(lp);
        dialog.show();
    }
}
