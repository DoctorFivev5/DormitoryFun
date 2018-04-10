package com.example.doctorfive.entity;

import java.io.Serializable;

/**
 * Created by DoctorFive on 2018/3/31.
 */

public class MessagAndObject implements Serializable {
    private String resultCode;
    private User data;

    public MessagAndObject(){
        super();
    }

    public MessagAndObject(String resultCode, User data) {
        super();
        this.resultCode = resultCode;
        this.data = data;
    }
    public String getResultCode() {
        return resultCode;
    }
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
    public User getData() {
        return data;
    }
    public void setData(User data) {
        this.data = data;
    }
}
