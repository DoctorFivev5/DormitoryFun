package com.example.doctorfive.util;

/**
 * Created by DoctorFive on 2018/1/3.
 */

public class CalendarUtil {
    public static String dateFormat(int year, int month, int day){
        String date = "" + year;
        month++;
        if (month < 10)
            date = date + 0 + month;
        else
            date = date + month;
        if (day < 10)
            date = date + 0 +day;
        else
            date = date + day;
        return date;

    }

    public static String timeFormat(int hour, int minute){
        String time = "";
        if (hour < 10)
            time = time + 0 + hour + ":";
        else
            time = time + hour + ":";
        if (minute < 10)
            time = time + 0 + minute;
        else
            time = time + minute;
        return time;
    }
}
