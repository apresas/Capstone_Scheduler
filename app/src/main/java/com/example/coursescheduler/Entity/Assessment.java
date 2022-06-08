package com.example.coursescheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessments")
public class Assessment {
    @PrimaryKey(autoGenerate = false)
    private int assessmentID;

    private String assessmentTitle;
    private String assessmentType;
    private String startDate;
    private String endDate;
    private int courseID;

    // Constructor

    public Assessment(String assessmentTitle, String assessmentType, String startDate, String endDate, int courseID, int assessmentID) {
        this.assessmentTitle = assessmentTitle;
        this.assessmentType = assessmentType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseID = courseID;
        this.assessmentID = assessmentID;
    }

    // Getters

    public int getAssessmentID() {
        return assessmentID;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getCourseID() {
        return courseID;
    }

    // Setters

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}
