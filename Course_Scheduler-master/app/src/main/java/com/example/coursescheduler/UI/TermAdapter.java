package com.example.coursescheduler.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursescheduler.Entity.Term;
import com.example.coursescheduler.R;

import java.util.ArrayList;
import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermHolder> {
    private List<Term> terms = new ArrayList();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public TermHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.term_item, parent, false);
        return new TermHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermHolder holder, int position) {
        Term currentTerm = terms.get(position);
        int ID = currentTerm.getTermID();
        holder.termIDTextView.setText(Integer.toString(ID));
        holder.textViewTitle.setText(currentTerm.getTermTitle());
        holder.textViewStart.setText(currentTerm.getStartDate());
        holder.textViewEnd.setText(currentTerm.getEndDate());

    }

    @Override
    public int getItemCount() {
        return terms.size();
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
        notifyDataSetChanged();
    }

    public Term getTermAt(int position) {
        return terms.get(position);
    }

    class TermHolder extends RecyclerView.ViewHolder {

        private TextView termIDTextView;
        private TextView textViewTitle;
        private TextView textViewStart;
        private TextView textViewEnd;

        public TermHolder(@NonNull View itemView) {
            super(itemView);
            termIDTextView = itemView.findViewById(R.id.text_view_termID);
            textViewTitle = itemView.findViewById(R.id.text_view_term_title);
            textViewStart = itemView.findViewById(R.id.edit_start);
            textViewEnd = itemView.findViewById(R.id.edit_end);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(terms.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Term term);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
