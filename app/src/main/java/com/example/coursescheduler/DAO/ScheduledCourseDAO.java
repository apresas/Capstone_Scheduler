package com.example.coursescheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.coursescheduler.Entity.Course;
import com.example.coursescheduler.Entity.ScheduledCourse;

import java.util.List;

@Dao
public interface ScheduledCourseDAO {

    @Insert
    void insert(ScheduledCourse sc);

    @Update
    void update(ScheduledCourse sc);

    @Delete
    void delete(ScheduledCourse sc);

    @Query("DELETE FROM all_courses")
    void deleteAllCourses();

    @Query("SELECT * FROM all_courses ORDER BY courseID DESC")
    LiveData<List<ScheduledCourse>> getAllScheduledCourses();

//    @Query("SELECT * FROM courses ORDER BY courseID DESC")
//    List<Course> getAllAssignedCourses();
//
//    @Query("SELECT* FROM COURSES WHERE termID = :termID ORDER BY courseID ASC")
//    LiveData<List<Course>> getAssignedCourses(int termID);
//
//    @Query("SELECT * FROM COURSES WHERE termID = :termID ORDER BY termID DESC")
//    List<Course> getAssignedTermID(int termID);


}
