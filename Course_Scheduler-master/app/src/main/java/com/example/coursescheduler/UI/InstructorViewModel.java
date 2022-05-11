package com.example.coursescheduler.UI;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.coursescheduler.Database.ScheduleRepo;
import com.example.coursescheduler.Entity.Instructor;

import java.util.List;

public class InstructorViewModel extends AndroidViewModel {
    private ScheduleRepo repository;
    private LiveData<List<Instructor>> allInstructors;
    private LiveData<List<Instructor>> assignedInstructors;

    public InstructorViewModel(@NonNull Application application) {
        super(application);
        repository = new ScheduleRepo(application);
        allInstructors = repository.getAllInstructors();
    }
    public void insert(Instructor instructor) {repository.insertInstructor(instructor);}

    public void update(Instructor instructor) {repository.updateInstructor(instructor);}

    public void delete(Instructor instructor) {repository.deleteInstructor(instructor);}

    public LiveData<List<Instructor>> getAssignedInstructors(int courseID) {
        assignedInstructors = repository.getAssignedInstructors(courseID);
        return assignedInstructors;
    }
}
