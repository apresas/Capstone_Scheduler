package com.example.coursescheduler.UI;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.coursescheduler.Database.ScheduleRepo;
import com.example.coursescheduler.Entity.Assessment;
import com.example.coursescheduler.Entity.Course;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {

    private ScheduleRepo repository;
    private LiveData<List<Assessment>> allAssessments;
    private LiveData<List<Assessment>> assignedAssessments;

    public AssessmentViewModel(@NonNull Application application) {
        super(application);
        repository = new ScheduleRepo(application);
        allAssessments = repository.getAllAssessments();
    }

    public void insert(Assessment assessment) {
        repository.insertAssessment(assessment);
    }

    public void update(Assessment assessment) {
        repository.updateAssessment(assessment);
    }

    public void delete(Assessment assessment) {
        repository.deleteAssessment(assessment);
    }

    public void deleteAllAssessments() {
        repository.deleteAllAssessments();
    }

    public LiveData<List<Assessment>> getAllAssessments() {
        return allAssessments;
    }

    public LiveData<List<Assessment>> getAllAssignedAssessments(int courseID) {
        assignedAssessments = repository.getAssignedAssessments(courseID);
        return assignedAssessments;
    }
}