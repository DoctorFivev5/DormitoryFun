package com.example.doctorfive.entity;

/**
 * Created by DoctorFive on 2017/12/18.
 */

public class Optionitem {
    private String name;
    private int imageId;


    public Optionitem(String name,int imageId){
        this.name = name;
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }
}
