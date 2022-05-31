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
import com.example.coursescheduler.Entity.Term;
import com.example.coursescheduler.R;

import java.util.ArrayList;
import java.util.List;

public class ScheduledCourseAdapter extends RecyclerView.Adapter<ScheduledCourseAdapter.ScheduledCourseHolder> {
    private List<ScheduledCourse> sc = new ArrayList();
    private List<Term> terms = new ArrayList();
    private OnItemClickListener listener;
    AddEditCourseActivity addEditCourseActivity;
    static int courseID;


    @NonNull
    @Override
    public ScheduledCourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_item_add, parent, false);
        return new ScheduledCourseHolder(itemView);
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

    class ScheduledCourseHolder extends RecyclerView.ViewHolder {

        private TextView courseIDTextView;
        private TextView textViewTitle;
        private TextView textViewStart;
        private TextView textViewEnd;
        private Button addBtn;

        public ScheduledCourseHolder(@NonNull View itemView) {
            super(itemView);
            courseIDTextView = itemView.findViewById(R.id.text_view_courseID);
            textViewTitle = itemView.findViewById(R.id.text_view_course_title);
            textViewStart = itemView.findViewById(R.id.edit_course_start);
            textViewEnd = itemView.findViewById(R.id.edit_course_end);
            addBtn = itemView.findViewById(R.id.add_course_btn);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(sc.get(position));
                    }
                }
            });

        }
    }


    public interface OnItemClickListener {
        void onItemClick(ScheduledCourse sc);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
