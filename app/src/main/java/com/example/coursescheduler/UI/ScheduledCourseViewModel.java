package com.example.coursescheduler.UI;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.coursescheduler.Database.ScheduleRepo;
import com.example.coursescheduler.Entity.Course;
import com.example.coursescheduler.Entity.ScheduledCourse;

import java.util.List;

public class ScheduledCourseViewModel extends AndroidViewModel {

    private ScheduleRepo repository;
    private LiveData<List<ScheduledCourse>> allCourses;
    private LiveData<List<ScheduledCourse>> assignedCourses;
    private List<Course> assignedTermID;
    private List<Course> assignedTermIDList;

    public ScheduledCourseViewModel(@NonNull Application application) {
        super(application);
        repository = new ScheduleRepo(application);
        allCourses = repository.getAllSchduledCourses();
    }

    public void insert(ScheduledCourse sc) {
        repository.insertScheduledCourse(sc);
    }

    public void update(ScheduledCourse sc) {
        repository.updateScheduledCourse(sc);
    }

//    public void delete(ScheduledCourse sc) {
//        repository.deleteScheduleCourse(sc);
//    }
//
//    public void deleteAllCourses() {
//        repository.deleteAllCourses();
//    }

    public LiveData<List<ScheduledCourse>> getAllScheduledCourses() {
        return allCourses;
    }

//    public LiveData<List<Course>> getAssignedCourses(int termID) {
//        assignedCourses = repository.getAssignedCourses(termID);
//        return assignedCourses;
//    }
//
//    public List<Course> getAssignedTermID(int termID) {
//        assignedTermID = repository.getAssignedTermID(termID);
//        return assignedTermID;
//    }


}