package com.example.doctorfive.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.doctorfive.base.BaseActivity;
import com.example.doctorfive.db.DBHelper;
import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.entity.User;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

/**
 * 编辑个人信息界面
 */
public class EditImformationActivity extends BaseActivity {

    private TextView returnText;
    private TextView completeText;
    private EditText autograph;
    private EditText username;
    private Spinner sex;
    private String sexText;//保存选择的性别
    private EditText school;
    private EditText email;
    //private EditText buildingNum;
    //private EditText dormitoryNum;

    private User myUser;
    private LoadingDialog loadingDialog;
    private DBHelper myDBHelper;
    private DBHelper.DBListener DBListener;
    Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    loadingDialog.close();
                    myDBHelper.update(myUser);
                    Intent intent2 = new Intent(EditImformationActivity.this, PersonalInformation.class);
                    Bundle bundle2 = new Bundle();
                    bundle2.putSerializable("myUser",myUser);
                    intent2.putExtras(bundle2);
                    setResult(0x11,intent2);
                    finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_imformation);
        initView();
        initData();

        returnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

        completeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myUser.setUsername(username.getText().toString());
                myUser.setSchool(school.getText().toString());
                myUser.setSex(sexText);
                myUser.setEmail(email.getText().toString());
                myUser.setAutograph(autograph.getText().toString());
                //需要添加对dormitoryID进行格式判断
                //myUser.setDormitoryID(buildingNum.getText()+"#"+dormitoryNum.getText());
                myDBHelper.okhttpChangeUserImformationPost(myUser);
                loadingDialog.show();
            }
        });
    }

    private void initData() {
        autograph.setText(myUser.getAutograph());
        username.setText(myUser.getUsername());
        sex.getSelectedItem();
        sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sexText = parent.getItemAtPosition(position).toString();//获取选择项的值
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        school.setText(myUser.getSchool());
        email.setText(myUser.getEmail());
    }

    private void initView() {
        DBListener = new DBHelper.DBListener() {
            @Override
            public void doNetRequestChange(Object object) {
                Message message = new Message();
                Log.e("Edit-DBListener","mmp");
                message.what = 1;
                myHandler.sendMessage(message);
            }
        };
        myDBHelper = new DBHelper(this, DBListener);
        initDialog();
        Intent intent1 = getIntent();
        Bundle bundle = intent1.getExtras();
        myUser = new User();
        myUser = (User) bundle.getSerializable("myUser");
        returnText = (TextView) findViewById(R.id.editimformation_left_text);
        completeText = (TextView) findViewById(R.id.editimformation_right_text);
        autograph = (EditText) findViewById(R.id.autographtext);
        username = (EditText) findViewById(R.id.usernametext);
        sex = (Spinner) findViewById(R.id.sextext);
        school = (EditText) findViewById(R.id.schooltext);
        email = (EditText) findViewById(R.id.emailtext);
        //buildingNum = (EditText) findViewById(R.id.building_num);
        //dormitoryNum = (EditText) findViewById(R.id.dormitory_num);
    }

    private void initDialog() {
        loadingDialog= new LoadingDialog(this);
        loadingDialog.setLoadingText("更新...")
                .setSuccessText("更新成功")
                .setFailedText("网络出错")
                .closeFailedAnim()
                .setInterceptBack(false);
    }




}
