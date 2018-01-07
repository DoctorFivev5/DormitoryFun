package com.example.doctorfive.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.doctorfive.base.MyApplication;
import com.example.doctorfive.entity.Schedule;
import com.example.doctorfive.entity.Student;
import com.example.doctorfive.entity.Timetable;
import com.example.doctorfive.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DoctorFive on 2017/11/21.
 * 数据库操作类
 */


public class DBHelper {
    private static final String DATABASE_NAME = "datastorage1";// 保存数据库名称
    private static final int DATABASE_VERSION = 2;// 保存数据库版本号
    private static final String[] TABLE_NAME = {"User", "Student","Timetable","Schedule"};// 保存表名称
    private static final String[] USER_COLUMNS = { "user_id","username", "password", "phoneNum" , "sex", "email", "school", "userIcon", "stuNum","state", "dormitoryID", "autograph"};
    private static final String[] STUDENT_COLUMNS = {"studentNum", "name", "className", "stuPassword"};//缺少课表id
    private static final String[] TIMETABLE_COLUMNS = {"class_id", "class1", "class2", "class3", "class4", "class5", "class6", "class7", "class8", "class9", "class10", "class11", "class12", "class13", "class14", "class15", "class16", "class17", "class18", "class19", "class20", "class21", "class22", "class23", "class24", "class25", "class26", "class27", "class28", "class29", "class30", "class31", "class32", "class33", "class34", "class35", "class36", "class37", "class38", "class39", "class40", "class41", "class42", "class43", "class44", "class45", "class46", "class47", "class48", "class49", "stuNum", "term"};
    private static final String[] SCHEDULE_COLUMNS ={"schedule_id", "day", "type", "title", "startTime", "overtime", "remarks", "user_id"};
    private DBOpenHelper helper;
    private SQLiteDatabase db;

    private static class DBOpenHelper extends SQLiteOpenHelper {
        //定义student表sql语句
        private static final String CREATE_TABLE_USER = "create table " + TABLE_NAME[0]
                + " ( " + USER_COLUMNS[0] + " integer primary key autoincrement, "
                + USER_COLUMNS[1] + " varchar(60) ,"
                + USER_COLUMNS[2] + " varchar(20) , "
                + USER_COLUMNS[3] + " varchar(15) , "
                + USER_COLUMNS[4] + " char(4), "
                + USER_COLUMNS[5] + " varchar(50) , "
                + USER_COLUMNS[6] + " varchar(50), "
                + USER_COLUMNS[7] + " varchar(256), "
                + USER_COLUMNS[8] + " char(12), "
                + USER_COLUMNS[9] + " integer , "
                + USER_COLUMNS[10] + " integer ,"
                + USER_COLUMNS[11] + " Text);";// 定义创建user表格的SQL语句

        private static final String CREATE_TABLE_STUDENT = "create table " + TABLE_NAME[1]
                + " ( " + STUDENT_COLUMNS[0] + " char(12) primary key, "
                + STUDENT_COLUMNS[1] + " varchar(60),"
                + STUDENT_COLUMNS[2] + " varchar(100), "
                + STUDENT_COLUMNS[3] + " varchar(20)); ";// 定义创建Student表格的SQL语句

