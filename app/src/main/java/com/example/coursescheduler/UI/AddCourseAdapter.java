package com.example.coursescheduler.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursescheduler.Entity.Course;
import com.example.coursescheduler.Entity.Term;
import com.example.coursescheduler.R;

import java.util.ArrayList;
import java.util.List;

public class AddCourseAdapter extends RecyclerView.Adapter<AddCourseAdapter.AddCourseHolder> {
    private List<Course> courses = new ArrayList();
    private List<Term> terms = new ArrayList();
    private OnItemClickListener listener;
    AddEditCourseActivity addEditCourseActivity;


    @NonNull
    @Override
    public AddCourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_item_add, parent, false);
        return new AddCourseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AddCourseHolder holder, int position) {
        Course currentCourse = courses.get(position);
        int ID = currentCourse.getCourseID();
        int termID = currentCourse.getTermID();
        holder.courseIDTextView.setText(Integer.toString(ID));
        holder.textViewTitle.setText(currentCourse.getCourseTitle());
        holder.textViewTermID.setText(Integer.toString(termID));
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

    class AddCourseHolder extends RecyclerView.ViewHolder {

        private TextView courseIDTextView;
        private TextView textViewTitle;
        private TextView textViewTermID;
        private TextView textViewInstructor;
        private TextView textViewStatus;
        private TextView textViewStart;
        private TextView textViewEnd;

        public AddCourseHolder(@NonNull View itemView) {
            super(itemView);
            courseIDTextView = itemView.findViewById(R.id.text_view_courseID);
            textViewTitle = itemView.findViewById(R.id.text_view_course_title);
            textViewTermID = itemView.findViewById(R.id.edit_termID);
            textViewInstructor = itemView.findViewById(R.id.instuctorName);
//            textViewStatus = itemView.findViewById(R.id.edit_status);
            textViewStart = itemView.findViewById(R.id.edit_course_start);
            textViewEnd = itemView.findViewById(R.id.edit_course_end);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(courses.get(position));
                    }
                }
            });

            itemView.findViewById(R.id.add_course_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Test");

                }

                // Save Course
                private void saveCourse() {
                    String title = textViewTitle.getText().toString();
                    String start = textViewStart.getText().toString();
                    String end = textViewEnd.getText().toString();
                    String termID = textViewTermID.getText().toString();
                    String courseID = courseIDTextView.getText().toString();


                    Intent data = new Intent();
                    data.putExtra(AddEditCourseActivity.EXTRA_TERM_ID, termID);
                    data.putExtra(AddEditCourseActivity.EXTRA_TITLE, title);
                    data.putExtra(AddEditCourseActivity.EXTRA_START, start);
                    data.putExtra(AddEditCourseActivity.EXTRA_END, end);

                    int id = addEditCourseActivity.getIntent().getIntExtra(AddEditCourseActivity.EXTRA_COURSE_ID, -1);
                    System.out.println("Save Course ID: " + id);
                    if (id != -1) {
                        data.putExtra(AddEditCourseActivity.EXTRA_COURSE_ID, id);
                        System.out.println("Save Course IF ID: " + id);
                    }
                    System.out.println("Save Course ELSE ID: " + id);

                    addEditCourseActivity.setResult(Activity.RESULT_OK, data);
                    addEditCourseActivity.finish();

//                    courseID = id;
                    System.out.println("courseID: " + courseID + "ID: " + id);


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
