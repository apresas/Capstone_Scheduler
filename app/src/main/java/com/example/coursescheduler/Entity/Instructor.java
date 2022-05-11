package com.example.coursescheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "instructors")
public class Instructor {
    @PrimaryKey(autoGenerate = true)
    private int instructorID;

    private String instructorName;
    private String email;
    private String phoneNumber;
    private int courseID;

    // Constructor
    public Instructor(String instructorName, String email, String phoneNumber, int courseID) {
        this.instructorName = instructorName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.courseID = courseID;
    }

    // Getters

    public int getInstructorID() {
        return instructorID;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getCourseID() {
        return courseID;
    }


    // Setters

    public void setInstructorID(int instructorID) {
        this.instructorID = instructorID;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}
