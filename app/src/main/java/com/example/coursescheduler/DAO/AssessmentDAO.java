package com.example.coursescheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.coursescheduler.Entity.Assessment;
import com.example.coursescheduler.Entity.Course;
import com.example.coursescheduler.Entity.Term;

import java.util.List;

@Dao
public interface AssessmentDAO {

    @Insert
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("DELETE FROM assessments")
    void deleteAllAssessments();

    @Query("SELECT * FROM assessments ORDER BY assessmentID DESC")
    LiveData<List<Assessment>> getAllAssessments();

    @Query("SELECT * FROM ASSESSMENTS WHERE courseID = :courseID ORDER BY assessmentID ASC")
    LiveData<List<Assessment>> getAssignedAssessments(int courseID);

}
