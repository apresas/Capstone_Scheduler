package com.example.coursescheduler.DAO;

import androidx.core.app.ComponentActivity;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.coursescheduler.Entity.Course;
import com.example.coursescheduler.Entity.Term;
import com.example.coursescheduler.UI.AddEditTermActivity;

import java.util.List;

@Dao
public interface CourseDAO {

    @Insert
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("DELETE FROM courses")
    void deleteAllCourses();

    @Query("SELECT * FROM courses ORDER BY courseID DESC")
    LiveData<List<Course>> getAllCourses();

    @Query("SELECT * FROM courses ORDER BY courseID DESC")
    List<Course> getAllAssignedCourses();

    @Query("SELECT* FROM COURSES WHERE termID = :termID ORDER BY courseID ASC")
    LiveData<List<Course>> getAssignedCourses(int termID);

    @Query("SELECT * FROM COURSES WHERE termID = :termID ORDER BY termID DESC")
    List<Course> getAssignedTermID(int termID);


}
