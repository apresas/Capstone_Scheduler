package com.example.coursescheduler.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursescheduler.Entity.Course;
import com.example.coursescheduler.Entity.Instructor;
import com.example.coursescheduler.Entity.Note;
import com.example.coursescheduler.R;

import java.util.ArrayList;
import java.util.List;

public class InstructorAdapter extends RecyclerView.Adapter<InstructorAdapter.InstructorHolder> {
    private List<Course> courses = new ArrayList();
    private List<Instructor> instructors = new ArrayList();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public InstructorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.instructor_item, parent, false);
        return new InstructorHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructorHolder holder, int position) {
        Instructor currentInstructor = instructors.get(position);
        int ID = currentInstructor.getInstructorID();
        int instructorCourseID = currentInstructor.getCourseID();

        holder.instructorName.setText(currentInstructor.getInstructorName());
        holder.email.setText(currentInstructor.getEmail());
        holder.phone.setText(currentInstructor.getPhoneNumber());
        holder.instructorID.setText(Integer.toString(ID));


    }

    @Override
    public int getItemCount() {
        return instructors.size();
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
        notifyDataSetChanged();
    }

    public Instructor getInstructorAt(int position) {
        return instructors.get(position);
    }

    class InstructorHolder extends RecyclerView.ViewHolder {
        private TextView instructorName;
        private TextView email;
        private TextView phone;
        private TextView instructorID;


        public InstructorHolder(@NonNull View itemView) {
            super(itemView);
            instructorName = itemView.findViewById(R.id.instuctorName);
            email = itemView.findViewById(R.id.emailField);
            phone = itemView.findViewById(R.id.phoneField);
            instructorID = itemView.findViewById(R.id.instructorID);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(instructors.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Instructor instructor);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
