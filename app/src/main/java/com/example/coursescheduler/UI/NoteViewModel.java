package com.example.coursescheduler.UI;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.coursescheduler.Database.ScheduleRepo;
import com.example.coursescheduler.Entity.Note;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private ScheduleRepo repository;
    private LiveData<List<Note>> allNotes;
    private LiveData<List<Note>> assignedNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new ScheduleRepo(application);
        allNotes = repository.getAllNotes();
    }
    public void insert(Note note) {repository.insertNote(note);}

    public void update(Note note) {repository.updateNote(note);}

    public void delete(Note note) {repository.deleteNote(note);}

    public LiveData<List<Note>> getAllNotes() {return allNotes;}

    public LiveData<List<Note>> getAssignedNotes(int courseID) {
        assignedNotes = repository.getAssignedNotes(courseID);
        return assignedNotes;
    }

}
