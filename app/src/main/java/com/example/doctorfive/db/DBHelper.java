package com.example.doctorfive.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.doctorfive.base.MyApplication;
import com.example.doctorfive.entity.MessagAndObject;
import com.example.doctorfive.entity.Pwd;
import com.example.doctorfive.entity.Schedule;
import com.example.doctorfive.entity.Student;
import com.example.doctorfive.entity.Timetable;
import com.example.doctorfive.entity.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
    private static final int LOGIN_OF_SECCESS = 111;
    private static User myUser;
    private DBListener myDBListener;
    private DBOpenHelper helper;
    private SQLiteDatabase db;
    private String servicesIP = "47.100.162.55";
    //private String servicesIP = "192.168.42.119";
    //private String servicesIP = "192.168.1.102";


    static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 111:
                    myUser = (User) msg.obj;
                    break;
            }
        }
    }
    MyHandler myHandler = new MyHandler();


    private Request request;//网页返回结果
    public static final MediaType TYPE_OF_JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType TYPE_OF_PNG = MediaType.parse("image/png");
    private static class DBOpenHelper extends SQLiteOpenHelper {
        //定义student表sql语句
        private static final String CREATE_TABLE_USER = "create table " + TABLE_NAME[0]
                + " ( " + USER_COLUMNS[0] + " integer primary key autoincrement, "
                + USER_COLUMNS[1] + " varchar(60) ,"
                //+ USER_COLUMNS[2] + " varchar(20) , "
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

    public DBHelper(Context context, DBListener DBListener){
        helper = new DBOpenHelper(context);// 创建SQLiteOpenHelper对象
        db = helper.getWritableDatabase();// 获得可写的数据库
        myDBListener = DBListener;
    }

    /**
     * 向本地user表格中插入数据 如果本地存在用户就不更新数据
     * @param user
     */
    public void insert(User user) {
        if (haveLocalUser(user.getPhoneNum())){
            update(user);
        }else{
            ContentValues values = new ContentValues();
            values.put(USER_COLUMNS[1], user.getUsername());
            values.put(USER_COLUMNS[3], user.getPhoneNum());
            values.put(USER_COLUMNS[4], user.getSex());
            values.put(USER_COLUMNS[5], user.getEmail());
            values.put(USER_COLUMNS[6], user.getSchool());
            values.put(USER_COLUMNS[7], user.getUserIcon());
            values.put(USER_COLUMNS[8], user.getStuNum());
            values.put(USER_COLUMNS[9], user.getState());
            values.put(USER_COLUMNS[10], user.getDormitoryID());
            values.put(USER_COLUMNS[11], user.getAutograph());
            db.insert(TABLE_NAME[0], null, values);
        }
    }

    /**
     * 检查是否有登陆过的本地用户
     * @param phoneNum
     * @return true代表存在本地user用户 false表示没有
     */
    public  Boolean haveLocalUser(String phoneNum){
        String sql = "select * from User where phoneNum=?";
        Cursor cursor = db.rawQuery(sql, new String[]{phoneNum});
        if (cursor.moveToFirst()){
            cursor.close();
            return true;
        }else {
            cursor.close();
            return false;
        }
    }
    public void insert(Student student) {// 向student表格中插入数据
        ContentValues values = new ContentValues();
        values.put(STUDENT_COLUMNS[0], student.getStuNum());
        values.put(STUDENT_COLUMNS[1], student.getName());
        values.put(STUDENT_COLUMNS[2], student.getClassName());
        values.put(STUDENT_COLUMNS[3], student.getStuPassword());
        db.insert(TABLE_NAME[1], null, values);
        okhttpStudentRegisterPost(JSON.toJSONString(student));
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

    public void insert(Schedule schedule) {// 向schedule表格中插入数据
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


    public String forgetPassword(User user){//查询手机号和学号是否匹配
        String password;
        String phoneNum = user.getPhoneNum();
        String stuNum = user.getStuNum();
        String sql = "select password from User where phoneNum=? and stuNum=?";
        Cursor cursor = db.rawQuery(sql, new String[]{phoneNum,stuNum});
        if (cursor.moveToFirst()){
            password = cursor.getString(cursor.getColumnIndex("password"));
            cursor.close();
            return password;
        }else
            cursor.close();
            return null;
    }


//    private static final String[] USER_COLUMNS = { " id","username", "password", "name", "sex", "studentNum", "phoneNum", "email", "school", "state", "dormitoryID"};


    /**
     * 登录方法 该不是在主线程中进行
     * @param pwd pwd对象
     */
    public void login(Pwd pwd){
        okhttpUserLoginPost(JSON.toJSONString(pwd));
    }
    /**
     * 注册方法 该不是在主线程中进行
     * @param pwd pwd对象
     */
    public void register(Pwd pwd){
        okhttpUserRegisterPost(JSON.toJSONString(pwd));
    }

    /**
     * 向服务器请求 用户登录的post方法
     * @param jsonString json格式的String对象
     */
    public void okhttpUserLoginPost(String jsonString){
        String url = "http://"+servicesIP+":8080/DormitoryFun/login";
        OkHttpClient okHttpClient = new OkHttpClient();//创建okhttp实例
        RequestBody body = RequestBody.create(TYPE_OF_JSON, jsonString);
        Request request = new Request.Builder().post(body).url(url).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("DBHelper","服务器凉了");

                return;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                onResponseToUserLogin(call, response);

            }
        });
    }
    /**
     * 向服务器请求 用户注册的post方法
     * @param jsonString json格式的String对象
     */
    private void okhttpUserRegisterPost(String jsonString) {
        String url = "http://"+servicesIP+":8080/DormitoryFun/register";
        OkHttpClient okHttpClient = new OkHttpClient();//创建okhttp实例
        RequestBody body = RequestBody.create(TYPE_OF_JSON, jsonString);
        Request request = new Request.Builder().post(body).url(url).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("DBHelper","服务器凉了");
                return;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                onResponseToUserRegister(call, response);

            }
        });
    }

    /**
     * 向服务器请求 用户注册的post方法
     * @param jsonString json格式的String对象
     */
    private void okhttpStudentRegisterPost(String jsonString) {
        String url = "http://"+servicesIP+":8080/DormitoryFun/studentImformation";
        OkHttpClient okHttpClient = new OkHttpClient();//创建okhttp实例
        RequestBody body = RequestBody.create(TYPE_OF_JSON, jsonString);
        Request request = new Request.Builder().post(body).url(url).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("DBHelper","服务器凉了");
                return;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //学生信息注册成功
                Log.e("StudentRegister","学生信息注册成功");
            }
        });
    }

    public void okhttpChangeUserImformationPost(User user){
        String url = "http://"+servicesIP+":8080/DormitoryFun/changeImfor";
        OkHttpClient okHttpClient = new OkHttpClient();//创建okhttp实例
        RequestBody body = RequestBody.create(TYPE_OF_JSON, JSON.toJSONString(user));
        Request request = new Request.Builder().post(body).url(url).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("DBHelper","服务器凉了");
                return;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //学生信息注册成功
                //byte bytes[] = response.body().bytes();
                //String jsonStr = new String(bytes);
                String jsonStr = response.body().string();
                JSONObject jsonObject = JSONObject.parseObject(jsonStr);
                JSON json = (JSON) JSON.toJSON(jsonObject);
                MessagAndObject messagAndObject = JSON.toJavaObject(json, MessagAndObject.class);
                if (messagAndObject.getResultCode().equals("0")) {
                    return;
                }
                else if(messagAndObject.getResultCode().equals("1")) {
                    myUser = messagAndObject.getData();
                    myDBListener.doNetRequestChange(myUser);
                }
            }
        });
    }



    public void okhttpChangeUserIconPost(final User user, File icon){
        String url = "http://"+servicesIP+":8080/DormitoryFun/changeUserIcon";
        OkHttpClient okHttpClient = new OkHttpClient();//创建okhttp实例
        /*设置超时时间及缓存
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
        */
        //RequestBody body = RequestBody.create(TYPE_OF_PNG, new File(imagePath));
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("phoneNum", user.getPhoneNum())//设置post的参数
                .addFormDataPart("image", "0.png", RequestBody.create(TYPE_OF_PNG, icon))
                .build();
        Request request = new Request.Builder().post(requestBody).url(url).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("DBHelper","服务器凉了");
                return;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //学生信息注册成功
                //byte bytes[] = response.body().bytes();
                //String jsonStr = new String(bytes);
                String jsonStr = response.body().string();
                JSONObject jsonObject = JSONObject.parseObject(jsonStr);
                JSON json = (JSON) JSON.toJSON(jsonObject);
                MessagAndObject messagAndObject = JSON.toJavaObject(json, MessagAndObject.class);
                if (messagAndObject.getResultCode().equals("0")) {
                    return;
                }
                else if(messagAndObject.getResultCode().equals("1")) {
                    myUser = messagAndObject.getData();
                    myDBListener.doNetRequestChange(myUser);
                }
            }
        });
    }

    /**
     * onResponse服务器登录请求成功调用的方法
     * @param call
     * @param response
     * @throws IOException
     */
    private void onResponseToUserLogin(Call call, Response response) throws IOException {
        String responseBody = response.body().string();
        Log.e("DBHelper","from onResponseToUserLogin:"+responseBody);
        JSONObject jsonObject = JSONObject.parseObject(responseBody);
        JSON json = (JSON) JSON.toJSON(jsonObject);
        myUser = JSON.toJavaObject(json, User.class);
        //java.lang.ClassCastException: com.alibaba.fastjson.JSONObject cannot be cast to com.example.doctorfive.entity.User
        myDBListener.doNetRequestChange(myUser);
    }

    /**
     * onResponse服务器注册请求成功调用的方法
     * @param call
     * @param response
     * @throws IOException
     */
    private void onResponseToUserRegister(Call call, Response response) throws IOException {
        String responseBody = response.body().string();
        Log.e("DBHelper","from onResponseToUserRegister:"+responseBody);
        //“1”表示注册成功 “0”表示注册失败
        if (responseBody.equals("1")){
            //这里回调接口 RegisterActivity实现的接口
            myDBListener.doNetRequestChange(new User());
        }else {
            myDBListener.doNetRequestChange(null);
        }
    }


    /**
     * 外部调用DBHelper的网络操作 需要实现的回调接口
     */
    public interface DBListener {
        void doNetRequestChange(User user);

    }

    public User getMyUser() {
        return myUser;
    }

    public User export(User user) {//bianlishuju
        String phoneNum = user.getPhoneNum();
        String sql = "select * from User where phoneNum=?";
        Cursor cursor = db.rawQuery(sql, new String[]{phoneNum});
        if(cursor.moveToFirst()) {
            user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("user_id"))));
            user.setUsername(cursor.getString(cursor.getColumnIndex("username")));// android.database.CursorIndexOutOfBoundsException: Index 0 requested, with a size of 0
            //user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            user.setPhoneNum(cursor.getString(cursor.getColumnIndex("phoneNum")));
            user.setSex(cursor.getString(cursor.getColumnIndex("sex")));
            user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            user.setSchool(cursor.getString(cursor.getColumnIndex("school")));
            user.setUserIcon(cursor.getString(cursor.getColumnIndex("userIcon")));
            user.setStuNum(cursor.getString(cursor.getColumnIndex("stuNum")));
            user.setState(Integer.parseInt(cursor.getString(cursor.getColumnIndex("state"))));
            user.setDormitoryID(cursor.getString(cursor.getColumnIndex("dormitoryID")));
            user.setAutograph(cursor.getString(cursor.getColumnIndex("autograph")));
            cursor.close();
            return user;
        }else
        {
            cursor.close();
            //Toast.makeText(MyApplication.getContext(),"未知错误",Toast.LENGTH_SHORT).show();
            Log.e("DBHelper--User--export","本地用户为空");
            return user;
        }
    }

    public Student export(Student student){
        String stuNum = student.getStuNum();
        if (stuNum==null||stuNum=="")
            return null;
        String sql = "select * from Student where studentNum=?";
        Cursor cursor = db.rawQuery(sql, new String[]{stuNum});
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
            //Toast.makeText(MyApplication.getContext(),"export--未知错误",Toast.LENGTH_SHORT).show();
            Log.e("DBHelper--export():", " student本地数据为空");
            return null;
        }
    }

    public Timetable export(String stuNum, int term){
        Timetable timetable = new Timetable();
        Log.e("DBHelper",stuNum+" "+String.valueOf(term));
        String sql = "select * from Timetable where stuNum=? and term=?";
        Cursor cursor = db.rawQuery(sql, new String[]{stuNum,String.valueOf(term)});
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
        String sql = "select * from Schedule where schedule_id=?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(scheduleId)});
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
        String sql = "select * from Schedule where user_id=?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(schedule.getUserID())});
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

        String sql = "select * from Schedule where user_id=? and day=?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(user_id), date});
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
        String sql_1 = "select phoneNum from User where phoneNum=?";
        Cursor cursor = db.rawQuery(sql_1, new String[]{phoneNum});
        if (cursor.moveToFirst()){
            String sql_2 = "update User set stuNum=? where phoneNum=?";
            db.execSQL(sql_2,new String[] {stuNum,phoneNum});
        }else {
            Toast.makeText(MyApplication.getContext(), "凉凉", Toast.LENGTH_SHORT).show();
        }
    }

    public void update(User user){
        //更新用户信息
        String username = user.getUsername();
        String phoneNum = user.getPhoneNum();
        String userIcon = user.getUserIcon();
        String stuNum = user.getStuNum();
        String sex = user.getSex();
        String email = user.getEmail();
        String school = user.getSchool();
        String autograph = user.getAutograph();
        int state = user.getState();
        String dormitoryID = user.getDormitoryID();
        String sql_1 = "select phoneNum from User where phoneNum=?";
        Cursor cursor = db.rawQuery(sql_1, new String[]{phoneNum});
        if (cursor.moveToFirst()){
            String sql_2 = "update User set username=?, userIcon=?, stuNum=?, sex=?, email=?, school=?, autograph=?, state=?, dormitoryID=?  where phoneNum=?";
            db.execSQL(sql_2,new String[] {username, userIcon, stuNum, sex, email,school,autograph, String.valueOf(state), dormitoryID, phoneNum});
            Log.e("DB",username+phoneNum+userIcon+stuNum+sex+email+school+autograph);
            cursor.close();
        }else {
            Log.e("DBHelper_updata_user","emmm");
            cursor.close();
        }
    }


}
