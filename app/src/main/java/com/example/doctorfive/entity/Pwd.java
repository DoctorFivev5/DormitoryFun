package com.example.doctorfive.entity;

/**
 * Created by DoctorFive on 2018/3/21.
 */

public class Pwd {
    private String phoneNum;
    private String password;

    public Pwd(String phoneNum, String password){
        this.phoneNum = phoneNum;
        this.password = password;
    }
    public String getPhoneNum() {
        return phoneNum;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
