package com.example.doctorfive.weight;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctorfive.db.DBHelper;
import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.entity.DormitoryPwd;
import com.example.doctorfive.entity.User;
import com.example.doctorfive.ui.activity.JoinDormitoryActivity;
import com.example.doctorfive.ui.activity.MainActivity;
import com.example.doctorfive.ui.activity.WebActivity;

/**
 * Created by DoctorFive on 2018/6/4.
 */

public class JoinDormitoryDialog extends DialogFragment implements View.OnClickListener{

    private Bundle bundle;
    private TextView dormitoryNum;
    private EditText dormitoryPassword;
    private Button join;
    private String number;
    private User myUser;
    private DBHelper myDBHelper;
    private DBHelper.DBListener myDBListener;
    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    //JoinDormitoryDialog.this.dismiss();
                    Toast.makeText(getContext(),"密码错误",Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    JoinDormitoryDialog.this.dismiss();
                    myUser.setDormitoryID(number);
                    myDBHelper.update(myUser);
                    Intent intent = new Intent(getContext(),MainActivity.class);
                    intent.putExtra("myUser",myUser);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    break;
            }
        }
    };

    public static JoinDormitoryDialog getInstance() {
        JoinDormitoryDialog joinDormitoryDialog = new JoinDormitoryDialog();
        return joinDormitoryDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //1 通过样式定义,DialogFragment.STYLE_NORMAL这个很关键的
        setStyle(DialogFragment.STYLE_NORMAL, R.style.myDialog);
        //2代码设置 无标题 无边框  这个就很坑爹，这么设置很多系统效果就都没有了
        //setStyle(DialogFragment.STYLE_NO_TITLE|DialogFragment.STYLE_NO_FRAME,0);
        myDBListener = new DBHelper.DBListener() {
            @Override
            public void doNetRequestChange(Object object) {
                if (object==null){
                    Message message = new Message();
                    message.what = 0;
                    myHandler.sendMessage(message);
                }else {
                    Message message = new Message();
                    message.what = 1;
                    myHandler.sendMessage(message);
                }
            }
        };
        myDBHelper = new DBHelper(getContext(),myDBListener);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //3 在此处设置 无标题 对话框背景色
        //getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // dialog的背景色
        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.RED));
        getDialog().getWindow().setDimAmount(0.8f);//背景黑暗度
        bundle = getArguments();
        if (bundle != null){
            Log.e("bundle","!!!!!!!!!!!!!");
            number = bundle.getString("number");
            myUser = (User) bundle.getSerializable("myUser");
        }

        this.getDialog().setOnKeyListener(new DialogInterface.OnKeyListener()
        {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event){
                Log.e("keyCode",keyCode+".......");
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) { //退格的拦截，用来过滤返回键

                    return true;
                }
                if (keyCode == KeyEvent.KEYCODE_DEL){
                    return true;
                }
                if (keyCode == KeyEvent.KEYCODE_BACK) {                                  //返回键的操作
                    return true;
                }
                return false;
            }
        });
        return inflater.inflate(R.layout.dormitory_dialog, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        dormitoryNum = view.findViewById(R.id.dialog_join_dormitory_num);
        dormitoryPassword = view.findViewById(R.id.dialog_join_dormitory_password);
        dormitoryNum.setText(number);
        join = view.findViewById(R.id.dialog_join_dormitory_button);
        join.setOnClickListener(this);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //注意下面这个方法会将布局的根部局忽略掉，所以需要嵌套一个布局
//        Window dialogWindow = getDialog().getWindow();
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        lp.gravity =  Gravity.TOP;//改变在屏幕中的位置,如果需要改变上下左右具体的位置，比如100dp，则需要对布局设置margin
//        Display defaultDisplay = getActivity().getWindowManager().getDefaultDisplay();
//        lp.width = defaultDisplay.getWidth() - 200;  //改变宽度
//        lp.height=300;//   改变高度
//        dialogWindow.setAttributes(lp);

        getDialog().setCancelable(true);
        getDialog().setCanceledOnTouchOutside(true);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {//可以在这拦截返回键啊home键啊事件
                dialog.dismiss();
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_join_dormitory_button:
                //String number = dormitoryNum.getText().toString();
                String password = dormitoryPassword.getText().toString();
                Log.e("666",number+" "+password);
                //Intent intent = new Intent(getContext(), MainActivity.class);
                //intent.putExtra("url","http://jxnu.sunnysport.org/");
                //startActivity(intent);
                //发送网络请求并跳转
                DormitoryPwd dormitoryPwd = new DormitoryPwd();
                dormitoryPwd.setDormitoryNum(number);
                dormitoryPwd.setPassword(password);
                dormitoryPwd.setPhoneNum(myUser.getPhoneNum());
                myDBHelper.okhttpDormitoryPwd(dormitoryPwd);

                break;
        }
    }

}
