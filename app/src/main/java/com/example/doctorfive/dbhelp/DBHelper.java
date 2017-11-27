package com.example.doctorfive.dbhelp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.doctorfive.dormitoryfun.MyApplication;

/**
 * Created by DoctorFive on 2017/11/21.
 */

public class DBHelper {
    private static final String DATABASE_NAME = "datastorage1";// 保存数据库名称
    private static final int DATABASE_VERSION = 1;// 保存数据库版本号
    private static final String[] TABLE_NAME = {"Student"};// 保存表名称
    private static final String[] USER_COLUMNS = { "id","username", "password", "name", "sex", "studentNum", "phoneNum", "email", "school", "state", "dormitoryID"};
    private DBOpenHelper helper;
    private SQLiteDatabase db;

    private static class DBOpenHelper extends SQLiteOpenHelper {
        //定义student表sql语句
        private static final String CREATE_TABLE = "create table " + TABLE_NAME[0] + " ( " + USER_COLUMNS[0] + " integer primary key autoincrement, " + USER_COLUMNS[1] + " char(60),"+USER_COLUMNS[2]
                + " varchar(20), " + USER_COLUMNS[3] + " char(30), " + USER_COLUMNS[4] + " bit, " + USER_COLUMNS[5] + " varchar(12), " + USER_COLUMNS[6] + " varchar(15), " + USER_COLUMNS[7] + " varchar(50), " + USER_COLUMNS[8] + " char(50), " + USER_COLUMNS[9] + " integer, " + USER_COLUMNS[10] + " integer);";// 定义创建表格的SQL语句

        public DBOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_TABLE);// 创建表格
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
    public void insert(Student student) {// 向表格中插入数据
        ContentValues values = new ContentValues();
        values.put(USER_COLUMNS[1], student.getUsername());
        values.put(USER_COLUMNS[2], student.getPassword());
        values.put(USER_COLUMNS[3], student.getName());
        values.put(USER_COLUMNS[4], student.isSex());
        values.put(USER_COLUMNS[5], student.getStuNum());
        values.put(USER_COLUMNS[6], student.getPhoneNum());
        values.put(USER_COLUMNS[7], student.getEmail());
        values.put(USER_COLUMNS[8], student.getSchool());
        values.put(USER_COLUMNS[9], student.getState());
        values.put(USER_COLUMNS[10], student.getDormitoryID());
        db.insert(TABLE_NAME[0], null, values);
    }

    public void export(Cursor cursor, Student student) {// 向表格中插入数据
        if(cursor.moveToFirst()) {
            student.setUsername(cursor.getString(cursor.getColumnIndex("username")));// android.database.CursorIndexOutOfBoundsException: Index 0 requested, with a size of 0
            student.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            student.setName(cursor.getString(cursor.getColumnIndex("name")));
            student.setSex(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("sex"))));
            student.setStuNum(cursor.getString(cursor.getColumnIndex("studentNum")));
            student.setPhoneNum(cursor.getString(cursor.getColumnIndex("phoneNum")));
            student.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            student.setSchool(cursor.getString(cursor.getColumnIndex("school")));
            student.setState(Integer.parseInt(cursor.getString(cursor.getColumnIndex("state"))));
            student.setDormitoryID(cursor.getString(cursor.getColumnIndex("dormitoryID")));
            /*
            Toast.makeText(MyApplication.getContext(),cursor.getString(0),Toast.LENGTH_SHORT).show();
            Toast.makeText(MyApplication.getContext(),cursor.getString(1),Toast.LENGTH_SHORT).show();
            Toast.makeText(MyApplication.getContext(),cursor.getString(2),Toast.LENGTH_SHORT).show();
            Toast.makeText(MyApplication.getContext(),cursor.getString(3),Toast.LENGTH_SHORT).show();
            Toast.makeText(MyApplication.getContext(),cursor.getString(4),Toast.LENGTH_SHORT).show();
            Toast.makeText(MyApplication.getContext(),cursor.getString(5),Toast.LENGTH_SHORT).show();
            Toast.makeText(MyApplication.getContext(),cursor.getString(6),Toast.LENGTH_SHORT).show();
            Toast.makeText(MyApplication.getContext(),cursor.getString(7),Toast.LENGTH_SHORT).show();
            Toast.makeText(MyApplication.getContext(),cursor.getString(8),Toast.LENGTH_SHORT).show();
            Toast.makeText(MyApplication.getContext(),cursor.getString(9),Toast.LENGTH_SHORT).show();
            Toast.makeText(MyApplication.getContext(),cursor.getString(10),Toast.LENGTH_SHORT).show();
            */

        }else
        {
            Toast.makeText(MyApplication.getContext(),"没读到",Toast.LENGTH_SHORT).show();
        }
    }

//    private static final String[] USER_COLUMNS = { " id","username", "password", "name", "sex", "studentNum", "phoneNum", "email", "school", "state", "dormitoryID"};

    public SQLiteDatabase getDb(){
        return db;
    }
}
