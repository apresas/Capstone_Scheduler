package com.example.coursescheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.coursescheduler.Entity.Note;

import java.util.List;
@Dao
public interface NoteDAO {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM notes ORDER BY noteID DESC")
    LiveData<List<Note>> getAllNotes();

    @Query("SELECT * FROM NOTES WHERE COURSEID = :courseID ORDER BY noteID ASC")
    LiveData<List<Note>> getAssignedNotes(int courseID);
}
