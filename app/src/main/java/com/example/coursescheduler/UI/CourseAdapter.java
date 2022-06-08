package com.example.coursescheduler.UI;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursescheduler.Entity.Course;
import com.example.coursescheduler.Entity.Term;
import com.example.coursescheduler.R;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {
    private List<Course> courses = new ArrayList();
    private List<Term> terms = new ArrayList();
    private OnItemClickListener listener;
    AddEditCourseActivity addEditCourseActivity;
    static int courseID;


    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_item, parent, false);
        return new CourseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {
        Course currentCourse = courses.get(position);
        int ID = currentCourse.getCourseID();
        int termID = currentCourse.getTermID();
        holder.courseIDTextView.setText(Integer.toString(ID));
        holder.textViewTitle.setText(currentCourse.getCourseTitle());
        holder.textViewTermID.setText(Integer.toString(termID));
//        holder.textViewStatus.setText(currentCourse.getStatus());
        holder.textViewStart.setText(currentCourse.getStartDate());
        holder.textViewEnd.setText(currentCourse.getEndDate());


    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public void setCourse(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

    public Course getCourseAt(int position) {
        return courses.get(position);
    }

    class CourseHolder extends RecyclerView.ViewHolder {

        private TextView courseIDTextView;
        private TextView textViewTitle;
        private TextView textViewTermID;
        private TextView textViewInstructor;
        private TextView textViewStatus;
        private TextView textViewStart;
        private TextView textViewEnd;
        private Button addBtn;

        public CourseHolder(@NonNull View itemView) {
            super(itemView);
            courseIDTextView = itemView.findViewById(R.id.text_view_courseID);
            textViewTitle = itemView.findViewById(R.id.text_view_course_title);
            textViewTermID = itemView.findViewById(R.id.edit_termID);
            textViewStart = itemView.findViewById(R.id.edit_course_start);
            textViewEnd = itemView.findViewById(R.id.edit_course_end);
            addBtn = itemView.findViewById(R.id.add_course_btn);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(courses.get(position));
                    }
                }
            });

        }
    }


    public interface OnItemClickListener {
        void onItemClick(Course course);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
