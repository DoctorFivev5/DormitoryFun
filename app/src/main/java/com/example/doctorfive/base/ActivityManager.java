package com.example.doctorfive.base;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DoctorFive on 2018/4/8.
 */

public class ActivityManager{
    //建立链表集合
    private static List<Activity> activityList = new ArrayList<>();

    //向链表中，添加Activity
    public static void addActivity(Activity activity) {
        Log.e("addActivity",activity.toString());
        activityList.add(activity);
    }

    //向链表中，移除Activity
    public static void removeActivity(Activity activity){
        Log.e("removeActivity",activity.toString());
        activityList.remove(activity);
    }
    public static void finishAll(){

        //遍历 链表，依次杀掉各个Activity
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                Log.e("finishAll", activity.toString());
                activity.finish();
            }
        }
    }

    //结束整个应用程序
    public static void exit() {
        finishAll();
        //杀掉，这个应用程序的进程，释放 内存
        int id = android.os.Process.myPid();
        if (id != 0) {
            android.os.Process.killProcess(id);
        }
    }
}
