package com.example.coursescheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.coursescheduler.Entity.Instructor;

import java.util.List;

@Dao
public interface InstructorDAO {

    @Insert
    void insert(Instructor instructor);

    @Update
    void update(Instructor instructor);

    @Delete
    void delete(Instructor instructor);

    @Query("SELECT * FROM INSTRUCTORS ORDER BY instructorName DESC")
    LiveData<List<Instructor>> getAllInstructors();

    @Query("SELECT * FROM INSTRUCTORS WHERE courseID = :courseID ORDER BY instructorName DESC")
    LiveData<List<Instructor>> getAssignedInstructors(int courseID);
}
