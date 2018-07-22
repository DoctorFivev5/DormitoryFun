package com.example.doctorfive.entity;

public class MessageAndObject {
	private String resultCode;
    private Object data;

    public MessageAndObject(){
        super();
    }

    public MessageAndObject(String resultCode, Object data) {
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
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
}
