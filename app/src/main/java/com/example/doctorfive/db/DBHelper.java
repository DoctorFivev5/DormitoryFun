package com.example.doctorfive.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.doctorfive.base.MyApplication;
import com.example.doctorfive.entity.Student;
import com.example.doctorfive.entity.User;

/**
 * Created by DoctorFive on 2017/11/21.
 * 数据库操作类
 */

public class DBHelper {
    private static final String DATABASE_NAME = "datastorage1";// 保存数据库名称
    private static final int DATABASE_VERSION = 1;// 保存数据库版本号
    private static final String[] TABLE_NAME = {"User", "Student"};// 保存表名称
    private static final String[] USER_COLUMNS = { "user_id","username", "password", "phoneNum" , "sex", "email", "school","userIcon", "stuNum","state", "dormitoryID"};
    private static final String[] STUDENT_COLUMNS = {"studentNum", "name", "className", "stuPassword", "classArray"};//缺少课表id
    private DBOpenHelper helper;
    private SQLiteDatabase db;

    private static class DBOpenHelper extends SQLiteOpenHelper {
        //定义student表sql语句
        private static final String CREATE_TABLE_USER = "create table " + TABLE_NAME[0]
                + " ( " + USER_COLUMNS[0] + " integer primary key autoincrement, "
                + USER_COLUMNS[1] + " varchar(60),"
                + USER_COLUMNS[2] + " varchar(20), "
                + USER_COLUMNS[3] + " varchar(15), "
                + USER_COLUMNS[4] + " char(4), "
                + USER_COLUMNS[5] + " varchar(50), "
                + USER_COLUMNS[6] + " varchar(50), "
                + USER_COLUMNS[7] + " varchar(256), "
                + USER_COLUMNS[8] + " char(12), "
                + USER_COLUMNS[9] + " integer, "
                + USER_COLUMNS[10] + " integer);";// 定义创建user表格的SQL语句

