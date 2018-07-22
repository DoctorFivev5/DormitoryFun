package com.example.doctorfive.ui.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctorfive.base.BaseActivity;
import com.example.doctorfive.db.DBHelper;
import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.entity.Power;
import com.example.doctorfive.entity.User;

/**
 * tab我的 --> 电费页面
 */
public class PowerActivity extends BaseActivity {

    private TextView dormitory_id;
    private TextView power_fee;
    private TextView power_kwh;
    private DBHelper myDbHelper;
    private User myUser;
    private Power power;
    private DBHelper.DBListener myDBListener;
    Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    Toast.makeText(PowerActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Log.e("myUser dorId", power.toString());
                    dormitory_id.setText(power.getPower_dormitory_id());
                    power_fee.setText(Float.toString(power.getPower_fee()));
                    power_kwh.setText(Float.toString(power.getPower_kwh()));
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power);
        initView();
    }

    private void initView() {
        myDBListener = new DBHelper.DBListener() {
            @Override
            public void doNetRequestChange(Object object) {
                if (object == null){
                    Message message = new Message();
                    message.what = 0;
                    myHandler.sendMessage(message);
                }else {
                    power = (Power) object;
                    Message message = new Message();
                    message.what = 1;
                    myHandler.sendMessage(message);
                }
            }
        };
        myDbHelper = new DBHelper(this, myDBListener);
        myUser = (User) getIntent().getSerializableExtra("myUser");
        //Log.e("myUser dorId", myUser.getDormitoryID());
        Power power = new Power();
        power.setPower_dormitory_id(myUser.getDormitoryID());

        myDbHelper.okhttpQueryPower(myUser.getDormitoryID());

        dormitory_id = findViewById(R.id.power_dormitory_id);
        power_fee = findViewById(R.id.power_fee);
        power_kwh = findViewById(R.id.power_kwh);
    }


}
