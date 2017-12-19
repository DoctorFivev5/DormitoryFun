package com.example.doctorfive.ui.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doctorfive.util.cookies.CookieJarImpl;
import com.example.doctorfive.ui.activity.KCBActivity;
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
    private String phoneNum;//获得当前账号的手机号
    private EditText stuNum;//学号输入框的控件
    private EditText stuPassword;//教务在线的密码输入框的控件
    private Button login;//登录控件
    private Button kcb;//获取课程表的控件
    private Button logon;//取消登录的控件
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
                //case为2时：的道德msg为查询课表时请求的参数
                case 2:
                    getKCB(msg.obj);
                    break;
            }
        }
    };
    private PersistentCookieStore persistentCookieStore;//自定义持久化的cookies类
    private String[] classes = new String[49];//获取的课程序列数组
    private Request request;//网页返回结果
    private String __VIEWSTATE;//教务在线html动态post参数之一
    private String __EVENTVALIDATION;;//教务在线html动态post参数之一


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
        kcb = (Button) view.findViewById(R.id.kcb);
        logon = (Button) view.findViewById(R.id.logon);
        pref = getActivity().getSharedPreferences("userPwd",MODE_PRIVATE);
        phoneNum = pref.getString("phoneNum","");
        /*
        接下来就是先查找这个账户下的课表项
        如果有则直接导入课程表界面
        没有则查看登录帐号导入
         */
        login.setOnClickListener(this);
        kcb.setOnClickListener(this);
        logon.setOnClickListener(this);
    }

    /*
    初始化持久化的cookies
    初始化okhttp
     */
    private void initHttpCookies(){
        builder = new OkHttpClient.Builder();
        persistentCookieStore = new PersistentCookieStore(MyApplication.getContext());
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
                Toast.makeText(MyApplication.getContext(),"帐号或密码错误",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    /*
    得到教务在线网页post的html参数
     */
    private void getCS(final String url) {
        request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Toast.makeText(MyApplication.getContext(),"请检查网络~",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resp = response.body().string();
                Document parse = Jsoup.parse(resp);
                Element content = parse.getElementById("__VIEWSTATE");
                __VIEWSTATE = content.attr("value");
                Element content2 = parse.getElementById("__EVENTVALIDATION");
                __EVENTVALIDATION = content2.attr("value");
                Message msg = mHandler.obtainMessage();
                //判断是登录参数还是请求课程表参数
                if (url.equals(loginUrl)) {
                    msg.what = 1;
                }else if (url.equals(kcbUrl)){
                    msg.what = 2;
                }
                msg.obj = new MesValue(__VIEWSTATE, __EVENTVALIDATION);
                mHandler.sendMessage(msg);
            }
        });
    }

    public void login() {
        getCS(loginUrl);
    }

    public void getKCB(Object mesValue) {
        FormBody formBody = new FormBody.Builder()
                .add("__EVENTTARGET","")
                .add("__EVENTARGUMENT","")
                .add("__VIEWSTATE",((MesValue) mesValue).get__VIEWSTATE())
                .add("__EVENTVALIDATION", ((MesValue) mesValue).get__EVENTVALIDATION())//((MesValue) mesValue).get__EVENTVALIDATION()
                .add("_ctl1:ddlSterm", "2017/9/1 0:00:00")
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
                Intent intent = new Intent(MyApplication.getContext(),KCBActivity.class);
                intent.putExtra("kcb",classes);
                startActivity(intent);

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
            case R.id.logon:
                logon();
                break;
            case R.id.kcb:
                getCS(kcbUrl);
            default:
                break;
        }

    }


}

/**
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

