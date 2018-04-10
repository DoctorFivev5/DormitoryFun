package com.example.doctorfive.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.entity.CourseItem;

import java.util.List;

/**
 * Created by DoctorFive on 2018/4/5.
 */

public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter.ViewHolder> {

    private List<CourseItem> courseList;


    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView start_end_time;
        TextView courseName;
        TextView courseRoom;

        public ViewHolder(View itemView) {
            super(itemView);
            start_end_time = itemView.findViewById(R.id.start_end_time);
            courseName = itemView.findViewById(R.id.course_name);
            courseRoom = itemView.findViewById(R.id.course_room);
        }
    }

    public CourseRecyclerAdapter(List<CourseItem> courseList) {
        this.courseList = courseList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_course_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CourseItem courseItem = courseList.get(position);
        if (holder instanceof ViewHolder){
            holder.start_end_time.setText(CourseTime(courseItem.getCourseTime()));
            holder.courseName.setText(courseItem.getCourseName());
            holder.courseRoom.setText(courseItem.getCourseRoom());
        }
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    private String CourseTime(int courseTime){
        switch (courseTime){
            case 1:
                return "08:00--09:30";

            case 2:
                return "09:40--10:20";

            case 3:
                return "10:30-11:10";

            case 4:
                return "11:20--12:00";

            case 5:
                return "14:00--15:30";

            case 6:
                return "15:40--17:10";

            case 7:
                return "19:00--20:30";

            default:
                return null;

        }
    }
}
