package com.example.coursescheduler.UI;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.coursescheduler.Database.ScheduleRepo;
import com.example.coursescheduler.Entity.Course;
import com.example.coursescheduler.Entity.Term;

import java.util.List;

public class TermViewModel extends AndroidViewModel {

    private ScheduleRepo repository;
    private LiveData<List<Term>> allTerms;
    private LiveData<List<Course>> assignedCourses;
//    private List<Course> allAssignedCourses;

    public TermViewModel(@NonNull Application application) {
        super(application);
        repository = new ScheduleRepo(application);
        allTerms = repository.getAllTerms();
//        allAssignedCourses = repository.getAllAssignedCourses();
    }

    public void insert(Term term) {
        repository.insertTerm(term);
    }

    public void update(Term term) {
        repository.updateTerm(term);
    }

    public void delete(Term term) {
        repository.deleteTerm(term);
    }

    public void deleteAllTerms() {
        repository.deleteAllTerms();
    }

    public LiveData<List<Term>> getAllTerms() {
        return allTerms;
    }

//    public List<Course> allAssignedCourses() {
//        return allAssignedCourses;
//    }

    public LiveData<List<Course>> getAssignedCourses(int termID) {
        assignedCourses = repository.getAssignedCourses(termID);
        return assignedCourses;
    }
}