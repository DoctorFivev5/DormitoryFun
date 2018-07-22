package com.example.doctorfive.weight;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.ui.activity.WebActivity;

/**
 * Created by DoctorFive on 2018/6/4.
 */

public class UtilsDialog extends DialogFragment implements View.OnClickListener{
    public static UtilsDialog getInstance() {
        UtilsDialog utilsDialog = new UtilsDialog();
        return utilsDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //1 通过样式定义,DialogFragment.STYLE_NORMAL这个很关键的
        setStyle(DialogFragment.STYLE_NORMAL, R.style.myDialog);

        //2代码设置 无标题 无边框  这个就很坑爹，这么设置很多系统效果就都没有了
        //setStyle(DialogFragment.STYLE_NO_TITLE|DialogFragment.STYLE_NO_FRAME,0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //3 在此处设置 无标题 对话框背景色
        //getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // dialog的背景色
        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.RED));
        //getDialog().getWindow().setDimAmount(0.8f);//背景黑暗度
        return inflater.inflate(R.layout.utilsdialog, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        view.findViewById(R.id.sunnysport).setOnClickListener(this);
        view.findViewById(R.id.cet).setOnClickListener(this);
        view.findViewById(R.id.library).setOnClickListener(this);
        view.findViewById(R.id.self_study).setOnClickListener(this);
        view.findViewById(R.id.school_calendar).setOnClickListener(this);
        view.findViewById(R.id.school_mail).setOnClickListener(this);
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
            case R.id.sunnysport:
                Intent intent1 = new Intent(getContext(), WebActivity.class);
                intent1.putExtra("url","http://jxnu.sunnysport.org/");
                startActivity(intent1);
                this.dismiss();
                break;
            case R.id.cet:
                Intent intent2 = new Intent(getContext(), WebActivity.class);
                intent2.putExtra("url","http://cet.neea.edu.cn/cet");
                startActivity(intent2);
                this.dismiss();
                break;
            case R.id.library:
                Intent intent3 = new Intent(getContext(), WebActivity.class);
                intent3.putExtra("url","http://tsg.jxnu.edu.cn/");
                startActivity(intent3);
                this.dismiss();
                break;
            case R.id.self_study:
                Intent intent4 = new Intent(getContext(), WebActivity.class);
                intent4.putExtra("url","http://zwfp.jxnu.jadl.net/");
                startActivity(intent4);
                this.dismiss();
                break;
            case R.id.school_calendar:
                Intent intent5 = new Intent(getContext(), WebActivity.class);
                intent5.putExtra("url","http://www.jxnu.edu.cn/s/2/t/690/94/67/info103527.htm");
                startActivity(intent5);
                this.dismiss();
                break;
            case R.id.school_mail:
                Intent intent6 = new Intent(getContext(), WebActivity.class);
                intent6.putExtra("url","http://mail.jxnu.edu.cn/mobile/?q=login");
                startActivity(intent6);
                this.dismiss();
                break;
        }
    }
}
