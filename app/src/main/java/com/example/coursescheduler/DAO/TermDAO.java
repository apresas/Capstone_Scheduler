package com.example.coursescheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.coursescheduler.Entity.Term;

import java.util.List;

@Dao
public interface TermDAO {

    @Insert
    void insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);

    @Query("DELETE FROM terms")
    void deleteAllTerms();

    @Query("SELECT * FROM terms ORDER BY termID DESC")
    LiveData<List<Term>> getAllTerms();

    @Query("SELECT * FROM terms ORDER BY termID DESC")
    List<Term> getAllAssignedTerms();

//    @Query("SELECT termID FROM terms WHERE termID ==")
//    void getTermID();
}
