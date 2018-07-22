package com.example.doctorfive.entity;

public class Dormitory {
	private String dormitory_id;
	private String dormitory_name;
	private int dormitory_num;
	private String dormitory_text;
	private String dormitory_password;
	
	public Dormitory() {
		super();
	}
	public Dormitory(String dormitory_id, String dormitory_name, int dormitory_num, String dormitory_text,
			String dormitory_password) {
		super();
		this.dormitory_id = dormitory_id;
		this.dormitory_name = dormitory_name;
		this.dormitory_num = dormitory_num;
		this.dormitory_text = dormitory_text;
		this.dormitory_password = dormitory_password;
	}
	public String getDormitory_id() {
		return dormitory_id;
	}
	public void setDormitory_id(String dormitory_id) {
		this.dormitory_id = dormitory_id;
	}
	public String getDormitory_name() {
		return dormitory_name;
	}
	public void setDormitory_name(String dormitory_name) {
		this.dormitory_name = dormitory_name;
	}
	public int getDormitory_num() {
		return dormitory_num;
	}
	public void setDormitory_num(int dormitory_num) {
		this.dormitory_num = dormitory_num;
	}
	public String getDormitory_text() {
		return dormitory_text;
	}
	public void setDormitory_text(String dormitory_text) {
		this.dormitory_text = dormitory_text;
	}
	public String getDormitory_password() {
		return dormitory_password;
	}
	public void setDormitory_password(String dormitory_password) {
		this.dormitory_password = dormitory_password;
	}
	@Override
	public String toString() {
		return "Dormitory [dormitory_id=" + dormitory_id + ", dormitory_name=" + dormitory_name + ", dormitory_num="
				+ dormitory_num + ", dormitory_text=" + dormitory_text + ", dormitory_password=" + dormitory_password
				+ "]";
	}
	
	
	
}
