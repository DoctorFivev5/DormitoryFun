package com.example.doctorfive.entity;

/**
 * Created by DoctorFive on 2018/4/5.
 */

public class CourseItem {
    private int courseTime;
    private String courseName;
    private String courseRoom;

    public CourseItem(int courseTime, String courseName, String courseRoom) {
        this.courseTime = courseTime;
        this.courseName = courseName;
        this.courseRoom = courseRoom;
    }

    public int getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(int courseTime) {
        this.courseTime = courseTime;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseRoom() {
        return courseRoom;
    }

    public void setCourseRoom(String courseRoom) {
        this.courseRoom = courseRoom;
    }
}
