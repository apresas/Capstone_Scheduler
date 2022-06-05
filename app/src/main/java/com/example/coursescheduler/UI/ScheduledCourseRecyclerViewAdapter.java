package com.example.coursescheduler.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursescheduler.Entity.Course;
import com.example.coursescheduler.Entity.ScheduledCourse;
import com.example.coursescheduler.R;

import java.util.ArrayList;
import java.util.List;

public class ScheduledCourseRecyclerViewAdapter extends RecyclerView.Adapter<ScheduledCourseRecyclerViewAdapter.scCourseViewHolder> {
    List<ScheduledCourse> scCourses = new ArrayList();
    List<Course> courses;

    public ScheduledCourseRecyclerViewAdapter(List<ScheduledCourse> data) {this.scCourses = data;}

    @NonNull
    @Override
    public scCourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_item_add, parent, false);
        scCourseViewHolder scViewHolder = new scCourseViewHolder(view);
        return new scCourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull scCourseViewHolder holder, int position) {
        Course course = courses.get(position);
        ScheduledCourse scheduledCourse = scCourses.get(position);
        holder.courseTitle.setText(scheduledCourse.getCourseTitle());
        holder.courseStart.setText(scheduledCourse.getStartDate());
        holder.courseEnd.setText(scheduledCourse.getEndDate());
        holder.courseID.setText(scheduledCourse.getCourseID());
        holder.scheduledCourse = scheduledCourse;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setScheduledCourse(List<ScheduledCourse> sc) {
        this.scCourses = sc;
        notifyDataSetChanged();
    }

    public ScheduledCourse getScheduledCourseAt(int position) {
        return scCourses.get(position);
    }

    public static class scCourseViewHolder extends RecyclerView.ViewHolder {
        TextView courseTitle;
        TextView courseTermID;
        TextView courseStart;
        TextView courseEnd;
        TextView courseID;
        Button addCourseButton;
        Course course;
        ScheduledCourse scheduledCourse;
        int position;
        View rootview;
        public scCourseViewHolder(@NonNull View itemView){
            super(itemView);
            rootview = itemView;
            courseTitle = itemView.findViewById(R.id.text_view_add_course_title);
            courseStart = itemView.findViewById(R.id.edit_add_course_start);
            courseEnd = itemView.findViewById(R.id.edit_add_course_end);
            courseTermID = itemView.findViewById(R.id.edit_add_termID);
            courseID = itemView.findViewById(R.id.text_view_add_courseID);
        }
    }

}
