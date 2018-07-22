package com.example.doctorfive.entity;

/**
 * Created by DoctorFive on 2018/5/19.
 */

public class DormitoryAndUser {
   private Dormitory dormitory;
   private User user;

    public DormitoryAndUser() {
    }

    public DormitoryAndUser(Dormitory dormitory, User user) {
        this.dormitory = dormitory;
        this.user = user;
    }

    public Dormitory getDormitory() {
        return dormitory;
    }

    public void setDormitory(Dormitory dormitory) {
        this.dormitory = dormitory;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
