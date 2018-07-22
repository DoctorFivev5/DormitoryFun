package com.example.doctorfive.entity;

public class Power {
	private int power_id;
	private float power_fee;
	private float power_kwh;
	private String power_dormitory_id;

	public int getPower_id() {
		return power_id;
	}
	public void setPower_id(int power_id) {
		this.power_id = power_id;
	}
	public float getPower_fee() {
		return power_fee;
	}
	public void setPower_fee(float power_fee) {
		this.power_fee = power_fee;
	}
	public float getPower_kwh() {
		return power_kwh;
	}
	public void setPower_kwh(float power_kwh) {
		this.power_kwh = power_kwh;
	}
	public String getPower_dormitory_id() {
		return power_dormitory_id;
	}
	public void setPower_dormitory_id(String power_dormitory_id) {
		this.power_dormitory_id = power_dormitory_id;
	}

	@Override
	public String toString() {
		return "Power{" +
				"power_id=" + power_id +
				", power_fee=" + power_fee +
				", power_kwh=" + power_kwh +
				", power_dormitory_id='" + power_dormitory_id + '\'' +
				'}';
	}
}
