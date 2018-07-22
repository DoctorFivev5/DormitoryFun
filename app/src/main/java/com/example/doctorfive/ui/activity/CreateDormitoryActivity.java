
package com.example.doctorfive.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.doctorfive.base.BaseActivity;
import com.example.doctorfive.db.DBHelper;
import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.entity.Dormitory;
import com.example.doctorfive.entity.DormitoryAndUser;
import com.example.doctorfive.entity.User;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class CreateDormitoryActivity extends BaseActivity implements View.OnClickListener {

    private User myUser;
    private TextView returnButton;
    private Button completeButton;
    private TextView joinDormitoryButton;
    private EditText dormitoryName;
    private EditText dormitoryText;
    private Spinner dormitoryNum;
    private EditText dormitoryPwd;
    private EditText dormitoryPwd2;
    private EditText dormitoryBulid;
    private EditText dormitoryRoom;
    private DBHelper myDBHelper;
    private String dormitoryNumText;
    private LoadingDialog loadingDialog;
    private DBHelper.DBListener dbListener;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    //宿舍已存在
                    loadingDialog.loadFailed();

                    break;
                case 1:
                   //添加宿舍成功
                    loadingDialog.loadSuccess();
                    loadingDialog.close();
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dormitory);
        initView();


    }

    private void initView() {
        myUser = (User) getIntent().getSerializableExtra("myUser");
        returnButton = findViewById(R.id.create_dormitory_left_text);
        joinDormitoryButton = findViewById(R.id.create_dormitory_right_text);
        completeButton = findViewById(R.id.create_dormitory_finish);
        dormitoryName = findViewById(R.id.dormitory_nametext);
        dormitoryText = findViewById(R.id.dormitory_texttext);
        dormitoryNum = findViewById(R.id.dormitory_num_text);
        dormitoryNum.getSelectedItem();
        dormitoryNum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dormitoryNumText = parent.getItemAtPosition(position).toString();//获取选择项的值
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dormitoryPwd = findViewById(R.id.dormitory_pwd_text);
        dormitoryPwd2 = findViewById(R.id.dormitory_confirm_text);
        dormitoryBulid = findViewById(R.id.building_num);
        dormitoryRoom = findViewById(R.id.dormitory_room);
        initDialog();
        dbListener = new DBHelper.DBListener() {
            @Override
            public void doNetRequestChange(Object object) {

                Message message = new Message();
                if (object==null){

                    message.what = 0;
                    handler.sendMessage(message);
                }else {
                    DormitoryAndUser dormitoryAndUser = (DormitoryAndUser) object;
                    myUser = dormitoryAndUser.getUser();
                    myUser.setDormitoryID(dormitoryAndUser.getDormitory().getDormitory_id());

                    myDBHelper.update(myUser);
                    myUser = myDBHelper.export(myUser);

                    message.what = 1;
                    handler.sendMessage(message);
                }
            }
        };
        myDBHelper = new DBHelper(this, dbListener);
        returnButton.setOnClickListener(this);
        completeButton.setOnClickListener(this);
        joinDormitoryButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_dormitory_left_text:
                finish();
                break;

            case R.id.create_dormitory_right_text:
                Intent intent = new Intent(CreateDormitoryActivity.this, JoinDormitoryActivity.class);
                intent.putExtra("myUser",myUser);
                //FLAG_ACTIVITY_CLEAR_TOP 无效？？？？？？？？？？？？？？？？
                startActivity(intent);
                break;

            case R.id.create_dormitory_finish:
                initDialog();
                Dormitory dormitory = new Dormitory();
                dormitory.setDormitory_id(dormitoryBulid.getText().toString()+"#"+dormitoryRoom.getText().toString());
                //以6#s203的形式存入数据库
                dormitory.setDormitory_name(dormitoryName.getText().toString());
                dormitory.setDormitory_num(Integer.parseInt(dormitoryNumText));
                dormitory.setDormitory_text(dormitoryText.getText().toString());
                dormitory.setDormitory_password(dormitoryPwd.getText().toString());
                myDBHelper.okhttpCreateDormitoryPost(new DormitoryAndUser(dormitory, myUser));
                Log.e("myUser", myUser.toString());
                loadingDialog.show();
                break;
        }
    }

    private void initDialog() {
        loadingDialog= new LoadingDialog(this);
        loadingDialog.setLoadingText("更新...")
                .setSuccessText("更新成功")
                .setFailedText("宿舍已存在")
                .closeFailedAnim()
                .setInterceptBack(false);
    }
}
