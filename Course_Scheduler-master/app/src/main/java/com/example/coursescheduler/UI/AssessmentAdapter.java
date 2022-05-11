package com.example.coursescheduler.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursescheduler.Entity.Assessment;
import com.example.coursescheduler.Entity.Course;
import com.example.coursescheduler.R;

import java.util.ArrayList;
import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentHolder> {
    private List<Assessment> assessments = new ArrayList();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public AssessmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assessment_item, parent, false);
        return new AssessmentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentHolder holder, int position) {
        Assessment currentAssessment = assessments.get(position);
        int ID = currentAssessment.getAssessmentID();
        int courseID = currentAssessment.getCourseID();

        holder.assessmentIDTextView.setText(Integer.toString(ID));
        holder.textViewTitle.setText(currentAssessment.getAssessmentTitle());
        holder.textViewCourseID.setText(Integer.toString(courseID));
        holder.textViewType.setText(currentAssessment.getAssessmentType());
        holder.textViewStart.setText(currentAssessment.getStartDate());
        holder.textViewEnd.setText(currentAssessment.getEndDate());

    }

    @Override
    public int getItemCount() {
        return assessments.size();
    }

    public void setAssessments(List<Assessment> assessments) {
        this.assessments = assessments;
        notifyDataSetChanged();
    }

    public Assessment getAssessmentAt(int position) {
        return assessments.get(position);
    }

    class AssessmentHolder extends RecyclerView.ViewHolder {

        private TextView assessmentIDTextView;
        private TextView textViewTitle;
        private TextView textViewCourseID;
        private TextView textViewType;
        private TextView textViewStart;
        private TextView textViewEnd;

        public AssessmentHolder(@NonNull View itemView) {
            super(itemView);
            assessmentIDTextView = itemView.findViewById(R.id.text_view_assessmentID);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewCourseID = itemView.findViewById(R.id.edit_courseID);
            textViewType = itemView.findViewById(R.id.text_view_type);
            textViewStart = itemView.findViewById(R.id.text_view_assessment_start);
            textViewEnd = itemView.findViewById(R.id.text_view_assessment_end);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(assessments.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Assessment assessment);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
