package com.example.doctorfive.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doctorfive.db.DBHelper;
import com.example.doctorfive.entity.Student;
import com.example.doctorfive.entity.Timetable;
import com.example.doctorfive.entity.User;
import com.example.doctorfive.util.CheckoutNetworkUtil;
import com.example.doctorfive.util.cookies.CookieJarImpl;
import com.example.doctorfive.entity.MesValue;
import com.example.doctorfive.base.MyApplication;
import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.util.cookies.PersistentCookieStore;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimetableInterface extends Fragment implements View.OnClickListener {

    private String loginUrl = "http://jwc.jxnu.edu.cn/Default_Login.aspx?preurl=";//教务在线登录url
    private String kcbUrl = "http://jwc.jxnu.edu.cn/User/default.aspx?&&code=111&uctl=MyControl%5cxfz_kcb.ascx&MyAction=Personal";//教务在线课表url
    private SharedPreferences pref;  //SharedPreferences的提取操作实例
    private SharedPreferences.Editor editor;  //SharedPreferences的存储操作实例

    private DBHelper myDBHelper;
    private User myUser;
    private String phoneNum;//获得当前账号的手机号
    private EditText stuNum;//学号输入框的控件
    private EditText stuPassword;//教务在线的密码输入框的控件
    private Student mStudent;//传递给activity的学生
    private Button login;//登录控件
    //private Button kcb;//获取课程表的控件
    //private Button logon;//取消登录的控件
    private String stuNumS;//学号输入框的值
    private String stuPasswordS;//教务在线密码输入框的值
    private OkHttpClient.Builder builder;// OkHttpClient内部类Builder对象
    private OkHttpClient okHttpClient;// OkHttpClientd对象
    private Handler mHandler = new Handler() {//在子线程里获取返回值
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //case为1时：得到的msg为登录教务在线的请求的参数
                case 1:
                    goJXNU(msg.obj);
                    break;
                //case为2时：得到的msg为查询课表时请求的参数
                case 2:
                    getKCB(msg.obj);
                    break;
                //case为3时：一个mainactivity的回调函数 响应课表的更新
                case 3:
                    listterner.process((Student) msg.obj);
                    break;
                //case为101时：课表登陆出错（没有输入学号）
                case 101:
                    Toast.makeText(getActivity(),"请您输入学号",Toast.LENGTH_SHORT).show();
                    break;
                //case为102时：课表登陆出错（学号不存在）
                case 102:
                    Toast.makeText(getActivity(),"学号不存在",Toast.LENGTH_SHORT).show();
                    break;
                //case为103时：课表登陆出错（没有输入密码）
                case 103:
                    Toast.makeText(getActivity(),"请您输入密码",Toast.LENGTH_SHORT).show();
                    break;
                //case为104时：课表登陆出错（您的密码不正确）
                case 104:
                    Toast.makeText(getActivity(),"您的密码不正确",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private PersistentCookieStore persistentCookieStore;//自定义持久化的cookies类
    private String[] classes = new String[49];//获取的课程序列数组
    private Request request;//网页返回结果
    private String __VIEWSTATE;//教务在线html动态post参数之一
    private String __EVENTVALIDATION;;//教务在线html动态post参数之一

    private FragmentInteraction listterner;//  定义用来与外部activity交互，获取到宿主activity
    public void onStart() {
        super.onStart();
        if (isAdded()) {//判断Fragment已经依附Activity
            phoneNum = getArguments().getString("phoneNum");
        }
    }

    // 当FRagmen被加载到activity的时候会被回调


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if((Activity)context instanceof FragmentInteraction) {
            listterner = (FragmentInteraction) (Activity)context; // 2.2 获取到宿主activity并赋值
        } else{
            throw new IllegalArgumentException("activity must implements FragmentInteraction");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timetable_interface, container, false);
        initViews(view);
        initHttpCookies();

        return view;
    }

    /*
    初始化控件
     */
    private void initViews(View view){
        stuNum = (EditText) view.findViewById(R.id.stuNum1);
        stuPassword = (EditText) view.findViewById(R.id.stuPassword1);
        login = (Button) view.findViewById(R.id.login);
        //kcb = (Button) view.findViewById(R.id.kcb);
        //logon = (Button) view.findViewById(R.id.logon);
        pref = getActivity().getSharedPreferences("userPwd",MODE_PRIVATE);
        phoneNum = pref.getString("phoneNum","");
        myDBHelper = new DBHelper(getActivity());
        myUser = new User();
        myUser.setPhoneNum(phoneNum);
        myUser = myDBHelper.export(myUser);
        Log.e("initView",myUser.getUsername());
        /*
        接下来就是先查找这个账户下的课表项
        如果有则直接导入课程表界面
        没有则查看登录帐号导入
         */
        login.setOnClickListener(this);
        //kcb.setOnClickListener(this);
        //logon.setOnClickListener(this);
    }

    /*
    初始化持久化的cookies
    初始化okhttp
     */
    private void initHttpCookies(){
        builder = new OkHttpClient.Builder();
        persistentCookieStore = new PersistentCookieStore(MyApplication.getContext());
        persistentCookieStore.removeAll();
        CookieJarImpl cookieJarImpl = new CookieJarImpl(persistentCookieStore);
        builder.cookieJar(cookieJarImpl);
        okHttpClient = builder.build();
    }
    /*
     * 登录教务在线
     * 得到cookies
     */
    private void goJXNU(Object mesValue) {
        stuNumS = stuNum.getText().toString();
        stuPasswordS = stuPassword.getText().toString();
        //登录教务在线的所有参数post
        FormBody formBody = new FormBody.Builder()
                .add("__EVENTTARGET","")
                .add("__EVENTARGUMENT","")
                .add("__LASTFOCUS","")
                .add("__VIEWSTATE",((MesValue) mesValue).get__VIEWSTATE() )
                .add("__EVENTVALIDATION",((MesValue) mesValue).get__EVENTVALIDATION() )
                .add("rblUserType", "User")
                .add("ddlCollege","180     ")
                .add("StuNum",	stuNumS)
                .add("TeaNum","")
                .add("Password",stuPasswordS)
                .add("Login", "登陆")
                .build();
        request = new Request.Builder().post(formBody).url(loginUrl).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //Toast.makeText(MyApplication.getContext(),"帐号或密码错误",Toast.LENGTH_SHORT).show();
                return;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String resp = response.body().string();
                Document parse = Jsoup.parse(resp);
                Elements getIsPwd = parse.select("script");
                String s = getIsPwd.html();
                Log.e("TimetableInterface",getIsPwd.html());
                //Boolean isStuNum = s.matches("[\\u5b66][\\u53f7]");//学号不存在
                //Boolean isPasswordEmpty = s.matches("[\\u8bf7][\\u60a8][\\u8f93][\\u5165][\\u5bc6][\\u7801]{0,6}");//请您输入密码
                //Boolean isPassword = s.matches("[\\u60a8][\\u7684][\\u5bc6][\\u7801][\\u4e0d][\\u6b63][\\u786e]{1}");//您的密码不正确
                Message msg = mHandler.obtainMessage();
                Boolean haveStuNum = s.matches(".*(请您输入学号).*");
                Boolean isStuNum = s.matches(".*(学号不存在).*");
                Boolean isPasswordEmpty = s.matches(".*(请您输入密码).*");
                Boolean isPassword = s.matches(".*(您的密码不正确).*");
                if(haveStuNum){
                    msg.what = 101;//传递msg  请您输入学号
                    mHandler.sendMessage(msg);
                    return;
                }else if (isStuNum){
                    msg.what = 102;//传递msg  学号不存在
                    mHandler.sendMessage(msg);
                    return;
                }else if(isPasswordEmpty){
                    msg.what = 103;//传递msg  请您输入密码
                    mHandler.sendMessage(msg);
                    return;
                }if (isPassword){
                    msg.what = 104;//传递msg  您的密码不正确
                    mHandler.sendMessage(msg);
                    return;
                }else {
                    getCS(kcbUrl);
                }

            }
        });
    }

    /*
    得到教务在线网页post的html参数
     */
    private void getCS(final String url) {
        //需要检测网络状态
        if (CheckoutNetworkUtil.isNetworkAvalible(getActivity())) {
            request = new Request.Builder().url(url).build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //Toast.makeText(MyApplication.getContext(), "请检查网络~", Toast.LENGTH_SHORT).show();
                    return;
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String resp = response.body().string();
                    Document parse = Jsoup.parse(resp);
                    //如果返回密码错误则停止获得参数并需要重新输入学号密码
                    Element content = parse.getElementById("__VIEWSTATE");
                    __VIEWSTATE = content.attr("value");
                    Element content2 = parse.getElementById("__EVENTVALIDATION");
                    __EVENTVALIDATION = content2.attr("value");
                    Message msg = mHandler.obtainMessage();
                    //判断是登录参数还是请求课程表参数
                    if (url.equals(loginUrl)) {
                        msg.what = 1;
                    } else if (url.equals(kcbUrl)) {
                        msg.what = 2;

                    }
                    msg.obj = new MesValue(__VIEWSTATE, __EVENTVALIDATION);
                    mHandler.sendMessage(msg);
                }
            });
        }else{
            Toast.makeText(getActivity(),"当前无可用网络连接",Toast.LENGTH_SHORT).show();
        }
    }

    public void login() {
        getCS(loginUrl);
    }

    public void getKCB(final Object mesValue) {
        FormBody formBody = new FormBody.Builder()
                .add("__EVENTTARGET","")
                .add("__EVENTARGUMENT","")
                .add("__VIEWSTATE",((MesValue) mesValue).get__VIEWSTATE())
                .add("__EVENTVALIDATION", ((MesValue) mesValue).get__EVENTVALIDATION())//((MesValue) mesValue).get__EVENTVALIDATION()
                .add("_ctl1:ddlSterm", "2018/3/1 0:00:00")//2017/9/1 0:00:00
                .add("_ctl1:btnSearch","确定")
                .build();
        request = new Request.Builder().post(formBody).url(kcbUrl).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String html = response.body().string();
                html = html.replaceAll("<br>","").replaceAll("&nbsp;"," ");//去除html里的<br>标签和空格
                Document parse = Jsoup.parse(html);
                Element table = parse.getElementsByTag("table").first();
                Elements div = table.getElementsByTag("td");
                //分析教务在线课程表页面得到表格课程序列
                //16-22 12
                //24-30 3
                //32-38 4
                //40-46 5
                //50-56 67
                //58-64 89
                //67-73 晚上
                for (int i = 16, j = 0; i < 74; i++){
                    if(i==23||i==31||i==39||i==47||i==48||i==49||i==57||i==65||i==66)
                        continue;
                    classes[j] = div.get(i).text();
                    j++;
                }
                //接下来更新用户表中的学号
                /*
                就达成课程表入库，学生入库，学号入用户库
                再查询课表
                然后再打开kcbfragment
                 */
                Timetable timetable = new Timetable(classes,stuNum.getText().toString(),17182);
                myDBHelper.insert(timetable);//新增插入课表
                mStudent = new Student();
                mStudent.setStuNum(stuNumS);
                mStudent.setStuPassword(stuPasswordS);//新增插入学生
                myDBHelper.insert(mStudent);
                Log.e("TimetableInterface",phoneNum+" 0");
                myUser.setStuNum(stuNumS);
                myUser.setIcon("http://jwc.jxnu.edu.cn/StudentPhoto/"+ stuNumS+".jpg?a=20171124191233");
                myDBHelper.update(myUser);//把学号插入用户
                myUser = myDBHelper.export(myUser);
                Log.e("Time",myUser.getIcon()+" "+myUser.getUsername()+" "+myUser.getStuNum());
                Message message = mHandler.obtainMessage();
                message.what=3;
                message.obj = mStudent;
                mHandler.sendMessage(message);
            }
        });
    }

    /*
    取消登录教务在线
     */
    public void logon() {
        persistentCookieStore.removeAll();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.login:
                login();
                break;

            default:
                break;
        }

    }

    // 1 定义了所有activity必须实现的接口方法case R.id.kcb: getCS(kcbUrl);case R.id.logon:logon(); break;



    public interface FragmentInteraction {
        void process(Student student);
    }

}



/*
 builder.cookieJar(new CookieJar() {
 //            private final HashMap<String, List<Cookie>> cookieStore = new HashMap<String, List<Cookie>>();
 //
 //            @Override
 //            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
 //                cookieStore.put(url.host(), cookies);
 //            }
 //
 //            @Override
 //            public List<Cookie> loadForRequest(HttpUrl url) {
 //                List<Cookie> cookies = cookieStore.get(url.host());
 //                return cookies != null ? cookies : new ArrayList<Cookie>();
 //            }
 //        });
 */

