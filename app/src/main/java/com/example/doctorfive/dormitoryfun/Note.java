package com.example.doctorfive.dormitoryfun;

import android.widget.Toast;

//import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by DoctorFive on 2017/10/21.
 */

public class Note  implements Serializable {
    private String text;
    private String calendar;

    public Note(String text){
        this.text = text;
        setCalendar();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCalendar() {
        return calendar;
    }

    public void setCalendar() {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        calendar = month+"月"+day+"日"+"  "+hour+":"+minute;
        //Toast.makeText(MyApplication.getContext(),calendar)
    }
}
