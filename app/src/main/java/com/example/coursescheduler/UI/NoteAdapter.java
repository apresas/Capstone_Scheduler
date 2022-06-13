//package com.example.coursescheduler.UI;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.coursescheduler.Entity.Course;
//import com.example.coursescheduler.Entity.Note;
//import com.example.coursescheduler.Entity.Term;
//import com.example.coursescheduler.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
//    private List<Course> courses = new ArrayList();
//    private List<Note> notes = new ArrayList();
//    private OnItemClickListener listener;
//
//    @NonNull
//    @Override
//    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.note_item, parent, false);
//        return new NoteHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
//        Note currentNote = notes.get(position);
//        int ID = currentNote.getNoteID();
//        int noteCourseID = currentNote.getCourseID();
//
//        holder.noteTitle.setText(currentNote.getNoteTitle());
//        holder.noteBody.setText(currentNote.getNoteBody());
//        holder.noteCourseID.setText(Integer.toString(noteCourseID));
//        holder.noteID.setText(Integer.toString(ID));
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return notes.size();
//    }
//
//    public void setNotes(List<Note> notes) {
//        this.notes = notes;
//        notifyDataSetChanged();
//    }
//
//    public Note getNoteAt(int position) {
//        return notes.get(position);
//    }
//
//    class NoteHolder extends RecyclerView.ViewHolder {
//        private TextView noteTitle;
//        private TextView noteID;
//        private TextView noteCourseID;
//        private TextView noteBody;
//
//
//        public NoteHolder(@NonNull View itemView) {
//            super(itemView);
//            noteTitle = itemView.findViewById(R.id.text_view_note_title);
//            noteID = itemView.findViewById(R.id.text_view_noteID);
//            noteCourseID = itemView.findViewById(R.id.edit_noteID);
//            noteBody = itemView.findViewById(R.id.edit_note_body);
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = getAdapterPosition();
//                    if (listener != null && position != RecyclerView.NO_POSITION) {
//                        listener.onItemClick(notes.get(position));
//                    }
//                }
//            });
//        }
//    }
//
//    public interface OnItemClickListener {
//        void onItemClick(Note note);
//    }
//
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.listener = listener;
//    }
//
//}
