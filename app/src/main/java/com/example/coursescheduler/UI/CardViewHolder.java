package com.example.coursescheduler.UI;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursescheduler.R;

public class CardViewHolder extends RecyclerView.ViewHolder {
    TextView itemTitle;
    TextView startDate;
    TextView endDate;
    TextView courseID;
    TextView termID;
    public CardViewHolder(View v) {
        super(v);
        itemTitle = (TextView) v.findViewById(R.id.edit_text_courseTitle);
        startDate = (TextView) v.findViewById(R.id.edit_course_start);
        endDate = (TextView) v.findViewById(R.id.edit_course_end);
        courseID = (TextView) v.findViewById(R.id.text_view_courseID);
        termID = (TextView) v.findViewById(R.id.edit_termID);
    }
}
