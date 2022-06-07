package com.example.coursescheduler.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "all_courses")
public class ScheduledCourse implements Parcelable {
    @PrimaryKey(autoGenerate = false)
    private int courseID;
    private String courseTitle;
    private String startDate;
    private String endDate;

    // Constructor
    public ScheduledCourse(String courseTitle, String startDate, String endDate, int courseID) {
        this.courseTitle = courseTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseID = courseID;
    }

    protected ScheduledCourse(Parcel in) {
        courseID = in.readInt();
        courseTitle = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        courseID = in.readInt();
    }

    public static final Creator<ScheduledCourse> CREATOR = new Creator<ScheduledCourse>() {
        @Override
        public ScheduledCourse createFromParcel(Parcel in) {
            return new ScheduledCourse(in);
        }

        @Override
        public ScheduledCourse[] newArray(int size) {
            return new ScheduledCourse[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(courseID);
        parcel.writeString(courseTitle);
        parcel.writeString(startDate);
        parcel.writeString(endDate);
    }
}
