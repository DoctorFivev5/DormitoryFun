package com.example.doctorfive.entity;

import java.util.Date;

/**
 * 用户消息javabean
 * Created by DoctorFive on 2018/5/20.
 */

public class UserMessage {
    private int message_id;
    private String message_text;
    private int message_state;
    private String message_time;
    private String message_from;
    private String message_to;
    private String message_is_dormitory;


    public UserMessage() {
        super();
    }
    public UserMessage(int message_id, String message_text, int message_state, String message_time, String message_from,
                       String message_to, String message_is_dormitory) {
        super();
        this.message_id = message_id;
        this.message_text = message_text;
        this.message_state = message_state;
        this.message_time = message_time;
        this.message_from = message_from;
        this.message_to = message_to;
        this.message_is_dormitory = message_is_dormitory;
    }
    public int getMessage_id() {
        return message_id;
    }
    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }
    public String getMessage_text() {
        return message_text;
    }
    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }
    public int getMessage_state() {
        return message_state;
    }
    public void setMessage_state(int message_state) {
        this.message_state = message_state;
    }
    public String getMessage_time() {
        return message_time;
    }
    public void setMessage_time(String message_time) {
        this.message_time = message_time;
    }
    public String getMessage_from() {
        return message_from;
    }
    public void setMessage_from(String message_from) {
        this.message_from = message_from;
    }
    public String getMessage_to() {
        return message_to;
    }
    public void setMessage_to(String message_to) {
        this.message_to = message_to;
    }
    public String getMessage_is_dormitory() {
        return message_is_dormitory;
    }
    public void setMessage_is_dormitory(String message_is_dormitory) {
        this.message_is_dormitory = message_is_dormitory;
    }
    @Override
    public String toString() {
        return "UserMessage [message_id=" + message_id + ", message_text=" + message_text + ", message_state="
                + message_state + ", message_time=" + message_time + ", message_from=" + message_from + ", message_to="
                + message_to + ", message_is_dormitory=" + message_is_dormitory + "]";
    }

}
