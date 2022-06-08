package com.example.coursescheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Query;

import com.example.coursescheduler.DAO.CourseDAO;

@Entity(tableName = "courses")
public class Course {
    @PrimaryKey(autoGenerate = false)
    public int courseID;

    public String courseTitle;
    public String startDate;
    public String endDate;
    public int termID;

    // Constructor
    public Course(String courseTitle, String startDate, String endDate, int termID, int courseID) {
        this.courseTitle = courseTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.termID = termID;
        this.courseID = courseID;
    }

    // Getters
    public int getCourseID() {
        return courseID;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getTermID() {return termID;}


    // Setters

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }


    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int setTermID(int termID) {
        this.termID = termID;
        return termID;
    }
}
