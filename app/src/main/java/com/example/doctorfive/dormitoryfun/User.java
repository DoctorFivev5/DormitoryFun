package com.example.doctorfive.dormitoryfun;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DoctorFive on 2017/10/12.
 */

public class User extends DataSupport implements Serializable {
    private int id;
    private String username;//用户名
    private String password;//密码
    private String sex;//性别
    private String name;//姓名
    private String phoneNum;//手机号
    private String school;//学校
    private String stuNum;//学号
    private String dorNum;//宿舍楼栋
    private String roomNum;//房间号
    private String email;//电子邮箱
    private byte[] headshot;//头像
    private List<Note> commentList = new ArrayList<Note>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public String getDorNum() {
        return dorNum;
    }

    public void setDorNum(String dorNum) {
        this.dorNum = dorNum;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getHeadshot() {
        return headshot;
    }

    public void setHeadshot(byte[] headshot) {
        this.headshot = headshot;
    }
}
