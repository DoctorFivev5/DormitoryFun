package com.example.doctorfive.dbhelp;

import com.example.doctorfive.dormitoryfun.Note;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DoctorFive on 2017/10/12.
 */
//private static final String[] USER_COLUMNS = { "_id", "username" + "username", "password", "name", "sex", "studentNum", "phoneNUM", "email", "school", "state", "dormitoryID"};

public class Student {
    private int id;
    private String username;//用户名
    private String password;//密码
    private String name;//姓名
    private boolean sex;//性别
    private String stuNum;//学号
    private String phoneNum;//手机号
    private String email;//电子邮箱
    private String school;//学校
    private int state; //状态
    private int dormitoryID;//房间号

    //对学号和手机号的类型进行了修改
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
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

    public int getDormitoryID() {
        return dormitoryID;
    }

    public void setDormitoryID(int dormitoryID) {
        this.dormitoryID = dormitoryID;
    }
}
