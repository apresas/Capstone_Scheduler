package com.example.coursescheduler.UI;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursescheduler.Entity.Course;
import com.example.coursescheduler.Entity.ScheduledCourse;
import com.example.coursescheduler.Entity.Term;
import com.example.coursescheduler.R;

import java.util.ArrayList;
import java.util.List;

public class ScheduledCourseAdapter extends RecyclerView.Adapter<ScheduledCourseAdapter.ScheduledCourseHolder> {
    public static final String EXTRA_COURSE_ID_DISPLAY =
            "com.example.coursescheduler.EXTRA_COURSE_ID_DISPLAY";
    public static final String EXTRA_COURSE_ID =
            "com.example.coursescheduler.EXTRA_ID";
    public static final String EXTRA_TERM_ID =
            "com.example.coursescheduler.EXTRA_TERM_ID";
    public static final String EXTRA_TITLE =
            "com.example.coursescheduler.EXTRA_TITLE";
    public static final String EXTRA_INSTRUCTOR =
            "com.example.coursescheduler.EXTRA_INSTRUCTOR";
    public static final String EXTRA_INSTRUCTOR_POS =
            "com.example.coursescheduler.EXTRA_INSTRUCTOR_POS";
    public static final String EXTRA_STATUS =
            "com.example.coursescheduler.EXTRA_STATUS";
    public static final String EXTRA_STATUS_POS =
            "com.example.coursescheduler.EXTRA_STATUS_POS";
    public static final String EXTRA_START =
            "com.example.coursescheduler.EXTRA_START";
    public static final String EXTRA_END =
            "com.example.coursescheduler.EXTRA_END";

    public List<ScheduledCourse> sc = new ArrayList();
    private List<Term> terms = new ArrayList();
    private OnItemClickListener listener;
    CourseViewModel courseViewModel;
    AddEditTermActivity addEditTermActivity;
    CourseDialog courseDialog;
    static String termID;
    static int courseTermID;
    private OnCourseListener onCourseListener;

    public ScheduledCourseAdapter(List<ScheduledCourse> sc, OnCourseListener onCourseListener){
        this.sc = sc;
        this.onCourseListener = onCourseListener;
    }


    @NonNull
    @Override
    public ScheduledCourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_item_add, parent, false);
        return new ScheduledCourseHolder(itemView, onCourseListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduledCourseHolder holder, int position) {
        ScheduledCourse currentCourse = sc.get(position);
        int ID = currentCourse.getCourseID();
        holder.courseIDTextView.setText(Integer.toString(ID));
        holder.textViewTitle.setText(currentCourse.getCourseTitle());
        holder.textViewStart.setText(currentCourse.getStartDate());
        holder.textViewEnd.setText(currentCourse.getEndDate());

    }

    @Override
    public int getItemCount() {
        return sc.size();
    }

    public void setScheduledCourse(List<ScheduledCourse> sc) {
        this.sc = sc;
        notifyDataSetChanged();
    }

    public ScheduledCourse getScheduledCourseAt(int position) {
        return sc.get(position);
    }

    class ScheduledCourseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView courseIDTextView;
        private TextView textViewTitle;
        private TextView textViewStart;
        private TextView textViewEnd;
        private TextView editTermID;
        private Button addBtn;
        OnCourseListener onCourseListener;

        public ScheduledCourseHolder(@NonNull View itemView, OnCourseListener onCourseListener) {
            super(itemView);
            courseIDTextView = itemView.findViewById(R.id.text_view_add_courseID);
            textViewTitle = itemView.findViewById(R.id.text_view_add_course_title);
            textViewStart = itemView.findViewById(R.id.edit_add_course_start);
            textViewEnd = itemView.findViewById(R.id.edit_add_course_end);
            editTermID = itemView.findViewById(R.id.edit_termID);
            addBtn = itemView.findViewById(R.id.add_course_btn);
            this.onCourseListener = onCourseListener;


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(sc.get(position));
                    }
                }
            });

            itemView.findViewById(R.id.add_course_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
//                    courseDialog.onCourseClick(position);
                    ScheduledCourse scheduledCourse = sc.get(position);
                    System.out.println("List size: " + sc.size());
                    String title = scheduledCourse.getCourseTitle();
                    String start = scheduledCourse.getStartDate();
                    String end = scheduledCourse.getEndDate();
                    int courseID = scheduledCourse.getCourseID();
                    int termCourseID = courseTermID;

                    Course c = new Course(title, start, end, termCourseID, courseID);
                    System.out.println("Title: " + c.getCourseTitle());
                    System.out.println("Start: " + c.getStartDate());
                    System.out.println("End: " + c.getEndDate());
                    System.out.println("Term: " + c.getTermID());
                    System.out.println("Course: " + c.getCourseID());
                    System.out.println("Position: " + position);



//                    onCourseListener.onCourseClick(position);

//                    String title = textViewTitle.getText().toString();
//                    String start = textViewStart.getText().toString();
//                    String end = textViewEnd.getText().toString();
//
//                    Course course = new Course(title, start, end, courseTermID);
//                    courseViewModel.insert(course);

//                    addEditTermActivity.saveScheduledCourse();
//                    saveCourse();
//                    String title = textViewTitle.getText().toString();
//                    String start = textViewStart.getText().toString();
//                    String end = textViewEnd.getText().toString();
//
//                    Course course = new Course(title, start, end, courseTermID);
//
//                    courseViewModel.insert(course);

                }



                // Save Course
//                private void saveCourse() {
//                    String title = textViewTitle.getText().toString();
//                    String start = textViewStart.getText().toString();
//                    String end = textViewEnd.getText().toString();
//                    String courseID = courseIDTextView.getText().toString();
////                    String termID = textViewTermID.getText().toString();
//
//
//
//                    Intent data = new Intent();
//                    data.putExtra(addEditTermActivity.EXTRA_TERM_ID, termID);
//                    data.putExtra(addEditTermActivity.EXTRA_COURSE_ID, courseID);
//                    data.putExtra(addEditTermActivity.EXTRA_TITLE, title);
//                    data.putExtra(addEditTermActivity.EXTRA_START, start);
//                    data.putExtra(addEditTermActivity.EXTRA_END, end);
//
//
////                    int id = addEditCourseActivity.getIntent().getIntExtra(AddEditCourseActivity.EXTRA_COURSE_ID, -1);
////                    System.out.println("Save Course ID: " + id);
////                    if (id != -1) {
////                        data.putExtra(AddEditCourseActivity.EXTRA_COURSE_ID, id);
////                        System.out.println("Save Course IF ID: " + id);
////                    }
////                    System.out.println("Save Course ELSE ID: " + id);
//
//                    addEditTermActivity.setResult(Activity.RESULT_OK, data);
//                    addEditTermActivity.finish();
////
//////                    courseID = id;
////                    System.out.println("courseID: " + courseID + "ID: " + id);
//
//
//                }
            });
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onCourseListener.onCourseClick(getAdapterPosition());
        }
    }
    public interface OnCourseListener {
        void onCourseClick(int position);
    }



    public interface OnItemClickListener {
        void onItemClick(ScheduledCourse sc);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
