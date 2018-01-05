package com.example.doctorfive.entity;

import java.io.Serializable;

/**
 * Created by DoctorFive on 2017/12/31.
 * 日程详情
 */

public class Schedule implements Serializable {
    private int id;             //日程id
    private String day;            //记录为何天的日程  格式为20170919
    private String type;        //日程基本类型
    private String title;       //日程标题
    private String startTime;   //日程开始时间
    private String remindTime;  //日程持续时间
    private String remarks;     //备注
    private int userID;         //外键 user表id

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
