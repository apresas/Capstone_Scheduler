package com.example.coursescheduler.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "all_courses")
public class ScheduledCourse extends Course{
    @PrimaryKey(autoGenerate = false)
    public int courseID;
    public String courseTitle;
    public String startDate;
    public String endDate;

    // Constructor
    public ScheduledCourse(String courseTitle, String startDate, String endDate, int termID, int courseID) {
        super(courseTitle,startDate,endDate,termID, courseID);
        this.courseTitle = courseTitle;
        this.startDate = startDate;
        this.endDate = endDate;
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


}
