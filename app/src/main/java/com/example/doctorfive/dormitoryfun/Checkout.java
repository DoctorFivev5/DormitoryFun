package com.example.doctorfive.dormitoryfun;

import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by DoctorFive on 2017/10/16.
 */

public class Checkout {

    private static String phoneNum_Z = "^1[3|4|5|7|8][0-9]{9}$";
    private static String password_Z = "^[0-9a-zA-Z]{9,16}$";

    public static boolean isMobileNO(String mobiles) {
        if (TextUtils.isEmpty(mobiles)){
            Toast.makeText(MyApplication.getContext(),"手机号为空！",Toast.LENGTH_SHORT).show();
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

    public static boolean isPasswordO(String password) {

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