        private static final String CREATE_TABLE_TIMETABLE = "create table " + TABLE_NAME[2]
                + " ( " + TIMETABLE_COLUMNS[0] + " integer primary key autoincrement, "
                + TIMETABLE_COLUMNS[1] + " varchar(100),"
                + TIMETABLE_COLUMNS[2] + " varchar(100),"
                + TIMETABLE_COLUMNS[3] + " varchar(100),"
                + TIMETABLE_COLUMNS[4] + " varchar(100),"
                + TIMETABLE_COLUMNS[5] + " varchar(100),"
                + TIMETABLE_COLUMNS[6] + " varchar(100),"
                + TIMETABLE_COLUMNS[7] + " varchar(100),"
                + TIMETABLE_COLUMNS[8] + " varchar(100),"
                + TIMETABLE_COLUMNS[9] + " varchar(100),"
                + TIMETABLE_COLUMNS[10] + " varchar(100),"
                + TIMETABLE_COLUMNS[11] + " varchar(100),"
                + TIMETABLE_COLUMNS[12] + " varchar(100),"
                + TIMETABLE_COLUMNS[13] + " varchar(100),"
                + TIMETABLE_COLUMNS[14] + " varchar(100),"
                + TIMETABLE_COLUMNS[15] + " varchar(100),"
                + TIMETABLE_COLUMNS[16] + " varchar(100),"
                + TIMETABLE_COLUMNS[17] + " varchar(100),"
                + TIMETABLE_COLUMNS[18] + " varchar(100),"
                + TIMETABLE_COLUMNS[19] + " varchar(100),"
                + TIMETABLE_COLUMNS[20] + " varchar(100),"
                + TIMETABLE_COLUMNS[21] + " varchar(100),"
                + TIMETABLE_COLUMNS[22] + " varchar(100),"
                + TIMETABLE_COLUMNS[23] + " varchar(100),"
                + TIMETABLE_COLUMNS[24] + " varchar(100),"
                + TIMETABLE_COLUMNS[25] + " varchar(100),"
                + TIMETABLE_COLUMNS[26] + " varchar(100),"
                + TIMETABLE_COLUMNS[27] + " varchar(100),"
                + TIMETABLE_COLUMNS[28] + " varchar(100),"
                + TIMETABLE_COLUMNS[29] + " varchar(100),"
                + TIMETABLE_COLUMNS[30] + " varchar(100),"
                + TIMETABLE_COLUMNS[31] + " varchar(100),"
                + TIMETABLE_COLUMNS[32] + " varchar(100),"
                + TIMETABLE_COLUMNS[33] + " varchar(100),"
                + TIMETABLE_COLUMNS[34] + " varchar(100),"
                + TIMETABLE_COLUMNS[35] + " varchar(100),"
                + TIMETABLE_COLUMNS[36] + " varchar(100),"
                + TIMETABLE_COLUMNS[37] + " varchar(100),"
                + TIMETABLE_COLUMNS[38] + " varchar(100),"
                + TIMETABLE_COLUMNS[39] + " varchar(100),"
                + TIMETABLE_COLUMNS[40] + " varchar(100),"
                + TIMETABLE_COLUMNS[41] + " varchar(100),"
                + TIMETABLE_COLUMNS[42] + " varchar(100),"
                + TIMETABLE_COLUMNS[43] + " varchar(100),"
                + TIMETABLE_COLUMNS[44] + " varchar(100),"
                + TIMETABLE_COLUMNS[45] + " varchar(100),"
                + TIMETABLE_COLUMNS[46] + " varchar(100),"
                + TIMETABLE_COLUMNS[47] + " varchar(100),"
                + TIMETABLE_COLUMNS[48] + " varchar(100),"
                + TIMETABLE_COLUMNS[49] + " varchar(100),"
                + TIMETABLE_COLUMNS[50] + " varchar(100),"
                + TIMETABLE_COLUMNS[51] + " int); ";// 定义创建Timetable表格的SQL语句

