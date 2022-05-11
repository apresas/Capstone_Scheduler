package com.example.coursescheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int noteID;

    private String noteTitle;
    private String noteBody;
    private int courseID;

    // Constructor

    public Note(String noteTitle, String noteBody, int courseID) {
        this.noteTitle = noteTitle;
        this.noteBody = noteBody;
        this.courseID = courseID;
    }

    // Getters

    public int getNoteID() {
        return noteID;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteBody() {
        return noteBody;
    }

    public int getCourseID() {
        return courseID;
    }

    // Setters

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public void setNoteBody(String noteBody) {
        this.noteBody = noteBody;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}
