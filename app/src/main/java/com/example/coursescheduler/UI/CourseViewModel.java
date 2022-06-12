package com.example.coursescheduler.UI;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Query;

import com.example.coursescheduler.Database.ScheduleRepo;
import com.example.coursescheduler.Entity.Course;
import com.example.coursescheduler.Entity.Term;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class CourseViewModel extends AndroidViewModel {

    private ScheduleRepo repository;
    private LiveData<List<Course>> allCourses;
    private LiveData<List<Course>> assignedCourses;
    private List<Course> assignedTermID;
    private List<Course> assignedTermIDList;

    public CourseViewModel(@NonNull Application application) {
        super(application);
        repository = new ScheduleRepo(application);
        allCourses = repository.getAllCourses();
    }

    public void insert(Course course) {
        repository.insertCourse(course);
    }

    public void update(Course course) {
        repository.updateCourse(course);
    }

    public void delete(Course course) {
        repository.deleteCourse(course);
    }

    public void deleteAllCourses() {
        repository.deleteAllCourses();
    }

    public LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }

    public LiveData<List<Course>> getAssignedCourses(int termID) {
        assignedCourses = repository.getAssignedCourses(termID);
        return assignedCourses;
    }

    public LiveData<List<Course>> searchCourse(String title) {
        assignedCourses = repository.getSearchCourses(title);
        return assignedCourses;
    }

    public List<Course> getAssignedTermID(int termID) {
        assignedTermID = repository.getAssignedTermID(termID);
        return assignedTermID;
    }


}