        private static final String CREATE_TABLE_SCHEDULE = "create table " + TABLE_NAME[3]
                + " ( " + SCHEDULE_COLUMNS[0] + " integer primary key autoincrement, "
                + SCHEDULE_COLUMNS[1] + " varchar(8), "
                + SCHEDULE_COLUMNS[2] + " varchar(20),"
                + SCHEDULE_COLUMNS[3] + " text, "
                + SCHEDULE_COLUMNS[4] + " varchar(20), "
                + SCHEDULE_COLUMNS[5] + " varchar(10), "
                + SCHEDULE_COLUMNS[6] + " text, "
                + SCHEDULE_COLUMNS[7] + " integer);"; // 定义创建Schedule表格的SQL语句
        public DBOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_TABLE_USER);// 创建User表格
            sqLiteDatabase.execSQL(CREATE_TABLE_STUDENT);// 创建Student表格
            sqLiteDatabase.execSQL(CREATE_TABLE_TIMETABLE);//创建Timetable表格
            sqLiteDatabase.execSQL(CREATE_TABLE_SCHEDULE);//创建Schedule表格
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            //sqLiteDatabase.execSQL(CREATE_TABLE_SCHEDULE);//创建Schedule表格
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
        values.put(USER_COLUMNS[4], user.isSex()?"男":"女");
        values.put(USER_COLUMNS[5], user.getEmail());
        values.put(USER_COLUMNS[6], user.getSchool());
        values.put(USER_COLUMNS[7], user.getIcon());
        values.put(USER_COLUMNS[8], user.getStuNum());
        values.put(USER_COLUMNS[9], user.getState());
        values.put(USER_COLUMNS[10], user.getDormitoryID());
        values.put(USER_COLUMNS[11], user.getAutograph());
        db.insert(TABLE_NAME[0], null, values);
    }

    public void insert(Student student) {// 向student表格中插入数据
        ContentValues values = new ContentValues();
        values.put(STUDENT_COLUMNS[0], student.getStuNum());
        values.put(STUDENT_COLUMNS[1], student.getName());
        values.put(STUDENT_COLUMNS[2], student.getClassName());
        values.put(STUDENT_COLUMNS[3], student.getStuPassword());
        db.insert(TABLE_NAME[1], null, values);
    }

    public void insert(Timetable timetable){
        ContentValues values = new ContentValues();
        values.put(TIMETABLE_COLUMNS[1], timetable.getClass1());
        values.put(TIMETABLE_COLUMNS[2], timetable.getClass2());
        values.put(TIMETABLE_COLUMNS[3], timetable.getClass3());
        values.put(TIMETABLE_COLUMNS[4], timetable.getClass4());
        values.put(TIMETABLE_COLUMNS[5], timetable.getClass5());
        values.put(TIMETABLE_COLUMNS[6], timetable.getClass6());
        values.put(TIMETABLE_COLUMNS[7], timetable.getClass7());
        values.put(TIMETABLE_COLUMNS[8], timetable.getClass8());
        values.put(TIMETABLE_COLUMNS[9], timetable.getClass9());
        values.put(TIMETABLE_COLUMNS[10], timetable.getClass10());
        values.put(TIMETABLE_COLUMNS[11], timetable.getClass11());
        values.put(TIMETABLE_COLUMNS[12], timetable.getClass12());
        values.put(TIMETABLE_COLUMNS[13], timetable.getClass13());
        values.put(TIMETABLE_COLUMNS[14], timetable.getClass14());
        values.put(TIMETABLE_COLUMNS[15], timetable.getClass15());
        values.put(TIMETABLE_COLUMNS[16], timetable.getClass16());
        values.put(TIMETABLE_COLUMNS[17], timetable.getClass17());
        values.put(TIMETABLE_COLUMNS[18], timetable.getClass18());
        values.put(TIMETABLE_COLUMNS[19], timetable.getClass19());
        values.put(TIMETABLE_COLUMNS[20], timetable.getClass20());
        values.put(TIMETABLE_COLUMNS[21], timetable.getClass21());
        values.put(TIMETABLE_COLUMNS[22], timetable.getClass22());
        values.put(TIMETABLE_COLUMNS[23], timetable.getClass23());
        values.put(TIMETABLE_COLUMNS[24], timetable.getClass24());
        values.put(TIMETABLE_COLUMNS[25], timetable.getClass25());
        values.put(TIMETABLE_COLUMNS[26], timetable.getClass26());
        values.put(TIMETABLE_COLUMNS[27], timetable.getClass27());
        values.put(TIMETABLE_COLUMNS[28], timetable.getClass28());
        values.put(TIMETABLE_COLUMNS[29], timetable.getClass29());
        values.put(TIMETABLE_COLUMNS[30], timetable.getClass30());
        values.put(TIMETABLE_COLUMNS[31], timetable.getClass31());
        values.put(TIMETABLE_COLUMNS[32], timetable.getClass32());
        values.put(TIMETABLE_COLUMNS[33], timetable.getClass33());
        values.put(TIMETABLE_COLUMNS[34], timetable.getClass34());
        values.put(TIMETABLE_COLUMNS[35], timetable.getClass35());
        values.put(TIMETABLE_COLUMNS[36], timetable.getClass36());
        values.put(TIMETABLE_COLUMNS[37], timetable.getClass37());
        values.put(TIMETABLE_COLUMNS[38], timetable.getClass38());
        values.put(TIMETABLE_COLUMNS[39], timetable.getClass39());
        values.put(TIMETABLE_COLUMNS[40], timetable.getClass40());
        values.put(TIMETABLE_COLUMNS[41], timetable.getClass41());
        values.put(TIMETABLE_COLUMNS[42], timetable.getClass42());
        values.put(TIMETABLE_COLUMNS[43], timetable.getClass43());
        values.put(TIMETABLE_COLUMNS[44], timetable.getClass44());
        values.put(TIMETABLE_COLUMNS[45], timetable.getClass45());
        values.put(TIMETABLE_COLUMNS[46], timetable.getClass46());
        values.put(TIMETABLE_COLUMNS[47], timetable.getClass47());
        values.put(TIMETABLE_COLUMNS[48], timetable.getClass48());
        values.put(TIMETABLE_COLUMNS[49], timetable.getClass49());
        values.put(TIMETABLE_COLUMNS[50], timetable.getStuNum());
        values.put(TIMETABLE_COLUMNS[51], timetable.getTerm());
        db.insert(TABLE_NAME[2], null, values);

    }

    public void insert(Schedule schedule) {// 向student表格中插入数据
        ContentValues values = new ContentValues();
        values.put(SCHEDULE_COLUMNS[1], schedule.getDay());
        values.put(SCHEDULE_COLUMNS[2], schedule.getType());
        values.put(SCHEDULE_COLUMNS[3], schedule.getTitle());
        values.put(SCHEDULE_COLUMNS[4], schedule.getStartTime());
        values.put(SCHEDULE_COLUMNS[5], schedule.getRemindTime());
        values.put(SCHEDULE_COLUMNS[6], schedule.getRemarks());
        values.put(SCHEDULE_COLUMNS[7], schedule.getUserID());
        db.insert(TABLE_NAME[3], null, values);
    }

    public Boolean checkHavePhoneNum(String phoneNum){//查询手机号是否被注册
        Cursor cursor = db.rawQuery("select phoneNum from User where phoneNum=?", new String[]{phoneNum});
        if (cursor.moveToFirst()){
            Toast.makeText(MyApplication.getContext(), "该手机号已经被注册！", Toast.LENGTH_SHORT).show();
            cursor.close();
            return false;
        }else{
            cursor.close();
            return true;
        }

    }

    public Boolean checkHaveUser(User user){//查询手机号和密码是否匹配
        String phoneNum = user.getPhoneNum();
        String password = user.getPassword();
        Cursor cursor = db.rawQuery("select phoneNum from User where phoneNum=? and password=?", new String []{phoneNum,password});
        if (cursor.moveToFirst()){
            cursor.close();
            return true;
        }else{
            cursor.close();
            return false;
        }

    }

    public String forgetPassword(User user){//查询手机号和学号是否匹配
        String password;
        String phoneNum = user.getPhoneNum();
        String stuNum = user.getStuNum();
        Cursor cursor = db.rawQuery("select password from User where phoneNum=? and stuNum=?", new String[]{phoneNum,stuNum});
        if (cursor.moveToFirst()){
            password = cursor.getString(cursor.getColumnIndex("password"));
            cursor.close();
            return password;
        }else
            cursor.close();
            return null;
    }


    public User export(User user) {//bianlishuju
        String phoneNum = user.getPhoneNum();
        Cursor cursor = db.rawQuery("select * from User where phoneNum=?", new String[]{phoneNum});
        if(cursor.moveToFirst()) {
            user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("user_id"))));
            user.setUsername(cursor.getString(cursor.getColumnIndex("username")));// android.database.CursorIndexOutOfBoundsException: Index 0 requested, with a size of 0
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            user.setPhoneNum(cursor.getString(cursor.getColumnIndex("phoneNum")));
            if (cursor.getString(cursor.getColumnIndex("sex")).equals("男"))
                user.setSex(true);
            else
                user.setSex(false);
            user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            user.setSchool(cursor.getString(cursor.getColumnIndex("school")));
            user.setIcon(cursor.getString(cursor.getColumnIndex("userIcon")));
            user.setStuNum(cursor.getString(cursor.getColumnIndex("stuNum")));
            user.setState(Integer.parseInt(cursor.getString(cursor.getColumnIndex("state"))));
            user.setDormitoryID(cursor.getString(cursor.getColumnIndex("dormitoryID")));
            user.setAutograph(cursor.getString(cursor.getColumnIndex("autograph")));
            cursor.close();
            return user;
        }else
        {
            cursor.close();
            Toast.makeText(MyApplication.getContext(),"未知错误",Toast.LENGTH_SHORT).show();
            return user;
        }
    }




    public Student export(Student student){
        String stuNum = student.getStuNum();
        if (stuNum==null)
            return null;
        Cursor cursor = db.rawQuery("select * from Student where studentNum=?", new String[]{stuNum});
        if(cursor.moveToFirst()) {
            student.setStuNum(cursor.getString(cursor.getColumnIndex("studentNum")));// android.database.CursorIndexOutOfBoundsException: Index 0 requested, with a size of 0
            student.setName(cursor.getString(cursor.getColumnIndex("name")));
            student.setClassName(cursor.getString(cursor.getColumnIndex("className")));
            student.setStuPassword(cursor.getString(cursor.getColumnIndex("stuPassword")));
            cursor.close();
            return student;
        }else
        {
            cursor.close();
            Toast.makeText(MyApplication.getContext(),"未知错误",Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public Timetable export(String stuNum, int term){
        Timetable timetable = new Timetable();
        Log.e("DBHelper",stuNum+" "+String.valueOf(term));
        Cursor cursor = db.rawQuery("select * from Timetable where stuNum=? and term=?", new String[]{stuNum,String.valueOf(term)});
        if(cursor.moveToFirst()) {
            timetable.setId(cursor.getInt(cursor.getColumnIndex("class_id")));
            timetable.setClass1(cursor.getString(cursor.getColumnIndex("class1")));
            timetable.setClass2(cursor.getString(cursor.getColumnIndex("class2")));
            timetable.setClass3(cursor.getString(cursor.getColumnIndex("class3")));
            timetable.setClass4(cursor.getString(cursor.getColumnIndex("class4")));
            timetable.setClass5(cursor.getString(cursor.getColumnIndex("class5")));
            timetable.setClass6(cursor.getString(cursor.getColumnIndex("class6")));
            timetable.setClass7(cursor.getString(cursor.getColumnIndex("class7")));
            timetable.setClass8(cursor.getString(cursor.getColumnIndex("class8")));
            timetable.setClass9(cursor.getString(cursor.getColumnIndex("class9")));
            timetable.setClass10(cursor.getString(cursor.getColumnIndex("class10")));
            timetable.setClass11(cursor.getString(cursor.getColumnIndex("class11")));
            timetable.setClass12(cursor.getString(cursor.getColumnIndex("class12")));
            timetable.setClass13(cursor.getString(cursor.getColumnIndex("class13")));
            timetable.setClass14(cursor.getString(cursor.getColumnIndex("class14")));
            timetable.setClass15(cursor.getString(cursor.getColumnIndex("class15")));
            timetable.setClass16(cursor.getString(cursor.getColumnIndex("class16")));
            timetable.setClass17(cursor.getString(cursor.getColumnIndex("class17")));
            timetable.setClass18(cursor.getString(cursor.getColumnIndex("class18")));
            timetable.setClass19(cursor.getString(cursor.getColumnIndex("class19")));
            timetable.setClass20(cursor.getString(cursor.getColumnIndex("class20")));
            timetable.setClass21(cursor.getString(cursor.getColumnIndex("class21")));
            timetable.setClass22(cursor.getString(cursor.getColumnIndex("class22")));
            timetable.setClass23(cursor.getString(cursor.getColumnIndex("class23")));
            timetable.setClass24(cursor.getString(cursor.getColumnIndex("class24")));
            timetable.setClass25(cursor.getString(cursor.getColumnIndex("class25")));
            timetable.setClass26(cursor.getString(cursor.getColumnIndex("class26")));
            timetable.setClass27(cursor.getString(cursor.getColumnIndex("class27")));
            timetable.setClass28(cursor.getString(cursor.getColumnIndex("class28")));
            timetable.setClass29(cursor.getString(cursor.getColumnIndex("class29")));
            timetable.setClass30(cursor.getString(cursor.getColumnIndex("class30")));
            timetable.setClass31(cursor.getString(cursor.getColumnIndex("class31")));
            timetable.setClass32(cursor.getString(cursor.getColumnIndex("class32")));
            timetable.setClass33(cursor.getString(cursor.getColumnIndex("class33")));
            timetable.setClass34(cursor.getString(cursor.getColumnIndex("class34")));
            timetable.setClass35(cursor.getString(cursor.getColumnIndex("class35")));
            timetable.setClass36(cursor.getString(cursor.getColumnIndex("class36")));
            timetable.setClass37(cursor.getString(cursor.getColumnIndex("class37")));
            timetable.setClass38(cursor.getString(cursor.getColumnIndex("class38")));
            timetable.setClass39(cursor.getString(cursor.getColumnIndex("class39")));
            timetable.setClass40(cursor.getString(cursor.getColumnIndex("class40")));
            timetable.setClass41(cursor.getString(cursor.getColumnIndex("class41")));
            timetable.setClass42(cursor.getString(cursor.getColumnIndex("class42")));
            timetable.setClass43(cursor.getString(cursor.getColumnIndex("class43")));
            timetable.setClass44(cursor.getString(cursor.getColumnIndex("class44")));
            timetable.setClass45(cursor.getString(cursor.getColumnIndex("class45")));
            timetable.setClass46(cursor.getString(cursor.getColumnIndex("class46")));
            timetable.setClass47(cursor.getString(cursor.getColumnIndex("class47")));
            timetable.setClass48(cursor.getString(cursor.getColumnIndex("class48")));
            timetable.setClass49(cursor.getString(cursor.getColumnIndex("class49")));
            timetable.setStuNum(cursor.getString(cursor.getColumnIndex("stuNum")));
            timetable.setTerm(cursor.getInt(cursor.getColumnIndex("term")));
            Log.e("DBHelper",timetable.getClass1());
            cursor.close();
            return timetable;
        }else
        {
            Log.e("DBHelper","没有这个学期的课表或学号错误！");
            cursor.close();
            return null;
        }
    }

    public Schedule export(int scheduleId){
        Cursor cursor = db.rawQuery("select * from Schedule where schedule_id=?", new String[]{String.valueOf(scheduleId)});
        if (cursor.moveToFirst()){
            Schedule schedule = new Schedule();
            schedule.setId(cursor.getInt(cursor.getColumnIndex("schedule_id")));
            schedule.setDay(cursor.getString(cursor.getColumnIndex("day")));
            schedule.setType(cursor.getString(cursor.getColumnIndex("type")));
            schedule.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            schedule.setStartTime(cursor.getString(cursor.getColumnIndex("startTime")));
            schedule.setRemindTime(cursor.getString(cursor.getColumnIndex("overtime")));
            schedule.setRemarks(cursor.getString(cursor.getColumnIndex("remarks")));
            schedule.setUserID(cursor.getInt(cursor.getColumnIndex("user_id")));
            return schedule;
        }else{
            Log.w("DBHelper","scheduleId id null");
            return null;
        }

    }

    public Schedule export(Schedule schedule){
        Cursor cursor = db.rawQuery("select * from Schedule where user_id=?", new String[]{String.valueOf(schedule.getUserID())});
        if (cursor.moveToLast()) {
             do {
                 schedule.setId(cursor.getInt(cursor.getColumnIndex("schedule_id")));
                 schedule.setDay(cursor.getString(cursor.getColumnIndex("day")));
                 schedule.setType(cursor.getString(cursor.getColumnIndex("type")));
                 schedule.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                 schedule.setStartTime(cursor.getString(cursor.getColumnIndex("startTime")));
                 schedule.setRemindTime(cursor.getString(cursor.getColumnIndex("overtime")));
                 schedule.setRemarks(cursor.getString(cursor.getColumnIndex("remarks")));
                 schedule.setUserID(cursor.getInt(cursor.getColumnIndex("user_id")));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return schedule;
    }
    public List<Schedule> export(int user_id, String date){
        List<Schedule> scheduleList = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from Schedule where user_id=? and day=?", new String[]{String.valueOf(user_id), date});
        if (cursor.moveToFirst()) {
            do {
                Schedule schedule = new Schedule();
                schedule.setId(cursor.getInt(cursor.getColumnIndex("schedule_id")));
                schedule.setDay(cursor.getString(cursor.getColumnIndex("day")));
                schedule.setType(cursor.getString(cursor.getColumnIndex("type")));
                schedule.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                schedule.setStartTime(cursor.getString(cursor.getColumnIndex("startTime")));
                schedule.setRemindTime(cursor.getString(cursor.getColumnIndex("overtime")));
                schedule.setRemarks(cursor.getString(cursor.getColumnIndex("remarks")));
                schedule.setUserID(cursor.getInt(cursor.getColumnIndex("user_id")));
                scheduleList.add(schedule);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return scheduleList;
    }

    public void update(String phoneNum, String stuNum){
        //更新学号
        //String phoneNum = user.getPhoneNum();
        Cursor cursor = db.rawQuery("select phoneNum from User where phoneNum=?", new String[]{phoneNum});
        if (cursor.moveToFirst()){
            db.execSQL("update User set stuNum=? where phoneNum=?",new String[] {stuNum,phoneNum});
        }else {
            Toast.makeText(MyApplication.getContext(), "凉凉", Toast.LENGTH_SHORT).show();
        }
    }
//"user_id","username", "password", "phoneNum" , "sex", "email", "school", "userIcon", "stuNum","state", "dormitoryID"
    public void update(User user){
        //更新用户信息
        String username = user.getUsername();
        String phoneNum = user.getPhoneNum();
        String userIcon = user.getIcon();
        String stuNum = user.getStuNum();
        String sex = user.isSex()?"男":"女";
        String email = user.getEmail();
        String school = user.getSchool();
        String autograph = user.getAutograph();
        Cursor cursor = db.rawQuery("select phoneNum from User where phoneNum=?", new String[]{phoneNum});
        if (cursor.moveToFirst()){
            db.execSQL("update User set username=?, userIcon=?, stuNum=?, sex=?, email=?, school=?, autograph=?  where phoneNum=?",new String[] {username, userIcon, stuNum, sex, email,school,autograph, phoneNum});
            Log.e("DB",username+phoneNum+userIcon+stuNum+sex+email+school+autograph);
        }else {
            Log.e("DBHelper_updata_user","emmm");
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