        private static final String CREATE_TABLE_STUDENT = "create table " + TABLE_NAME[1]
                + " ( " + STUDENT_COLUMNS[0] + " char(12) primary key, "
                + STUDENT_COLUMNS[1] + " varchar(60),"
                + STUDENT_COLUMNS[2] + " varchar(100), "
                + STUDENT_COLUMNS[3] + " varchar(20), "
                + STUDENT_COLUMNS[4] + " text); ";// 定义创建Student表格的SQL语句
        public DBOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_TABLE_USER);// 创建User表格
            sqLiteDatabase.execSQL(CREATE_TABLE_STUDENT);// 创建Student表格
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }

    public DBHelper(Context context){
        helper = new DBOpenHelper(context);// 创建SQLiteOpenHelper对象
        db = helper.getWritableDatabase();// 获得可写的数据库
    }

    //增删改差方法
    public void insert(User user) {// 向user表格中插入数据
        ContentValues values = new ContentValues();
        values.put(USER_COLUMNS[1], user.getUsername());
        values.put(USER_COLUMNS[2], user.getPassword());
        values.put(USER_COLUMNS[3], user.getPhoneNum());
        values.put(USER_COLUMNS[4], user.isSex());
        values.put(USER_COLUMNS[5], user.getEmail());
        values.put(USER_COLUMNS[6], user.getSchool());
        values.put(USER_COLUMNS[7], user.getIcon());
        values.put(USER_COLUMNS[8], user.getStuNum());
        values.put(USER_COLUMNS[9], user.getState());
        values.put(USER_COLUMNS[10], user.getDormitoryID());
        db.insert(TABLE_NAME[0], null, values);
    }

    public void insert(Student student) {// 向student表格中插入数据
        ContentValues values = new ContentValues();
        values.put(STUDENT_COLUMNS[0], student.getStuNum());
        values.put(STUDENT_COLUMNS[1], student.getName());
        values.put(STUDENT_COLUMNS[2], student.getClassName());
        values.put(STUDENT_COLUMNS[3], student.getStuPassword());
        values.put(STUDENT_COLUMNS[4], student.getClassArray());
        db.insert(TABLE_NAME[1], null, values);
    }

    public Boolean checkHavePhoneNum(String phoneNum){//查询手机号是否被注册
        Cursor cursor = db.rawQuery("select phoneNum from User where phoneNum=?", new String[]{phoneNum});
        if (cursor.moveToFirst()){
            Toast.makeText(MyApplication.getContext(), "该手机号已经被注册！", Toast.LENGTH_SHORT).show();
            return false;
        }else
            return true;
    }

    public Boolean checkHaveUser(User user){//查询手机号和密码是否匹配
        String phoneNum = user.getPhoneNum();
        String password = user.getPassword();
        Cursor cursor = db.rawQuery("select phoneNum from User where phoneNum=? and password=?", new String []{phoneNum,password});
        if (cursor.moveToFirst()){
            return true;
        }else
            return false;
    }

    public String forgetPassword(User user){//查询手机号和学号是否匹配
        String password;
        String phoneNum = user.getPhoneNum();
        String stuNum = user.getStuNum();
        Cursor cursor = db.rawQuery("select phoneNum, studentNum, password from User where phoneNum=? and studentNum=?", new String[]{phoneNum,stuNum});
        if (cursor.moveToFirst()){
            password = cursor.getString(cursor.getColumnIndex("password"));
            return password;
        }else
            return null;
    }


    public User export(User user) {//bianlishuju
        String phoneNum = user.getPhoneNum();
        Cursor cursor = db.rawQuery("select * from User where phoneNum=?", new String[]{phoneNum});
        if(cursor.moveToFirst()) {
            user.setUsername(cursor.getString(cursor.getColumnIndex("username")));// android.database.CursorIndexOutOfBoundsException: Index 0 requested, with a size of 0
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            user.setPhoneNum(cursor.getString(cursor.getColumnIndex("phoneNum")));
            user.setSex(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("sex"))));
            user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            user.setSchool(cursor.getString(cursor.getColumnIndex("school")));
            user.setIcon(cursor.getString(cursor.getColumnIndex("userIcon")));
            user.setStuNum(cursor.getString(cursor.getColumnIndex("stuNum")));
            user.setState(Integer.parseInt(cursor.getString(cursor.getColumnIndex("state"))));
            user.setDormitoryID(cursor.getString(cursor.getColumnIndex("dormitoryID")));
            return user;
        }else
        {
            Toast.makeText(MyApplication.getContext(),"未知错误",Toast.LENGTH_SHORT).show();
            return user;
        }
    }


    public Student export(Student student){
        String stuNum = student.getStuNum();
        if (stuNum==null)
            return null;
        Cursor cursor = db.rawQuery("selecm Student where studentNum=?", new String[]{stuNum});
        if(cursor.moveToFirst()) {
            student.setStuNum(cursor.getString(cursor.getColumnIndex("username")));// android.database.CursorIndexOutOfBoundsException: Index 0 requested, with a size of 0
            student.setName(cursor.getString(cursor.getColumnIndex("name")));
            student.setClassName(cursor.getString(cursor.getColumnIndex("className")));
            student.setStuPassword(cursor.getString(cursor.getColumnIndex("stuPassword")));
            student.setClassArray(cursor.getString(cursor.getColumnIndex("classArray")));
            return student;
        }else
        {
            Toast.makeText(MyApplication.getContext(),"未知错误",Toast.LENGTH_SHORT).show();
            return null;
        }
    }


//    private static final String[] USER_COLUMNS = { " id","username", "password", "name", "sex", "studentNum", "phoneNum", "email", "school", "state", "dormitoryID"};

    public SQLiteDatabase getDb(){
        return db;
    }
}

/*
private static final String CREATE_TABLE_USER = "create table " + TABLE_NAME[0]
                + " ( " + USER_COLUMNS[0] + " integer primary key autoincrement, "
                + USER_COLUMNS[1] + " varchar(60) default '傻逼',"
                + USER_COLUMNS[2] + " varchar(20), "
                + USER_COLUMNS[3] + " varchar(15), "
                + USER_COLUMNS[4] + " char(4) default '女', "
                + USER_COLUMNS[5] + " varchar(50) default 'xxx@null.com', "
                + USER_COLUMNS[6] + " varchar(50) default '江西师范大学', "
                + USER_COLUMNS[7] + " varchar(256) default 'null', "
                + USER_COLUMNS[8] + " char(12) default 'null', "
                + USER_COLUMNS[9] + " integer default 1, "
                + USER_COLUMNS[10] + " integer default 0);";// 定义创建user表格的SQL语句
 */