package com.example.doctorfive.entity;

/**
 * Created by DoctorFive on 2017/12/6.
 * 教务在线登陆查询参数实体类
 */

public class MesValue {
    private String __VIEWSTATE;
    private String __EVENTVALIDATION;

    public MesValue(String __VIEWSTATE, String __EVENTVALIDATION) {
        this.__VIEWSTATE = __VIEWSTATE;
        this.__EVENTVALIDATION = __EVENTVALIDATION;
    }

    public String get__VIEWSTATE() {
        return __VIEWSTATE;
    }

    public void set__VIEWSTATE(String __VIEWSTATE) {
        this.__VIEWSTATE = __VIEWSTATE;
    }

    public String get__EVENTVALIDATION() {
        return __EVENTVALIDATION;
    }

    public void set__EVENTVALIDATION(String __EVENTVALIDATION) {
        this.__EVENTVALIDATION = __EVENTVALIDATION;
    }
}
