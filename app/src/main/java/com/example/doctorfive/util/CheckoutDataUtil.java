package com.example.doctorfive.util;

import android.text.TextUtils;
import android.widget.Toast;

import com.example.doctorfive.base.MyApplication;
import com.example.doctorfive.db.DBHelper;
import com.example.doctorfive.entity.User;

/**
 * Created by DoctorFive on 2017/10/16.
 * 检查手机号和密码工具类
 */

public class CheckoutDataUtil {

    private static String phoneNum_Z = "^1[3|4|5|7|8][0-9]{9}$";
    private static String password_Z = "^[0-9a-zA-Z]{9,16}$";
    private static DBHelper dbHelper = new DBHelper(MyApplication.getContext());

    public static boolean isPhoneNum(String mobiles){//检查手机号格式
        if (TextUtils.isEmpty(mobiles)){
            Toast.makeText(MyApplication.getContext(),"手机号不能为空！",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            if (mobiles.matches(phoneNum_Z))
                return true;
            else {
                Toast.makeText(MyApplication.getContext(), "请输入正确的手机号！", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    /*
    public static boolean isRegister(String mobiles) {//判断手机号是否正确且是否被注册
        if(isPhoneNum(mobiles)&&dbHelper.checkHavePhoneNum(mobiles)){
            return true;
        }else
            return false;
    }
    */
    /*
    public static boolean isLogin(User user) {//判断手机号是否正确且帐号密码是否匹配
        String phoneNum = user.getPhoneNum();
        if(isPhoneNum(phoneNum)){
            if (dbHelper.login(user))
                return true;
            else {
                Toast.makeText(MyApplication.getContext(),"帐号或密码错误！",Toast.LENGTH_SHORT).show();
                return false;
            }
        }else
            return false;
    }
    */

    public boolean forgetPassword(User user){
        String phoneNum = user.getPhoneNum();
        String stuNum = user.getStuNum();
        String password = dbHelper.forgetPassword(user);
        if (isPhoneNum(phoneNum)){
            if (password==null)
                return false;
            else
                Toast.makeText(MyApplication.getContext(),"您的密码为："+password,Toast.LENGTH_SHORT).show();
                return true;
        }else
            return false;

    }

    public static boolean isPasswordStyle(String password) {

        if (TextUtils.isEmpty(password)){
            Toast.makeText(MyApplication.getContext(),"请输入密码！",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            if (password.matches(password_Z))
                return true;
            else {
                Toast.makeText(MyApplication.getContext(), "请输入至少9位不含字符的密码！", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }
}
