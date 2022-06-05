package com.example.coursescheduler.UI;

import android.content.Context;
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

    public List<ScheduledCourse> sc = new ArrayList();
    private List<Term> terms = new ArrayList();
    private OnItemClickListener listener;
    static int courseTermID;
    private OnFragmentInteractionListener iListener;



    public ScheduledCourseAdapter(List<ScheduledCourse> sc, OnFragmentInteractionListener fragmentInteractionListener){
        this.sc = sc;
        this.iListener = fragmentInteractionListener;
//        this.onCourseListener = onCourseListener;
    }

//    public ScheduledCourseAdapter(Context context, List<ScheduledCourse> sc){
//        this.sc = sc;
//        this.context = context;
//    }


    @NonNull
    @Override
    public ScheduledCourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_item_add, parent, false);
        return new ScheduledCourseHolder(itemView, iListener);
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
        View rootview;
        OnFragmentInteractionListener iListener;
//        OnCourseListener onCourseListener;

        public ScheduledCourseHolder(@NonNull View itemView, OnFragmentInteractionListener iListener) {
            super(itemView);
            this.iListener = iListener;
            courseIDTextView = itemView.findViewById(R.id.text_view_add_courseID);
            textViewTitle = itemView.findViewById(R.id.text_view_add_course_title);
            textViewStart = itemView.findViewById(R.id.edit_add_course_start);
            textViewEnd = itemView.findViewById(R.id.edit_add_course_end);
            editTermID = itemView.findViewById(R.id.edit_termID);
            addBtn = itemView.findViewById(R.id.add_course_btn);
            rootview = itemView;


            Intent intent = new Intent(rootview.getContext(), AddEditTermActivity.class);
            rootview.getContext().startActivity(intent);
//            this.onCourseListener = onCourseListener;


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
//                    System.out.println("Text Click at Position: " + position);
                    ScheduledCourse scheduledCourse = sc.get(position);
                    String title = scheduledCourse.getCourseTitle();
                    String start = scheduledCourse.getStartDate();
                    String end = scheduledCourse.getEndDate();
                    int courseID = scheduledCourse.getCourseID();
                    int termCourseID = courseTermID;
                    Course c = new Course(title, start, end, termCourseID, courseID);
//                    Intent i = new Intent(rootview.getContext(), AddEditTermActivity.class);
//                    i.putExtra(AddEditTermActivity.EXTRA_TITLE, title);
//                    i.putExtra(AddEditTermActivity.EXTRA_START, start);
//                    i.putExtra(AddEditTermActivity.EXTRA_END, end);
//                    i.putExtra(AddEditTermActivity.EXTRA_TERM_ID, courseTermID);
//                    i.putExtra(AddEditTermActivity.EXTRA_COURSE_ID, courseID);
//                    rootview.getContext().startActivity(i);

//                    iListener.onFragmentInteraction(title, start, end, courseID, courseTermID);
//                    iListener.goToCourseDetails(c);
                    System.out.println("DONE");

                }
            });

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            System.out.println("VIEW CLICK TEST");
//            scListener.onFragmentInteraction();
//            onCourseListener.onCourseClick(getAdapterPosition());
        }
    }


    public interface OnItemClickListener {
        void onItemClick(ScheduledCourse sc);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

//    public void sendCourse(ScheduledCourse sc) {
//        Intent intent = new Intent();
//        intent.putExtra(AddEditTermActivity.SELECTED_COURSE, sc);
//        startActivity(intent);
//
//    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String title, String start, String end, int courseID, int termID);
//        void goToCourseDetails(Course course);
    }

}
