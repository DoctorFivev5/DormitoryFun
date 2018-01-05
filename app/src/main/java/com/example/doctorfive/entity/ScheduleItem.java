package com.example.doctorfive.entity;

/**
 * Created by DoctorFive on 2017/12/27.
 * 显示在日程界面的list项
 */

public class ScheduleItem {
    private int scheduleId; //用来保存日程id
    private int typeImageId;//日程类型图片
    private String schedulTitle;//日程标题
    private String schedulTime;//日程时间
    private int stateImageId;//跳转图标

    public ScheduleItem(int scheduleId, int typeImageId, String schedulTitle, String schedulTime, int stateImageId) {
        this.scheduleId = scheduleId;
        this.typeImageId = typeImageId;
        this.schedulTitle = schedulTitle;
        this.schedulTime = schedulTime;
        this.stateImageId = stateImageId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getTypeImageId() {
        return typeImageId;
    }

    public String getSchedulTime() {
        return schedulTime;
    }

    public void setSchedulTime(String schedulTime) {
        this.schedulTime = schedulTime;
    }

    public String getSchedulTitle() {
        return schedulTitle;
    }

    public void setSchedulTitle(String schedulTitle) {
        this.schedulTitle = schedulTitle;
    }

    public void setTypeImageId(int typeImageId) {
        this.typeImageId = typeImageId;
    }


    public int getStateImageId() {
        return stateImageId;
    }

    public void setStateImageId(int stateImageId) {
        this.stateImageId = stateImageId;
    }
}
