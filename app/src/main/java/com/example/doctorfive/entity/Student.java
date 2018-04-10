package com.example.doctorfive.entity;

import java.io.Serializable;

/**
 * Created by DoctorFive on 2017/12/11.
 * 学生实体类
 */

public class Student implements Serializable {
    private String stuNum;
    private String name;
    private String className;
    private String stuPassword;
    private String phoneNum;
    //private String timetableID;//课程表id外键




    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStuPassword() {
        return stuPassword;
    }

    public void setStuPassword(String stuPassword) {
        this.stuPassword = stuPassword;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}

