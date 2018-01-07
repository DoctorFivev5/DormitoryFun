package com.example.doctorfive.ui.fragment;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.doctorfive.adapter.ScheduleRecyclerAdapter;
import com.example.doctorfive.db.DBHelper;
import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.entity.Schedule;
import com.example.doctorfive.entity.ScheduleItem;
import com.example.doctorfive.ui.activity.ScheduleActivity;
import com.example.doctorfive.util.CalendarUtil;
import com.example.doctorfive.weight.MyItemDecoration;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleInterface extends Fragment implements CalendarView.OnDateSelectedListener, View.OnClickListener {


    private final int scheduleCode = 0x01;
    private int user_id;            //用来记录用户id 进行该用户的日程操作
    private String date;               //记录当前选择的日期
    private TextView mTextMonthDay;//月份和日期
    private TextView mTextYear;//年份
    private TextView mTextLunar;//农历
    private TextView mTextCurrentDay;//当前日期
    private CalendarView mCalendarView;//日历控件
    private View mFrameLayout;
    private int mYear;
    private RecyclerView myRecyclerView;
    private LinearLayoutManager layoutManager ;
    private List<ScheduleItem> myScheduleItemList = new ArrayList<>();
    private List<Schedule> myScheduleList;
    private ScheduleRecyclerAdapter myScheduleRecyclerAdapter;
    private View headerView;
    private FloatingActionButton fab;

    private DBHelper myDBHelper;
    private int mMouth;
    private int mDay;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        user_id = getArguments().getInt("user_id");
        Log.e("如果我是dj你会爱我吗？","0 "+user_id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule_interface, container, false);
        initView(view);

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==scheduleCode){
            Schedule mySchedule = (Schedule) data.getSerializableExtra("mySchedule");
            Log.e("idididididididi"," "+mySchedule.getId());
            ScheduleItem scheduleItem = new ScheduleItem(mySchedule.getId(), R.mipmap.ic_launcher,mySchedule.getTitle(),mySchedule.getStartTime(),R.drawable.enter);
            addItem(0, scheduleItem);//得到日期----日期下的日程----日程添加进recyclerview
        }

    }

    private void initView(View view){
        //setStatusBarDarkMode();
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        mCalendarView = (CalendarView) view.findViewById(R.id.calendarView);
        mTextMonthDay = (TextView) view.findViewById(R.id.tv_month_day);
        mTextYear = (TextView) view.findViewById(R.id.tv_year);
        mTextLunar = (TextView) view.findViewById(R.id.tv_lunar);
        mTextCurrentDay = (TextView) view.findViewById(R.id.tv_current_day);
        mFrameLayout = view.findViewById(R.id.fl_current1);


        initValue(view);
        initData();
        myRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(layoutManager);
        myScheduleRecyclerAdapter = new ScheduleRecyclerAdapter(myScheduleItemList);
        myRecyclerView.setAdapter(myScheduleRecyclerAdapter);
        myRecyclerView.addItemDecoration( new HorizontalDividerItemDecoration.Builder(getContext())
                .color(R.color.powderblue)
                .sizeResId(R.dimen.divider)
                .marginResId(R.dimen.leftmargin, R.dimen.rightmargin)
                .build());
        //为RecyclerView添加HeaderView
        //headerView = LayoutInflater.from(getActivity()).inflate(R.layout.recycle_header, myRecyclerView, false);
        //setHeaderView(myRecyclerView);
        //initHeaderView(headerView);
        //initHeaderData();
        //setFooterView(mRecyclerView);
        mCalendarView.setOnDateSelectedListener(this);
        onDateFirstSelected(mCalendarView.getSelectedCalendar());
        fab.setOnClickListener(this);
        mTextMonthDay.setOnClickListener(this);
        mFrameLayout.setOnClickListener(this);


    }

    public void initValue(View view){
        myDBHelper = new DBHelper(getActivity());
        mYear = mCalendarView.getCurYear();
        mMouth = mCalendarView.getCurMonth();
        mDay = mCalendarView.getCurDay();
        mTextMonthDay.setText(mMouth + "月" + mDay + "日");
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));
        date = CalendarUtil.dateFormat(mYear, mMouth-1, mDay);
    }
    public void initData(){
        Log.e("initData",date);
        myScheduleList = myDBHelper.export(user_id,date);
        int i = 0;
        //ScheduleItem scheduleitem;
        for (Schedule schedule : myScheduleList){
            //scheduleitem = new ScheduleItem(R.mipmap.ic_launcher,schedule.getTitle(),schedule.getStartTime(),R.drawable.enter);
            myScheduleItemList.add(new ScheduleItem(schedule.getId(), R.mipmap.ic_launcher,schedule.getTitle(),schedule.getStartTime(),R.drawable.enter));
            i++;

            Log.e("Scednnnnnnn",""+i);
        }
        /*
        ScheduleItem scheduleitem = new ScheduleItem(R.mipmap.ic_launcher,"阿西吧",mCalendarView.getSelectedCalendar().getMonth() + "月" + mCalendarView.getSelectedCalendar().getDay() + "日",R.drawable.enter);
        myScheduleItemList.add(scheduleitem);
        */

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.fab:
                Intent intent = new Intent(getContext(), ScheduleActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("user_id",user_id);
                bundle1.putString("date",date);
                intent.putExtras(bundle1);
                startActivityForResult(intent,scheduleCode);
                break;
            case R.id.tv_month_day:
                mCalendarView.showSelectLayout(mYear);
                mTextLunar.setVisibility(View.GONE);
                mTextYear.setVisibility(View.GONE);
                mTextMonthDay.setText(String.valueOf(mYear));
                break;
            case R.id.fl_current1:
                mCalendarView.scrollToCurrent();
                break;
        }
    }

    public void onDateFirstSelected(Calendar calendar){
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();
    }

    @Override
    public void onDateSelected(Calendar calendar) {
        onDateFirstSelected(calendar);
        //点击一个日期之后，输出这天的所有日程
        date = CalendarUtil.dateFormat(calendar.getYear(), calendar.getMonth()-1, calendar.getDay());
        Log.e("onDateSelected",date);
        myRecyclerView.removeAllViews();
        myScheduleRecyclerAdapter.notifyDataSetChanged();
        myScheduleItemList.clear();
        initData();
        //myScheduleRecyclerAdapter = new ScheduleRecyclerAdapter(myScheduleItemList);
        //myRecyclerView.setAdapter(myScheduleRecyclerAdapter);
    }

    private void setHeaderView(RecyclerView view){
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.recycle_header, view, false);
        myScheduleRecyclerAdapter.setHeaderView(header);
    }

    private void initHeaderView(View view) {
    }
    private void initHeaderData(){

    }

    private void setFooterView(RecyclerView view){
        View footer = LayoutInflater.from(getActivity()).inflate(R.layout.recycle_footer, view, false);
        myScheduleRecyclerAdapter.setFooterView(footer);
    }

    /**
     *
     * @param position  插入第几项 改项目插入首项
     * @param scheduleItem  存放list的对象
     */

    public void addItem(int position, ScheduleItem scheduleItem) {
        myScheduleItemList.add(position, scheduleItem);
        myScheduleRecyclerAdapter.notifyItemInserted(position);//通知演示插入动画
        myScheduleRecyclerAdapter.notifyItemRangeChanged(position, myScheduleRecyclerAdapter.getItemCount());//通知数据与界面重新绑定

    }



}
