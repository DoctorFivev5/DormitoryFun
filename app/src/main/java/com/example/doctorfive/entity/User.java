package com.example.doctorfive.entity;

import java.io.Serializable;

/**
 * Created by DoctorFive on 2017/10/12.
 * 用户实体类
 */

public class User implements Serializable {
    private int id;
    private String username;//用户名
    private String password;//密码
    private String phoneNum;//手机号
    private boolean sex;//性别
    private String email;//电子邮箱
    private String school;//学校
    private String icon;//头像路径
    private String stuNum;//学号
    private int state; //状态
    private String  dormitoryID;//房间号
    private String autograph;   //签名

    //对学号和手机号和房间号的类型进行了修改
    //修改类型从int 改为 string


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public String getDormitoryID() {
        return dormitoryID;
    }

    public void setDormitoryID(String dormitoryID) {
        this.dormitoryID = dormitoryID;
    }

    public String getAutograph() {
        return autograph;
    }

    public void setAutograph(String autograph) {
        this.autograph = autograph;
    }
}
